package pl.szotaa.repperybackend.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.szotaa.repperybackend.user.domain.User;

@Component
@RequiredArgsConstructor
public class JwtTokenFactory {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public JwtToken getJwtToken(User user){
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                ));

        return this.jwtTokenUtil.getJwtToken(authentication);
    }
}
