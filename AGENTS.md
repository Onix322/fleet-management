# Agent Rules for Fleet Management

These rules are the source of truth for every session on this repository.

## Token Efficiency & Workflow

- **Be concise:** Skip greetings, filler, and summaries. Output only requested code, diffs, or direct technical answers.
- **Minimal context:** Read only exact files and line ranges needed. Never dump entire directories unless instructed.
- **Surgical edits:** Modify only target functions/blocks; never rewrite entire files or reformat unrelated code.
- **Plan first:** If a change spans >2 files, propose a brief bulleted plan before generating diffs.
- **No build loops:** If `mvn -o compile` fails twice on the same error, stop and ask instead of retrying.

## Approval-Required Actions

- **AGENTS.md (highest priority):** Never modify, delete, or rename without explicit prior approval. When approved (
  "yes, you can modify AGENTS.md"), show the exact diff first and wait for confirmation.
- **Code & Docs:** Before creating, editing, or deleting ANY file, present the exact diff (`before → after`) and wait
  for explicit approval. Include affected `docs/` updates in the same diff review.
- **Git:** Present the exact command and wait for approval before running any state-changing git command (`commit`,
  `push`, `branch`, `tag`, `merge`, `rebase`, `reset`, `checkout`, `restore`, `rm`, `mv`, `stash`, `cherry-pick`).
  Read-only commands (`status`, `log`, `diff`, `branch -v`) are exempt.
    - **Branch Naming Convention:** When creating or proposing new branches, use these standard prefixes:
        - `feat/` (e.g., `feat/add-itp-field`, `feat/update-ownership-types`) — for new functionality or feature
          extensions.
        - `fix/` (e.g., `fix/uuid-parsing-error`) — for bug fixes and correcting issues in existing code.
        - `chore/` (e.g., `chore/update-enums`, `chore/minor-updates`) — for maintenance, config tweaks, or minor
          housekeeping.
        - `refactor/` (e.g., `refactor/ownership-type`) — for code restructuring or improvements without behavior
          changes.
        - `update/` or `enhancement/` (e.g., `update/vehicle-model`, `enhancement/add-pti-date`) — for descriptive
          extensions of existing features.
        - `docs/` (e.g., `docs/api-documentation`) — for modifications restricted strictly to documentation.
        - `test/` (e.g., `test/add-repository-integration-tests`) — for adding or updating unit/integration tests.


- **Shell safety:** Never run destructive commands (`rm -rf`, `git reset --hard`, `mvn clean` on shared builds). Use
  text-based interactions only.

## Scope Exclusion

- Never read, modify, or delete anything inside `null/`.

## Documentation

## Code conventions
- Java annotations use the **Jakarta** namespace. All `com.google.inject.*`
  annotations are replaced with their `jakarta.inject.*` equivalents.
  Guice 7 recognizes `jakarta.inject.Inject`, `jakarta.inject.Singleton`,
  and `jakarta.inject.Named`.
- JAX-RS resources (e.g. `VanController`) must use `jakarta.inject.Inject`
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

## Persistence, Build & Runtime

## Build & runtime
- `mvn -o compile` is the offline build check. It must pass before any
  runtime verification.
- The running application listens on `localhost:8080`; PostgreSQL is on
  `5432`. After code changes the running process must be rebuilt and
  restarted (by the user, per the approval rule above); the stale process
  will not reflect new annotations or bindings.
- After rebuilding, verify:
  ```sh
  curl -i -X POST 'http://localhost:8080/create' \
    -H 'Content-Type: application/json' \
    -d '{"id":"6f1c0d2e-9b3a-4f7c-8d21-2a4b6c8d0e1f"}'
  ```
