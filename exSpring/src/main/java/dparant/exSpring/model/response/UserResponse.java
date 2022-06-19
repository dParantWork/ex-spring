package dparant.exSpring.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import dparant.exSpring.model.Gender;
import dparant.exSpring.model.validator.AgeConstraint;
import dparant.exSpring.model.validator.AgeValidator;
import dparant.exSpring.model.validator.CountryValidator;
import dparant.exSpring.model.validator.CountryConstraint;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * Model class representing a UserRequest
 *
 * @author dylan
 */
@Data
//Request -> Instance
public class UserResponse {

    /**
     * Username for a UserRequest, this field shouldn't be null or Blank and must be unique for every UserRequest
     */
    @ApiModelProperty(notes = "UserRequest's username", example = "samplename123", required = true)
    private String username;

    /**
     * birthdate for a UserRequest, this field shouldn't be null and must respect the pattern "dd-MM-yyyy'
     *
     * @see AgeValidator
     */
    @JsonFormat(pattern = "dd-MM-yyyy")
    @ApiModelProperty(notes = "UserRequest's birthdate", example = "dd-MM-yyyy", required = true, dataType = "java.util.LocalDate")
    @AgeConstraint
    private LocalDate birthdate;

    /**
     * Country for a UserRequest, this field shouldn't be null or Blank, and he must be french
     *
     * @see CountryValidator
     */
    @ApiModelProperty(notes = "UserRequest's country", example = "France", required = true)
    @CountryConstraint
    private String country;

    /**
     * Phone number for a UserRequest, this field isn't required but if it's given, it must have 10 character or less
     */
    @ApiModelProperty(notes = "UserRequest's phone number", example = "0132125664", required = false)
    private String phoneNumber;

    /**
     * Gender for a UserRequest, this field is not required but if it's given, it must have a valid value
     *
     * @see Gender
     */
    @ApiModelProperty(notes = "UserRequest's gender", example = "Female", required = false)
    private Gender gender;

}
