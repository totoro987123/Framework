package com.polysoft.framework.Shared;

import java.lang.reflect.Field;

import com.polysoft.framework.Shared.Annotations.ServiceVariable;
import com.polysoft.framework.Shared.Exceptions.SameServiceExecption;

/**
 * Utility class for processing service field annotations.
 */
public final class ServiceLoader {

    /**
     * Utility class; therefore, no constuctor.
     */
    private ServiceLoader() { }

    // UTILITY METHODS

    /**
     * Processes service field annotations and loads the corresponding services.
     * @param game Central game class.
     * @param services Array of services to be scanned for annotations.
     */
    public static void processVariableAnnotations(final Game game,
                                                final Service[] services) {
        for (Service service : services) {
            Class<?> clazz = service.getClass();

            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);

                if (field.isAnnotationPresent(ServiceVariable.class)) {
                    ServiceVariable serviceVariable = field.getAnnotation(ServiceVariable.class);
                    String serviceName = serviceVariable.serviceName();

                    try {
                        if (service.getName().equals(serviceName)) {
                            throw new SameServiceExecption("Service " + service.getName() + " is trying to load itself!");
                        }

                        field.set(service, game.getService(serviceName));
                    } catch (IllegalArgumentException | IllegalAccessException | SameServiceExecption e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
