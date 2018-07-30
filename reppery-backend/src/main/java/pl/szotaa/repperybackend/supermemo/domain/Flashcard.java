package pl.szotaa.repperybackend.supermemo.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Flashcard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    private String front;

    private String back;

    @Builder.Default
    private Integer repetitions = 0;

    @Builder.Default
    private Integer interval = 1;

    @Builder.Default
    private Double easiness = 2.5;

    @Builder.Default
    private LocalDate nextDueDate = LocalDate.now();
}
