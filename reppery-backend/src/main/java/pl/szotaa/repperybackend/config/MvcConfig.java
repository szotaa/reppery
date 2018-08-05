package pl.szotaa.repperybackend.config;

import java.util.Collections;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

@Configuration
public class MvcConfig {

    /**
     * Workaround for angular routing not working (Spring's Whitelabel Error Page was displayed on every angular route GET.
     * @return ModelAndView redirecting to index.html (Angular App root).
     */

    @Bean
    public ErrorViewResolver customErrorViewResolver() {
        ModelAndView redirectToAngularApp = new ModelAndView("forward:/index.html", Collections.emptyMap());
        return ((request, status, model) -> status == HttpStatus.NOT_FOUND ? redirectToAngularApp : null);
    }
}
