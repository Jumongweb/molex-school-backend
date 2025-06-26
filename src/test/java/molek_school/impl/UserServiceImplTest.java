package molek_school.impl;


import lombok.extern.slf4j.Slf4j;
import molek_school.dto.request.LoginRequest;
import molek_school.dto.request.RegisterRequest;
import molek_school.dto.response.LoginResponse;
import molek_school.dto.response.UserResponse;
import molek_school.exceptions.MolexException;
import molek_school.exceptions.ResourceNotFoundException;
import molek_school.model.User;
import molek_school.repository.UserRepository;
import molek_school.service.interfac.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    private User user;
    private RegisterRequest registerRequest;
    @Autowired
    private UserRepository userRepository;
    private Long firstUserId;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");
        registerRequest.setEmail("john.doe@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setConfirmPassword("password123");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("john.doe@example.com");
        loginRequest.setPassword("password123");

    }

    @Test
    void testRegisterUser_NullRequest_ThrowsException() {
        MolexException ex = assertThrows(MolexException.class, () -> userService.register(null));
        assertEquals("RegisterRequest is null", ex.getMessage());
    }

    @Test
    void testRegisterWhenPasswordDoNotMatch_ThrowsException() {
        registerRequest.setConfirmPassword("passwor");
        MolexException ex = assertThrows(MolexException.class, () -> userService.register(registerRequest));
    }

    @Test
    @Order(1)
    void testRegisterUserSuccessful(){
        UserResponse userResponse = userService.register(registerRequest);
        firstUserId = userResponse.getId();
        assertNotNull(userResponse);
        assertEquals(registerRequest.getFirstName(), userResponse.getFirstName());
        assertEquals(registerRequest.getLastName(), userResponse.getLastName());
        assertEquals(registerRequest.getEmail(), userResponse.getEmail());
        assertNotEquals(registerRequest.getPassword(), userResponse.getPassword());
    }

    @Test
    @Order(2)
    void findUserById(){
        UserResponse userResponse = userService.findUserById(firstUserId);
        assertNotNull(userResponse);
    }

    @Test
    void testFindUserWithNullId(){
        assertThrows(MolexException.class, () -> userService.findUserById(null));
    }

    @Test
    void testFindUserThatDoesNotExist(){
        firstUserId = 10000000L;
        assertThrows(MolexException.class, () -> userService.findUserById(firstUserId));
    }

    @Test
    @Order(3)
    void loginUser(){
        LoginResponse loginResponse = userService.login(loginRequest);
        assertThat(loginResponse).isNotNull();
        assertThat(loginResponse.getMessage()).isEqualTo("Login successful");
    }

    @Test
    void loginUserWithWrongPassword(){
        loginRequest.setPassword("wrongPassword");
        assertThrows(MolexException.class, ()->userService.login(loginRequest));
    }

    @Test
    void loginUserWithEmailNotInTheDatabase(){
        loginRequest.setEmail("wrongEmail");
        assertThrows(ResourceNotFoundException.class, ()->userService.login(loginRequest));
    }

    @AfterAll
    void tearDown() {

    }

}
