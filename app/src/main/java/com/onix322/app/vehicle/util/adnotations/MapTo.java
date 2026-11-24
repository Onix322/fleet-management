package com.onix322.app.vehicle.util.adnotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.FIELD)
public @interface MapTo {
    String toField();
    Class<?> foundIn();
}
