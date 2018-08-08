package pl.szotaa.repperybackend.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User with specified email already exists in the database")
public class EmailAlreadyTakenException extends Exception {
}
