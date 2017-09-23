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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//Initialize the Controller for the Guess
public class CodeGuessController implements Initializable {
	
	//Initializes variables to be manipulated in within the Controller
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
	//Sets the number of lives on the GUI equal to a variable that can be decremented and checked
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		guessInput.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	                try {
						checkGuess();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        }
	    });
		numLives.setText(String.valueOf(numberOfLives));
		guessInput.lengthProperty().addListener(new ChangeListener<Number>() {

			//Limits the amount of chars entered for the Dialog Box for Guess
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				if (guessInput.getText().length() > 4) {
					String s = guessInput.getText().substring(0, 4);
	                guessInput.setText(s);
				}	
			}
		});
		
		//Checks to make sure the input is only numerical values
		guessInput.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	                guessInput.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    });
		
		//Checks for the pressing of the Enter key and receives input after it's pressed
		guessInput.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	                try {
						checkGuess(ke);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        }
	    });

	}
	
	/**
	 * @param event
	 * @throws IOException
	 */
	//Reads the "code.txt" file and sets a String to the contents of the file
	@FXML
	public void checkGuess(Event event) throws IOException{
		FileReader reader = new FileReader("code.txt");
		BufferedReader bufferedReader = new BufferedReader(reader);
		String code = bufferedReader.readLine();
		bufferedReader.close();
		
		//Deletes the file code is stored in and proceeds to the victory screen
		if(guessInput.getText().equals(code)){
			Stage stage =(Stage)guessButton.getScene().getWindow();
			Scene newScene = new Scene(FXMLLoader.load(getClass().getResource("/GameWin.fxml")));
			stage.setScene(newScene);
			File file = new File("code.txt");
			file.delete();
		}
		
		//If the code entered was not completely correct
		else
		{
			//Sets a String equal to input to be compared to code
			String enteredCode = guessInput.getText();
			//If any char in guess matches position and char in code
			for (int i = 0; i < enteredCode.length(); i++) {
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
				
				//If any char in guess matches a char in code, but isn't in the correct position
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
				
				//If any char in guess does not match any of the chars in code
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
			}
			//Decrement Lives
			numberOfLives--;
			numLives.setText(String.valueOf(numberOfLives));
			//Ends the game and deletes the file out of lives
			if(numberOfLives == 0){
				Stage stage =(Stage)guessButton.getScene().getWindow();
				Scene newScene = new Scene(FXMLLoader.load(getClass().getResource("/GameLoss.fxml")));
				stage.setScene(newScene);
				File file = new File("code.txt");
				file.delete();
			}
		}	
	}
	//Searches the guess to check if it contains any chars that code has
	public boolean containsChar(String s, char search) {
	    if (s.length() == 0)
	        return false;
	    else
	        return s.charAt(0) == search || containsChar(s.substring(1), search);
	}
}
