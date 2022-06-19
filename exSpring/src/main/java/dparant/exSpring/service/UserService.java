package dparant.exSpring.service;

import com.sun.jdi.request.InvalidRequestStateException;
import dparant.exSpring.model.request.UserRequest;
import dparant.exSpring.model.response.UserResponse;

import java.util.NoSuchElementException;

/**
 * Service interface managing any operation for a UserRequest
 */
public interface UserService {

    /**
     * Creates a userRequest
     *
     * @param userRequest The UserRequest who needs to be created
     * @return the created User
     * @throws InvalidRequestStateException exception thrown if a user is found
     */
    UserResponse createUser(UserRequest userRequest) throws InvalidRequestStateException;

    /**
     * Gets a user
     *
     * @param username The username for the UserRequest
     * @return The founded User
     * @throws NoSuchElementException exception thrown if a user is not found
     */
    UserResponse getUser(String username) throws NoSuchElementException;
}
