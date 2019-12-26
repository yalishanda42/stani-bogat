package sample.services.interfaces;

import java.util.List;

public interface GameService {
    void startNewGame();
    boolean pickAnswerAndCheckIfGameIsOver(String answer);
    void giveUp();
    String getCurrentCategoryPriceText();
    String getCurrentQuestionText();
    List<String> getCurrentAnswersInOrder();
    int getPriceWon();

    // TODO: add 3 jokers functionality
}
