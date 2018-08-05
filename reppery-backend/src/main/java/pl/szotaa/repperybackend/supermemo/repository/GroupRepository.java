package pl.szotaa.repperybackend.supermemo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.szotaa.repperybackend.supermemo.domain.Group;
import pl.szotaa.repperybackend.user.domain.User;

public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> getAllByOwner(User user);
}
