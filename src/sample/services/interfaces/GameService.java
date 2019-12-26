package sample.services.interfaces;

public interface GameService {
    void startNewGame();
    void pickAnswer(/*TODO: decide the type of parameters*/);
    void giveUp();
    void advanceToNextQuestion();
    /*TODO: decide the correct return type*/ void getGameState();

    // TODO: add 3 jokers functionality
}
