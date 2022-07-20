package africa.semicolon.sendAm.services;

import africa.semicolon.sendAm.dtos.requests.RegisterUserRequest;
import africa.semicolon.sendAm.dtos.responses.FindUserResponse;
import africa.semicolon.sendAm.dtos.responses.RegisterUserResponse;
import africa.semicolon.sendAm.exceptions.RegisterFailureException;
import africa.semicolon.sendAm.exceptions.SendAmAppException;
import africa.semicolon.sendAm.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    private UserService userService;

    @BeforeEach
    void testSetup() {
        userService = new UserServiceImpl();
    }

    @Test
    void afterRegister_repositoryContainsOneElement() {
        RegisterUserRequest newForm = createRegisterForm();
        userService.register(newForm);
//    userService.register(registerForm);
        assertEquals(1, userService.getRepository().count());
    }

    private RegisterUserRequest createRegisterForm() {
        RegisterUserRequest registerForm = new RegisterUserRequest();
        registerForm.setFirstName("Increase");
        registerForm.setLastName("Uwadiale");
        registerForm.setEmailAddress("sugarlove@email.com");
        registerForm.setAddress("home sweet home");
        registerForm.setPhoneNumber("69");
        return registerForm;
    }

    @Test
    public void duplicateEmail_throwExceptionTest() {
        RegisterUserRequest newForm = createRegisterForm();
        userService.register(newForm);
        assertThrows(SendAmAppException.class, () -> userService.register(newForm));
        assertThrows(RegisterFailureException.class, () -> userService.register(newForm));

    }

    @Test
    public void duplicateEmailWithDifferentCase_throwExceptionTest() {
        RegisterUserRequest newForm = createRegisterForm();
        userService.register(newForm);
        newForm.setEmailAddress("sugarLove@email.com");
        assertThrows(SendAmAppException.class, () -> userService.register(newForm));
        assertThrows(RegisterFailureException.class, () -> userService.register(newForm));
    }
    @Test
    public void registrationReturnsCorrectResponseTest(){
        RegisterUserRequest newForm = createRegisterForm();
        RegisterUserResponse response = userService.register(newForm);
        assertEquals("Increase Uwadiale",response.getFullName());
        assertEquals("sugarlove@email.com", response.getEmail());
    }
    @Test
    public void findRegisteredUserByEmailTest(){
        RegisterUserRequest newForm = createRegisterForm();
        userService.register(newForm);

        FindUserResponse response = userService.findUserByEmail(newForm.getEmailAddress().toLowerCase());

        assertEquals("Increase Uwadiale",response.getFullName());
        assertEquals("sugarlove@email.com", response.getUserEmail());
    }

    @Test
    public void findUnRegisteredUserByEmail_throwsExceptionTest(){
        RegisterUserRequest newForm = createRegisterForm();
        userService.register(newForm);

        assertThrows(UserNotFoundException.class,()->userService.findUserByEmail("miju@email.com"));
    }
    @Test
    public void findUserByEmailIsNotCaseSensitiveTest(){
        RegisterUserRequest newForm = createRegisterForm();
        userService.register(newForm);

        FindUserResponse response = userService.findUserByEmail("sugarLOVE@email.com");
        assertEquals("Increase Uwadiale", response.getFullName());
        assertEquals("sugarlove@email.com", response.getUserEmail());
    }
}
