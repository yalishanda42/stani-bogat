package sample.models;

public class Answer {
    private String answer;
    private boolean isCorrect;

    public Answer(String answer, boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public String getAnswerText() {
        return this.answer;
    }

    public boolean isCorrect() {
        return this.isCorrect;
    }
}
