package sample.user_interface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {

    @FXML
    private Button infoButton;
    @FXML
    private TextArea infoField;

    public StartController() {
        infoButton = new Button();
        infoField = new TextArea();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        infoField.setEditable(false);
        infoField.setWrapText(true);
        infoButton.setWrapText(true);
    }

    public void setGameScene(ActionEvent event) throws IOException {
        Parent questionsParent = FXMLLoader.load(getClass().getResource("game_page.fxml"));
        Scene questionsScene = new Scene(questionsParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(questionsScene);
        window.show();
    }

    public void init(String message, String buttonText) {
        infoField.setText(message);
        infoButton.setText(buttonText);
    }
}
