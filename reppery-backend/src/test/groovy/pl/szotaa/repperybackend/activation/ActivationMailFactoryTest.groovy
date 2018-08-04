package pl.szotaa.repperybackend.activation

import com.sendgrid.Mail
import pl.szotaa.repperybackend.activation.util.ActivationUtil
import pl.szotaa.repperybackend.user.domain.User
import spock.lang.Specification

class ActivationMailFactoryTest extends Specification {

    def activationUtil = Mock(ActivationUtil)
    def activationMailFactory = new ActivationMailFactory(activationUtil)

    def "Should build Mail object properly"(){
        given:
            def user = User.builder()
                        .email("user@email.com")
                        .emailActivationToken("token")
                        .build()
        when:
            def result = activationMailFactory.getActivationEmail(user)

        then:
            result instanceof Mail
    }
}
