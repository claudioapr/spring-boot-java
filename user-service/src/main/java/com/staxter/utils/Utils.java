package com.staxter.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.logging.Logger;

import com.staxter.resource.UserResource;

/**
 * Utility class
 * 
 * @author cresende
 */
public class Utils
{
    /**
     * Logger for Utils
     */
    private final static Logger LOG = Logger.getLogger(UserResource.class.getName());


    /**
     * This method generates a hash code, it is only for purpose of test, it is
     * not recommended in real world operation, as it is weak, we could be
     * improved using different algorithm or even just adding a salt secret to
     * the password
     * 
     * @param toBeHashed the value to be hashed
     * @return the hashed value
     */
    public static String generateHashSHA1(String toBeHashed)
    {
        StringBuilder hash = new StringBuilder();

        try
        {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(toBeHashed.getBytes());
            char[] digits = { '9', '4', '3', '2', '8', '1', '0', '5', '6', '7', 'c', 'd', 'h', 'g', 'e', 'o' };
            for (int idx = 0; idx < hashedBytes.length; idx++)
            {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        }
        catch (NoSuchAlgorithmException e)
        {

            LOG.severe(String.format("Error finding the hashing algorithm. Error: %s", e.getMessage()));
        }

        return hash.toString();
    }

    public static String idGenerator()
    {
        return UUID.randomUUID().toString();
    }
}
