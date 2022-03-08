package trivia.game;

import trivia.types.QuestionCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QuestionManagement {

    private final Map<QuestionCategory, List<String>> questions = new HashMap<>();

    public String showNextQuestion(QuestionCategory questionCategory) {
        List<String> categoryQuestions = questions.get(questionCategory);
        String question = categoryQuestions.get(0);
        categoryQuestions.remove(0);
        return question;
    }

    public void generateRandom(int numberOfQuestions){
        for (QuestionCategory questionCategory : QuestionCategory.values()) {
            questions.put(questionCategory, new ArrayList<>());
        }
        for (int i = 0; i < numberOfQuestions; i++) {
            questions.get(QuestionCategory.POP).add("Pop Question " + i);
            questions.get(QuestionCategory.SCIENCE).add("Science Question " + i);
            questions.get(QuestionCategory.SPORTS).add("Sports Question " + i);
            questions.get(QuestionCategory.ROCK).add("Rock Question " + i);

        }
    }

}
