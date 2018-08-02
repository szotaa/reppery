package pl.szotaa.repperybackend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szotaa.repperybackend.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
