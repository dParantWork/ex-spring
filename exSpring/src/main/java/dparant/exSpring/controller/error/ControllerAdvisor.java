package dparant.exSpring.controller.error;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.sun.jdi.request.InvalidRequestStateException;
import dparant.exSpring.controller.UserController;
import dparant.exSpring.model.Gender;
import dparant.exSpring.model.request.UserRequest;
import dparant.exSpring.model.validator.AgeValidator;
import dparant.exSpring.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


/**
 * Error Handler for our project, Every response caught will be transformed into a ResponseEntity
 *
 * @author dylan
 */
@ControllerAdvice
public class ControllerAdvisor {
    /**
     * Date formatter for the response of the API
     */
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

    /**
     * Handler for exceptions of type DateTimeParseException, this error is thrown when a date has an unparsable value
     *
     * @param ex      Exception thrown during a method
     * @param request input of the request which thrown the exception
     * @return ResponseEntity Response with a status 400
     * @see UserRequest
     * @see UserController#postUser(UserRequest)
     */
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<Object> handleDateTimeParseException(DateTimeParseException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", formatter.format(LocalDateTime.now()));
        body.put("message", "Invalid date : " + ex.getParsedString());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler for exceptions of type InvalidFormatException, this error is thrown when the gender doesn't have
     * a value of the enum Gender (Male, Female)
     *
     * @param ex      Exception thrown during a method
     * @param request input of the request which thrown the exception
     * @return ResponseEntity Response with a status 400
     * @see Gender
     * @see UserController#postUser(UserRequest)
     */
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", formatter.format(LocalDateTime.now()));
        body.put("message", ex.getOriginalMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler for exceptions of type MethodArgumentNotValidException, this error is thrown when
     * the input data object is not valid
     *
     * @param ex      Exception thrown during a method
     * @param request input of the request which thrown the exception
     * @return ResponseEntity Response with status 400
     * @see UserController#postUser(UserRequest)
     * @see UserRequest
     * @see AgeValidator
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", formatter.format(LocalDateTime.now()));
        body.put("message", "Validation failed: " + ex.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", ")));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler for exceptions of type  MethodArgumentNotValidException, this error is thrown when an object isn't found in the database
     *
     * @param ex      Exception thrown during a method
     * @param request input of the request which thrown the exception
     * @return ResponseEntity : Response with a status 404
     * @see UserServiceImpl#getUser(String)
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", formatter.format(LocalDateTime.now()));
        body.put("message", "No data found");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }


    /**
     * Handler for exceptions of type  MethodArgumentNotValidException, this error is thrown when an object is found in the database
     *
     * @param ex      Exception thrown during a method
     * @param request input of the request which thrown the exception
     * @return ResponseEntity : Response with a status 400
     * @see UserServiceImpl#createUser(UserRequest)
     */
    @ExceptionHandler(InvalidRequestStateException.class)
    public ResponseEntity<Object> handleInvalidRequestStateException(InvalidRequestStateException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", formatter.format(LocalDateTime.now()));
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}