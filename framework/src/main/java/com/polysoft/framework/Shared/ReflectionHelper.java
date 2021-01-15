package com.polysoft.framework.Shared;

import com.polysoft.framework.Shared.Interfaces.Service;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;


/**
 * Reflection Helper for easily doing various reflection operations.
 * @author totoro987123
 * @version 1.0
 * @since January 10, 2020
 */
public class ReflectionHelper {

    // INSTANCE FIELDS

    /**
     * Identifies the main game instance.
     */
    private Game game;




    // CONSTRUCTORS

    /**
     * Default constructor.
     * @param gameInstance The main game instance.
     */
    public ReflectionHelper(final Game gameInstance) {
        this.game = gameInstance;
    }




    // PRIVATE METHODS

    /**
     * Gets a classpath for the non-shared packages which should be searched.
     * @return  String The classpath of the specific
     *          packages that should be searched.
     */
    private String getPath() {
        if (game.isServer()) {
            return "com.polysoft.framework.Server";
        } else {
            return "com.polysoft.framework.Client";
        }
    }




    // PUBLIC METHODS

    /**
     * Returns a set of all classes that implements the Service interface.
     * @return A set of all services.
     */
    public Set<Class<? extends Service>> findServices() {
        String path = this.getPath();

        Reflections reflectionsShared = new Reflections("com.polysoft.framework.Shared", 
                                        new SubTypesScanner());
        Reflections reflectionsLocal = new Reflections(path, new SubTypesScanner());

        Set<Class<? extends Service>> services = new HashSet<
                                            Class<? extends Service>>();

        reflectionsShared.getSubTypesOf(Service.class);

        services.addAll(reflectionsLocal.getSubTypesOf(Service.class));

        return services;
    }

    
    /**
     * Gets all classes which are sub classes of the given class.
     * @param superClass The superclass, whose subclasses will be searched for.
     * @return A set of all classes which are sub classes of the given class.
     */
    public Set<Class<?>> getSubTypeClasses(final Class<?> superClass) {
        String path = this.getPath();

        Reflections reflectionsShared = new Reflections("com.polysoft.framework.Shared", 
                                        new SubTypesScanner());

        Reflections reflectionsLocal = new Reflections(path, new SubTypesScanner());

        Set<Class<?>> classes = new HashSet<Class<?>>();

        classes.addAll(reflectionsShared.getSubTypesOf(superClass));
        classes.addAll(reflectionsLocal.getSubTypesOf(superClass));

        return classes;
    }


    /**
     * Converts a set of service classes into an array of service objects.
     * @param services List of all service classes to be converted.
     * @return An array of all service objects created from the list.
     */
    public Service[] convertToServiceArray(final Set<Class<? extends Service>> services) {
        Service[] result = new Service[services.size()];
        int counter = 0;

        for (Class<? extends Service> serviceClass : services) {
            try {
                Constructor<?> constructor = serviceClass.getConstructor();
                Service service = (Service) constructor.newInstance();

                result[counter] = service;
                counter++;
            } catch (NoSuchMethodException | SecurityException
                    | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return result;
    }


    /**
     * Returns an array of objects made from the classes passed in.
     * @param classes Set of classes to be make into objects.
     * @return An array of objects.
     */
    public Object[] convertClasslistToObjects(final Set<Class<?>> classes) {
        Object[] result = new Object[classes.size()];
        int counter = 0;

        for (Class<?> clazz : classes) {
            try {
                Constructor<?> constructor = clazz.getConstructor();
                Object object = constructor.newInstance();

                result[counter] = object;
                counter++;
            } catch (NoSuchMethodException | SecurityException
                    | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return result;
    }


    /**
     * Gets all the fields of a given class with a given annotation.
     * @param clazz The class which will be searched for fields.
     * @param annotation The annotation to check that the fields have.
     * @return A set of all fields with the given annotation,
     */
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


    /**
     * Returns all methods of the given class with the given annotation.
     * @param clazz The class which will be searched for methods.
     * @param annotation The annotation to check that the methods have.
     * @return A set of all methods with the given annotation.
     */
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


    /**
     * Gets an array of all services.
     * @return An array of all services.
     */
    public Service[] getServices() {
        return this.convertToServiceArray(this.findServices());
    }

}
