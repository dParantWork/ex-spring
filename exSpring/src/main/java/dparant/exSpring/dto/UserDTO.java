package dparant.exSpring.dto;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.ConstraintValidatorContext;
import java.sql.Date;
import java.time.LocalDate;

/**
 * DTO for a User allowing to query the table "usr" inside the database
 *
 * @see dparant.exSpring.model.User
 *
 * @author dylan
 */
@Entity
@Table(name = "usr")
public class UserDTO {
    /**
     * Primary key, username for a User
     */
    @Id
    @Column(name = "username")
    @Getter @Setter private String username;
    /**
     * Birthdate of a User, he must have 18 years old
     *
     * @see dparant.exSpring.model.validator.AgeValidator#isValid(LocalDate, ConstraintValidatorContext)
     */
    @Column(name = "birthdate")
    @Getter @Setter private Date birthdate;

    /**
     * Country of a User
     */
    @Column(name = "country")
    @Getter @Setter private String country;
    /**
     * Phone number of a User
     */
    @Column(name = "phone_number")
    @Getter @Setter private String phoneNumber;

    /**
     * Gender of a User
     */
    @Column(name = "gender")
    @Getter @Setter private String gender;


}
