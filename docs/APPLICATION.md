# Application

> **In short:** A repeatable implementation model for adding features to the `app` module.
>
> **Read this if:** you are adding a feature, entity, repository, service, or REST resource.
>
> **This is a template, not a catalog of current features.** It intentionally uses `Example` names so it stays valid as the application evolves.
>
> **Related:** [README](../README.md) · [Framework](FRAMEWORK.md) · [API](API.md) · [Development](DEVELOPMENT.md)

The `app` module is the application layer. This document describes **how to structure and wire a new feature**, not which features currently exist.

---

## 1. Purpose

Use this document when you need to answer:

- where does a new feature live?
- how is an entity registered?
- how does a REST resource reach the database?
- where does business logic belong?
- how does the feature become part of the running application?

The examples use `Example` as a disposable placeholder—think of it as a scratch/test feature. Replace it with a name from your domain.

---

## 2. Rules

1. **One feature, one package.** Keep related entities, services, repositories, and resources together under `com.onix322.app.<feature>`.
2. **One Guice module per feature.** The module registers entities and, when needed, generic repository bindings; services and concrete repositories are created through just-in-time bindings.
3. **Entities are registered through the multibinder.** The `persistence` module's `HibernateORMImpl` consumes the resulting `Set<Class<?>>`.
4. **REST resources are discovered by package scan.** Jersey scans `com.onix322.app`, so no explicit resource registration is required.
5. **Business logic belongs in services.** Repositories handle persistence; resources handle HTTP.
6. **Depend on framework contracts.** Use `ORM`, `Database`, and `Configuration`; do not reference `HibernateORMImpl`, `PostgresSQLImpl`, or `TomcatServer`.
7. **Keep feature boundaries.** A feature should not depend on another feature's entities or repositories. Expose services when cross-feature collaboration is needed.
8. **Use Jakarta injection annotations.** Annotate constructors with `jakarta.inject.Inject` and singletons with `jakarta.inject.Singleton`. Guice and HK2 both recognize them.

The framework ships with PostgreSQL and Hibernate as the default `Database` and `ORM` implementations. Feature code depends only on the contracts, so either default can be replaced without changing the feature.

---

## 3. Feature package template

```text
com.onix322.app.<feature>
├── guice/
│   └── <Feature>Module.java
└── resource/
    ├── controller/
    │   └── <Feature>Controller.java
    ├── model/
    │   ├── <DomainType>.java
    │   └── impl/
    │       └── <Entity>.java
    ├── repository/
    │   └── <Entity>Repository.java
    └── service/
        └── <Feature>Service.java
```

| Location | Responsibility |
| --- | --- |
| `guice` | Guice wiring, entity registration, generic repository bindings |
| `resource/controller` | JAX-RS endpoints, HTTP status codes, request/response shaping |
| `resource/model` | Domain contracts and API-facing types |
| `resource/model/impl` | JPA entities |
| `resource/repository` | Queries and persistence operations through `ORM` or `BaseRepository` |
| `resource/service` | Business rules, validation, and transaction orchestration |

The `repository` and `service` directories are optional for very small features, but keeping the separation makes features easier to grow.

---

## 4. Implementation flow

1. Choose the feature name and package.
2. Define the domain model and the JPA entity.
3. Register the entity in the feature's Guice module.
4. Add a repository for persistence operations.
5. Add a service for business rules.
6. Add a REST resource.
7. Install the feature module from `AppModule`.
8. Test the feature.
9. Document new endpoints in [API.md](API.md) if the HTTP surface changes.

The rest of this document expands each step with a generic example.

A feature can also start as a single resource and grow into the full structure:

```java
@Path("/example")
@Produces(MediaType.APPLICATION_JSON)
public class ExampleController {

    @GET
    public String ping() {
        return "ok";
    }
}
```

Add the entity, repository, and service layers when the feature needs persistence or business rules.

---

## 5. Example feature

The example uses a feature called `example`, with an `ExampleEntity`, `ExampleRepository`, `ExampleService`, and `ExampleController`.

### 5.1 Entity hierarchy

There is no shared application base entity in the current codebase. Declare `@Id` and its generation strategy on the mapped class that owns the identifier:

```java
@Id
@GeneratedValue(strategy = GenerationType.UUID)
private UUID id;
```

If several entity types share mapped fields, define an abstract `@Entity` or `@MappedSuperclass` and let the concrete types extend it:

```java
@MappedSuperclass
@Data
public abstract class ExampleBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}
```

Choose one identifier strategy and use it consistently. The current entities use JPA-generated UUIDs, so clients do not need to send an identifier when creating a resource.

### 5.2 Entity

```java
@Entity
@Table(name = "example")
@EqualsAndHashCode(callSuper = true)
@Data
public class ExampleEntity extends ExampleBase {

    private String name;
    private boolean active;
}
```

Rules for entities:

- one `@Entity` per persisted type;
- an explicit `@Table` name;
- `@EqualsAndHashCode(callSuper = true)` when extending a mapped superclass or entity;
- no HTTP or business logic inside the entity.

### 5.3 Repository

The repository is the persistence boundary. Feature repositories extend the framework's `BaseRepository<T, ID>` for standard CRUD and use the `ORM` contract for feature-specific queries.

A concrete repository with a no-arg constructor needs no explicit binding:

```java
import jakarta.inject.Singleton;

@Singleton
public class ExampleRepository extends BaseRepository<ExampleEntity, UUID> {

    public ExampleRepository() {
        super(ExampleEntity.class);
    }

    public List<ExampleEntity> findActive() {
        return orm.executeWithResult(em ->
                em.createQuery(
                        "select e from ExampleEntity e where e.active = true",
                        ExampleEntity.class
                ).getResultList()
        );
    }
}
```

A generic repository is also possible, but it needs an explicit binding per entity because Guice cannot resolve the erased type parameter:

```java
@Singleton
public class ExampleRepository<T> extends BaseRepository<T, UUID> {

    public ExampleRepository(Class<T> type) {
        super(type);
    }
}
```

`BaseRepository` injects `ORM` into a `protected` field, so subclasses only pass the entity type to `super(...)`. The inherited `orm` field remains available for feature-specific queries.

`BaseRepository` provides `find`, `findAll`, `save`, `delete`, `count` and `exists`. Each method runs in its own transaction.

During an HTTP request all repository calls share one Hibernate session, opened by `OpenSessionInViewFilter` from the `persistence` module and closed after the response is written. That is why returning an entity with lazy collections works: the collections load while Jackson serializes, inside the still-open session. Outside a request the session is opened and closed per call.

Do not work around lazy loading in the mapping. Prefer `FetchType.LAZY` with `@BatchSize`, keep the default session scope, and add explicit `join fetch` queries only where a use case needs them.

- `save` upserts with `merge` — inserts a new entity, updates an existing one;
- custom queries use `orm.execute` or `orm.executeWithResult` directly.

The recommendation is to extend `BaseRepository` for standard CRUD and to use `ORM` directly when a use case needs more control: explicit queries, locking, flush timing, batch operations, or custom session semantics. Both can be used in the same repository class — the inherited methods are ordinary `ORM` transactions, so inherited CRUD and custom `orm.execute(...)` calls coexist without conflict.

Do not add feature-specific methods to `BaseRepository`. Extend it in the feature and keep the extra queries local.

### 5.4 Service

The service contains business rules and coordinates repository calls.

```java
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class ExampleService {

    private final ExampleRepository repository;

    @Inject
    public ExampleService(ExampleRepository repository) {
        this.repository = repository;
    }

    public List<ExampleEntity> list() {
        return repository.findAll();
    }

    public ExampleEntity create(ExampleEntity entity) {
        // Validate and apply business rules here.
        return repository.save(entity);
    }

    public ExampleEntity getById(UUID id) {
        return repository.find(id)
                .orElseThrow(() -> new NotFoundException("Example not found"));
    }
}
```

If a use case needs several database operations in one transaction, perform them inside a single `ORM` callback in the repository or service rather than across multiple `ORM` calls.

A generic service follows the same shape:

```java
@Singleton
public class ExampleService<T extends ExampleBase> {

    private final ExampleRepository<T> repository;

    @Inject
    public ExampleService(ExampleRepository<T> repository) {
        this.repository = repository;
    }
}
```

### 5.5 Resource

The resource maps HTTP to the service. It does not contain persistence logic.

Resources are created by Jersey/HK2, so their constructor must use `jakarta.inject.Inject`. `com.google.inject.Inject` is not recognized there.

```java
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/examples")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class ExampleController {

    private final ExampleService service;

    @Inject
    public ExampleController(ExampleService service) {
        this.service = service;
    }

    @GET
    public List<ExampleEntity> list() {
        return service.list();
    }

    @POST
    public Response create(ExampleEntity entity) {
        ExampleEntity created = service.create(entity);
        return Response.status(Response.Status.CREATED)
                .entity(created)
                .build();
    }
}
```

The resource is discovered automatically because it is under `com.onix322.app`.

Resources are created by Jersey/HK2, not by Guice. Use `jakarta.inject.Inject` on the resource constructor — HK2 does not recognize `com.google.inject.Inject` there. Do not bind the resource class in a Guice module; Jersey manages its lifecycle, and the HK2 bridge resolves the service dependency from Guice.

The current controllers catch expected exceptions and return an explicit `Response` with a status code and a message. There is no global `ExceptionMapper`; add one when the application needs a shared error format.

### 5.6 Polymorphic entity types

When a feature has several entity types that share a common shape — for example `ExampleA` and `ExampleB` both extending an abstract `ExampleAbstract` — one REST endpoint can accept and return the shared type, and Jackson resolves the concrete subtype from a discriminator property in the JSON body.

Annotate the abstract entity:

```java
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ExampleA.class, name = "a"),
    @JsonSubTypes.Type(value = ExampleB.class, name = "b"),
})
public abstract class ExampleAbstract {
    // shared fields
}
```

Effect on the JSON contract:

- every request and response body for the feature carries the discriminator property;
- the value of the discriminator selects the concrete subtype (`"a"` → `ExampleA`, `"b"` → `ExampleB`);
- a value not registered in `@JsonSubTypes` is rejected by Jackson.

The discriminator property name is chosen per hierarchy. Current entities use names such as `type` and `vehicle_type`, so clients must send the property that belongs to the hierarchy they are addressing.

To add a subtype, declare the concrete entity and register it in `@JsonSubTypes`; no other wiring is needed. The JPA mapping stays independent: `@Inheritance(TABLE_PER_CLASS)` or `@Inheritance(SINGLE_TABLE)` decides how rows are stored, while the Jackson annotations only shape the JSON.

Because `BaseRepository.save` upserts through `merge`, the pattern round-trips cleanly: a body carrying an existing identifier updates that row, a new identifier inserts one.

### 5.7 Feature module

The module registers the persisted entities of the feature. Services and concrete repositories with a `@Singleton` annotation and an injectable constructor do not need an explicit binding; Guice creates them just in time.

A feature that only contributes entities uses the multibinder directly:

```java
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

public class ExampleModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<Class<?>> entities =
                Multibinder.newSetBinder(binder(), new TypeLiteral<Class<?>>() {});
        entities.addBinding().toInstance(ExampleEntity.class);
    }
}
```

A feature with a generic repository also registers one repository binding per entity. The support module provides `InitializerModule` for this:

```java
import com.google.inject.AbstractModule;
import onix322.initializer.guice.InitializerModule;

import java.util.Set;

public class ExampleModule extends AbstractModule {

    private static final Set<Class<?>> EXAMPLE_ENTITIES = Set.of(
            ExampleBase.class,
            ExampleEntity.class
    );

    @Override
    protected void configure() {
        install(new InitializerModule(EXAMPLE_ENTITIES, ExampleRepository.class));
    }
}
```

`InitializerModule` adds every class to the entity multibinder and binds `ExampleRepository<Entity>` through `TypeFactory`.

`ReportModule` in the application currently performs the same entity and repository registration inline with `TypeFactory`; both approaches are equivalent.

Add explicit bindings only when the injector cannot infer the target: an interface bound to an implementation, a named or qualified dependency, a provider, a generic repository, or a class that needs a scope other than the one declared on the class.

Add every persisted entity of the feature to the same multibinder. The framework collects them and passes the complete set to Hibernate.

### 5.8 Install the module

`AppModule` installs the feature modules:

```java
public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new ExampleModule());
        // Install other feature modules here.
    }
}
```

`AppModule` is installed by `LauncherModule`, which is the application entry point:

```java
public class LauncherModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new ServerModule());
        install(new ConfigModule());
        install(new PersistenceModule());
        install(new AppModule());
    }

    public static void main(String[] args) {
        Server.launch(new LauncherModule());
    }
}
```

Do not add a `main` method to `AppModule`; the composition root and entry point live in `launcher`.

### 5.9 Verify

After starting the application:

```bash
curl -i http://localhost:8080/examples
```

The exact response depends on the feature implementation and is documented in [API.md](API.md).

---

## 6. Design rules

### Transactions

- Each `ORM` call is one transaction.
- During a request, the session is shared through `ORM.begin()` / `ORM.end()`, managed by `OpenSessionInViewFilter` in the `persistence` module. Do not call them from feature code.
- Put multi-step operations that must be atomic inside a single `execute` or `executeWithResult` callback. `jakarta.transaction.Transactional` is not processed by any interceptor in this stack and has no effect.
- Do not keep an `EntityManager` outside the callback or pass it between layers.

### Identifiers

- The current entities use `@GeneratedValue(strategy = GenerationType.UUID)`.
- Keep the chosen strategy consistent across entities.
- A client that sends an identifier on create is responsible for its uniqueness; otherwise omit it and let the provider generate one.

### API models

- Returning an entity is acceptable for a small feature.
- Use a dedicated request/response type when the HTTP shape differs from the persistence shape or when the API must remain stable while the entity evolves.

### Error handling

- Return JAX-RS `Response` values with explicit status codes.
- Add a custom `ExceptionMapper` when the application needs a shared error format.

### Boundaries

- Do not reference framework implementation classes from `app`.
- Do not let one feature reach into another feature's repository.
- Expose a service when another feature needs to collaborate.

---

## 7. Testing

Add tests under:

```text
app/src/test/java/com/onix322/app/<feature>
```

The current test suite uses JUnit 5 and Mockito. Tests are unit tests around controllers and services; they do not start PostgreSQL or a real Hibernate session. Focus on:

- domain rules and service behavior;
- repository logic with mocked `ORM` (as in `BaseRepositoryTest`);
- resource behavior when the HTTP contract is important.

Generic repository binding is covered by `TypeFactoryTest` in the `initializer` module.

---

## 8. Checklist

- [ ] feature package created under `com.onix322.app.<feature>`;
- [ ] entity defined and annotated;
- [ ] entity registered in the feature module;
- [ ] repository uses `BaseRepository` and/or the `ORM` contract;
- [ ] generic repository has an explicit `InitializerModule` or `TypeFactory` binding;
- [ ] service contains the business rules;
- [ ] resource exposes the HTTP contract;
- [ ] feature module installed from `AppModule`;
- [ ] tests added;
- [ ] [API.md](API.md) updated if endpoints changed.

---

## 9. References

- Framework contracts and startup: [FRAMEWORK.md](FRAMEWORK.md)
- Configuration: [CONFIGURATION.md](CONFIGURATION.md)
- HTTP reference: [API.md](API.md)
- Build, run, and conventions: [DEVELOPMENT.md](DEVELOPMENT.md)
