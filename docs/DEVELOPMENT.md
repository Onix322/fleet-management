# Development Guide

> **In short:** How to build, run, test, and extend the project locally.
>
> **Read this if:** you are setting up a workstation, working on the framework, or implementing an application feature.
>
> **Prerequisites:** JDK 21, Maven 3.9+, and a running PostgreSQL instance.
>
> **Related:** [README](../README.md) · [Framework](FRAMEWORK.md) · [Application](APPLICATION.md) · [Configuration](CONFIGURATION.md) · [API](API.md)

This guide covers the local build, run workflow, project conventions, and the split between framework and application work.

---

## 1. Prerequisites

| Tool | Requirement |
| --- | --- |
| JDK | 21 |
| Maven | 3.9 or newer |
| PostgreSQL | A running instance reachable from the configured JDBC URL |
| IntelliJ IDEA | Optional; the repository contains IDEA project metadata |

Use a JDK 21 toolchain. Lombok 1.18.36 is pinned in the parent POM and does not support newer JDK internals, so building with JDK 25 or 27 can fail with a `com.sun.tools.javac.code.TypeTag :: UNKNOWN` compiler error even though the source release is 21.

Lombok is used throughout the codebase. When working in an IDE, install the Lombok plugin and enable annotation processing.

---

## 2. Build

Run from the repository root:

```bash
mvn -o compile
```

Useful variants:

| Command | Effect |
| --- | --- |
| `mvn -o compile` | Compile all modules |
| `mvn -o test` | Compile and run all tests |
| `mvn -o package` | Compile, test and package all modules |
| `mvn -o install` | Install all module artifacts into the local Maven repository |
| `mvn -o -pl app -am package` | Build the `app` module and its dependencies |

Each module produces a library JAR in its `target/` directory. The application entry point is `com.onix322.launcher.LauncherModule` in the `launcher` module.

---

## 3. Database setup

The default configuration is defined in `config/src/main/resources/application.properties`. Either make those values valid in your local PostgreSQL instance, or change the properties.

Example with a dedicated role and database:

```sql
CREATE ROLE fleet WITH LOGIN PASSWORD 'fleet';
CREATE DATABASE fleet OWNER fleet;
```

```properties
database.url=jdbc:postgresql://localhost:5432/fleet
database.user=fleet
database.password=fleet
```

Hibernate manages the schema according to `hibernate.hbm2ddl.auto`, currently `create-drop`. On startup, Hibernate drops and recreates the mapped entity tables, so existing data is lost. Use another strategy when data must survive restarts.

PostgreSQL and Hibernate are the default `Database` and `ORM` implementations. They can be replaced through their contracts; the application code depends only on `Database` and `ORM`.

Full reference: [CONFIGURATION.md](CONFIGURATION.md).

---

## 4. Run

### 4.1 IntelliJ IDEA

1. Open the repository root as a Maven project.
2. Set the project SDK to JDK 21.
3. Enable annotation processing for Lombok.
4. Run `com.onix322.launcher.LauncherModule` (`launcher/src/main/java/com/onix322/launcher/LauncherModule.java`).

### 4.2 Maven

Install the modules first so the `launcher` module can resolve its reactor siblings:

```bash
mvn -o -DskipTests install
mvn -pl launcher exec:java -Dexec.mainClass=com.onix322.launcher.LauncherModule
```

The second command uses the Maven Exec plugin, resolved from the configured Maven repositories.

### 4.3 Startup behavior

`LauncherModule.main` calls:

```java
Server.launch(new LauncherModule());
```

`LauncherModule` installs `ServerModule`, `ConfigModule`, `PersistenceModule` and `AppModule`. Startup performs the following work before accepting traffic:

1. loads `application.properties`;
2. creates the PostgreSQL data source;
3. builds the Hibernate `SessionFactory` and registers the entities from the Guice multibinder;
4. creates the Tomcat server;
5. configures the Jersey servlet and registers the filters from the `Set<Filter>` multibinder;
6. starts the connector and blocks on `tomcat.getServer().await()`.

`PersistenceModule` installs `DatabaseModule` and `FilterModule`. `FilterModule` contributes `OpenSessionInViewFilter`, which keeps one Hibernate session open for the whole request.

If the database is unreachable or the configuration is invalid, startup fails while the injector creates the eager singletons. See [FRAMEWORK.md](FRAMEWORK.md) for the full sequence.

---

## 5. Verify the running application

```bash
curl -i http://localhost:8080/
```

A missing root resource produces a `404`; the important check is that Tomcat answers. See [API.md](API.md) for HTTP conventions and endpoint testing examples. The port is controlled by `server.port`.

---

## 6. IDE setup

The repository includes `.idea/` metadata:

- Maven project rooted at `pom.xml`;
- project SDK configured as Temurin 21;
- Lombok annotation processing configured for all modules.

Recommended IDE settings:

| Setting | Value |
| --- | --- |
| Project SDK | JDK 21 |
| Maven home | Your local Maven 3.9+ installation |
| Annotation processing | Enabled |
| Lombok plugin | Installed |
| Encoding | UTF-8 |

The parent POM sets `project.build.sourceEncoding` to `UTF-8`.

---

## 7. Framework vs application

The project separates stable infrastructure from evolving business code:

| Layer | Modules | Documentation |
| --- | --- | --- |
| Framework | `config`, `persistence`, `server`, `launcher` | [FRAMEWORK.md](FRAMEWORK.md) |
| Support | `initializer` | [FRAMEWORK.md](FRAMEWORK.md) |
| Application | `app` | [APPLICATION.md](APPLICATION.md) |

When a change affects infrastructure, update the framework docs. When a change affects features, endpoints, or entities, update the application docs.

---

## 8. Conventions

### 8.1 Module per concern

- `config` — configuration only;
- `persistence` — persistence, transactions, and the request-scoped session filter;
- `server` — HTTP container and Guice/HK2 bridges;
- `launcher` — composition root and entry point;
- `initializer` — generic entity/repository registration support;
- `app` — business features.

Business code does not belong in the framework or support modules.

### 8.2 Interface and implementation

Framework contracts are interfaces with separate implementations:

```text
Configuration  → ConfigurationImpl
Database       → PostgresSQLImpl
ORM            → HibernateORMImpl
Server         → TomcatServer
```

Guice modules bind the interface to the implementation.

The contracts are replaceable. To swap Tomcat, implement `Server` and change the binding in `ServerModule`; to swap PostgreSQL or Hibernate, implement `Database` or `ORM` and change the binding in `PostgresSQLModule` or `HibernateModule`. Feature code and the startup path remain tied to the interfaces.

### 8.3 Constructor injection

Services use constructor injection, frequently through Lombok:

```java
@Singleton
@AllArgsConstructor(onConstructor_ = @Inject)
public class SomeService {
    private final Configuration configuration;
}
```

Dependency fields are `final` where the class uses a generated constructor.

Use the Jakarta annotations everywhere:

```java
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
```

| Annotation | Use it for |
| --- | --- |
| `jakarta.inject.Inject` | constructors and the rare injected field |
| `jakarta.inject.Singleton` | classes that must have one instance |
| `jakarta.inject.Named` | named property bindings, for example `@Named("server.port")` |
| `jakarta.inject.Qualifier` | custom qualifiers |

Guice 7 recognizes all of them, and Jersey/HK2 recognizes `Inject`, `Named`, and `Qualifier`. This matters because resources are created by Jersey while services and repositories are created by Guice. Do not bind JAX-RS resources in Guice modules; Jersey discovers them by package scan.

Keep `com.google.inject` imports only for injector configuration: `AbstractModule`, `bind(...)`, `@Provides`, `Multibinder`, `TypeLiteral`, `Types`, `Provider`, `Injector`, `Names.bindProperties`.

`BaseRepository` and `@HK2Inject` fields are the exceptions that use field injection.

### 8.4 Feature modules

Each application feature has its own Guice module under `app/src/main/java/com/onix322/app/<feature>/guice/`.

The module is responsible for:

- registering JPA entities in the `Set<Class<?>>` multibinder;
- registering generic repository bindings through `InitializerModule` or `TypeFactory`;
- binding feature-specific services or implementations when just-in-time binding is not enough.

Concrete services and repositories with `@Singleton` and an injectable constructor do not need explicit bindings. Generic repositories do, because their type parameters are erased.

Feature modules are installed from `AppModule.configure()`.

### 8.5 REST resources

REST resources live under `com.onix322.app` and are discovered by Jersey through:

```java
jerseyConfig.packages("com.onix322.app");
```

No explicit registration is needed. A resource is any class with `@Path` and JAX-RS method annotations. Resources use `jakarta.inject.Inject` on the constructor because HK2 creates them.

### 8.6 Lombok usage

The `@Inject` passed to `onConstructor_` is `jakarta.inject.Inject`.

| Annotation | Use |
| --- | --- |
| `@Data` | Getters, setters, `equals`, `hashCode`, `toString` |
| `@Getter` | Read-only accessors |
| `@AllArgsConstructor(onConstructor_ = @Inject)` | Constructor injection |
| `@RequiredArgsConstructor(onConstructor_ = @Inject)` | Constructor injection for final fields |
| `@EqualsAndHashCode(callSuper = true)` | Correct equality across entity hierarchy |

---

## 9. Working on the application layer

Application work is expected to change frequently. The implementation model and generic example live in [APPLICATION.md](APPLICATION.md).

Typical workflow:

1. create or extend a feature package under `com.onix322.app`;
2. add or update the feature's Guice module;
3. register new entities in the entity multibinder, or install `InitializerModule` for a generic repository;
4. add repositories under `resource/repository`, extending `BaseRepository` for standard CRUD;
5. add REST resources under `resource/controller`;
6. rely on the framework contracts (`Configuration`, `Database`, `ORM`) and `BaseRepository` instead of implementation classes;
7. keep associations `LAZY`; the request-scoped session installed by `PersistenceModule` makes them load during serialization;
8. update [API.md](API.md) when endpoints change.

---

## 10. Working on the framework layer

Framework changes are less frequent and affect all applications built on the framework.

### 10.1 Replace an implementation

1. implement the contract (`Configuration`, `Database`, `ORM`, or `Server`);
2. change the binding in the relevant Guice module;
3. keep the startup path and application code unchanged.

### 10.2 Add a framework module

1. create a Maven module;
2. add it to the parent `pom.xml`;
3. create an `AbstractModule` that binds its contracts;
4. install it from `LauncherModule`;
5. document it in [FRAMEWORK.md](FRAMEWORK.md).

### 10.3 Add a configuration key

1. add the key to `application.properties`;
2. read it through `Configuration.getProperty(key, type)`;
3. document it in [CONFIGURATION.md](CONFIGURATION.md).

---

## 11. Testing

JUnit Jupiter and Mockito are declared as test dependencies for every module, and Maven Surefire is configured in the parent POM.

```bash
mvn -o test
```

Test sources belong in each module's `src/test/java` directory. The current suite is made of unit tests with mocked dependencies:

| Module | Test class | Focus |
| --- | --- | --- |
| `persistence` | `BaseRepositoryTest` | `BaseRepository` CRUD and criteria queries with a mocked `ORM`/`EntityManager` |
| `initializer` | `TypeFactoryTest` | reflective generic repository creation and member injection |
| `app` | `VanControllerTest` | controller status codes and response bodies with a mocked service |
| `app` | `InspectionReportControllerTest` | controller status codes and response bodies with a mocked service |
| `app` | `VehicleServiceTest` | service validation and repository delegation with a mocked repository |

The `app` test packages use an `integration` name, but the tests do not start a database or an HTTP container. Add a separate integration test setup when persistence behavior against PostgreSQL must be verified.

---

## 12. Logging

- SLF4J API;
- Logback Classic as the implementation.

No `logback.xml` file is present, so Logback uses its built-in default configuration with a console appender.

Hibernate SQL logging is controlled by:

```properties
hibernate.show_sql=true
hibernate.format_sql=true
```

Set `hibernate.show_sql=false` to reduce SQL output.

---

## 13. Build artifacts

Each Maven module produces a library JAR:

| Module | Contents |
| --- | --- |
| `config` | Configuration classes and `application.properties` |
| `persistence` | Database/ORM contracts, Hibernate/PostgreSQL implementations, `BaseRepository`, session filter |
| `server` | Embedded server, Jersey configuration, Guice/HK2 bridges |
| `launcher` | `LauncherModule` and the application entry point |
| `initializer` | `InitializerModule` and `TypeFactory` |
| `app` | Feature modules, resources, services, repositories, entities |

The application is assembled at runtime from these JARs plus their transitive dependencies. There is no shaded or executable JAR.

---

## 14. Operational checks

| Symptom | Check |
| --- | --- |
| Startup fails on database connection | PostgreSQL is running on the configured host and port; database, user and password match `application.properties` |
| Port already in use | Another process is listening on `server.port`; change the port or stop the process |
| No REST response | The process is running; request the configured connector port |
| Schema/data disappears after restart | `hibernate.hbm2ddl.auto` is `create-drop`; change it if data must persist |
| Configuration change has no effect | Rebuild and restart; properties are loaded once at startup |
| Lombok-generated methods missing in the IDE | Lombok plugin installed and annotation processing enabled |
| Compiler fails with `TypeTag :: UNKNOWN` | Maven is running on a JDK newer than 21; switch to a JDK 21 toolchain |
