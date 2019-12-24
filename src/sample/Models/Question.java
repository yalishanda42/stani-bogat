package sample.Models;

import java.util.ArrayList;

public class Question {
    private String questionText;
    private PriceCategory questionPrice;
    private ArrayList<Answer> questionAnswers;

    public Question(String text, PriceCategory price, ArrayList<Answer> answers){
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

    public ArrayList<Answer> getQuestionAnswers() {
        return questionAnswers;
    }
}
