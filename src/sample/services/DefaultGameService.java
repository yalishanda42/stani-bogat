package sample.services;

import org.xml.sax.SAXException;
import sample.models.Answer;
import sample.models.PriceCategory;
import sample.models.Question;
import sample.services.interfaces.DatabaseService;
import sample.services.interfaces.GameService;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class DefaultGameService implements GameService {

    private final DatabaseService databaseService;

    private Map<PriceCategory, Question> currentGameQuestions;
    private List<PriceCategory> categoriesInOrderFromStartToEnd;
    private PriceCategory currentQuestionCategory;
    private Integer currentPriceCategoryIndexInList;
    private List<Answer> currentAnswersRandomlyShuffled;
    private PriceCategory lastGuaranteedPrice;
    private Integer priceWonOnGameOver;

    public DefaultGameService() throws ParserConfigurationException, SAXException, IOException {
        databaseService = new DefaultDatabaseService();
    }

    @Override
    public void startNewGame() {
        Map<PriceCategory, List<Question>> allQuestions = databaseService
                .fetchAllQuestionsFromDatabase()
                .getContainerOfAllQuestions();

        currentGameQuestions = new HashMap<>();
        for (Map.Entry<PriceCategory, List<Question>> entry : allQuestions.entrySet()) {
            List<Question> questions = entry.getValue();
            int randomIndex = ThreadLocalRandom.current().nextInt(questions.size());
            Question chosenQuestion = questions.get(randomIndex);

            currentGameQuestions.put(entry.getKey(), chosenQuestion);
        }

        categoriesInOrderFromStartToEnd = new ArrayList<>(allQuestions.keySet());
        Collections.sort(categoriesInOrderFromStartToEnd);
        if (!categoriesInOrderFromStartToEnd.isEmpty()) {
            currentPriceCategoryIndexInList = -1;
            advanceToNextQuestion();
        }
    }

    @Override
    public boolean pickAnswerAndCheckIfGameIsOver(String answerText) {
        Answer chosenAnswer = null;
        for (Answer answer : currentAnswersRandomlyShuffled) {
            if (Objects.equals(answer.getAnswerText(), answerText)) {
                chosenAnswer = answer;
            }
        }

        if (chosenAnswer == null) {
            throw new NullPointerException("Please specify valid answer string!");
        }

        if (chosenAnswer.isCorrect()) {
            return advanceToNextQuestion();
        } else {
            gameOver(false);
            return true;
        }
    }

    @Override
    public void giveUp() {
        gameOver(true);
    }

    @Override
    public String getCurrentCategoryPriceText() {
        if (currentQuestionCategory == null) {
            return null;
        }
        return currentQuestionCategory.getPrice().toString();
    }

    @Override
    public String getCurrentQuestionText() {
        if (currentQuestionCategory == null) {
            return null;
        }
        return currentGameQuestions.get(currentQuestionCategory).getQuestionText();
    }

    @Override
    public List<String> getCurrentAnswersInOrder() {
        if (currentQuestionCategory == null) {
            return null;
        }
        return currentAnswersRandomlyShuffled.stream().map(Answer::getAnswerText).collect(Collectors.toList());
    }

    @Override
    public int getPriceWon() {
        return priceWonOnGameOver == null ? 0 : priceWonOnGameOver;
    }

    // Helpers

    private void shuffleCurrentQuestionAnswers() {
        if (currentQuestionCategory == null) {
            return;
        }

        List<Answer> currentAnswers = currentGameQuestions.get(currentQuestionCategory).getQuestionAnswers();
        Collections.shuffle(currentAnswers);
        currentAnswersRandomlyShuffled = currentAnswers;
    }

    private boolean advanceToNextQuestion() {
        currentPriceCategoryIndexInList++;
        if (currentPriceCategoryIndexInList == categoriesInOrderFromStartToEnd.size()) {
            gameOver(true);
            return true;
        }
        currentQuestionCategory = categoriesInOrderFromStartToEnd.get(currentPriceCategoryIndexInList);
        shuffleCurrentQuestionAnswers();

        if ((currentPriceCategoryIndexInList + 1) % 5 == 0) {
            lastGuaranteedPrice = currentQuestionCategory;
        }

        return false;
    }

    private void gameOver(boolean winCurrentPrice) {
        if (winCurrentPrice) {
            int priceWonIndex = currentPriceCategoryIndexInList - 1;
            if (priceWonIndex < 0) {
                currentQuestionCategory = null;
            } else {
                currentQuestionCategory = categoriesInOrderFromStartToEnd.get(priceWonIndex);
            }
            priceWonOnGameOver = currentQuestionCategory == null ? 0 : currentQuestionCategory.getPrice();
        } else {
            priceWonOnGameOver = lastGuaranteedPrice == null ? 0 : lastGuaranteedPrice.getPrice();
        }

        currentQuestionCategory = null;
        currentAnswersRandomlyShuffled = null;
        lastGuaranteedPrice = null;
    }
}
