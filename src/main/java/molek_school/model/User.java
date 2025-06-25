package molek_school.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "FirstName cannot be empty")
    private String firstName;
    @NotNull(message = "LastName cannot be empty")
    private String lastName;
    @NotNull(message = "Email cannot be empty")
    @Email
    private String email;
    @NotNull(message = "Password cannot be empty")
    private String password;

}
