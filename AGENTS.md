# Agent Rules for Fleet Management

These rules are the source of truth for every session on this repository.

## Token Efficiency & Workflow
- **Be concise:** Skip greetings, filler, and summaries. Output only requested code, diffs, or direct technical answers.
- **Minimal context:** Read only exact files and line ranges needed. Never dump entire directories unless instructed.
- **Surgical edits:** Modify only target functions/blocks; never rewrite entire files or reformat unrelated code.
- **Plan first:** If a change spans >2 files, propose a brief bulleted plan before generating diffs.
- **No build loops:** If `mvn -o compile` fails twice on the same error, stop and ask instead of retrying.

## Approval-Required Actions
- **AGENTS.md (highest priority):** Never modify, delete, or rename without explicit prior approval. When approved ("yes, you can modify AGENTS.md"), show the exact diff first and wait for confirmation.
- **Code & Docs:** Before creating, editing, or deleting ANY file, present the exact diff (`before → after`) and wait for explicit approval. Include affected `docs/` updates in the same diff review.
- **Git:** Present the exact command and wait for approval before running any state-changing git command (`commit`, `push`, `branch`, `tag`, `merge`, `rebase`, `reset`, `checkout`, `restore`, `rm`, `mv`, `stash`, `cherry-pick`). Read-only commands (`status`, `log`, `diff`, `branch -v`) are exempt.
- **Shell safety:** Never run destructive commands (`rm -rf`, `git reset --hard`, `mvn clean` on shared builds). Use text-based interactions only.

## Scope Exclusion
- Never read, modify, or delete anything inside `null/`.

## Documentation
- Write all docs in **English** (`README.md` and `docs/`).
- Do not include file-by-file trees, current-domain catalogues (use generic `Example` placeholders), or "Known Issues" / "Limitations" sections.
- `docs/FRAMEWORK.md`: stable framework (`config`, `database`, `server`, `context`).
- `docs/APPLICATION.md`: volatile application layer (generic feature implementation model).
- `docs/API.md`: generic HTTP / API conventions.
- `docs/CONFIGURATION.md`: property ownership, defaults, replacement steps; separate general (`server.*`, `database.url|user|password`) from `hibernate.*`.
- `docs/DEVELOPMENT.md`: build, run, DB setup, IDE, conventions, testing.

## Code Conventions
- Use **Jakarta** annotations (`jakarta.inject.Inject`, `@Singleton`, `@Named`), never `com.google.inject.*`.
- JAX-RS resources (e.g., `VanController`) use constructor `@Inject`, are instantiated by Jersey/HK2, and **must not** be bound in Guice modules.
- Concrete `@Singleton` classes with injectable constructors use Guice JIT bindings; add explicit bindings only for interfaces, qualifiers, providers, or specific implementations.
- Repositories extend `BaseRepository<T, ID>` (`com.onix322.database.repository`), passing `Class<T>` to `super(type)` and using inherited `@Inject protected ORM orm;`. Direct `ORM` usage can coexist when finer control is needed.
- `Van` inherits `UUID id` without `@GeneratedValue`; JSON payloads for `Van` endpoints must supply an explicit `UUID`.

## Persistence, Build & Runtime
- Defaults: PostgreSQL (`localhost:5432`) + Hibernate; replaceable via `Database` and `ORM` contracts.
- Config source of truth: `config/src/main/resources/application.properties`.
- `hibernate.hbm2ddl.auto=create-drop` is dev-only; production requires Flyway or Liquibase migrations.
- Run `mvn -o compile` for offline build verification before runtime checks.
- App runs on `localhost:8080`. User handles rebuild/restart after approved changes.
- **Security:** No auth implemented yet; add via a dedicated security module before open-sourcing.
