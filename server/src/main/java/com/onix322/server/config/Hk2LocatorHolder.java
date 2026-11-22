package com.onix322.server.config;

import org.glassfish.hk2.api.ServiceLocator;

/**
 * Holds the HK2 {@link ServiceLocator} created by Jersey. It only exists after Jersey has
 * initialized {@link GuiceToHK2Bridge}, so it is read lazily, at injection time.
 */
public final class Hk2LocatorHolder {

    private volatile ServiceLocator locator;

    void set(ServiceLocator locator) {
        this.locator = locator;
    }

    ServiceLocator get() {
        ServiceLocator current = locator;
        if (current == null) {
            throw new IllegalStateException(
                    "HK2 ServiceLocator is not available yet; GuiceToHK2Bridge has not run");
        }
        return current;
    }
}
