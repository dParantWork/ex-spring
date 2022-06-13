package dparant.exSpring.service;

import dparant.exSpring.model.User;

import java.net.URI;
import java.util.NoSuchElementException;

/**
 * Service interface managing any operation for a User
 */
public interface UserService {

    /**
     * Creates a user
     *
     * @param user The User who needs to be created
     *
     * @return the URI for getting the created user
     */
    public abstract URI createUser(User user);

    /**
     * Gets a user
     *
     * @param username The username for the User
     *
     * @throws NoSuchElementException
     *
     * @return The founded User
     */
    public abstract User getUser(String username) throws NoSuchElementException;
}
