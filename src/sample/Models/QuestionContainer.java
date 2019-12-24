package sample.Models;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestionContainer {
    private HashMap<PriceCategory, ArrayList<Question>> containerOfAllQuestions;
    private static final QuestionContainer instanceOfQuestionContainer = new QuestionContainer();

    public void addQuestionToContainer(PriceCategory category, Question questionToAdd) {
        containerOfAllQuestions.putIfAbsent(category, new ArrayList<Question>());
        containerOfAllQuestions.get(category).add(questionToAdd);
    }

    public ArrayList<Question> getQuestionsFromContainerByPriceCategory(PriceCategory category) {
        return containerOfAllQuestions.get(category);
    }

    public HashMap<PriceCategory, ArrayList<Question>> getContainerOfAllQuestions() {
        return containerOfAllQuestions;
    }

    public int getNumberOfAllQuestions() {
        return containerOfAllQuestions.size();
    }

    private QuestionContainer() {
        containerOfAllQuestions = new HashMap<>();
    }

    public static QuestionContainer getInstanceOfQuestionContainer() {
        return instanceOfQuestionContainer;
    }

}
