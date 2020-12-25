package com.staxter.model;

/**
 * Class providing a builder to generates a user
 * 
 * @author cresende
 */
public class UserBuilder
{
    /**
     * User first name
     */
    private String firstName;

    /**
     * User last name
     */
    private String lastName;

    /**
     * Unique user name
     */
    private String userName;

    /**
     * User password
     */
    private String plainTextPassword;


    /**
     * Creates a instance of the builder
     * 
     * @return
     */
    public static UserBuilder create()
    {
        return new UserBuilder();
    }

    public UserBuilder withFirstName(String firstName)
    {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder withLastName(String lastName)
    {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder withUserName(String userName)
    {
        this.userName = userName;
        return this;
    }

    public UserBuilder withPassword(String password)
    {
        this.plainTextPassword = password;
        return this;
    }

    public User build()
    {
        return new User(firstName, lastName, userName, plainTextPassword);
    }

}
