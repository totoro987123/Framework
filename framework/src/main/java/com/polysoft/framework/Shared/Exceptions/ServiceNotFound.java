package com.polysoft.framework.Shared.Exceptions;

/**
 * Exception when a service trying to be loaded does not exist.
 */
@SuppressWarnings("serial")
public class ServiceNotFound extends Exception {

    /**
     * Default constructor.
     * @param errorMessage Message to be displayed.
     */
    public ServiceNotFound(final String errorMessage) {
        super(errorMessage);
    }

}
