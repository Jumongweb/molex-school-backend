package molek_school.repository;


import molek_school.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);

}
