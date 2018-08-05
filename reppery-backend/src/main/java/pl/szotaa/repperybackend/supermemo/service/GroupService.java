package pl.szotaa.repperybackend.supermemo.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.szotaa.repperybackend.supermemo.domain.Group;
import pl.szotaa.repperybackend.supermemo.exception.GroupNotFoundException;
import pl.szotaa.repperybackend.supermemo.repository.GroupRepository;
import pl.szotaa.repperybackend.user.domain.User;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public void add(Group group){
        group.setOwner(this.getCurrentUser());
        groupRepository.save(group);
    }

    public Group getById(long id) throws GroupNotFoundException {
        return groupRepository.findById(id).orElseThrow(GroupNotFoundException::new);
    }

    public List<Group> getAllGroupsOwnedByCurrentUser(){
        return groupRepository.getAllByOwner(this.getCurrentUser());
    }

    private User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optional = Optional.ofNullable((User) auth.getPrincipal());
        return optional.<IllegalStateException>orElseThrow(() -> {
            throw new IllegalStateException("Principal object is null.");
        });
    }
}
