package trivia.game;

import lombok.Data;
import trivia.types.QuestionCategory;

@Data
public class Board {

    public static final int PLACES = 12;

    public QuestionCategory getCurrentCategory(int step) {
        if (step == 0 || step == 4 || step == 8) {
            return QuestionCategory.POP;
        } else if (step == 1 || step == 5 || step == 9) {
            return QuestionCategory.SCIENCE;
        } else if (step == 2 || step == 6 || step == 10) {
            return QuestionCategory.SPORTS;
        } else if (step == 3 || step == 7 || step == 11) {
            return QuestionCategory.ROCK;
        }
        return null;
    }
}
