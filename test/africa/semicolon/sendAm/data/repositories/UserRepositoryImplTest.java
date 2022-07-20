package africa.semicolon.sendAm.data.repositories;

import africa.semicolon.sendAm.data.models.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryImplTest {
    UserRepository userRepository = new UserRepositoryImpl();
    @Test
    void createUserTest() {
        User owner = new User("Ademiju Taiwo", "ademiju@email.com");
        User createdUser = userRepository.create(owner);
        assertEquals(createdUser.getFullName(), "Ademiju Taiwo");
        assertEquals(createdUser.getEmail(), ("ademiju@email.com"));
        assertEquals(1, userRepository.count());
    }

    @Test
    void findUserByEmailTest() {
        User firstOwner = new User("Ademiju Taiwo", "ademiju@email.com");
        User secondOwner = new User("Increase Lois", "increase@email.com");
        User thirdOwner = new User("Dami Sanni", "dami@email.com");
        UserRepository userRepository = new UserRepositoryImpl();
        userRepository.create(firstOwner);
        userRepository.create(secondOwner);
        userRepository.create(thirdOwner);

        User foundUser = userRepository.findByEmail("increase@email.com");
        User anotherUser = userRepository.findByEmail("ademiju@email.com");

        assertEquals(secondOwner, foundUser);
        assertEquals("increase@email.com", foundUser.getEmail());
        assertEquals(firstOwner, anotherUser);
        assertEquals("ademiju@email.com", anotherUser.getEmail());

    }

    @Test
    void deleteUserByEmailTest() {
       createThreeUsers();
        assertEquals(3, userRepository.count());
        userRepository.delete("dami@email.com");
        assertEquals(2, userRepository.count());
    }
    void createThreeUsers(){
        User firstOwner = new User("Ademiju Taiwo", "ademiju@email.com");
        User secondOwner = new User("Increase Lois", "increase@email.com");
        User thirdOwner = new User("Dami Sanni", "dami@email.com");
        userRepository.create(firstOwner);
        userRepository.create(secondOwner);
        userRepository.create(thirdOwner);
    }
    @Test
    void deleteByEmailWorksTest(){
        createThreeUsers();
         userRepository.delete("dami.email.com");
        User deletedUser = userRepository.findByEmail("dami.email.com");
        assertNull(deletedUser);

    }
@Test
    void deleteByUserTest(){
    User firstOwner = new User("Ademiju Taiwo", "ademiju@email.com");
    User secondOwner = new User("Increase Lois", "increase@email.com");
    User thirdOwner = new User("Dami Sanni", "dami@email.com");
    userRepository.create(firstOwner);
    userRepository.create(secondOwner);
    userRepository.create(thirdOwner);
    userRepository.delete(secondOwner);
    assertEquals(2, userRepository.count());
    User deletedUser = userRepository.findByEmail("dami.email.com");
    assertNull(deletedUser);
    }
    @Test
    void findAllUserTest(){
        createThreeUsers();
       List<User>  users = userRepository.findAll();
        assertEquals(3, users.size() );
    }

}