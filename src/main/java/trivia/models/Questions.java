package trivia.models;

import trivia.types.QuestionCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Questions class represents questions
 * for Trivia quiz.
 *
 * @author Igor Radovanovic
 * @version 1.0
 * @since 2022-02-24
 */
public class Questions {

    //missing abstractions: Questions are grouped in Decks where every Deck is of specific Category
    //present that in code

    private final Map<QuestionCategory, List<String>> questions = new HashMap<>();

    /**
     * Constructor is filling up Map with dummy values for the questions
     */
    public Questions() {
        for (QuestionCategory questionCategory : QuestionCategory.values()) {
            questions.put(questionCategory, new ArrayList<>());
        }
        //50 MAGIC NUMBER, who should tell how many questions are in the game?
        for (int i = 0; i < 50; i++) {
            questions.get(QuestionCategory.POP).add("Pop Question " + i);
            questions.get(QuestionCategory.SCIENCE).add("Science Question " + i);
            questions.get(QuestionCategory.SPORTS).add("Sports Question " + i);
            questions.get(QuestionCategory.ROCK).add("Rock Question " + i);

        }
    }

    /**
     * This method displays current Question and removes it from the List
     */
    public String showNextQuestion(QuestionCategory questionCategory) {
        List<String> categoryQuestions = questions.get(questionCategory);
        String question = categoryQuestions.get(0);
        categoryQuestions.remove(0);
        return question;
    }

}
