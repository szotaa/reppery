package pl.szotaa.repperybackend.supermemo.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class Flashcard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    private String front;

    private String back;

    @Builder.Default
    private Double difficulty = 0.3;

    private Double interval;

    private LocalDate lastReviewed;
}
