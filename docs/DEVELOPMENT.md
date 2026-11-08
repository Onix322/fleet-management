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

Lombok is used throughout the codebase. When working in an IDE, install the Lombok plugin and enable annotation processing.

---

## 2. Build

Run from the repository root:

```bash
mvn clean package
```

Useful variants:

| Command | Effect |
| --- | --- |
| `mvn clean compile` | Compile all modules |
| `mvn clean package` | Compile, test and package all modules |
| `mvn clean install` | Install all module artifacts into the local Maven repository |
| `mvn -pl app -am clean package` | Build the `app` module and its dependencies |
| `mvn -o clean package` | Offline build when all dependencies are cached |

Each module produces a library JAR in its `target/` directory. The application is started through the `com.onix322.App` main class.

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

Hibernate manages the schema according to `hibernate.hbm2ddl.auto`, currently `update`. On startup, the mapped entity tables are created or updated.

PostgreSQL and Hibernate are the default `Database` and `ORM` implementations. They can be replaced through their contracts; the application code depends only on `Database` and `ORM`.

Full reference: [CONFIGURATION.md](CONFIGURATION.md).

---

## 4. Run

### 4.1 IntelliJ IDEA

1. Open the repository root as a Maven project.
2. Set the project SDK to JDK 21.
3. Enable annotation processing for Lombok.
4. Run `com.onix322.App` (`app/src/main/java/com/onix322/App.java`).

### 4.2 Maven

Install the modules first so the `app` module can resolve its reactor siblings:

```bash
mvn -DskipTests install
mvn -pl app exec:java -Dexec.mainClass=com.onix322.App
```

The second command uses the Maven Exec plugin, resolved from the configured Maven repositories.

### 4.3 Startup behavior

`App.main` calls:

```java
Server.launch(ApplicationContext.getInstance(), new App());
```

Startup performs the following work before accepting traffic:

1. loads `application.properties`;
2. creates the PostgreSQL data source;
3. builds the Hibernate `SessionFactory` and registers the entities from the Guice multibinder;
4. creates the Tomcat server;
5. configures the Jersey servlet;
6. starts the connector and blocks on `tomcat.getServer().await()`.

If the database is unreachable or the configuration is invalid, startup fails while the injector creates the eager singletons. See [FRAMEWORK.md](FRAMEWORK.md) for the full sequence.

---

## 5. Verify the running application

```bash
curl -i http://localhost:8080/
```

See [API.md](API.md) for HTTP conventions and endpoint testing examples. The port is controlled by `server.port`.

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
| Framework | `config`, `database`, `server`, `context` | [FRAMEWORK.md](FRAMEWORK.md) |
| Application | `app` | [APPLICATION.md](APPLICATION.md) |

When a change affects infrastructure, update the framework docs. When a change affects features, endpoints, or entities, update the application docs.

---

## 8. Conventions

### 8.1 Module per concern

- `config` — configuration only;
- `database` — persistence only;
- `server` — HTTP container only;
- `context` — framework composition;
- `app` — entry point and business features.

Business code does not belong in the framework modules.

### 8.2 Interface and implementation

Framework contracts are interfaces with separate implementations:

```text
Configuration  → ConfigurationImpl
Database       → PostgresSQL
ORM            → HibernateORM
Server         → TomcatServer
```

Guice modules bind the interface to the implementation.

The contracts are replaceable. To swap Tomcat, implement `Server` and change the binding in `ServerModule`; to swap PostgreSQL or Hibernate, implement `Database` or `ORM` and change the binding in `DatabaseModule`. Feature code and the startup path remain tied to the interfaces.

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

Keep `com.google.inject` imports only for injector configuration: `AbstractModule`, `bind(...)`, `@Provides`, `Multibinder`, `TypeLiteral`, `Injector`, `Names.bindProperties`.

### 8.4 Feature modules

Each application feature has its own Guice module under `app/src/main/java/com/onix322/app/<feature>/guice/`.

The module is responsible for:

- registering JPA entities in the `Set<Class<?>>` multibinder;
- binding feature-specific services;
- exposing resource registration hooks.

Feature modules are installed from `App.configure()`.

### 8.5 REST resources

REST resources live under `com.onix322.app` and are discovered by Jersey through:

```java
jerseyConfig.packages("com.onix322.app");
```

No explicit registration is needed. A resource is any class with `@Path` and JAX-RS method annotations.

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
3. register new entities in the entity multibinder;
4. add repositories under `resource/repository`, extending `BaseRepository` for standard CRUD;
5. add REST resources under `resource/controller`;
6. rely on the framework contracts (`Configuration`, `Database`, `ORM`) and `BaseRepository` instead of implementation classes;
7. update [API.md](API.md) when endpoints change.

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
4. install it from `ApplicationContext`;
5. document it in [FRAMEWORK.md](FRAMEWORK.md).

### 10.3 Add a configuration key

1. add the key to `application.properties`;
2. read it through `Configuration.getProperty(key, type)`;
3. document it in [CONFIGURATION.md](CONFIGURATION.md).

---

## 11. Testing

JUnit Jupiter is declared as a test dependency for every module, and Maven Surefire is configured in the parent POM.

```bash
mvn test
```

Test sources belong in each module's `src/test/java` directory. There are currently no test classes in the repository.

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
| `database` | Database and ORM classes |
| `server` | Embedded server and Jersey bridge |
| `context` | `ApplicationContext` module |
| `app` | Entry point, features, resources, entities |

The application is assembled at runtime from these JARs plus their transitive dependencies. There is no shaded or executable JAR.

---

## 14. Operational checks

| Symptom | Check |
| --- | --- |
| Startup fails on database connection | PostgreSQL is running on the configured host and port; database, user and password match `application.properties` |
| Port already in use | Another process is listening on `server.port`; change the port or stop the process |
| No REST response | The process is running; request the configured connector port |
| Configuration change has no effect | Rebuild and restart; properties are loaded once at startup |
| Lombok-generated methods missing in the IDE | Lombok plugin installed and annotation processing enabled |
