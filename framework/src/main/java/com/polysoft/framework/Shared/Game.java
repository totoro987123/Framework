package com.polysoft.framework.Shared;

import com.github.thorbenkuck.netcom2.network.shared.CommunicationRegistration;
import com.polysoft.framework.Shared.Annotations.AnnotationProcessor;
import com.polysoft.framework.Shared.Exceptions.ObjectNotRegisteredException;
import com.polysoft.framework.Shared.Exceptions.ServiceNotFound;
import com.polysoft.framework.Shared.Interfaces.Service;
import java.util.HashMap;

/**
 * Main class for the game.
 * @author totoro987123
 * @version 1.0
 * @since January 8, 2020
 */
public class Game {

    // INSTANCE FIELDS

    /**
     * HashMap for storing all services with their associated names.
     */
    private HashMap<String, Service> services = new HashMap<String, Service>();

    /**
     * Boolean indicating if this game instance is the server or the client.
     */
    private boolean server = false;

    /**
     * Reflection helper object used for auxiliary reflection methods.
     */
    private ReflectionHelper reflectionHelper;

    /**
     * CommunicationRegistration object for registration of events.
     */
    private CommunicationRegistration communicationRegistration;




    // CONSTRUCTORS

    /**
     * Constructs a default game class.
     * @param server Boolean for if it is the server of the client.
     */
    public Game(boolean server) {
        this.server = server;

        this.reflectionHelper = new ReflectionHelper(this);

        AnnotationProcessor.game = this;
        AnnotationProcessor.reflectionHelper = this.reflectionHelper;
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




    // PROTECTED METHODS

    /**
     * Sets if this is the server of the client.
     * @param server A boolean representing whether or not this is the server.
     */
    protected void setServer(boolean server) {
        this.server = server;
    }

    /**
     * Sets the communication registration object.
     * @param communicationRegistration The communication registration object.
     */
    protected void setCommunicationRegistration(CommunicationRegistration communicationRegistration) {
        this.communicationRegistration = communicationRegistration;

        this.communicationRegistration.addDefaultCommunicationHandler((object) -> {
            String className = object.getClass().getSimpleName();
            
            try {
                throw new ObjectNotRegisteredException("Error: Object of class " + className + " is not registered!");
            } catch (ObjectNotRegisteredException e) {
                e.printStackTrace();
            }
        });
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
    }


    /**
     * Loads the game by processing annotations of all loaded services.
     */
    public void load() {
        this.addServices(this.reflectionHelper.getServices());

        // Initialize all services.
        for (Service service : this.services.values()) {
            service.initialize();
        }
    }


    /**
     * Returns a boolean indicating whether or not this is the server.
     * @return Boolean indicating whether or not this is the server.
     */
    public boolean isServer() {
        return this.server;
    }


    /**
     * Returns the communication registration object.
     * @return CommunicationRegistration returns the communication registration object.
     */
    public CommunicationRegistration getCommunicationRegistration() {
        return this.communicationRegistration;
    }

}
