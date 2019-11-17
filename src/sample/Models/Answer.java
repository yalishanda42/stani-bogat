package sample.Models;

public class Answer {
    private String answer;
    private boolean isCorrect;

    public Answer(String answer, boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public String getAnswer() {
        return this.answer;
    }

    public boolean isCorrect() {
        return this.isCorrect;
    }
}
