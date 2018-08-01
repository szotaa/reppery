package pl.szotaa.repperybackend.supermemo.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents correctness of user answer.
 *
 * <ul>
 *  <li>BLACKOUT - complete blackout, no answer at all</li>
 *  <li>INCORRECT - incorrect answer</li>
 *  <li>ALMOST - incorrect answer, where the correct one seemed easy to recall</li>
 *  <li>DIFFICULT - correct answer recalled with serious difficulty</li>
 *  <li>HESITATED - correct answer after a hesitation</li>
 *  <li>PERFECT - perfect answer</li>
 * </ul>
 *
 *
 * @author szotaa
 */

public enum AnswerQuality {

    BLACKOUT,
    INCORRECT,
    ALMOST,
    DIFFICULT,
    HESITATED,
    PERFECT;

    private final int value;

    AnswerQuality(){
        this.value = ordinal();
    }

    @JsonValue
    public int getValue(){
        return this.value;
    }

    @JsonCreator
    public static AnswerQuality getFromValue(int value) throws IllegalArgumentException {
        try{
            return AnswerQuality.values()[value];
        } catch (ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException();
        }
    }
}
