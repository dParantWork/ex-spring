package dparant.exSpring.mapper;

import dparant.exSpring.dto.User;
import dparant.exSpring.model.Gender;
import dparant.exSpring.model.request.UserRequest;
import dparant.exSpring.model.response.UserResponse;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Test class for the user mapper
 *
 * @author dylan
 */
public class UserMapperTest {

    /**
     * test method to map a userDTO to user
     */
    @Test
    public void testUserDtoToUser() {
        User userDTO = new User();
        userDTO.setUsername("test");
        userDTO.setBirthdate(Date.valueOf(LocalDate.now()));
        userDTO.setCountry("France");
        userDTO.setPhoneNumber("0303030303");
        userDTO.setGender("Male");

        UserResponse userResponse = UserMapper.INSTANCE.userToUserResponse(userDTO);

        Assert.assertTrue(userResponse.getUsername().equals(userDTO.getUsername()));
        Assert.assertTrue(userResponse.getBirthdate().equals(userDTO.getBirthdate().toLocalDate()));
        Assert.assertTrue(userResponse.getCountry().equals(userDTO.getCountry()));
        Assert.assertTrue(userResponse.getPhoneNumber().equals(userDTO.getPhoneNumber()));
        Assert.assertTrue(userResponse.getGender().name().equals(userDTO.getGender()));
    }

    /**
     * test method to map a user to userDTO
     */
    @Test
    public void testUserToDto() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("test");
        userRequest.setBirthdate(LocalDate.now());
        userRequest.setCountry("France");
        userRequest.setPhoneNumber("0303030303");
        userRequest.setGender(Gender.Female);

        User userDto = UserMapper.INSTANCE.userRequestToUser(userRequest);

        Assert.assertTrue(userDto.getUsername().equals(userRequest.getUsername()));
        Assert.assertTrue(userDto.getBirthdate().toLocalDate().equals(userRequest.getBirthdate()));
        Assert.assertTrue(userDto.getCountry().equals(userRequest.getCountry()));
        Assert.assertTrue(userDto.getPhoneNumber().equals(userRequest.getPhoneNumber()));
        Assert.assertTrue(userDto.getGender().equals(userRequest.getGender().name()));
    }
}