package molek_school.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import molek_school.dto.request.LoginRequest;
import molek_school.dto.request.RegisterRequest;
import molek_school.exceptions.MolexException;
import molek_school.service.interfac.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/auth/")
@Slf4j
public class AuthController {

    private final UserService userService;


    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try{
            return new ResponseEntity<>(userService.register(registerRequest), HttpStatus.CREATED);
        } catch (MolexException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try{
            log.info("Received login request: {}", loginRequest);
            return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
        } catch (MolexException exception){
            log.error("Login failed: {}", exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected error during login", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error");
        }
    }

}
