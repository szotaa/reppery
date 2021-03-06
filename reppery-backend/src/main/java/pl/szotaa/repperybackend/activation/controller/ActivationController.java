package pl.szotaa.repperybackend.activation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szotaa.repperybackend.activation.exception.ActivationTokenNotFoundException;
import pl.szotaa.repperybackend.activation.service.ActivationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/activate")
public class ActivationController {

    private final ActivationService activationService;

    @GetMapping("/{emailActivationToken}")
    public ResponseEntity<Void> activate(@PathVariable String emailActivationToken)
            throws ActivationTokenNotFoundException {
        this.activationService.activate(emailActivationToken);
        return ResponseEntity.ok().build();
    }
}
