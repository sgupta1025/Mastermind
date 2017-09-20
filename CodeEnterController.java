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

//Initializes the Controlled for CodeEnter
public class CodeEnterController implements Initializable {
	//Initializes the buttons and pane to be manipulated by the Controller
	@FXML
	Button submitButton = new Button();
	@FXML
	TextField enteredCode = new TextField(); 
	@FXML
	AnchorPane anchorPane = new AnchorPane();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		enteredCode.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	                try {
						submitCode();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        }
	    });
		
		//Limits the amount of chars entered for the Dialog Box for Guess
		enteredCode.lengthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2)
			{
				if (enteredCode.getText().length() > 4) {
					String s = enteredCode.getText().substring(0, 4);
	                enteredCode.setText(s);
				}
			}
		});
		
		//Checks to make sure the input is only numerical values
		enteredCode.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	                enteredCode.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    });

	}
	
	//Creates a file that stores the string "code"
	@FXML
	public void submitCode(Event event) throws IOException{
		FileWriter writer = new FileWriter("code.txt");
		writer.write(enteredCode.getText());
		writer.close();
		Stage stage =(Stage)submitButton.getScene().getWindow();
		Scene newScene = new Scene(FXMLLoader.load(getClass().getResource("/CodeGuess.fxml")));
		stage.setScene(newScene);
	}

}
