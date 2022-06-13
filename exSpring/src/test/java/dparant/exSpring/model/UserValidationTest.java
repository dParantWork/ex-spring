package dparant.exSpring.model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
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
     *  test the creation of a user without the non required fields
     */
    @Test
    public void testUserCreationOKWithoutNonRequired() {
        User user = new User();
        user.setBirthdate(LocalDate.of(1900,10,10));
        user.setUsername("etst");
        user.setCountry("France");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertTrue(violations.isEmpty());
    }

    /**
     *  test the creation of a user with the non required fields
     */
    @Test
    public void testUserCreationOKWithNonRequired() {
        User user = new User();
        user.setBirthdate(LocalDate.of(1900,10,10));
        user.setUsername("etst");
        user.setCountry("France");
        user.setPhoneNumber("1212121212");
        user.setGender(Gender.Female);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertTrue(violations.isEmpty());
    }

    /**
     *  test the creation of a user without the username
     */
    @Test
    public void testUserCreationFailWithoutUsername() {
        User user = new User();
        user.setBirthdate(LocalDate.of(1900,10,10));
        user.setCountry("France");
        user.setPhoneNumber("1212121212");
        user.setGender(Gender.Female);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     *  test the creation of a user with a blank username
     */
    @Test
    public void testUserCreationFailWithUsernameEmpty() {
        User user = new User();
        user.setBirthdate(LocalDate.of(1900,10,10));
        user.setUsername("");
        user.setCountry("France");
        user.setPhoneNumber("1212121212");
        user.setGender(Gender.Female);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     *  test the creation of a user without the birthdate
     */
    @Test
    public void testUserCreationFailWithoutBirthdate() {
        User user = new User();
        user.setUsername("test");
        user.setCountry("France");
        user.setPhoneNumber("1212121212");
        user.setGender(Gender.Female);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     *  test the creation of a user without the birthdate less than 18 years old
     */
    @Test
    public void testUserCreationFailWithBirthdateLessThan18() {
        User user = new User();
        user.setBirthdate(LocalDate.now());
        user.setUsername("test");
        user.setCountry("France");
        user.setPhoneNumber("1212121212");
        user.setGender(Gender.Female);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     *  test the creation of a user without the country
     */
    @Test
    public void testUserCreationFailWithoutCountry() {
        User user = new User();
        user.setBirthdate(LocalDate.of(1900,10,10));
        user.setUsername("erce");
        user.setPhoneNumber("1212121212");
        user.setGender(Gender.Female);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertFalse(violations.isEmpty());
    }
    /**
     *  test the creation of a user with a blank country
     */
    @Test
    public void testUserCreationFailWithCountryEmpty() {
        User user = new User();
        user.setBirthdate(LocalDate.of(1900,10,10));
        user.setCountry("");
        user.setUsername("terztz");
        user.setPhoneNumber("1212121212");
        user.setGender(Gender.Female);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     *  test the creation of a user with a phone number longer than 10 characters
     */
    @Test
    public void testUserCreationFailWithPhoneNumberLongerThan10() {
        User user = new User();
        user.setBirthdate(LocalDate.of(1900,10,10));
        user.setCountry("France");
        user.setUsername("terztz");
        user.setPhoneNumber("121212121000002");
        user.setGender(Gender.Female);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertFalse(violations.isEmpty());
    }

}