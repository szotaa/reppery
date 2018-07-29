package pl.szotaa.repperybackend.supermemo.service;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szotaa.repperybackend.supermemo.domain.AnswerPerformance;
import pl.szotaa.repperybackend.supermemo.domain.Flashcard;
import pl.szotaa.repperybackend.supermemo.repository.FlashcardRepository;


import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final FlashcardRepository repository;

    public void review(Flashcard flashcard, AnswerPerformance performance) {
        double percentOverdue = this.getPercentOverdue(flashcard, performance);
        double calculatedDifficulty = this.calculateDifficulty(flashcard.getDifficulty(), percentOverdue, performance);
        flashcard.setDifficulty(calculatedDifficulty);
        double difficultyWeight = 3 - 1.7 * calculatedDifficulty;
        double calculatedInterval = this.calculateInterval(flashcard.getInterval(), difficultyWeight, percentOverdue, performance);
        flashcard.setInterval(calculatedInterval);
        repository.save(flashcard);
    }

    private double getPercentOverdue(Flashcard flashcard, AnswerPerformance performance){
        double result = 1;
        if(performance.getValue() >= 0.6){
            result = Math.min(2, (DAYS.between(flashcard.getLastReviewed(), LocalDate.now()))/flashcard.getInterval());
        }
        return result;
    }

    private double calculateDifficulty(double currentDifficulty, double percentOverdue, AnswerPerformance performance){
        double result = currentDifficulty + percentOverdue * (8 - 9 * performance.getValue()) / 17;
        if(result > 1) {
            result = 1;
        } else if (result < 0) {
            result = 0;
        }
        return result;
    }

    private double calculateInterval(double currentInterval, double difficultyWeight, double percentOverdue, AnswerPerformance performance){
        double result = currentInterval;
        double performanceValue = performance.getValue();
        if(performanceValue >= 0.6){
            result *= 1 + (difficultyWeight - 1) * percentOverdue;
        } else {
            result *= (1 / (difficultyWeight * difficultyWeight));
        }
        return result < 1 ? 1 : result;
    }
}
