package pl.szotaa.repperybackend.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.szotaa.repperybackend.user.domain.User;

@Component
public class JwtTokenUtil {

    @Value("${jwt.expiration}")
    private long expirationTimeMs;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public JwtToken getJwtToken(Authentication auth){
        User user = (User) auth.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + this.expirationTimeMs);
        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, this.jwtSecret)
                .compact();

        return new JwtToken(token);
    }

    public String getUsernameFromJwt(String token){
        return Jwts.parser()
                .setSigningKey(this.jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
