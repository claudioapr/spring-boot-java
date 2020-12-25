package com.staxter.model;

/**
 * Class providing definition for the user credential
 * 
 * @author cresende
 */
public class UserCredential
{
    /**
     * User user name
     */
    private String userName;

    /**
     * User password
     */
    private String password;


    public UserCredential(String userName, String password)
    {
        super();
        this.userName = userName;
        this.password = password;
    }

    /**
     * Returns the password.
     * 
     * @return a String containing the password of this UserCredential
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Returns the userName.
     * 
     * @return a String containing the userName of this UserCredential
     */
    public String getUserName()
    {
        return userName;
    }

}
