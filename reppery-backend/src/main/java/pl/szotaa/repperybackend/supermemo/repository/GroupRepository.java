package pl.szotaa.repperybackend.supermemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szotaa.repperybackend.supermemo.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
