package dparant.exSpring.repository;

import dparant.exSpring.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for a User allowing us to perform operation on the database
 *
 * @author dylan
 */
public interface UserRepository extends JpaRepository <UserDTO, String> {
}
