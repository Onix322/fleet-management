# Configuration

> **In short:** Where configuration comes from, which properties exist, and how values are parsed and injected.
>
> **Read this if:** you are setting up an environment, changing a connection setting, or adding a new configuration key.
>
> **Related:** [README](../README.md) · [Framework](FRAMEWORK.md) · [Application](APPLICATION.md) · [Development](DEVELOPMENT.md) · [API](API.md)

Fleet Management reads its runtime configuration from a single classpath resource and exposes it through the `Configuration` interface and Guice named bindings. This document belongs to the framework layer.

The framework ships with **PostgreSQL** and **Hibernate** as the default implementations of the `Database` and `ORM` contracts. They are defaults, not hard-coded dependencies: another database or ORM can be supplied by binding the same contracts to different implementations.

---

## 1. Configuration source

| Item | Value |
| --- | --- |
| File | `config/src/main/resources/application.properties` |
| Loaded by | `com.onix322.config.guice.ConfigModule` |
| Loader | `com.onix322.config.util.ConfigurationLoader` |
| Format | Java `Properties` |
| When loaded | Once, during Guice injector creation |

`ConfigModule.configure()` performs the load:

```java
ConfigurationLoader loader = new ConfigurationLoader();
loader.loadProperties();
Names.bindProperties(binder(), loader.getProperties());

bind(ConfigurationLoader.class).toInstance(loader);
bind(Configuration.class).to(ConfigurationImpl.class);
```

The resource is resolved with the class loader:

```java
getClass().getClassLoader().getResourceAsStream("application.properties")
```

If the resource is missing, startup fails with `IllegalStateException: File not found in classpath: application.properties`. An I/O failure is wrapped in `UncheckedIOException`.

Because the file lives in the `config` module, it is packaged into `config-1.0-SNAPSHOT.jar` and is available on the application classpath as long as the `config` module is a dependency.

---

## 2. Reading properties in code

Injection annotations in the examples are `jakarta.inject.Inject` and `jakarta.inject.Named`. Both Guice and Jersey/HK2 recognize them.

### 2.1 Through `Configuration`

```java
@Inject
public MyService(Configuration configuration) {
    Integer port = configuration.getProperty("server.port", Integer.class);
    Boolean showSql = configuration.getProperty("hibernate.show_sql", Boolean.class);
    String url = configuration.getProperty("database.url", String.class);
}
```

### 2.2 Through Guice named bindings

`Names.bindProperties` creates a named binding for every property:

```java
@Inject
public MyService(@Named("server.port") String port) {
    // ...
}
```

The property name is the binding name. Named bindings expose the raw string values; typed access is provided by `Configuration`.

### 2.3 Through `ConfigurationLoader`

Inject `ConfigurationLoader` to access raw values or the complete `Properties` object:

```java
String raw = loader.getRawProperty("server.port");
Properties all = loader.getProperties();
```

---

## 3. Type parsing

`PropertyParser.parse(String, Class<T>)` supports the following target types:

| Target type | Accepted input | Result for blank input |
| --- | --- | --- |
| `String` | Any string | Trimmed value; blank becomes `""` |
| `Integer` | Integer text | `null` |
| `Long` | Long text | `null` |
| `Boolean` | `true` / `false`, case-insensitive | `null` |
| `Double` | Decimal text | `null` |

Additional behavior:

- A missing key (`null` raw value) always produces `null`.
- A boolean value other than `true` or `false` throws `IllegalArgumentException`.
- Any unsupported target type throws `IllegalArgumentException`.
- Values are trimmed before conversion.

---

## 4. Properties and default implementations

The default implementations are:

| Contract | Default implementation | Technology |
| --- | --- | --- |
| `Database` | `PostgresSQL` | PostgreSQL JDBC (`PGSimpleDataSource`) |
| `ORM` | `HibernateORM` | Hibernate ORM |

Both are selected in `DatabaseModule`. Replacing an implementation is a framework-layer change; see [FRAMEWORK.md](FRAMEWORK.md).

### 4.1 Property ownership

| Group | Properties | Owner | Database-specific? | Hibernate-specific? |
| --- | --- | --- | --- | --- |
| General server | `server.port`, `server.base.dir`, `server.context.path`, `server.api.path` | `TomcatServer` / Jersey | No | No |
| General database connection | `database.url`, `database.user`, `database.password` | `Database` implementation (default `PostgresSQL`) | No | No |
| Hibernate dialect | `hibernate.dialect` | `HibernateORM` | No (the value names PostgreSQL) | Yes |
| Hibernate behavior | `hibernate.hbm2ddl.auto`, `hibernate.show_sql`, `hibernate.format_sql` | `HibernateORM` | No | Yes |

The `database.url`, `database.user` and `database.password` keys are **general connection properties**. They are not tied to PostgreSQL: any `Database` implementation can read them and map them to its own driver. The default implementation (`PostgresSQL`) happens to interpret them as PostgreSQL JDBC settings.

Property names are implementation details, not part of the `Database` or `ORM` contracts. A replacement implementation can read different keys.

### 4.2 General server properties

These properties configure Tomcat and Jersey. They do **not** depend on any database or ORM and remain valid if either implementation is replaced.

| Property | Type | Default | Behavior |
| --- | --- | --- | --- |
| `server.port` | `Integer` | `8080` | Tomcat HTTP port. A missing, blank or `0` value resolves to `8080`. |
| `server.base.dir` | `String` | *(empty)* | Tomcat base directory. Blank resolves to `System.getProperty("java.io.tmpdir")`. |
| `server.context.path` | `String` | *(empty)* | Tomcat context path. Empty string registers the root context. |
| `server.api.path` | `String` | `/*` | Servlet mapping for the Jersey `ServletContainer`. |

Notes:

- `server.port=0` does not request an ephemeral port; it is replaced with `8080`.
- When `server.base.dir` is blank, `docBase` passed to Tomcat is `null`.
- `server.context.path` is passed to `Tomcat.addContext(contextPath, docBase)`.
- `server.api.path` is a servlet mapping, not a JAX-RS base path. Jersey discovers resources from the `com.onix322.app` package.

### 4.3 General database connection properties

These properties are read by the default `Database` implementation, `PostgresSQL`, and passed to `PGSimpleDataSource`. The keys themselves are **generic connection settings**, not PostgreSQL-specific: any database implementation can consume the same keys if it maps them to its driver.

| Property | Type | Default | Behavior in `PostgresSQL` |
| --- | --- | --- | --- |
| `database.url` | `String` | `jdbc:postgresql://localhost:5432/postgres` | JDBC URL passed to `PGSimpleDataSource.setUrl` |
| `database.user` | `String` | `root` | Database user passed to `PGSimpleDataSource.setUser` |
| `database.password` | `String` | `root` | Database password passed to `PGSimpleDataSource.setPassword` |

The default value of `database.url` is a PostgreSQL JDBC URL only because `PostgresSQL` is the default implementation. When another `Database` implementation is bound, the same keys can hold a URL for that database.

`HibernateORM` obtains the `DataSource` from the `Database` contract while building the `SessionFactory`; it does not read `database.url`, `database.user`, or `database.password` directly.

### 4.4 Hibernate properties

These properties are read by `HibernateORM`. They configure the default `ORM` implementation. If you replace `ORM`, these keys may no longer be used.

| Property | Type | Default | Behavior |
| --- | --- | --- | --- |
| `hibernate.dialect` | `String` | `org.hibernate.dialect.PostgreSQLDialect` | Hibernate dialect, passed to `AvailableSettings.DIALECT` |
| `hibernate.hbm2ddl.auto` | `String` | `update` | Passed to `AvailableSettings.HBM2DDL_AUTO` |
| `hibernate.show_sql` | `Boolean` | `true` | Passed to `AvailableSettings.SHOW_SQL` |
| `hibernate.format_sql` | `Boolean` | `true` | Passed to `AvailableSettings.FORMAT_SQL` |

All `hibernate.*` properties are read from `Configuration` by `HibernateORM`, not by the `Database` implementation. The `Database` contract only supplies the `DataSource`; the dialect is supplied by the ORM.

With `hibernate.hbm2ddl.auto=update`, Hibernate creates or updates the schema to match the registered entities when `HibernateORM.init()` builds the `SessionFactory`.

### 4.5 Complete default file

```properties
# General server configuration (independent of the database and ORM)
server.port=8080
server.base.dir=
server.context.path=
server.api.path=/*

# General database connection (read by the default Database implementation: PostgresSQL)
database.url=jdbc:postgresql://localhost:5432/postgres
database.user=root
database.password=root

# Hibernate (default ORM implementation: HibernateORM)
hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
hibernate.hbm2ddl.auto=update
hibernate.show_sql=true
hibernate.format_sql=true
```

### 4.6 Replacing the defaults

To replace PostgreSQL, which is only the default `Database` implementation:

1. implement the `Database` contract;
2. bind `Database` to the new implementation in `DatabaseModule`;
3. keep using the general `database.url`, `database.user` and `database.password` keys if the new implementation reads them, or use different keys;
4. update this document and [FRAMEWORK.md](FRAMEWORK.md).

To replace Hibernate:

1. implement the `ORM` contract;
2. bind `ORM` to the new implementation in `DatabaseModule`;
3. replace or remove the `hibernate.*` keys according to the new implementation;
4. update this document and [FRAMEWORK.md](FRAMEWORK.md).

The `server.*` properties and the general `database.*` connection keys are unaffected by the `ORM` replacement.

---

## 5. Changing configuration

1. Edit `config/src/main/resources/application.properties`.
2. Rebuild the affected module or the whole project.
3. Restart the application.

```bash
mvn clean package
```

Because the file is a classpath resource, a running process keeps the values loaded at startup. Changes require a restart.

Multiple environment-specific property files are not present; the build uses the single `application.properties` above.

---

## 6. Operational notes

### Database credentials (general, read by the default `Database` implementation)

`database.user` and `database.password` are general connection properties; the default `PostgresSQL` implementation reads them. The default values are development credentials stored as plain text in the properties file. Treat the file as sensitive when the application uses non-development credentials, and restrict access to the built artifact accordingly.

### Schema management (Hibernate, default `ORM` implementation)

`hibernate.hbm2ddl.auto=update` is a Hibernate setting. It lets Hibernate modify the database schema at startup, derived from the registered entities. A replacement `ORM` implementation decides schema handling on its own and may not use this key.

### Connection creation (default `Database` implementation)

`PostgresSQL.getDataSource()` returns a `PGSimpleDataSource` configured with URL, user and password. `HibernateORM` obtains the `DataSource` through the `Database` contract once, while constructing the `SessionFactory`. A replacement `Database` implementation can build its own data source from the general `database.*` keys or from different keys.

### Instance scope

`ConfigurationLoader`, `ConfigurationImpl`, `PropertyParser`, `PostgresSQL` and `HibernateORM` are singletons. Property values are read from the loaded `Properties` object for the lifetime of the process.

`PostgresSQL` and `HibernateORM` are the default contract implementations; the singleton scope belongs to the framework wiring, not to the property keys.

---

## 7. Configuration class map

| Class | Package | Responsibility |
| --- | --- | --- |
| `Configuration` | `com.onix322.config` | Public configuration contract |
| `ConfigurationImpl` | `com.onix322.config.impl` | Typed property access |
| `ConfigurationLoader` | `com.onix322.config.util` | Classpath resource loading and raw access |
| `PropertyParser` | `com.onix322.config.util` | String → Java type conversion |
| `ConfigModule` | `com.onix322.config.guice` | Guice bindings and named property bindings |

---

## 8. Example: typed access and fallback

```java
public final class ServerSettings {

    private final int port;
    private final String baseDir;

    @Inject
    public ServerSettings(Configuration configuration) {
        Integer configuredPort = configuration.getProperty("server.port", Integer.class);
        this.port = configuredPort == null || configuredPort == 0 ? 8080 : configuredPort;
        this.baseDir = configuration.getProperty("server.base.dir", String.class);
    }
}
```

This mirrors the resolution performed by `TomcatServer`: `null` and `0` fall back to `8080`, and a blank base directory falls back to the JVM temporary directory.
