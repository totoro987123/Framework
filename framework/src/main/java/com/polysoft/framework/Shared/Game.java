package com.polysoft.framework.Shared;

import com.github.thorbenkuck.netcom2.network.shared.CommunicationRegistration;
import com.polysoft.framework.Shared.Exceptions.ServiceNotFound;
import com.polysoft.framework.Shared.Interfaces.RemoteEvent;

import java.util.HashMap;

/**
 * Main class for the game.
 * @author totoro987123
 * @version 1.0
 * @since January 8, 2020
 */
public class Game {

    // INSTANCE VARIABLES

    /**
     * HashMap for storing all services with their associated names.
     */
    private HashMap<String, Service> services = new HashMap<String, Service>();
    private boolean server = false;
    private ReflectionHelper reflectionHelper;
    private CommunicationRegistration communicationRegistration;




    // CONSTRUCTORS

    /**
     * Constructs a default game class.
     */
    public Game(boolean server) {
        this.server = server;

        this.reflectionHelper = new ReflectionHelper(this);

        AnnotationProcessor.game = this;
        AnnotationProcessor.reflectionHelper = this.reflectionHelper;

        this.addServices(this.reflectionHelper.getServices());
    }




    // PRIVATE METHODS

    /**
     * Converts the hashmap of services into an array.
     * @return Service[] This retruns an array of all loaded services.
     */
    protected Service[] getServiceArray() {
        int arrayLength = this.services.size();
        Service[] serviceArray = new Service[arrayLength];
        int counter = 0;

        for (Service service : this.services.values()) {
            serviceArray[counter] = service;
            counter++;
        }

        return serviceArray;
    }



    // PUBLIC METHODS

    /**
     * Finds the service with the given name.
     * @param serviceName This is the name of the service that is being found.
     * @return service This returns the service with the given name
     *          or null if the service could not be found.
     */
    public Service getService(final String serviceName) {
        try {
            Service foundService = this.services.get(serviceName.toUpperCase());

            if (foundService == null) {
                throw new ServiceNotFound(
                    "Service with name "
                    + serviceName
                    + " could not be found!");
            }

            return foundService;
        } catch (ServiceNotFound e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Adds a service to the game's services.
     * @param service Service to add to the game.
     */
    public void addService(final Service service) {
        String name = service.getClass().getSimpleName().toUpperCase();
        this.services.put(name, service);
    }

    /**
     * Adds all listed services to the game's services.
     * @param newServices Array of services all to be added to the game.
     */
    public void addServices(final Service[] newServices) {
        for (Service service : newServices) {
            this.addService(service);
        }

        for (Service service : newServices) {
            AnnotationProcessor.applyAnnotations(service);
        }
    }

    /**
     * Loads the game by processing annotations of all loaded services.
     */
    public void load() {
        Object[] objects = this.reflectionHelper.convertClasslistToObjects(this.reflectionHelper.getSubTypeClasses(RemoteEvent.class));
        for (Object object : objects) {
            AnnotationProcessor.applyRemoteAnnotations(object);
        }


        for (Service service : this.services.values()) {
            service.initialize();
        }
    }

    public void setServer(boolean server) {
        this.server = server;
    }

    public boolean isServer() {
        return this.server;
    }

    public CommunicationRegistration getCommunicationRegistration() {
        return this.communicationRegistration;
    }

    public void setCommunicationRegistration(CommunicationRegistration communicationRegistration) {
        this.communicationRegistration = communicationRegistration;
    }
}
