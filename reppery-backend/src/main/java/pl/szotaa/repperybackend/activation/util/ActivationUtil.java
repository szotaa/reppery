package pl.szotaa.repperybackend.activation.util;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class ActivationUtil {

    public String getActivationUrl(String emailActivationToken){
        Optional<ServletRequestAttributes> attributes = Optional.ofNullable(
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes()
        );

        HttpServletRequest request = attributes
                .<IllegalStateException>orElseThrow(() -> {
                    throw new IllegalStateException("RequestContextHolder has null request attributes.");
                })
                .getRequest();

        return String.format("%s://%s:%d/api/user/%s",
                request.getScheme(),
                request.getServerName(),
                request.getServerPort(),
                emailActivationToken);
    }
}
