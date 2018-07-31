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

    public void updateByAnswer(AnswerQuality answerQuality){
        int answerValue = answerQuality.getValue();
        this.easiness = this.recalculateEasiness(answerValue);
        this.repetitions = this.recalculateRepetitions(answerValue);
        this.interval = this.recalculateInterval();
        this.nextDueDate = this.recalculateNextDueDate();
    }

    private double recalculateEasiness(int answerQualityValue){
        int subtractedValue = 5 - answerQualityValue;
        double easiness = this.easiness + 0.1 - subtractedValue * (0.08 + subtractedValue) * 0.02;
        if(easiness > 2.5){
            return 2.5;
        } else if (easiness < 1.3){
            return 1.3;
        } else {
            return easiness;
        }
    }

    private int recalculateRepetitions(int answerQualityValue){
        if(answerQualityValue < 3){
            return 0;
        } else {
            return this.repetitions + 1;
        }
    }

    private int recalculateInterval(){
        if(this.repetitions <= 1){
            return 1;
        } else if (this.repetitions == 2){
            return 6;
        } else {
            return (int) Math.round(this.interval * easiness);
        }
    }

    private LocalDate recalculateNextDueDate(){
        return LocalDate.now().plusDays(this.interval);
    }
}
