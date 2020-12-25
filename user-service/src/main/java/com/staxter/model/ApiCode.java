package com.staxter.model;

/**
 * Pre defined code for API reponse
 * 
 * @author cresende
 */
public enum ApiCode
{
 /**
  * In case the user already exists
  */
 USER_ALREADY_EXISTS,
 /**
  * When the user has successfully performed login in the system
  */
 LOGIN_SUCCESS,
 /**
  * When the user try to login but some problem come up
  */
 LOGIN_ERROR,
 /**
  * When some validations does not match
  */
 VALIDATION_ERROR

}
