package pl.szotaa.repperybackend.auth.service;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pl.szotaa.repperybackend.auth.jwt.JwtToken;
import pl.szotaa.repperybackend.auth.jwt.JwtTokenFactory;
import pl.szotaa.repperybackend.user.domain.User;

@Service
@Validated
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenFactory jwtTokenFactory;

    public JwtToken authenticate(@Valid User user){
        return jwtTokenFactory.getJwtToken(user);
    }
}
