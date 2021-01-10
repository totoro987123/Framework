package com.polysoft.framework.Shared;

/**
 * Service superclass for default service methods.
 */
public class Service {

    // INSTANCE VARIABLES


    // CONSTRUCTORS

    /**
     * Default constructor.
     * @param name Name of the service.
     */
    public Service() {
    }



    // PUBLIC METHODS

    /**
     * Gets the name of the service.
     * @return Retruns the name of the service.
     */

    /**
     * Starts the service's operations.
     * Should be overidden as neccessary by subclasses.
     */
    public void initialize() {
        //Overriden by subclasses.
    }

}
