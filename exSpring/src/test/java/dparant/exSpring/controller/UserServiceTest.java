package dparant.exSpring.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.jdi.request.InvalidRequestStateException;
import dparant.exSpring.model.Gender;
import dparant.exSpring.model.User;
import dparant.exSpring.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserServiceTest {
    private ObjectMapper objectMapper;

    private User user;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @BeforeEach
    private void initObjectMapper() {
       this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
       objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @BeforeEach
    private void initUserBasicData() {
        user = new User();
        user.setUsername("Joe323");
        user.setBirthdate(LocalDate.now());
        user.setCountry("France");
    }


    private URI getURI(String username) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(username)
                .toUri();
    }
    /**
     * Test "GET" for an existing user
     *
     * @see UserService#getUser(String)
     */
    @Test
    public void testGetUserOk() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Map<String, String> expectingResults = new HashMap<>();
        expectingResults.put("username", user.getUsername());
        expectingResults.put("birthdate", user.getBirthdate().format(dateTimeFormatter));
        expectingResults.put("country", user.getCountry());

        when(service.getUser(user.getUsername())).thenReturn(user);
        try {
            this.mockMvc.perform(get("/user/"+user.getUsername())).andDo(print())
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
        when(service.getUser(user.getUsername())).thenThrow(new NoSuchElementException());
        try {
            this.mockMvc.perform(get("/user/"+user.getUsername())).andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(content().string(containsString("No data found")));
        } catch (Exception e) {
            Assert.fail("Exception thrown : " + e.getMessage());
        }
    }

    /**
     * Test "POST" for a user
     *
     * @see UserService#createUser(User)
     */
    @Test
    public void testPostUserOk() {
        user.setBirthdate(LocalDate.of(1920,01,01));
        user.setPhoneNumber("0323232323");
        user.setGender(Gender.Female);

        URI uri = getURI(user.getUsername());

        when(service.createUser(user)).thenReturn(uri);
        try {
            this.mockMvc.perform(post("/user")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(user)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("location",uri.toString()));
        } catch (Exception e) {
            Assert.fail("Exception thrown : " + e.getMessage());
        }
    }

    /**
     * Test "POST" for a user without non required element
     *
     * @see UserService#createUser(User)
     */
    @Test
    public void testPostUserOkWithoutGenderAndPhoneNumber() {
        URI uri = getURI(user.getUsername());
        user.setBirthdate(LocalDate.of(1920,01,01));
        when(service.createUser(user)).thenReturn(uri);
        try {
            this.mockMvc.perform(post("/user")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(user)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("location",uri.toString()));
        } catch (Exception e) {
            Assert.fail("Exception thrown : " + e.getMessage());
        }
    }

    /**
     * Test "POST" for a user without country
     *
     * @see UserService#createUser(User)
     */
    @Test
    public void testPostUserFailMissingFields() {
        URI uri = getURI(user.getUsername());
        when(service.createUser(user)).thenReturn(uri);
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
     * Test "POST" for a non existing user but with a bad date
     *
     * @see UserService#createUser(User)
     */
    @Test
    public void testPostUserFailInvalidDate() {
        URI uri = getURI(user.getUsername());
        when(service.createUser(user)).thenReturn(uri);
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
     * @see UserService#createUser(User)
     */
    @Test
    public void testPostUserFailExistingUser() {
        URI uri = getURI(user.getUsername());
        when(service.createUser(user)).thenThrow(new InvalidRequestStateException("The user already exists"));
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