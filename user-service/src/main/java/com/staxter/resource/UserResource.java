package com.staxter.resource;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.staxter.exception.UserAlreadyExistsException;
import com.staxter.exception.UserNotFoundException;
import com.staxter.model.ApiCode;
import com.staxter.model.ApiReponse;
import com.staxter.model.User;
import com.staxter.model.UserCredential;
import com.staxter.userrepository.UserRepository;
import com.staxter.utils.Utils;

/**
 * Rest controller to make available operations user related
 * 
 * @author cresende
 */
@RestController
@RequestMapping("/userservice")
public class UserResource
{
    /**
     * Logger for User Resource
     */
    private final static Logger LOG = Logger.getLogger(UserResource.class.getName());

    /**
     * The injected repository
     */
    @Autowired
    private UserRepository repository;


    /**
     * Rest operation for registering a user
     * 
     * @param user object to be registered
     * @return ResponseEntity with the user or a Api Response in case the user
     *         already exists
     */
    @PostMapping("register")
    public ResponseEntity register(@RequestBody User user)
    {
        // verifies whether the username or password is null, if yes send error
        // response
        // it could have used SpringBoot VALIDATION lib but as one of the
        // requirement is use as less as possible of library to keep it
        // lightweight the validation will be done as it is
        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getHashedPassword()))
        {
            ApiReponse apiReponse = new ApiReponse(
                    ApiCode.VALIDATION_ERROR,
                    "Username and password cannot be nulll or empty");
            LOG.severe(apiReponse.toString());
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(apiReponse);
        }
        try
        {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(repository.createUser(user));
        }
        catch (UserAlreadyExistsException e)
        {
            ApiReponse apiReponse = new ApiReponse(ApiCode.USER_ALREADY_EXISTS, e.getMessage());
            LOG.severe(apiReponse.toString());

            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(apiReponse);
        }

    }

    /**
     * Rest operation for login into the system
     * 
     * @param credentials the user credential to be checked against the storage
     * @return ResponseEntity informing the user wheter was login or not
     *         successfully
     */
    @PostMapping("login")
    public ResponseEntity login(@RequestBody UserCredential credentials)
    {
        try
        {
            repository.findByUsernameAndPassword(
                    credentials.getUserName(),
                    Utils.generateHashSHA1(credentials.getPassword()));
        }
        catch (UserNotFoundException e)
        {
            ApiReponse apiReponse = new ApiReponse(ApiCode.LOGIN_ERROR, e.getMessage());
            LOG.severe(apiReponse.toString());
            return ResponseEntity.ok(apiReponse);

        }
        return ResponseEntity.ok(new ApiReponse(ApiCode.LOGIN_SUCCESS, "login performed successfully"));

    }

}
