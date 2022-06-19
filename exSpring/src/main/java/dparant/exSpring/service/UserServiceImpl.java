package dparant.exSpring.service;

import com.sun.jdi.request.InvalidRequestStateException;
import dparant.exSpring.dto.User;
import dparant.exSpring.mapper.UserMapper;
import dparant.exSpring.model.request.UserRequest;
import dparant.exSpring.model.response.UserResponse;
import dparant.exSpring.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * This class is used to manage any UserRequest operations
 *
 * @author dylan
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    /**
     * UserRequest repository allowing us to perform operation on the database
     */
    // injection field -> reflexion ..
    // injection constructor
    UserRepository userRepository;

    /**
     * Creates a userRequest
     *
     * @param userRequest The UserRequest who needs to be created
     * @return the created UserRequest
     * @throws InvalidRequestStateException exception thrown if the userRequest exists
     */
    @Override
    public UserResponse createUser(UserRequest userRequest) throws InvalidRequestStateException {
        User dto = UserMapper.INSTANCE.userRequestToUser(userRequest);
        if (userRepository.findById(dto.getUsername()).isPresent()) {
            throw new InvalidRequestStateException("The userRequest already exists");
        }
        User result = userRepository.save(dto);
        return UserMapper.INSTANCE.userToUserResponse(result);
    }

    /**
     * Gets a user
     *
     * @param username The username for the UserRequest
     * @return The founded UserRequest
     * @throws NoSuchElementException exception thrown if the user isn't found in the database
     */
    @Override
    public UserResponse getUser(String username) throws NoSuchElementException {
        Optional<User> userData = userRepository.findById(username);
        if (userData.isPresent()) {
            return UserMapper.INSTANCE.userToUserResponse(userData.get());
        } else {
            throw new NoSuchElementException();
        }
    }
}
