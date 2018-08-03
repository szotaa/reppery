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

    def "User email should be case insensitive"(){
        given:
            def user = User.builder().email("user@email.com").build()
            def equalUser = User.builder().email("USER@email.com").build()
        expect:
            user == equalUser
            user.hashCode() == equalUser.hashCode()
    }

    def "User email is persisted in lower case"(){
        given:
            def email = "USER@EMAIL.COM"
            def user = User.builder().email(email).build()
        when:
            user.prePersist()
        then:
            user.getEmail()== email.toLowerCase()
    }
}
