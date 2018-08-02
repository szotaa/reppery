package pl.szotaa.repperybackend.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Specified user does not exists in the database")
public class UserNotFoundException extends Exception {
}
