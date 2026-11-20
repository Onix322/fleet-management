package com.onix322.app.persistence;

import com.onix322.database.ORM;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;

/**
 * Keeps one persistence session bound to the request thread for the whole HTTP request.
 *
 * <p>The session stays open while Jersey/Jackson serialize the response, so lazily loaded
 * associations are initialized on demand instead of failing with
 * {@code LazyInitializationException}. Transactions remain per {@code ORM} call: this filter
 * only owns the session scope, not the transaction boundary.</p>
 */
@Singleton
public class OpenSessionInViewFilter implements Filter {

    private final ORM orm;

    @Inject
    public OpenSessionInViewFilter(ORM orm) {
        this.orm = orm;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        orm.begin();
        try {
            chain.doFilter(request, response);
        } finally {
            orm.end();
        }
    }
}
