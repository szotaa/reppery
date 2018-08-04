package pl.szotaa.repperybackend.user.service;

import com.sendgrid.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.szotaa.repperybackend.mail.domain.ActivationMailFactory;
import pl.szotaa.repperybackend.mail.service.EmailService;
import pl.szotaa.repperybackend.user.domain.User;
import pl.szotaa.repperybackend.user.exception.EmailAlreadyTakenException;
import pl.szotaa.repperybackend.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public void register(User user) throws EmailAlreadyTakenException {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new EmailAlreadyTakenException();
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        this.sendActivationEmail(user);
        userRepository.save(user);
    }

    public void activate(String emailActivationToken){
        User user = this.userRepository
                .findUserByEmailActivationToken(emailActivationToken)
                .orElseThrow(RuntimeException::new); // TODO: dedicated exception

        user.setEmailActivationToken(null);
        user.setIsEnabled(true);
        this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(username)
                .<UsernameNotFoundException>orElseThrow(() -> {
                    throw new UsernameNotFoundException(username);
                });
    }

    private void sendActivationEmail(User user){
        ActivationMailFactory factory = new ActivationMailFactory();
        Mail activationMail = factory.getActivationEmail(user);
        this.emailService.sendEmail(activationMail);
    }
}
