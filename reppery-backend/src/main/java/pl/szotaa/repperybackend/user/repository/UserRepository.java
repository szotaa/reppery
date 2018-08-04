package pl.szotaa.repperybackend.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.szotaa.repperybackend.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findUserByEmailActivationToken(String token);
}
