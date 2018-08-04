package pl.szotaa.repperybackend.activation.service

import com.sendgrid.Content
import com.sendgrid.Email
import com.sendgrid.Mail
import pl.szotaa.repperybackend.activation.ActivationMailFactory
import pl.szotaa.repperybackend.mail.service.EmailService
import pl.szotaa.repperybackend.user.domain.User
import pl.szotaa.repperybackend.user.repository.UserRepository
import spock.lang.Specification

class ActivationServiceTest extends Specification {

    def emailService = Mock(EmailService)
    def factory = Mock(ActivationMailFactory)
    def userRepository = Mock(UserRepository)
    def activationService = new ActivationService(emailService, factory, userRepository)

    def "SendActivationEmail should build and send email"(){
        given:
            factory.getActivationEmail(_) >> new Mail(
                    new Email(""),
                    "",
                    new Email(""),
                    new Content("", "")
            )
        when:
            activationService.sendActivationEmail(new User())
        then:
            1 * factory.getActivationEmail(_)
            1 * emailService.sendEmail(_)
    }

    def "Activate should find, nullify token, enable and persist user"(){
        given:
            def user = User.builder()
                    .emailActivationToken("token")
                    .isEnabled(false)
                    .build()
            userRepository.findUserByEmailActivationToken(_) >> Optional.of(user)

        when:
            activationService.activate("token")
        then:
            1 * userRepository.save(_) >> {
                it.isEnabled == true
                it.emailActivationToken == null
            }
    }
}
