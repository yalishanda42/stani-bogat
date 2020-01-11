package sample.user_interface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.exceptions.StartingGameException;
import sample.services.DefaultGameService;

public class GameController implements Initializable {

    @FXML
    private TextArea questionField;
    @FXML
    private TextArea infoField;
    @FXML
    private TextField answerAField;
    @FXML
    private TextField answerBField;
    @FXML
    private TextField answerCField;
    @FXML
    private TextField answerDField;
    @FXML
    private TextField questionNumberField;
    @FXML
    private TextField moneyField;
    @FXML
    private Button buttonA;
    @FXML
    private Button buttonB;
    @FXML
    private Button buttonC;
    @FXML
    private Button buttonD;

    private DefaultGameService gameService;
    private int questionNumber;

    public GameController() throws StartingGameException {
        questionField = new TextArea();
        answerAField = new TextField();
        answerBField = new TextField();
        answerCField = new TextField();
        answerDField = new TextField();
        questionNumberField = new TextField();
        moneyField = new TextField();
        gameService = new DefaultGameService();
        buttonA = new Button();
        buttonB = new Button();
        buttonC = new Button();
        buttonD = new Button();
        questionNumber = 0;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questionField.setEditable(false);
        questionField.setWrapText(true);
        answerAField.setEditable(false);
        answerBField.setEditable(false);
        answerCField.setEditable(false);
        answerDField.setEditable(false);
        moneyField.setEditable(false);
        questionNumberField.setEditable(false);
        gameService.startNewGame();
        loadNextQuestion();
    }

    private void setStartScene(String message, String buttonText) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("start_page.fxml"));
        Parent questionsParent = loader.load();
        Scene  questionsScene = new Scene(questionsParent);

        StartController controller = loader.getController();

        controller.init(message, buttonText);
        Stage window = (Stage) questionField.getScene().getWindow();
        window.setScene(questionsScene);
        window.show();
    }

    private void loadNextQuestion() {
        loadQuestion();
        loadAnswers();
        questionNumber++;
        questionNumberField.setText(Integer.toString(questionNumber));
        moneyField.setText(gameService.getCurrentCategoryPrizeText());
    }

    private void loadQuestion() {
        String question = gameService.getCurrentQuestionText();
        questionField.setText(question);
    }

    private void loadAnswers() {
        List<String> answers = gameService.getCurrentAnswers();
        String answerA = answers.get(0);
        String answerB = answers.get(1);
        String answerC = answers.get(2);
        String answerD = answers.get(3);
        answerAField.setText(answerA);
        answerBField.setText(answerB);
        answerCField.setText(answerC);
        answerDField.setText(answerD);
    }

    public void selectAnswerA() throws IOException {
        String answer = answerAField.getText();
        answerTheQuestion(answer);
    }

    public void selectAnswerB() throws IOException {
        String answer = answerBField.getText();
        answerTheQuestion(answer);
    }

    public void selectAnswerC() throws IOException {
        String answer = answerCField.getText();
        answerTheQuestion(answer);
    }

    public void selectAnswerD() throws IOException {
        String answer = answerDField.getText();
        answerTheQuestion(answer);
    }

    private void answerTheQuestion(String answer) throws IOException {
        if (answer != null) {
            boolean isCorrect = !gameService.pickAnswerAndCheckIfGameIsOver(answer);
            if (isCorrect) {
                loadNextQuestion();
            } else {
                lostGame();
            }
        }
    }

    private void lostGame() throws IOException {
        questionNumber = 0;

        String message = null;
        String buttonText = null;
        String money = Integer.toString(gameService.getPrizeWon());

        if (money.equals("100000")) {
            message = "Вие спечелихте голямата награда! Вашата печалба е " + money + ".";
        } else {
            message = "Вие загубихте! Вашата печалба е " + money + ".";
        }

        buttonText = "Започнете нова игра";

        setStartScene(message, buttonText);
    }

    public void giveUp() throws IOException {
        questionNumber = 0;

        gameService.giveUp();
        String money = Integer.toString(gameService.getPrizeWon());
        String message = "Вие се отказахте! Вашата печалба е " + money + ".";
        String buttonText = "Започнете нова игра";

        setStartScene(message, buttonText);
    }
}
