package pl.szotaa.repperybackend.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.szotaa.repperybackend.auth.jwt.JwtToken;
import pl.szotaa.repperybackend.auth.service.AuthService;
import pl.szotaa.repperybackend.user.domain.User;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<JwtToken> login(@RequestBody User user){
        return ResponseEntity.ok(this.authService.authenticate(user));
    }
}
