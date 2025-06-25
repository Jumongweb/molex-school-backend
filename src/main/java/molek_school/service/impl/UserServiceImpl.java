package molek_school.service.impl;

import com.jumong.E.TMotors.dto.request.LoginRequest;
import com.jumong.E.TMotors.dto.request.RegisterRequest;
import com.jumong.E.TMotors.dto.response.LoginResponse;
import com.jumong.E.TMotors.dto.response.UserResponse;
import com.jumong.E.TMotors.exception.CarException;
import com.jumong.E.TMotors.mapper.UserMapper;
import com.jumong.E.TMotors.model.User;
import com.jumong.E.TMotors.repository.UserRepository;
import com.jumong.E.TMotors.service.interfac.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public UserResponse register(RegisterRequest registerRequest){
        validate(registerRequest);
        User user = userMapper.toUserEntity(registerRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public UserResponse findUserById(Long userId) {
        if (userId == null) {
            throw new CarException("User ID cannot be null");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CarException("User Not Found"));
        return userMapper.toUserResponse(user);
    }

    private void validate(RegisterRequest registerRequest){
        if (registerRequest == null){
            log.info("RegisterRequest is null");
            throw new CarException("RegisterRequest is null");
        }
        if (registerRequest.getPassword() == null){
            log.info("RegisterRequest password is null");
            throw new CarException("RegisterRequest password is null");
        }

        if (registerRequest.getConfirmPassword() == null){
            log.info("RegisterRequest confirmPassword is null");
            throw new CarException("RegisterRequest confirmPassword is null");
        }
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())){
            throw new CarException("Passwords do not match");
        }
        User user = userRepository.findUserByEmail(registerRequest.getEmail());
        if (user != null){
            throw new CarException("User already exists");
        }
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        validateLoginRequest(loginRequest);

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        String token = "";
        if (authentication.isAuthenticated()){
            token = jwtService.generateToken(email);
        }

        User user = userRepository.findUserByEmail(email);
        log.info("------> User from login ---------> {}", user);
        if (user == null){
            log.info("User not found");
            throw new CarException("User Not Found");
        }
        if (!passwordEncoder.matches(password, user.getPassword())){
            log.info("Invalid email or password");
            throw new CarException("Invalid email or password");
        }
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage("Login successful");
        loginResponse.setToken(token);
        log.info("Login successful, token: {}", loginResponse.getToken());
        return loginResponse;
    }

    @Override
    public User findByEmail(String actorEmail) {
        return userRepository.findUserByEmail(actorEmail);
    }

    private void validateLoginRequest(LoginRequest loginRequest) {
        if (loginRequest == null){
            log.info("LoginRequest is null");
            throw new CarException("LoginRequest cannot be empty");
        }
        if (loginRequest.getEmail() == null){
            log.info("LoginRequest email is null");
            throw new CarException("Email cannot be empty");
        }
        if (loginRequest.getPassword() == null){
            log.info("LoginRequest password is null");
            throw new CarException("Password cannot be empty");
        }


    }

}
