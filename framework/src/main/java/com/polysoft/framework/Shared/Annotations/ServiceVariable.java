package com.polysoft.framework.Shared.Annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

/**
 * Annotation to mark service fields.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ServiceVariable {
    /**
     * Used to get the name put into the annotation.
     * @return Returns the service name.
     */
    String serviceName() default "";
}
