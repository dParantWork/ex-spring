package dparant.exSpring.mapper;


import dparant.exSpring.model.User;
import dparant.exSpring.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper between classes User and UserDTO
 *
 * @see User
 * @see UserDTO
 *
 * @author dylan
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    /**
     * Transform a User into a UserDTO, convert the enum type Gender in a String gender
     *
     * @param user User who needs to be converted
     *
     * @return UserDTO The converted object
     */
    @Mapping(target = "gender", source = "gender")
    UserDTO userToUserDTO(User user);

    /**
     * Transform a UserDTO into a User, convert the String gender in a enum Gender
     *
     * @param userDTO UserDTO who needs to be converted
     *
     * @return User The converted object
     */
    @Mapping(target = "gender", source = "gender")
    User userDTOToUser(UserDTO userDTO);
}

