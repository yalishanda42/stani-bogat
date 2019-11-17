package sample.Models;

import java.util.HashMap;

public class QuestionContainer {
    public HashMap<PriceCategory, Question> containerOfAllQuestions;
    private static int numberOfAllQuestionsInContainer = 0;
    private static final QuestionContainer instanceOfQuestionContainer = new QuestionContainer();

    public void addQuestionToContainer(PriceCategory category, Question questionToAdd) {
        containerOfAllQuestions.putIfAbsent(category, questionToAdd);
        numberOfAllQuestionsInContainer++;
    }

    public int getNumberOfAllQuestionsInContainer() {
        return numberOfAllQuestionsInContainer;
    }

    private QuestionContainer() {
        containerOfAllQuestions = new HashMap<>();
    }

    public static QuestionContainer getInstanceOfQuestionContainer() {
        return instanceOfQuestionContainer;
    }

}
