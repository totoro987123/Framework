package com.polysoft.framework.Shared.Annotations;

import com.polysoft.framework.Shared.Game;
import com.polysoft.framework.Shared.Interfaces.Service;
import com.polysoft.framework.Shared.ReflectionHelper;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Contains methods used to process and handle annotations.
 */
public final class AnnotationProcessor {

    /**
     * Default constructor made private to prevent instantiation.
     */
    private AnnotationProcessor() {}




    // STATIC FIELDS

    /**
     * Game instance used for various method calls.
     */
    public static Game game;

    /**
     * Reflection helper instance used for auxiliary reflection methods.
     */
    public static ReflectionHelper reflectionHelper;




    // PUBLIC METHODS

    /**
     * Applies the service annotation to all tagged fields and sets the value to the loaded service.
     * @param object Object onto which the service annotation will be applied. 
     */
    public static void applyServiceAnnotations(Object object) {
        Class<?> clazz = object.getClass();

        // Service Variable
        for (Field field : reflectionHelper.getFieldsWithAnnotation(clazz, ServiceVariable.class)) {
            try {
                String name = field.getType().getSimpleName();
                field.set(object, game.getService(name));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    
    /**
     * Applies the remoteEvent annotation to all tagged methods and binds the method to the remote.
     * @param object Object onto which the remoteEvent annotation will be applied. 
     */
    public static void applyRemoteAnnotations(Object object) {
        Class<?> clazz = object.getClass();
        Object newObject = object instanceof Service ? game.getService(clazz.getSimpleName()) : object;
    
        // RemoteEvents
        for (Method method : reflectionHelper.getMethodsWithAnnotation(clazz, RemoteMethod.class)) {
            method.setAccessible(true);
            try {
                Class<?> type = method.getParameterTypes()[0];

                game.getCommunicationRegistration()
                        .register(type)
                        .addFirst((session, incomingObject) -> {
                            try {
                                method.invoke(newObject, incomingObject, session);
                            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        });
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }
}
