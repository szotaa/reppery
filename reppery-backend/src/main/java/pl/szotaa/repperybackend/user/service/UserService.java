package pl.szotaa.repperybackend.user.service;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pl.szotaa.repperybackend.activation.service.ActivationService;
import pl.szotaa.repperybackend.user.domain.User;
import pl.szotaa.repperybackend.user.exception.EmailAlreadyTakenException;
import pl.szotaa.repperybackend.user.repository.UserRepository;

@Service
@Validated
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ActivationService activationService;

    public void register(@Valid User user) throws EmailAlreadyTakenException {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new EmailAlreadyTakenException();
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        this.activationService.sendActivationEmail(user);
        userRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(username)
                .<UsernameNotFoundException>orElseThrow(() -> {
                    throw new UsernameNotFoundException(username);
                });
    }
}
