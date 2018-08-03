package pl.szotaa.repperybackend.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szotaa.repperybackend.auth.service.JwtAuthenticationService;
import pl.szotaa.repperybackend.user.domain.User;
import pl.szotaa.repperybackend.user.exception.EmailAlreadyTakenException;
import pl.szotaa.repperybackend.user.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtAuthenticationService jwtAuthenticationService;

    @GetMapping
    public ResponseEntity<String> login(@RequestBody User user){
        return ResponseEntity.ok(jwtAuthenticationService.getAuthenticatedJwtToken(user));
    }

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody User user) throws EmailAlreadyTakenException {
        userService.register(user);
        return ResponseEntity.ok().build();
    }
}
