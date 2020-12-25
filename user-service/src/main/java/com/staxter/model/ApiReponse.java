package com.staxter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Generic API response to provide standard response, with a defined code and
 * description
 * 
 * @author cresende
 */
public class ApiReponse
{
    /**
     * The code of the response
     */
    @JsonProperty(value = "code")
    private ApiCode code;

    /**
     * The description of the response
     */
    @JsonProperty(value = "description")
    private String description;


    /**
     * Creates a new instance of ApiReponse.
     * 
     * @param code of the response
     * @param description of the response
     */
    public ApiReponse(ApiCode code, String description)
    {
        super();
        this.code = code;
        this.description = description;
    }

    /**
     * Returns the code.
     * 
     * @return a CodeException containing the code of this APIReponse
     */
    public ApiCode getCode()
    {
        return code;
    }

    /**
     * Returns the description.
     * 
     * @return a String containing the description of this APIReponse
     */
    public String getDescription()
    {
        return description;
    }

    @Override
    public String toString()
    {
        return String.format("[%s]. %s", code, description);
    }

}
