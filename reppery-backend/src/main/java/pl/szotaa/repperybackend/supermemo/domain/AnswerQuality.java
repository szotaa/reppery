package pl.szotaa.repperybackend.supermemo.domain;

public enum AnswerQuality {

    PERFECT(5),
    CORRECT_HESITATION(4),
    CORRECT_DIFFICLTY(3),
    INCORRECT_ALMOST(2),
    INCORRECT(1),
    BLACKOUT(0);

    private final int value;

    AnswerQuality(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
