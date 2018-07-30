package pl.szotaa.repperybackend.supermemo.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@Table(name = "flashcards")
@NoArgsConstructor
@AllArgsConstructor
public class Flashcard implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "front")
    private String front;

    @Column(name = "back")
    private String back;

    @Builder.Default
    @Column(name = "repetitions")
    private Integer repetitions = 0;

    @Builder.Default
    @Column(name = "interval")
    private Integer interval = 1;

    @Builder.Default
    @Column(name = "easiness")
    private Double easiness = 2.5;

    @Builder.Default
    @Column(name = "next_due_date")
    private LocalDate nextDueDate = LocalDate.now();
}
