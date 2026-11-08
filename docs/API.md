# API

> **In short:** HTTP conventions and a repeatable model for exposing application features as REST resources.
>
> **Read this if:** you are integrating a client, testing the API, or adding a REST resource.
>
> **This is a general reference, not a catalog of current endpoints.** The current endpoints change with the application. The implementation model for features is in [APPLICATION.md](APPLICATION.md).
>
> **Base URL:** `http://localhost:8080`
>
> **Related:** [README](../README.md) · [Application](APPLICATION.md) · [Framework](FRAMEWORK.md) · [Configuration](CONFIGURATION.md) · [Development](DEVELOPMENT.md)

The HTTP API is served by an embedded Tomcat instance through a Jersey (JAX-RS) servlet. This document describes the contract conventions and how to add a resource, not which resources currently exist.

---

## 1. Routing model

| Setting | Default | Effect |
| --- | --- | --- |
| `server.port` | `8080` | Connector port |
| `server.context.path` | *(empty)* | Root context |
| `server.api.path` | `/*` | All paths inside the context are routed to Jersey |

Consequences:

- there is no implicit `/api` prefix;
- a resource path is declared with `@Path` and is relative to the root context;
- Jersey discovers resources by scanning `com.onix322.app`;
- changing `server.api.path` changes the servlet mapping, not the `@Path` values.

Configuration details: [CONFIGURATION.md](CONFIGURATION.md).

---

## 2. Conventions

| Aspect | Convention |
| --- | --- |
| Protocol | HTTP/1.1 |
| Request/response media type | `application/json` |
| JSON provider | Jackson via `jersey-media-json-jackson` |
| Resource discovery | Package scan of `com.onix322.app` |
| Resource paths | Lowercase, plural nouns, hyphen-separated when needed |
| Authentication | Not configured by default |
| API versioning | Not configured by default |
| CORS | Not configured by default |

Use HTTP methods according to their semantics:

| Method | Purpose |
| --- | --- |
| `GET` | Read a resource or a collection |
| `POST` | Create a resource |
| `PUT` | Replace a resource |
| `PATCH` | Partially update a resource |
| `DELETE` | Remove a resource |

Return explicit status codes:

| Situation | Status |
| --- | --- |
| Successful read | `200 OK` |
| Successful creation | `201 Created` |
| Successful update | `200 OK` or `204 No Content` |
| Successful deletion | `204 No Content` |
| Invalid request body | `400 Bad Request` |
| Missing resource | `404 Not Found` |
| Conflict | `409 Conflict` |
| Unsupported media type | `415 Unsupported Media Type` |

---

## 3. Defining a resource

A resource is a class under `com.onix322.app.<feature>.resource.controller` with JAX-RS annotations. Jersey/HK2 creates resources, so the constructor uses `jakarta.inject.Inject`.

```java
import jakarta.inject.Inject;
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
public class ExampleResource {

    private final ExampleService service;

    @Inject
    public ExampleResource(ExampleService service) {
        this.service = service;
    }

    @GET
    public List<ExampleResponse> list() {
        return service.list();
    }

    @POST
    public Response create(ExampleRequest request) {
        ExampleResponse created = service.create(request);
        return Response.status(Response.Status.CREATED)
                .entity(created)
                .build();
    }
}
```

Rules:

- inject services through the constructor;
- keep persistence out of the resource;
- return a `Response` when you need to control status codes or headers;
- rely on the package scan for registration;
- use a dedicated request/response type when the HTTP shape differs from the entity.

The dependency injection bridge resolves constructor dependencies through Guice. See [FRAMEWORK.md](FRAMEWORK.md) for the Guice/HK2 integration.

---

## 4. Request and response bodies

### Serialization

Jackson serializes public getters and deserializes JSON into the declared parameter type.

- `UUID` values serialize as JSON strings.
- `null` fields are serialized as `null` unless Jackson is configured otherwise.
- Java time types require the Jackson JSR-310 module to be registered on the `ObjectMapper`; the dependency is available in the project.

### Entities versus API types

| Situation | Recommendation |
| --- | --- |
| Small feature, HTTP shape equals the entity | Returning the entity is acceptable |
| API must stay stable while the entity evolves | Use a response type |
| Request contains fields that must not be persisted directly | Use a request type |
| Entity contains sensitive or internal fields | Use a response type and exclude those fields |

Keep API types under the feature's `resource/model` package.

---

## 5. Error responses

There is no global error format by default. Choose a consistent shape, for example:

```json
{
  "error": "not_found",
  "message": "Resource not found"
}
```

Ways to produce errors:

- return `Response.status(...)` from the resource;
- throw a JAX-RS `WebApplicationException` with a response;
- register an `ExceptionMapper` for a shared format.

Unmatched paths produce a `404`. Without a custom exception mapper, the servlet container generates the default error page.

---

## 6. Pagination, filtering, and sorting

Use query parameters for collection endpoints:

```http
GET /examples?limit=20&offset=0
GET /examples?active=true
GET /examples?sort=name
```

Conventions:

| Parameter | Meaning |
| --- | --- |
| `limit` | Maximum number of items |
| `offset` | Number of items to skip |
| `sort` | Field and direction, for example `name` or `-createdAt` |
| Other query parameters | Feature-specific filters |

Bind query parameters with `@QueryParam`:

```java
@GET
public List<ExampleResponse> list(
        @QueryParam("limit") @DefaultValue("20") int limit,
        @QueryParam("offset") @DefaultValue("0") int offset
) {
    return service.list(limit, offset);
}
```

Validate and cap `limit` in the service layer.

---

## 7. Security

Authentication and authorization are not configured by default.

When they are needed, add them at the HTTP layer:

- a JAX-RS `ContainerRequestFilter` for authentication;
- a `ContainerResponseFilter` for response headers;
- a JAX-RS `SecurityContext` check in resources or a filter;
- a servlet filter when the concern applies to the whole container.

Do not expose entities directly when they contain credentials, internal identifiers, or other sensitive fields.

---

## 8. Versioning

No versioning scheme is configured by default.

Choose one before the public contract stabilizes:

| Strategy | Example |
| --- | --- |
| Path versioning | `/v1/examples` |
| Media type versioning | `Accept: application/vnd.example.v1+json` |
| Header versioning | `X-API-Version: 1` |

Path versioning is the simplest to test with `curl` and to document.

---

## 9. Testing an endpoint

Generic checks:

```bash
# Collection
curl -i http://localhost:8080/examples

# Create
curl -i -X POST http://localhost:8080/examples \
  -H 'Content-Type: application/json' \
  -d '{"name":"example"}'

# Single resource
curl -i http://localhost:8080/examples/00000000-0000-0000-0000-000000000000
```

Check:

- status code;
- `Content-Type`;
- response body shape;
- error body for invalid input;
- behavior for missing resources.

Use the same base URL that matches `server.port` and `server.context.path`.

---

## 10. Checklist for a new endpoint

- [ ] resource under `com.onix322.app.<feature>.resource.controller`;
- [ ] `@Path` with a clear, stable name;
- [ ] HTTP method annotations;
- [ ] `@Produces` and `@Consumes` where relevant;
- [ ] service injected through the constructor;
- [ ] request/response types chosen;
- [ ] explicit status codes;
- [ ] error behavior defined;
- [ ] pagination/filtering for collections;
- [ ] endpoint tested with `curl`;
- [ ] [APPLICATION.md](APPLICATION.md) and this document updated if the model changes.

---

## 11. References

- Feature implementation model: [APPLICATION.md](APPLICATION.md)
- Framework contracts, server, and Guice/HK2 bridge: [FRAMEWORK.md](FRAMEWORK.md)
- Server and routing configuration: [CONFIGURATION.md](CONFIGURATION.md)
- Build, run, and testing setup: [DEVELOPMENT.md](DEVELOPMENT.md)
