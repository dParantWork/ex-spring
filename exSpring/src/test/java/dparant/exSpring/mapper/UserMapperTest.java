package dparant.exSpring.mapper;

import dparant.exSpring.dto.UserDTO;
import dparant.exSpring.model.Gender;
import dparant.exSpring.model.User;
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
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("test");
        userDTO.setBirthdate(Date.valueOf(LocalDate.now()));
        userDTO.setCountry("France");
        userDTO.setPhoneNumber("0303030303");
        userDTO.setGender("Male");

        User user = UserMapper.INSTANCE.userDTOToUser(userDTO);

        Assert.assertTrue(user.getUsername().equals(userDTO.getUsername()));
        Assert.assertTrue(user.getBirthdate().equals(userDTO.getBirthdate().toLocalDate()));
        Assert.assertTrue(user.getCountry().equals(userDTO.getCountry()));
        Assert.assertTrue(user.getPhoneNumber().equals(userDTO.getPhoneNumber()));
        Assert.assertTrue(user.getGender().name().equals(userDTO.getGender()));
    }

    /**
     * test method to map a user to userDTO
     */
    @Test
    public void testUserToDto() {
        User user = new User();
        user.setUsername("test");
        user.setBirthdate(LocalDate.now());
        user.setCountry("France");
        user.setPhoneNumber("0303030303");
        user.setGender(Gender.Female);

        UserDTO userDto = UserMapper.INSTANCE.userToUserDTO(user);

        Assert.assertTrue(userDto.getUsername().equals(user.getUsername()));
        Assert.assertTrue(userDto.getBirthdate().toLocalDate().equals(user.getBirthdate()));
        Assert.assertTrue(userDto.getCountry().equals(user.getCountry()));
        Assert.assertTrue(userDto.getPhoneNumber().equals(user.getPhoneNumber()));
        Assert.assertTrue(userDto.getGender().equals(user.getGender().name()));
    }
}