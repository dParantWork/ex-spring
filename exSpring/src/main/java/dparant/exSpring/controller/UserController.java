package dparant.exSpring.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import dparant.exSpring.model.User;
import dparant.exSpring.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

/**
 * Controller of the API defining HTTP routes for a User
 *
 * @author dylan
 */
@RestController
public class UserController {

    /**
     * User service managing operation for a User
     */
    @Autowired
    UserService userService;

    /**
     * Gets a User
     *
     * @see dparant.exSpring.service.UserServiceImpl#getUser(String)
     * @see dparant.exSpring.controller.error.ControllerAdvisor#handleNoSuchElementException(NoSuchElementException, WebRequest)
     *
     * @param username The username of a User
     *
     * @throws NoSuchElementException
     *
     * @return ResponseEntity Response with the status 200 and the User correponding if it's a success, Response with status 404 if it's a failure
     */
    @GetMapping(path = "/user/{username}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "retrieves a User", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The data is returned"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    public ResponseEntity<User> getUser(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);
    }

    /**
     * Creates a User
     *
     * @see dparant.exSpring.service.UserServiceImpl#createUser(User)
     * @see dparant.exSpring.controller.error.ControllerAdvisor#handleDateTimeParseException(DateTimeParseException, WebRequest)
     * @see dparant.exSpring.controller.error.ControllerAdvisor#handleInvalidFormatException(InvalidFormatException, WebRequest)
     * @see dparant.exSpring.controller.error.ControllerAdvisor#handleMethodArgumentNotValidException(MethodArgumentNotValidException, WebRequest)
     *
     * @param user The User who needs to be created
     *
     * @throws DateTimeParseException
     * @throws InvalidFormatException
     * @throws MethodArgumentNotValidException
     *
     * @return ResponseEntity Response with a status 201 et the URI for getting the user if it's a success, A response with the status 400 if it's a failure
     */

    @PostMapping(path = "/user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "creates a User", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The data is created, the link is returned in the location header"),
            @ApiResponse(code = 400, message = "Some input data are not valid")
    }
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> postUser(@Valid @RequestBody User user) {
            return ResponseEntity.created(userService.createUser(user)).build();
    }

}
