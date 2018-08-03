package pl.szotaa.repperybackend.auth.jwt;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class JwtToken {

    private final String jwtToken;
}
