package dparant.exSpring.mapper;


import dparant.exSpring.dto.User;
import dparant.exSpring.model.request.UserRequest;
import dparant.exSpring.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper between classes UserRequest and User
 *
 * @author dylan
 * @see UserRequest
 * @see User
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Transform a UserRequest into a User, convert the enum type Gender in a String gender
     *
     * @param userRequest UserRequest who needs to be converted
     * @return User The converted object
     */
    @Mapping(target = "gender", source = "gender")
    User userRequestToUser(UserRequest userRequest);

    /**
     * Transform a User into a UserRequest, convert the String gender in a enum Gender
     *
     * @param userDTO User who needs to be converted
     * @return UserResponse The converted object
     */
    @Mapping(target = "gender", source = "gender")
    UserResponse userToUserResponse(User userDTO);
}

