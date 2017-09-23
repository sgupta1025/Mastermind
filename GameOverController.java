package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GameOverController implements Initializable {

	@FXML
	Button playAgainButton = new Button();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
	public void playAgain(Event E) throws IOException {
		Stage stage =(Stage)playAgainButton.getScene().getWindow();
		Scene newScene = new Scene(FXMLLoader.load(getClass().getResource("../CodeEnter.fxml")));
		stage.setScene(newScene);
	}
	

}
