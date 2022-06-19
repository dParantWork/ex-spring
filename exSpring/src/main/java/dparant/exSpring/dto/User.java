package dparant.exSpring.dto;


import dparant.exSpring.model.request.UserRequest;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * DTO for a UserRequest allowing to query the table "usr" inside the database
 *
 * @author dylan
 * @see UserRequest
 */
@Entity
@Table(name = "usr")
public class User {
    /**
     * Primary key, username for a UserRequest
     */
    @Id
    @Column(name = "username")
    @Getter
    @Setter
    private String username;
    /**
     * Birthdate of a UserRequest, he must be 18 years old
     */
    @Column(name = "birthdate")
    @Getter
    @Setter
    private Date birthdate;

    /**
     * Country of a UserRequest, he must be french
     */
    @Column(name = "country")
    @Getter
    @Setter
    private String country;
    /**
     * Phone number of a UserRequest
     */
    @Column(name = "phone_number")
    @Getter
    @Setter
    private String phoneNumber;

    /**
     * Gender of a UserRequest
     */
    @Column(name = "gender")
    @Getter
    @Setter
    private String gender;


}
