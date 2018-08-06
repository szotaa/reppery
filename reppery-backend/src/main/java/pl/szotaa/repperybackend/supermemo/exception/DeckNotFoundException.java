package pl.szotaa.repperybackend.supermemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Specified deck cannot be found in the database.")
public class DeckNotFoundException extends Exception {
}
