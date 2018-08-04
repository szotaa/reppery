package pl.szotaa.repperybackend.mail.service

import com.sendgrid.Content
import com.sendgrid.Email
import com.sendgrid.Mail
import com.sendgrid.SendGrid
import spock.lang.Specification

class EmailServiceTest extends Specification {

    def sendGrid = Mock(SendGrid)
    def emailService = new EmailService(sendGrid)

    def "Send email should execute sendGrid.api which sends email"(){
        given:
            def mail = new Mail(new Email(""), "", new Email(""), new Content("", ""))
        when:
            emailService.sendEmail(mail)
        then:
            1 * sendGrid.api(_)
    }
}
