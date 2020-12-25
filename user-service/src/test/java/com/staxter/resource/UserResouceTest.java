package com.staxter.resource;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.staxter.model.ApiCode;
import com.staxter.model.UserCredential;
import com.staxter.userrepository.UserRepositoryImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(UserResource.class)
@AutoConfigureMockMvc
public class UserResouceTest
{
    /**
     * As the real implementation is a mock itself (I am not using any database
     * only static list to mock everything)
     */
    @SpyBean
    private UserRepositoryImpl repository;

    @Autowired
    private MockMvc mvc;


    @Test
    public void testAddingSucessfullyAUser_ShouldReturnTheUser() throws Exception
    {
        StringBuilder user = new StringBuilder();
        user.append(" {");
        user.append("\"firstName\": \"Claudio Augusto\",");
        user.append(" \"lastName\": \"De Paulo resende\",");
        user.append("\"userName\": \"admin2\",");
        user.append(" \"password\": \"admin1234\"");
        user.append("}");
        mvc.perform(
                MockMvcRequestBuilders.post("/userservice/register").content(user.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Claudio Augusto"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("De Paulo resende"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("admin2"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testAddingUserWithoutUserName_ShouldThrowBadRequest() throws Exception
    {
        StringBuilder user = new StringBuilder();
        user.append(" {");
        user.append("\"firstName\": \"Claudio Augusto\",");
        user.append(" \"lastName\": \"De Paulo resende\",");
        user.append(" \"password\": \"admin1234\"");
        user.append("}");
        mvc.perform(
                MockMvcRequestBuilders.post("/userservice/register").content(user.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testAddingUserWithoutPassword_ShouldThrowBadRequest() throws Exception
    {
        StringBuilder user = new StringBuilder();
        user.append(" {");
        user.append("\"firstName\": \"Claudio Augusto\",");
        user.append(" \"lastName\": \"De Paulo resende\",");
        user.append("\"userName\": \"admin2\"");
        user.append("}");
        mvc.perform(
                MockMvcRequestBuilders.post("/userservice/register").content(user.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testTryingToAddTwoUserWithTheSameName_ShouldReturnUserAlredyExists() throws Exception
    {

        StringBuilder user = new StringBuilder();
        user.append(" {");
        user.append("\"firstName\": \"Claudio Augusto\",");
        user.append(" \"lastName\": \"De Paulo resende\",");
        user.append("\"userName\": \"admin\",");
        user.append(" \"password\": \"admin1234\"");
        user.append("}");

        // in case the user is not registered yet, to force the error
        mvc.perform(
                MockMvcRequestBuilders.post("/userservice/register").content(toJson(user))
                        .contentType(MediaType.APPLICATION_JSON));

        mvc.perform(
                MockMvcRequestBuilders.post("/userservice/register").content(user.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ApiCode.USER_ALREADY_EXISTS.toString()))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.description")
                                .value("A user with the given username already exists"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testLoginUsingRightCredentials_ShouldReturnSuccessLogin() throws Exception
    {
        StringBuilder user = new StringBuilder();
        user.append(" {");
        user.append("\"firstName\": \"Claudio Augusto\",");
        user.append(" \"lastName\": \"De Paulo resende\",");
        user.append("\"userName\": \"admin\",");
        user.append(" \"password\": \"admin1234\"");
        user.append("}");

        // in case the user is not registered yet
        mvc.perform(
                MockMvcRequestBuilders.post("/userservice/register").content(user.toString())
                        .contentType(MediaType.APPLICATION_JSON));

        UserCredential credential = new UserCredential("admin", "admin1234");

        mvc.perform(
                MockMvcRequestBuilders.post("/userservice/login").content(toJson(credential))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ApiCode.LOGIN_SUCCESS.toString()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testLoginUsingWrongPassword_ShouldReturnLoginError() throws Exception
    {
        StringBuilder user = new StringBuilder();
        user.append(" {");
        user.append("\"firstName\": \"Claudio Augusto\",");
        user.append(" \"lastName\": \"De Paulo resende\",");
        user.append("\"userName\": \"admin\",");
        user.append(" \"password\": \"admin1234\"");
        user.append("}");

        // in case the user is not registered yet
        mvc.perform(
                MockMvcRequestBuilders.post("/userservice/register").content(user.toString())
                        .contentType(MediaType.APPLICATION_JSON));

        UserCredential credential = new UserCredential("admin", "sdsdsd");

        mvc.perform(
                MockMvcRequestBuilders.post("/userservice/login").content(toJson(credential))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ApiCode.LOGIN_ERROR.toString()))
                .andDo(MockMvcResultHandlers.print());
    }

    public static String toJson(final Object obj)
    {
        try
        {
            return new ObjectMapper().writeValueAsString(obj);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
