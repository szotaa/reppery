package pl.szotaa.repperybackend.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szotaa.repperybackend.auth.jwt.JwtToken;
import pl.szotaa.repperybackend.auth.jwt.JwtTokenFactory;
import pl.szotaa.repperybackend.user.domain.User;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenFactory jwtTokenFactory;

    public JwtToken authenticate(User user){
        return jwtTokenFactory.getJwtToken(user);
    }
}
