package molek_school.security;

import lombok.extern.slf4j.Slf4j;
import molek_school.exceptions.ResourceNotFoundException;
import molek_school.model.User;
import molek_school.model.UserPrincipal;
import molek_school.repository.UserRepository;
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
    public UserDetails loadUserByUsername(String username) throws ResourceNotFoundException {
        User user = userRepository.findUserByEmail(username);
        if (user == null) {
            log.info("User not found");
            throw new ResourceNotFoundException("User not found");
        }

        return new UserPrincipal(user);
    }

}
