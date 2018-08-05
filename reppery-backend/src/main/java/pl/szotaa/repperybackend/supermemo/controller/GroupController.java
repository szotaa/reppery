package pl.szotaa.repperybackend.supermemo.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szotaa.repperybackend.supermemo.domain.Group;
import pl.szotaa.repperybackend.supermemo.service.GroupService;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroupsOwnedByCurrentUser(){
        return ResponseEntity.ok(groupService.getAllGroupsOwnedByCurrentUser());
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody Group group){
        this.groupService.add(group);
        return ResponseEntity.ok().build();
    }
}
