package com.polysoft.framework.Shared;

import com.polysoft.framework.Shared.Exceptions.ServiceNotFound;
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



    // CONSTRUCTORS

    /**
     * Constructs a default game class.
     */
    public Game() {
    }

    /**
     * Constructs a game that includes the starting services given.
     * @param startingServices Array of services to load at the start.
     */
    public Game(final Service[] startingServices) {
        this.addServices(startingServices);
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
            Service foundService = this.services.get(serviceName);

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
        System.out.println(service.getName());
        this.services.put(service.getName(), service);
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
        ServiceLoader.processVariableAnnotations(this, this.getServiceArray());
    }
}
