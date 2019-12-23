package sample.Models;

import java.util.HashMap;

public class QuestionContainer {
    private HashMap<PriceCategory, Question> containerOfAllQuestions;
    private static final QuestionContainer instanceOfQuestionContainer = new QuestionContainer();

    public void addQuestionToContainer(PriceCategory category, Question questionToAdd) {
        containerOfAllQuestions.putIfAbsent(category, questionToAdd);
    }

    public Question getQuestionFromContainerByPriceCategory(PriceCategory categoryOfNeededQuestion) {
        Question questionToReturn = containerOfAllQuestions.get(categoryOfNeededQuestion);
        return questionToReturn;
    }

    public HashMap<PriceCategory, Question> getContainerOfAllQuestions() {
        return containerOfAllQuestions;
    }

    public int getNumberOfAllQuestionsInContainer() {
        return containerOfAllQuestions.size();
    }

    private QuestionContainer() {
        containerOfAllQuestions = new HashMap<>();
    }

    public static QuestionContainer getInstanceOfQuestionContainer() {
        return instanceOfQuestionContainer;
    }

}
