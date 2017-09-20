package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

//allows Main to access the GUI
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//Initializes the Anchorpane for the first scene: CodeEnter 
			BorderPane root = new BorderPane(FXMLLoader.load(getClass().getResource("/CodeEnter.fxml")));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		//Catches an error should the code somehow fail
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Launches the GUI
	public static void main(String[] args) {
		launch(args);
	}
}