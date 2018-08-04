package pl.szotaa.repperybackend.activation.service;

import com.sendgrid.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szotaa.repperybackend.activation.ActivationMailFactory;
import pl.szotaa.repperybackend.activation.exception.ActivationTokenNotFoundException;
import pl.szotaa.repperybackend.mail.service.EmailService;
import pl.szotaa.repperybackend.user.domain.User;
import pl.szotaa.repperybackend.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ActivationService {

    private final EmailService emailService;
    private final ActivationMailFactory activationMailFactory;
    private final UserRepository userRepository;

    public void sendActivationEmail(User user){
        Mail activationEmail = activationMailFactory.getActivationEmail(user);
        emailService.sendEmail(activationEmail);
    }

    public void activate(String emailActivationToken) throws ActivationTokenNotFoundException {
        User user = this.userRepository
                .findUserByEmailActivationToken(emailActivationToken)
                .orElseThrow(ActivationTokenNotFoundException::new);

        user.setEmailActivationToken(null);
        user.setIsEnabled(true);
        this.userRepository.save(user);
    }
}
