package pl.szotaa.repperybackend.supermemo.domain;

/**
 * Represents correctness of user answer.
 *
 * <ul>
 *  <li>PERFECT - perfect answer</li>
 *  <li>HESITATED - correct answer after a hesitation</li>
 *  <li>DIFFICULT - correct answer recalled with serious difficulty</li>
 *  <li>ALMOST - incorrect answer, where the correct one seemed easy to recall</li>
 *  <li>INCORRECT - incorrect answer</li>
 *  <li>BLACKOUT - complete blackout, no answer at all</li>
 * </ul>
 *
 *
 * @author szotaa
 */

public enum AnswerQuality {

    PERFECT(5),
    HESITATED(4),
    DIFFICULT(3),
    ALMOST(2),
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
