package com.polysoft.framework.Shared;

/**
 * Service superclass for default service methods.
 */
public class Service {

    // INSTANCE VARIABLES

    /**
     * Name of the service.
     */
    private String serviceName;



    // CONSTRUCTORS

    /**
     * Default constructor.
     * @param name Name of the service.
     */
    public Service(final String name) {
        this.serviceName = name;
    }



    // PUBLIC METHODS

    /**
     * Gets the name of the service.
     * @return Retruns the name of the service.
     */
    public String getName() {
        return this.serviceName;
    }

    /**
     * Starts the service's operations.
     * Should be overidden as neccessary by subclasses.
     */
    public void initialize() {
        //Overriden by subclasses.
    }

}
