package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CodeGuessController implements Initializable {
	@FXML
	TextField guessInput = new TextField();
	@FXML
	Button guessButton = new Button();
	@FXML
	Text numLives = new Text();
	int numberOfLives = 3;
	@FXML
	Circle charIndicator1 = new Circle();
	@FXML
	Circle charIndicator2 = new Circle();
	@FXML
	Circle charIndicator3 = new Circle();
	@FXML
	Circle charIndicator4 = new Circle();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		numLives.setText(String.valueOf(numberOfLives));
		guessInput.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				
				if (guessInput.getText().length() > 4) {
					String s = guessInput.getText().substring(0, 4);
	                guessInput.setText(s);

				}
				
			}
			
		});
		guessInput.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	                guessInput.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    });

	}
	
	/**
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void checkGuess(Event event) throws IOException{
		FileReader reader = new FileReader("code.txt");
		BufferedReader bufferedReader = new BufferedReader(reader);
		String code = bufferedReader.readLine();
		bufferedReader.close();
		//String fixedCode = code.replace("\n", "");
		if(guessInput.getText().equals(code)){
			Stage stage =(Stage)guessButton.getScene().getWindow();
			Scene newScene = new Scene(FXMLLoader.load(getClass().getResource("GameWin.fxml")));
			stage.setScene(newScene);
			File file = new File("code.txt");
			file.delete();
		}
		else
		{
			String enteredCode = guessInput.getText();
			
			for (int i = 0; i < code.length(); i++) {
				if(code.charAt(i) == enteredCode.charAt(i)){
					switch (i) {
					case 0:
						charIndicator1.setFill(Color.GREEN);
						break;
					case 1:
						charIndicator2.setFill(Color.GREEN);
						break;
					case 2:
						charIndicator3.setFill(Color.GREEN);
						break;
					case 3:
						charIndicator4.setFill(Color.GREEN);
						break;
					}
				}
				else if (containsChar(enteredCode, code.charAt(i))) {
					switch (i) {
					case 0:
						charIndicator1.setFill(Color.YELLOW);
						break;
					case 1:
						charIndicator2.setFill(Color.YELLOW);
						break;
					case 2:
						charIndicator3.setFill(Color.YELLOW);
						break;
					case 3:
						charIndicator4.setFill(Color.YELLOW);
						break;
					}
				}
				else{
					switch (i) {
					case 0:
						charIndicator1.setFill(Color.RED);
						break;
					case 1:
						charIndicator2.setFill(Color.RED);
						break;
					case 2:
						charIndicator3.setFill(Color.RED);
						break;
					case 3:
						charIndicator4.setFill(Color.RED);
						break;
					}
				}
				//decrement lives
				
			}
			numberOfLives--;
			numLives.setText(String.valueOf(numberOfLives));
			if(numberOfLives == 0){
				Stage stage =(Stage)guessButton.getScene().getWindow();
				Scene newScene = new Scene(FXMLLoader.load(getClass().getResource("GameLoss.fxml")));
				stage.setScene(newScene);
				File file = new File("code.txt");
				file.delete();
			}
		}
		
	}
	public boolean containsChar(String s, char search) {
	    if (s.length() == 0)
	        return false;
	    else
	        return s.charAt(0) == search || containsChar(s.substring(1), search);
	}


}
