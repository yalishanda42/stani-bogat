package sample.models;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestionsContainer {
    private HashMap<PriceCategory, ArrayList<Question>> containerOfAllQuestions;

    public void addQuestionToContainer(PriceCategory category, Question questionToAdd) {
        containerOfAllQuestions.putIfAbsent(category, new ArrayList<>());
        containerOfAllQuestions.get(category).add(questionToAdd);
    }

    public HashMap<PriceCategory, ArrayList<Question>> getContainerOfAllQuestions() {
        return containerOfAllQuestions;
    }

    public QuestionsContainer() {
        containerOfAllQuestions = new HashMap<>();
    }
}
