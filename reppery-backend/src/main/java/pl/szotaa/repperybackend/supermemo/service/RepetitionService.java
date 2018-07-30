package pl.szotaa.repperybackend.supermemo.service;

import java.time.LocalDate;
import org.springframework.stereotype.Service;
import pl.szotaa.repperybackend.supermemo.domain.AnswerQuality;
import pl.szotaa.repperybackend.supermemo.domain.Flashcard;

@Service
public class RepetitionService {

    public Flashcard processAnswer(Flashcard flashcard, AnswerQuality answerQuality){
        int answerValue = answerQuality.getValue();
        double easiness = this.calculateEasiness(flashcard.getEasiness(), answerValue);
        int repetitions = this.calculateRepetitions(flashcard.getRepetitions(), answerValue);
        int interval = this.calculateInterval(flashcard.getInterval(), repetitions, easiness);
        LocalDate nextDueDate = this.calculateNextDueDate(interval);
        flashcard.setEasiness(easiness);
        flashcard.setRepetitions(repetitions);
        flashcard.setInterval(interval);
        flashcard.setNextDueDate(nextDueDate);
        return flashcard;
    }

    private double calculateEasiness(double currentEasiness, int answerQualityValue){
        int subtractedValue = 5 - answerQualityValue;
        double easiness = currentEasiness + 0.1 - subtractedValue * (0.08 + subtractedValue) * 0.02;
        if(easiness > 2.5){
            return 2.5;
        } else if (easiness < 1.3) {
            return 1.3;
        }
        else {
            return easiness;
        }
    }

    private int calculateRepetitions(int currentRepetitions, int answerQualityValue){
        if(answerQualityValue < 3){
            return 0;
        } else {
            return currentRepetitions + 1;
        }
    }

    private int calculateInterval(int currentInterval, int repetitions, double easiness){
        if(repetitions <= 1){
            return 1;
        } else if (repetitions == 2) {
            return 6;
        } else {
            return (int) Math.round(currentInterval * easiness);
        }
    }

    private LocalDate calculateNextDueDate(int interval){
        return LocalDate.now().plusDays(interval);
    }
}
