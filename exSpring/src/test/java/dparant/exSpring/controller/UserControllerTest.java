package dparant.exSpring.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.jdi.request.InvalidRequestStateException;
import dparant.exSpring.model.Gender;
import dparant.exSpring.model.request.UserRequest;
import dparant.exSpring.model.response.UserResponse;
import dparant.exSpring.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the user controller
 *
 * @author dylan
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {
    private ObjectMapper objectMapper;

    private UserRequest userRequest;

    private MockMvc mockMvc;

    @Mock
    private UserService service;

    @InjectMocks
    private UserController userController = new UserController(service);

    /**
     * inits the mockMvc
     */
    @BeforeAll
    public void initMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    /**
     * inits the object mapper
     */
    @BeforeAll
    private void initObjectMapper() {
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * inits the basics data for a user
     */
    @BeforeEach
    private void initUserBasicData() {
        userRequest = new UserRequest();
        userRequest.setUsername("Joe323");
        userRequest.setBirthdate(LocalDate.now());
        userRequest.setCountry("France");
    }

    /**
     * returns the uri
     *
     * @param username
     * @return
     */
    private URI getURI(String username) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/user/{username}")
                .buildAndExpand(username)
                .toUri();
    }

    /**
     * method mapping a userRequest to a userResponse
     *
     * @param user
     * @return
     */
    private UserResponse getUserResponse(UserRequest user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setBirthdate(user.getBirthdate());
        userResponse.setCountry(user.getCountry());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setGender(user.getGender());
        return userResponse;
    }

    /**
     * Test "GET" for an existing userRequest
     *
     * @see UserService#getUser(String)
     */
    @Test
    public void testGetUserOk() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Map<String, String> expectingResults = new HashMap<>();
        expectingResults.put("username", userRequest.getUsername());
        expectingResults.put("birthdate", userRequest.getBirthdate().format(dateTimeFormatter));
        expectingResults.put("country", userRequest.getCountry());

        when(service.getUser(userRequest.getUsername())).thenReturn(getUserResponse(userRequest));
        try {
            this.mockMvc.perform(get("/user/" + userRequest.getUsername())).andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(content().json(objectMapper.writeValueAsString(expectingResults)));
        } catch (Exception e) {
            Assert.fail("Exception thrown : " + e.getMessage());
        }
    }

    /**
     * Test "GET" for a non existing user
     *
     * @see UserService#getUser(String)
     */
    @Test
    public void testGetUserNotFound() {
        when(service.getUser(userRequest.getUsername())).thenThrow(new NoSuchElementException());
        try {
            this.mockMvc.perform(get("/user/" + userRequest.getUsername()));
            Assert.fail("The operation must throw an exception");
        } catch (Exception e) {
            Assert.assertNotNull(e);
        }
    }

    /**
     * Test "POST" for a user
     *
     * @see UserService#createUser(UserRequest)
     */
    @Test
    public void testPostUserOk() {
        userRequest.setBirthdate(LocalDate.of(1920, 01, 01));
        userRequest.setPhoneNumber("0323232323");
        userRequest.setGender(Gender.Female);

        URI uri = getURI(userRequest.getUsername());

        when(service.createUser(userRequest)).thenReturn(getUserResponse(userRequest));
        try {
            this.mockMvc.perform(post("/user")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(userRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("location", uri.toString()));
        } catch (Exception e) {
            Assert.fail("Exception thrown : " + e.getMessage());
        }
    }

    /**
     * Test "POST" for a userRequest without non required element
     *
     * @see UserService#createUser(UserRequest)
     */
    @Test
    public void testPostUserOkWithoutGenderAndPhoneNumber() {
        URI uri = getURI(userRequest.getUsername());
        userRequest.setBirthdate(LocalDate.of(1920, 01, 01));
        when(service.createUser(userRequest)).thenReturn(getUserResponse(userRequest));
        try {
            this.mockMvc.perform(post("/user")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(userRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("location", uri.toString()));
        } catch (Exception e) {
            Assert.fail("Exception thrown : " + e.getMessage());
        }
    }

    /**
     * Test "POST" for a userRequest without country
     *
     * @see UserService#createUser(UserRequest)
     */
    @Test
    public void testPostUserFailMissingFields() {
        when(service.createUser(userRequest)).thenReturn(getUserResponse(userRequest));
        try {
            this.mockMvc.perform(post("/user")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"username\":\"etst12\",\"birthdate\":\"12-10-1960\"}"))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            Assert.fail("Exception thrown : " + e.getMessage());
        }
    }

    /**
     * Test "POST" for a non existing userRequest but with a bad date
     *
     * @see UserService#createUser(UserRequest)
     */
    @Test
    public void testPostUserFailInvalidDate() {
        when(service.createUser(userRequest)).thenReturn(getUserResponse(userRequest));
        try {
            this.mockMvc.perform(post("/user")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"username\":\"etst12\",\"birthdate\":\"12-10-19680\",\"country\":\"France\"}"))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            Assert.fail("Exception thrown : " + e.getMessage());
        }
    }

    /**
     * Test "POST" for an existing user
     *
     * @see UserService#createUser(UserRequest)
     */
    @Test
    public void testPostUserFailExistingUser() {
        URI uri = getURI(userRequest.getUsername());
        when(service.createUser(userRequest)).thenThrow(new InvalidRequestStateException("The user already exists"));
        try {
            this.mockMvc.perform(post("/user")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"username\":\"etst12\",\"birthdate\":\"12-10-19680\",\"country\":\"France\"}"))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            Assert.fail("Exception thrown : " + e.getMessage());
        }
    }

}