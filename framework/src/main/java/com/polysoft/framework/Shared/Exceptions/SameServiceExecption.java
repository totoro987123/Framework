package com.polysoft.framework.Shared.Exceptions;

/**
 * Exception for when a service tries to load itself.
 */
@SuppressWarnings("serial")
public class SameServiceExecption extends Exception {

    /**
     * Default constructor.
     * @param errorMessage Message to be displayed.
     */
    public SameServiceExecption(final String errorMessage) {
        super(errorMessage);
    }

}
