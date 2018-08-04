package pl.szotaa.repperybackend.activation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,
                reason = "Specified email activation token could not be found in the database.")
public class ActivationTokenNotFoundException extends Exception {
}
