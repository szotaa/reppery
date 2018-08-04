package pl.szotaa.repperybackend.activation;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.szotaa.repperybackend.user.domain.User;

@Component
public class ActivationMailFactory {

    private static final Email FROM = new Email("noreply@reppery.com");
    private static final String SUBJECT = "Welcome to Repprey!";

    public Mail getActivationEmail(User user){
        Email to = new Email(user.getEmail());
        Content content = new Content(
                "text/plain",
                "Activate your account here: " + this.getActivationUrl(user.getEmailActivationToken()
                ));
        return new Mail(FROM, SUBJECT, to, content);
    }

    private String getActivationUrl(String emailActivationToken){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        return String.format("%s://%s:%d/api/user/%s",
                request.getScheme(),
                request.getServerName(),
                request.getServerPort(),
                emailActivationToken);
    }
}
