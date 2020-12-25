package com.staxter.exception;

public class UserAlreadyExistsException extends Exception
{
    /**
     * Exception to be thrown when a user with the same username is trying to be
     * added
     */
    private static final long serialVersionUID = 1L;


    /**
     * Creates a new instance of UserAlreadyExistsException.
     * 
     * @param message error message
     */
    public UserAlreadyExistsException(String message)
    {
        super(message);
    }

}
