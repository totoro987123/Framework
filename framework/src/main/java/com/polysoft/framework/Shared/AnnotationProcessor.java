package com.polysoft.framework.Shared;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.polysoft.framework.Shared.Annotations.*;

public final class AnnotationProcessor {
    private AnnotationProcessor() {}

    public static Game game;
    public static ReflectionHelper reflectionHelper;

    public static void applyAnnotations(Object object) {
        Class<?> clazz = object.getClass();

        // Service Variable
        for (Field field : reflectionHelper.getFieldsWithAnnotation(clazz, ServiceVariable.class)) {
            try {
                String name = field.getType().getSimpleName();
                field.set(object, game.getService(name));
                System.out.println("Set " + name);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void applyRemoteAnnotations(Object object) {
        Class<?> clazz = object.getClass();
        Object newObject = object instanceof Service ? game.getService(clazz.getSimpleName()) : object;
    
        // RemoteEvents
        for (Method method : reflectionHelper.getMethodsWithAnnotation(clazz, RemoteMethod.class)) {
            method.setAccessible(true);
            try {
                Class<?> type = method.getParameterTypes()[0];

                System.out.println("Com register added");
                game.getCommunicationRegistration()
                        .register(type)
                        .addFirst((Session, IncomingObject) -> {
                            try {
                                method.invoke(newObject, IncomingObject, Session);
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
