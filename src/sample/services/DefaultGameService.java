package sample.services;

import org.xml.sax.SAXException;
import sample.models.PriceCategory;
import sample.models.Question;
import sample.models.QuestionsContainer;
import sample.services.interfaces.DatabaseService;
import sample.services.interfaces.GameService;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class DefaultGameService implements GameService {

    private final DatabaseService databaseService;
    private HashMap<PriceCategory, Question> currentGameQuestions;

    public DefaultGameService() throws ParserConfigurationException, SAXException, IOException {
        databaseService = new DefaultDatabaseService();
        currentGameQuestions = new HashMap<>();
    }

    @Override
    public void startNewGame() {
        QuestionsContainer allQuestions = databaseService.fetchAllQuestionsFromDatabase();
        for (Map.Entry<PriceCategory, ArrayList<Question>> entry : allQuestions.getContainerOfAllQuestions().entrySet()) {
            ArrayList<Question> questions = entry.getValue();
            int randomIndex = ThreadLocalRandom.current().nextInt(questions.size());
            Question chosenQuestion = questions.get(randomIndex);

            currentGameQuestions.put(entry.getKey(), chosenQuestion);
        }
    }

    @Override
    public void pickAnswer() {
        // TODO
    }

    @Override
    public void giveUp() {
        // TODO
    }

    @Override
    public void advanceToNextQuestion() {
        // TODO
    }

    @Override
    public void getGameState() {
        // TODO
    }
}
