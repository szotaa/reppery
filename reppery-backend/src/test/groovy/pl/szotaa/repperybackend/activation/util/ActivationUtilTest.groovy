package pl.szotaa.repperybackend.activation.util

import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import spock.lang.Specification

class ActivationUtilTest extends Specification {

    def activationUtil = new ActivationUtil()

    def "GetActivationUrl should return correctly formatted activation url"(){
        given:
            def token = "token"
            def request = new MockHttpServletRequest()
            request.serverName = "serverName"
            request.serverPort = 8080
            request.scheme = "http"
            def servletRequestAttributes = new ServletRequestAttributes(request)
            RequestContextHolder.setRequestAttributes(servletRequestAttributes)
        when:
            def result = activationUtil.getActivationUrl(token)
        then:
            result == "http://serverName:8080/login?verify=token"
    }
}
