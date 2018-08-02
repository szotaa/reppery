package pl.szotaa.repperybackend.user.domain

import spock.lang.Specification

class UserTest extends Specification {

    def "Equal users should have equal hashcodes"(){
        setup:
            def user = User.builder().email("user@email.com").build()
            def equalUser = User.builder().email("user@email.com").build()
            def notEqualUser = User.builder().email("otherUser@email.com").build()
        expect:
            user.equals(equalUser)
            !user.equals(notEqualUser)
            user.hashCode() == equalUser.hashCode()
            user.hashCode() != notEqualUser.hashCode()
    }
}
