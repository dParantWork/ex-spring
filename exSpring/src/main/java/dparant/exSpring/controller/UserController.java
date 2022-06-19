package dparant.exSpring.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import dparant.exSpring.controller.error.ControllerAdvisor;
import dparant.exSpring.model.request.UserRequest;
import dparant.exSpring.model.response.UserResponse;
import dparant.exSpring.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

/**
 * Controller of the API defining HTTP routes for a UserRequest
 *
 * @author dylan
 */
@RestController
@AllArgsConstructor
public class UserController {

    /**
     * UserRequest service managing operation for a UserRequest
     */
    UserService userService;

    /**
     * Gets a UserRequest
     *
     * @param username The username of a UserRequest
     * @return ResponseEntity Response with the status 200 and the User correponding if it's a success, Response with status 404 if it's a failure
     * @throws NoSuchElementException exception thrown if the user searched is not found
     * @see UserService#getUser(String)
     * @see ControllerAdvisor#handleNoSuchElementException(NoSuchElementException, WebRequest)
     */
    @GetMapping(path = "/user/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "retrieves a UserRequest", response = ResponseEntity.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "The data is returned"), @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public ResponseEntity<UserResponse> getUser(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);
    }

    /**
     * Creates a UserRequest
     *
     * @param userRequest The UserRequest who needs to be created
     * @return ResponseEntity Response with a status 201 et the URI for getting the user if it's a success, A response with the status 400 if it's a failure
     * @throws DateTimeParseException          exception thrown if the birthdate has an invalid format
     * @throws InvalidFormatException          exception thrown if the gender has an invalid format
     * @throws MethodArgumentNotValidException exception thrown if any fail occurs during validations field
     * @see UserService#createUser(UserRequest)
     * @see ControllerAdvisor#handleDateTimeParseException(DateTimeParseException, WebRequest)
     * @see ControllerAdvisor#handleInvalidFormatException(InvalidFormatException, WebRequest)
     * @see ControllerAdvisor#handleMethodArgumentNotValidException(MethodArgumentNotValidException, WebRequest)
     */

    @PostMapping(path = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "creates a User", response = ResponseEntity.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "The data is created, the link is returned in the location header"), @ApiResponse(code = 400, message = "Some input data are not valid")})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserRequest> postUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse result = userService.createUser(userRequest);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(result.getUsername())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

}
