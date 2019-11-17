package sample.Models;

public class Question {
    private String questionText;
    private int questionPrice;
    private Answer[] questionAnswers;

    public Question(String text, int price, Answer answerA, Answer answerB, Answer answerC, Answer answerD){
        this.questionText=text;
        this.questionPrice=price;
        this.questionAnswers=new Answer[4];
        this.questionAnswers[0]=answerA;
        this.questionAnswers[1]=answerB;
        this.questionAnswers[2]=answerC;
        this.questionAnswers[3]=answerD;
    }

    public String getQuestionText(){
        return this.questionText;
    }

    public int getQuestionPrice(){
        return this.questionPrice;
    }

    public Answer[] getQuestionAnswers() {
        return questionAnswers;
    }
}
