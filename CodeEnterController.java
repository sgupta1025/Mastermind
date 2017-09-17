package application;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CodeEnterController implements Initializable {
	@FXML
	Button submitButton = new Button();
	@FXML
	TextField enteredCode = new TextField(); 
	@FXML
	AnchorPane anchorPane = new AnchorPane();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		enteredCode.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				
				if (enteredCode.getText().length() > 4) {
					String s = enteredCode.getText().substring(0, 4);
	                enteredCode.setText(s);

				}
				
			}
			
		});
		enteredCode.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	                enteredCode.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    });

	}
	@FXML
	public void submitCode(Event event) throws IOException{
		FileWriter writer = new FileWriter("code.txt");
		writer.write(enteredCode.getText());
		writer.close();
		Stage stage =(Stage)submitButton.getScene().getWindow();
		Scene newScene = new Scene(FXMLLoader.load(getClass().getResource("CodeGuess.fxml")));
		stage.setScene(newScene);
	}

}
