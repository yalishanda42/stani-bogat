package sample.services;

import org.xml.sax.SAXException;
import sample.models.Answer;
import sample.models.PrizeCategory;
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

    private Map<PrizeCategory, Question> currentGameQuestions;
    private List<PrizeCategory> categoriesInOrderFromStartToEnd;
    private PrizeCategory currentQuestionCategory;
    private Integer currentPrizeCategoryIndexInList;
    private List<Answer> currentAnswersRandomlyShuffled;
    private PrizeCategory lastGuaranteedPrize;
    private Integer prizeWonOnGameOver;

    public DefaultGameService() throws ParserConfigurationException, SAXException, IOException {
        databaseService = new DefaultDatabaseService();
    }

    @Override
    public void startNewGame() {
        Map<PrizeCategory, List<Question>> allQuestions = databaseService
                .fetchAllQuestionsFromDatabase()
                .getContainerOfAllQuestions();

        currentGameQuestions = new HashMap<>();
        for (Map.Entry<PrizeCategory, List<Question>> entry : allQuestions.entrySet()) {
            List<Question> questions = entry.getValue();
            int randomIndex = ThreadLocalRandom.current().nextInt(questions.size());
            Question chosenQuestion = questions.get(randomIndex);

            currentGameQuestions.put(entry.getKey(), chosenQuestion);
        }

        categoriesInOrderFromStartToEnd = new ArrayList<>(allQuestions.keySet());
        Collections.sort(categoriesInOrderFromStartToEnd);
        if (!categoriesInOrderFromStartToEnd.isEmpty()) {
            currentPrizeCategoryIndexInList = -1;
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
    public String getCurrentCategoryPrizeText() {
        if (currentQuestionCategory == null) {
            return null;
        }
        return currentQuestionCategory.getPrize().toString();
    }

    @Override
    public String getCurrentQuestionText() {
        if (currentQuestionCategory == null) {
            return null;
        }
        return currentGameQuestions.get(currentQuestionCategory).getQuestionText();
    }

    @Override
    public List<String> getCurrentAnswers() {
        if (currentQuestionCategory == null) {
            return null;
        }
        return currentAnswersRandomlyShuffled.stream().map(Answer::getAnswerText).collect(Collectors.toList());
    }

    @Override
    public int getPrizeWon() {
        return prizeWonOnGameOver == null ? 0 : prizeWonOnGameOver;
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
        currentPrizeCategoryIndexInList++;
        if (currentPrizeCategoryIndexInList == categoriesInOrderFromStartToEnd.size()) {
            gameOver(true);
            return true;
        }
        currentQuestionCategory = categoriesInOrderFromStartToEnd.get(currentPrizeCategoryIndexInList);
        shuffleCurrentQuestionAnswers();

        if ((currentPrizeCategoryIndexInList + 1) % 5 == 0) {
            lastGuaranteedPrize = currentQuestionCategory;
        }

        return false;
    }

    private void gameOver(boolean winCurrentPrize) {
        if (winCurrentPrize) {
            int prizeWonIndex = currentPrizeCategoryIndexInList - 1;
            if (prizeWonIndex < 0) {
                currentQuestionCategory = null;
            } else {
                currentQuestionCategory = categoriesInOrderFromStartToEnd.get(prizeWonIndex);
            }
            prizeWonOnGameOver = currentQuestionCategory == null ? 0 : currentQuestionCategory.getPrize();
        } else {
            prizeWonOnGameOver = lastGuaranteedPrize == null ? 0 : lastGuaranteedPrize.getPrize();
        }

        currentQuestionCategory = null;
        currentAnswersRandomlyShuffled = null;
        lastGuaranteedPrize = null;
    }
}
