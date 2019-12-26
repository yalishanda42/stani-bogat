package sample.services.interfaces;

import java.util.List;

public interface GameService {

    /* ====================================== ACTIONS ==================================================== */

    /**
     * Load and configure a new game of "Stani Bogat" with questions selected at random.
     * Set the current question to the first one.
     */
    void startNewGame();

    /**
     * Pick an answer to the current question.
     * If right - advance to the next question (or end the game if this was the last one).
     * If wrong - end the game.
     * @param answer The string with the answer picked (as returned by getCurrentAnswersInOrder())
     * @return Boolean, indicating whether the game has ended after the answer has been picked
     * @throws NullPointerException when the supplied answer string does not match any of the current ones'.
     */
    boolean pickAnswerAndCheckIfGameIsOver(String answer);

    /**
     * Safely end the game.
     * This method should be called when the user wants to end the game without giving answer to
     * the current question.
     */
    void giveUp();

    /* ====================================== INFORMATION ================================================= */

    /**
     * Get the current prize category.
     * @return Current prize category text (or null if none).
     */
    String getCurrentCategoryPrizeText();

    /**
     * Get the current question.
     * @return Current question text (or null if none).
     */
    String getCurrentQuestionText();

    /**
     * Get a list of the current question answers.
     * @return List of all current possible answers' text values (or null if none).
     */
    List<String> getCurrentAnswers();

    /**
     * Get the prize won. Should be called only after the game has ended.
     * @return Prize won.
     */
    int getPrizeWon();

    // TODO: add 3 jokers functionality
}
