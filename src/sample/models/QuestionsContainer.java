package sample.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionsContainer {
    private Map<PriceCategory, List<Question>> containerOfAllQuestions;

    public void addQuestionToContainer(PriceCategory category, Question questionToAdd) {
        containerOfAllQuestions.putIfAbsent(category, new ArrayList<>());
        containerOfAllQuestions.get(category).add(questionToAdd);
    }

    public Map<PriceCategory, List<Question>> getContainerOfAllQuestions() {
        return containerOfAllQuestions;
    }

    public QuestionsContainer() {
        containerOfAllQuestions = new HashMap<>();
    }
}
