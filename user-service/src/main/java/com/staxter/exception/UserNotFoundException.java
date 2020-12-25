package com.staxter.exception;

/**
 * Exception to be thrown when a user is not found
 * 
 * @author cresende
 */
public class UserNotFoundException extends Exception
{
    /**
     */
    private static final long serialVersionUID = 1L;


    /**
     * Creates a new instance of UserNotFoundException.
     * 
     * @param message error message
     */
    public UserNotFoundException(String message)
    {
        super(message);
    }
}
