package dparant.exSpring.service;

import com.sun.jdi.request.InvalidRequestStateException;
import dparant.exSpring.mapper.UserMapper;
import dparant.exSpring.model.User;
import dparant.exSpring.dto.UserDTO;
import dparant.exSpring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Implementation of the UserService interface, used to manage any User operations
 *
 * @author dylan
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * User repository allowing us to perform operation on the database
     */
    @Autowired
    UserRepository userRepository;

    /**
     * Creates a user
     *
     * @param user The User who needs to be created
     *
     * @return the URI for getting the created user
     */
    @Override
    public URI createUser(User user) throws InvalidRequestStateException {
        UserDTO dto = UserMapper.INSTANCE.userToUserDTO(user);
        if(userRepository.findById(dto.getUsername()).isPresent()) {
            throw new InvalidRequestStateException("The user already exists");
        }
        UserDTO result = userRepository
                .save(dto);
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(result.getUsername())
                .toUri();
    }

    /**
     * Gets a user
     *
     * @param username The username for the User
     *
     * @throws NoSuchElementException
     *
     * @return The founded User
     */
    @Override
    public User getUser(String username) throws NoSuchElementException {
        Optional<UserDTO> userData = userRepository.findById(username);
        if (userData.isPresent()) {
            return UserMapper.INSTANCE.userDTOToUser(userData.get());
        } else {
            throw new NoSuchElementException();
        }
    }
}
