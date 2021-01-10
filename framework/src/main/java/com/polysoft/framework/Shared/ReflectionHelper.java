package com.polysoft.framework.Shared;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

public class ReflectionHelper {

    private Game game;

    public ReflectionHelper(Game game) {
        this.game = game;
    }

    private String getPath() {
        if (game.isServer()) {
            return "com.polysoft.framework.Server";
        } else {
            return "com.polysoft.framework.Client";
        }
    }

    public Set<Class<? extends Service>> findServices() {
        String path = this.getPath();

        Reflections reflectionsShared = new Reflections("com.polysoft.framework.Shared", new SubTypesScanner());
        Reflections reflectionsLocal = new Reflections(path, new SubTypesScanner());

        Set<Class<? extends Service>> services = reflectionsShared.getSubTypesOf(Service.class);
        services.addAll(reflectionsLocal.getSubTypesOf(Service.class));

        return services;
    }

    public Set<Class<?>> getSubTypeClasses(Class<?> subType) {
        String path = this.getPath();

        Reflections reflectionsShared = new Reflections("com.polysoft.framework.Shared", new SubTypesScanner());
        Reflections reflectionsLocal = new Reflections(path, new SubTypesScanner());

        Set<Class<?>> classes = new HashSet<Class<?>>();

        classes.addAll(reflectionsShared.getSubTypesOf(subType));
        classes.addAll(reflectionsLocal.getSubTypesOf(Service.class));

        return classes;
    }
    
    public Service[] convertToServiceArray(Set<Class<? extends Service>> services) {
        Service[] result = new Service[services.size()];
        int counter = 0;

        for (Class<? extends Service> serviceClass : services) {
            try {
                Constructor<?> constructor = serviceClass.getConstructor();
                Service service = (Service) constructor.newInstance();

                result[counter] = service; 
                counter++;
            } catch (NoSuchMethodException | SecurityException | InstantiationException 
                    | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public Object[] convertClasslistToObjects(Set<Class<?>> classes) {
        Object[] result = new Object[classes.size()];
        int counter = 0;

        for (Class<?> clazz : classes) {
            try {
                Constructor<?> constructor = clazz.getConstructor();
                Object object = constructor.newInstance();

                result[counter] = object; 
                counter++;
            } catch (NoSuchMethodException | SecurityException | InstantiationException 
                    | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public Set<Field> getFieldsWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        Set<Field> fields = new HashSet<Field>();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(annotation)) {
                fields.add(field);
            }
        }
        
        return fields;
    }

    public Set<Method> getMethodsWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        Set<Method> methods = new HashSet<Method>();

        for (Method method : clazz.getDeclaredMethods()) {
            method.setAccessible(true);

            if (method.isAnnotationPresent(annotation)) {
                methods.add(method);
            }
        }
        
        return methods;
    }

    public Service[] getServices() {
        return this.convertToServiceArray(this.findServices());
    }
}
