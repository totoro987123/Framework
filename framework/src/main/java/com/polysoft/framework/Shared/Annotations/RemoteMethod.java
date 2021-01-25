package com.polysoft.framework.Shared.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark service fields.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RemoteMethod {

    /**
     * Checks if the method requires a login to run.
     * @return boolean Weather or not the user needs to be logged in to use this method.
     */
    public boolean login() default false;
}
