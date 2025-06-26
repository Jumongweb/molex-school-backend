package molek_school.service.interfac;


import molek_school.dto.request.LoginRequest;
import molek_school.dto.request.RegisterRequest;
import molek_school.dto.response.LoginResponse;
import molek_school.dto.response.UserResponse;
import molek_school.model.User;

public interface UserService {

    UserResponse register(RegisterRequest registerRequest);

    UserResponse findUserById(Long firstUserId);

    LoginResponse login(LoginRequest loginRequest);

    User findByEmail(String actorEmail);
}
