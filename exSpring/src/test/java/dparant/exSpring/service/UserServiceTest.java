package dparant.exSpring.service;

import com.sun.jdi.request.InvalidRequestStateException;
import dparant.exSpring.dto.User;
import dparant.exSpring.model.Gender;
import dparant.exSpring.model.request.UserRequest;
import dparant.exSpring.model.response.UserResponse;
import dparant.exSpring.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * Test class for the user service
 *
 * @author dylan
 */
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {
    private MockMvc mockMvc;

    private UserRequest userRequest;

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service = new UserServiceImpl(repository);

    /**
     * inits the mockMvc
     */
    @BeforeAll
    public void initMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(service).build();
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
     * method mapping a userRequest to a userResponse
     *
     * @param user
     * @return
     */
    private User getUserResponse(UserRequest user) {
        User userResponse = new User();
        userResponse.setUsername(user.getUsername());
        userResponse.setBirthdate(Date.valueOf(user.getBirthdate()));
        userResponse.setCountry(user.getCountry());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setGender(user.getGender().name());
        return userResponse;
    }

    /**
     * test for the getting method
     */
    @Test
    public void testGetUserOk() {
        userRequest.setGender(Gender.Male);

        when(repository.findById(userRequest.getUsername())).thenReturn(Optional.of(getUserResponse(userRequest)));
        try {
            UserResponse user = service.getUser(userRequest.getUsername());
            Assert.assertTrue(userRequest.getUsername().equals(user.getUsername()));
            Assert.assertTrue(userRequest.getBirthdate().equals(user.getBirthdate()));
        } catch (Exception e) {
            Assert.fail("Exception thrown :" + e);
        }
    }

    /**
     * test for the getting method fail
     */
    @Test
    public void testGetUserFail() {
        userRequest.setGender(Gender.Male);

        when(repository.findById(userRequest.getUsername())).thenReturn(Optional.empty());
        try {
            UserResponse user = service.getUser(userRequest.getUsername());
            Assert.fail("Any user should be get");
        } catch (NoSuchElementException e) {
            Assert.assertTrue(e instanceof NoSuchElementException);
        }
    }

    /**
     * test for the creating method
     */
    @Test
    public void testCreateUserOk() {
        userRequest.setGender(Gender.Male);
        when(repository.findById(userRequest.getUsername())).thenReturn(Optional.empty());
        when(repository.save(Mockito.any(User.class))).thenReturn(getUserResponse(userRequest));
        try {
            UserResponse user = service.createUser(userRequest);
            Assert.assertTrue(userRequest.getUsername().equals(user.getUsername()));
            Assert.assertTrue(userRequest.getBirthdate().equals(user.getBirthdate()));
        } catch (NoSuchElementException e) {
            Assert.fail("Exception thrown : " + e);
        }
    }

    /**
     * test for the creating method fail
     */
    @Test
    public void testCreateUserFail() {
        userRequest.setGender(Gender.Male);

        when(repository.findById(userRequest.getUsername())).thenReturn(Optional.of(getUserResponse(userRequest)));
        try {
            UserResponse user = service.createUser(userRequest);
            Assert.fail("Any user should be created");
        } catch (InvalidRequestStateException e) {
            Assert.assertTrue(e instanceof InvalidRequestStateException);
        }
    }
}
