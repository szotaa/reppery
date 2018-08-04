package pl.szotaa.repperybackend.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szotaa.repperybackend.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activate")
public class ActivationController {

    private final UserService userService;

    @GetMapping("/{emailActivationToken}")
    public ResponseEntity<Void> activate(@PathVariable String emailActivationToken){
        this.userService.activate(emailActivationToken);
        return ResponseEntity.ok().build();
    }
}
