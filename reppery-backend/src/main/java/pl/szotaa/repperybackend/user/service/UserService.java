package pl.szotaa.repperybackend.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.szotaa.repperybackend.user.domain.User;
import pl.szotaa.repperybackend.user.exception.EmailAlreadyTakenException;
import pl.szotaa.repperybackend.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(User user) throws EmailAlreadyTakenException {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new EmailAlreadyTakenException();
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(username)
                .<UsernameNotFoundException>orElseThrow(() -> {
                    throw new UsernameNotFoundException(username);
                });
    }
}