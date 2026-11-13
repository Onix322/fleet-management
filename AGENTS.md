# Agent Rules for Fleet Management

These rules apply to every session working on this project. They are the
source of truth for what may and may not be done without approval, and for
the conventions the codebase and documentation must follow.

## Approval-required actions
- **AGENTS.md (highest priority)**: this file is the most important file
  in the repository. It must NOT be modified, deleted, or renamed without
  my explicit prior approval of the exact change (diff shown first).
- **Code**: before applying ANY code change — creating, editing, or
  deleting any code file — you must first present the exact diff
  (before → after) and wait for my explicit approval. Nothing is
  applied without it.
- **Documentation**: when a code change affects documentation, propose the
  documentation updates alongside the code diff and get the same approval.
- **Git**: ANY git command that creates, updates, edits, modifies, or deletes
  repository state — e.g. `commit`, `push`, `branch` (create/rename/delete),
  `tag` (create/delete), `merge`, `rebase`, `reset`, `amend`, `checkout`,
  `restore`, `rm`, `mv`, `stash`, `cherry-pick` — must be presented with its
  exact command and explicitly approved before it is run. Read-only git
  commands (`status`, `log`, `diff`, `branch -v`, ...) are exempt.
- Do not run destructive shell commands that bypass the diff-approval rule.

## Scope exclusion
- The `null/` directory is completely out of scope. Never read, modify,
  or delete anything inside it.

## Documentation
- All documentation is written in **English**, in `README.md` and `docs/`.
- No file-by-file project tree and no current-domain catalogues in general
  docs. Use generic `Example` placeholders instead.
- No "Known Issues" or "Limitations" sections.
- `docs/FRAMEWORK.md` documents the stable framework (`config`, `database`,
  `server`, `context`).
- `docs/APPLICATION.md` documents the volatile application layer and serves
  as a generic feature implementation model.
- `docs/API.md` documents generic HTTP / API conventions.
- `docs/CONFIGURATION.md` documents property ownership, default
  implementations, and replacement steps. It must distinguish general
  properties (`server.*`, `database.url|user|password`) from
  Hibernate-specific ones (`hibernate.*`).
- `docs/DEVELOPMENT.md` documents build, run, database setup, IDE,
  conventions, and testing.

## Code conventions
- Java annotations use the **Jakarta** namespace. All `com.google.inject.*`
  annotations are replaced with their `jakarta.inject.*` equivalents.
  Guice 7 recognizes `jakarta.inject.Inject`, `jakarta.inject.Singleton`,
  and `jakarta.inject.Named`.
- JAX-RS resources (e.g. `VanResource`) must use `jakarta.inject.Inject`
  in the constructor. They are created by Jersey / HK2, not Guice, and must
  not be bound in Guice modules.
- Concrete `@Singleton` classes with injectable constructors use Guice JIT
  bindings. Explicit bindings are only needed for interfaces, qualifiers,
  providers, and implementation choices.
- `BaseRepository<T, ID>` lives in `com.onix322.database.repository`
  (framework `database` module) and is the recommended default for
  application repositories. It uses field injection
  (`@Inject protected ORM orm;`) and a constructor
  `BaseRepository(Class<T> type)`. Subclasses only pass the entity type.
  For more control, use the `ORM` contract directly. Both can coexist in
  the same repository.
- `Van` currently has only the inherited `UUID id` (no `@GeneratedValue`).
  JSON payloads for `Van` endpoints must include an explicit UUID.
- JAX-RS resources must not be bound in Guice modules.

## Persistence & configuration
- PostgreSQL and Hibernate are the defaults. They are replaceable through
  the `Database` and `ORM` contracts.
- The source of truth for configuration values is
  `config/src/main/resources/application.properties`.

## Build & runtime
- `mvn -o compile` is the offline build check. It must pass before any
  runtime verification.
- The running application listens on `localhost:8080`; PostgreSQL is on
  `5432`. After code changes the running process must be rebuilt and
  restarted; the stale process will not reflect new annotations or bindings.
- After rebuilding, verify:
  ```sh
  curl -i -X POST 'http://localhost:8080/create' \
    -H 'Content-Type: application/json' \
    -d '{"id":"6f1c0d2e-9b3a-4f7c-8d21-2a4b6c8d0e1f"}'
  ```
