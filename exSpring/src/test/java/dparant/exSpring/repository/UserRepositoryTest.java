package dparant.exSpring.repository;

import dparant.exSpring.dto.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Test class for the UserRequest Repository
 *
 * @author dylan
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    UserRepository repository;
    @Autowired
    private TestEntityManager entityManager;

    /**
     * test to retrieve a user
     */
    @Test
    public void testGetUserOK() {
        User user = new User();
        user.setUsername("Joe323");
        user.setBirthdate(Date.valueOf(LocalDate.now()));
        user.setCountry("France");
        entityManager.persist(user);
        User foundedUser = repository.findById(user.getUsername()).get();

        Assert.assertTrue(user.getUsername().equals(foundedUser.getUsername()));
        Assert.assertTrue(user.getBirthdate().equals(foundedUser.getBirthdate()));
        Assert.assertTrue(user.getCountry().equals(foundedUser.getCountry()));
    }

    /**
     * test to create a user
     */
    @Test
    public void testCreateUserOK() {
        User user = new User();
        user.setUsername("Joe323");
        user.setBirthdate(Date.valueOf(LocalDate.now()));
        user.setCountry("France");
        repository.save(user);
        Assert.assertNotNull(entityManager.getId(user));
    }
}
