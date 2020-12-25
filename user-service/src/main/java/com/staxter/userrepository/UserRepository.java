package com.staxter.userrepository;

import com.staxter.exception.UserAlreadyExistsException;
import com.staxter.exception.UserNotFoundException;
import com.staxter.model.User;

/**
 * User repository interface defines basic user retrieve and creation operation
 * 
 * @author cresende
 */
public interface UserRepository
{

    /**
     * Given a user stores it in the data storage
     * 
     * @param user user to be stored
     * @return the stored user
     * @throws UserAlreadyExistsException in case the username is already stored
     */
    User createUser(User user) throws UserAlreadyExistsException;

    /**
     * Given a username and a password it checks if there is in the data store a
     * user the matches with the criteria
     * 
     * @param userName user name to be looked up
     * @param password to be looked up
     * @return the find user
     * @throws UserNotFoundException is thrown in case the user is not found
     */
    User findByUsernameAndPassword(String userName, String password) throws UserNotFoundException;

}
