package sample.models;

import java.util.List;

public class Question {
    private String questionText;
    private PrizeCategory questionPrice;
    private List<Answer> questionAnswers;

    public Question(String text, PrizeCategory price, List<Answer> answers){
        this.questionText = text;
        this.questionPrice = price;
        this.questionAnswers = answers;
    }

    public String getQuestionText(){
        return this.questionText;
    }

    public PrizeCategory getQuestionPrice(){
        return this.questionPrice;
    }

    public List<Answer> getQuestionAnswers() {
        return questionAnswers;
    }
}
