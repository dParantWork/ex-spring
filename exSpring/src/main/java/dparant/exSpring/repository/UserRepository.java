package dparant.exSpring.repository;

import dparant.exSpring.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for a UserRequest allowing us to perform operation on the database
 *
 * @author dylan
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
