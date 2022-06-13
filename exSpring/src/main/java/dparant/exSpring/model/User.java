package dparant.exSpring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import dparant.exSpring.model.validator.AgeConstraint;
import dparant.exSpring.model.validator.CountryConstraint;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Model class representing a User
 *
 * @author dylan
 */
@Validated
@Data
public class User {

    /**
     * Username for a User, this field shouldn't be null or Blank and must be unique for every User
     */
    @NotNull(message = "The username is required")
    @NotBlank(message = "The username should not be blank")
    @ApiModelProperty(notes = "User's username", example = "samplename123", required = true)
    private String username;

    /**
     * birthdate for a User, this field shouldn't be null and must respect the pattern "dd-MM-yyyy'
     */
    @JsonFormat(pattern="dd-MM-yyyy")
    @NotNull(message = "The birthdate is required")
    @ApiModelProperty(notes = "User's birthdate", example = "dd-MM-yyyy", required = true, dataType = "java.util.LocalDate")
    @AgeConstraint
    private LocalDate birthdate;

    /**
     * Country for a User, this field shouldn't be null or Blank, and he must be french
     */
    @NotNull(message = "The country is required")
    @NotBlank(message = "The country should not be blank")
    @ApiModelProperty(notes = "User's country", example = "France", required = true)
    @CountryConstraint
    private String country;

    /**
     * Phone number for a User, this field isn't required but if it's given, it must have 10 character or less
     */
    @Size(max=10)
    @ApiModelProperty(notes = "User's phone number", example = "0132125664", required = false)
    private String phoneNumber;

    /**
     * Gender for a User, this field is not required but if it's given, it must have a valid value
     *
     * @see Gender
     */
    @ApiModelProperty(notes = "User's gender", example = "Female", required = false)
    private Gender gender;

}
