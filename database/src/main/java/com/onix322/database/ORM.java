package com.onix322.database;

import jakarta.persistence.EntityManager;

import java.util.function.Consumer;
import java.util.function.Function;

public interface ORM {
    <R> R executeWithResult(Function<EntityManager, R> action);

    void execute(Consumer<EntityManager> action);

    /**
     * Opens a unit-of-work scope on the current thread. While the scope is active, every
     * {@link #executeWithResult} and {@link #execute} call reuses the same session instead of
     * opening a new one. No transaction is started here; each call still runs in its own
     * transaction.
     */
    void begin();

    /**
     * Closes the scope opened by {@link #begin()}, rolls back any transaction still active and
     * releases the session. Must be called on the same thread that opened the scope.
     */
    void end();

    void close();
}
