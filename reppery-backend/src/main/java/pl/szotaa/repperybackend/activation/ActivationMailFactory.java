package pl.szotaa.repperybackend.activation;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.szotaa.repperybackend.activation.util.ActivationUtil;
import pl.szotaa.repperybackend.user.domain.User;

@Component
@RequiredArgsConstructor
public class ActivationMailFactory {

    private static final Email FROM = new Email("noreply@reppery.com");
    private static final String SUBJECT = "Welcome to Repprey!";
    private final ActivationUtil activationUtil;

    public Mail getActivationEmail(User user){
        Email to = new Email(user.getEmail());
        String activationUrl = this.activationUtil.getActivationUrl(user.getEmailActivationToken());
        Content content = new Content(
                "text/plain",
                "Activate your account here: " + activationUrl
                );
        return new Mail(FROM, SUBJECT, to, content);
    }
}
