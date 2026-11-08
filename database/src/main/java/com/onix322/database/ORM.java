package com.onix322.database;

import jakarta.persistence.EntityManager;

import java.util.function.Consumer;
import java.util.function.Function;

public interface ORM {
    <R> R executeWithResult(Function<EntityManager, R> action);

    void execute(Consumer<EntityManager> action);

    void close();
}
