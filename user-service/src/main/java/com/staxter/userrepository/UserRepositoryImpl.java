package com.staxter.userrepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.staxter.exception.UserAlreadyExistsException;
import com.staxter.exception.UserNotFoundException;
import com.staxter.model.User;
import com.staxter.utils.Utils;

/**
 * This class is a test class, with only test purpose, as this data, in the real
 * scenario should be stored in a real database or in the file system
 * 
 * @author cresende
 */
@Repository
public class UserRepositoryImpl implements UserRepository
{
    /**
     * The user storage
     */
    private static List<User> users = new ArrayList<>();


    /**
     * {@inheritDoc}
     */
    @Override
    public User createUser(User user) throws UserAlreadyExistsException
    {
        if (users.contains(user))
        {
            throw new UserAlreadyExistsException("A user with the given username already exists");
        }

        user.setId(Utils.idGenerator());
        users.add(user);
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findByUsernameAndPassword(String userName, String password) throws UserNotFoundException
    {
        List<User> found = users.stream().filter(user ->
        {
            if (userName == null || password == null)
            {
                return false;
            }
            if (user.getUserName() == null || user.getHashedPassword() == null)
            {
                return false;
            }
            return (user.getUserName().equals(userName) && user.getHashedPassword().equals(password));
        }).collect(Collectors.toList());

        if (found == null || found.isEmpty())
        {
            throw new UserNotFoundException("Wrong username or password provided");
        }
        return found.get(0);
    }
}
