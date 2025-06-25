package molek_school.security;

import com.jumong.E.TMotors.exception.UserNotFoundException;
import com.jumong.E.TMotors.model.User;
import com.jumong.E.TMotors.model.UserPrincipal;
import com.jumong.E.TMotors.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findUserByEmail(username);
        if (user == null) {
            log.info("User not found");
            throw new UserNotFoundException("User not found");
        }

        return new UserPrincipal(user);
    }

}
