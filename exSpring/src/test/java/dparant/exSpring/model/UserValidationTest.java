package dparant.exSpring.model;


import dparant.exSpring.model.request.UserRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

/**
 * Test class for the user's validator
 *
 * @author dylan
 */
public class UserValidationTest {

    private Validator validator;

    /**
     * set up the validator
     */
    @Before
    public void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * test the creation of a user without the non required fields
     */
    @Test
    public void testUserCreationOKWithoutNonRequired() {
        UserRequest userRequest = new UserRequest();
        userRequest.setBirthdate(LocalDate.of(1900, 10, 10));
        userRequest.setUsername("etst");
        userRequest.setCountry("France");
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        Assert.assertTrue(violations.isEmpty());
    }

    /**
     * test the creation of a user with the non required fields
     */
    @Test
    public void testUserCreationOKWithNonRequired() {
        UserRequest userRequest = new UserRequest();
        userRequest.setBirthdate(LocalDate.of(1900, 10, 10));
        userRequest.setUsername("etst");
        userRequest.setCountry("France");
        userRequest.setPhoneNumber("1212121212");
        userRequest.setGender(Gender.Female);
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        Assert.assertTrue(violations.isEmpty());
    }

    /**
     * test the creation of a user without the username
     */
    @Test
    public void testUserCreationFailWithoutUsername() {
        UserRequest userRequest = new UserRequest();
        userRequest.setBirthdate(LocalDate.of(1900, 10, 10));
        userRequest.setCountry("France");
        userRequest.setPhoneNumber("1212121212");
        userRequest.setGender(Gender.Female);
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * test the creation of a user with a blank username
     */
    @Test
    public void testUserCreationFailWithUsernameEmpty() {
        UserRequest userRequest = new UserRequest();
        userRequest.setBirthdate(LocalDate.of(1900, 10, 10));
        userRequest.setUsername("");
        userRequest.setCountry("France");
        userRequest.setPhoneNumber("1212121212");
        userRequest.setGender(Gender.Female);
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * test the creation of a user without the birthdate
     */
    @Test
    public void testUserCreationFailWithoutBirthdate() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("test");
        userRequest.setCountry("France");
        userRequest.setPhoneNumber("1212121212");
        userRequest.setGender(Gender.Female);
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * test the creation of a user without the birthdate less than 18 years old
     */
    @Test
    public void testUserCreationFailWithBirthdateLessThan18() {
        UserRequest userRequest = new UserRequest();
        userRequest.setBirthdate(LocalDate.now());
        userRequest.setUsername("test");
        userRequest.setCountry("France");
        userRequest.setPhoneNumber("1212121212");
        userRequest.setGender(Gender.Female);
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * test the creation of a user without the country
     */
    @Test
    public void testUserCreationFailWithoutCountry() {
        UserRequest userRequest = new UserRequest();
        userRequest.setBirthdate(LocalDate.of(1900, 10, 10));
        userRequest.setUsername("erce");
        userRequest.setPhoneNumber("1212121212");
        userRequest.setGender(Gender.Female);
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * test the creation of a user with a blank country
     */
    @Test
    public void testUserCreationFailWithCountryEmpty() {
        UserRequest userRequest = new UserRequest();
        userRequest.setBirthdate(LocalDate.of(1900, 10, 10));
        userRequest.setCountry("");
        userRequest.setUsername("terztz");
        userRequest.setPhoneNumber("1212121212");
        userRequest.setGender(Gender.Female);
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * test the creation of a user with a phone number longer than 10 characters
     */
    @Test
    public void testUserCreationFailWithPhoneNumberLongerThan10() {
        UserRequest userRequest = new UserRequest();
        userRequest.setBirthdate(LocalDate.of(1900, 10, 10));
        userRequest.setCountry("France");
        userRequest.setUsername("terztz");
        userRequest.setPhoneNumber("121212121000002");
        userRequest.setGender(Gender.Female);
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        Assert.assertFalse(violations.isEmpty());
    }

}