package com.polysoft.framework.Shared.Exceptions;

/**
 * Exception for when a service tries to load itself.
 */
@SuppressWarnings("serial")
public class ObjectNotRegisteredException extends Exception {

    /**
     * Default constructor.
     * @param errorMessage Message to be displayed.
     */
    public ObjectNotRegisteredException(final String errorMessage) {
        super(errorMessage);
    }

}
