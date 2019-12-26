package sample.models;

import java.util.List;

public class Question {
    private String questionText;
    private PriceCategory questionPrice;
    private List<Answer> questionAnswers;

    public Question(String text, PriceCategory price, List<Answer> answers){
        this.questionText = text;
        this.questionPrice = price;
        this.questionAnswers = answers;
    }

    public String getQuestionText(){
        return this.questionText;
    }

    public PriceCategory getQuestionPrice(){
        return this.questionPrice;
    }

    public List<Answer> getQuestionAnswers() {
        return questionAnswers;
    }
}
