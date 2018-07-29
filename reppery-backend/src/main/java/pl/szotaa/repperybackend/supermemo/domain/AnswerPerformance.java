package pl.szotaa.repperybackend.supermemo.domain;

public enum AnswerPerformance {

    PERFECT_ANSWER(1.0),
    CORRECT_ANSWER(0.8),
    GOOD_ANSWER(0.6),
    ALMOST_CORRECT_ANSWER(0.4),
    INCORRECT_ANSWER(0.2),
    NO_ANSWER(0.0);

    private final double value;

    AnswerPerformance(double value){
        this.value = value;
    }

    public double getValue(){
        return value;
    }
}
