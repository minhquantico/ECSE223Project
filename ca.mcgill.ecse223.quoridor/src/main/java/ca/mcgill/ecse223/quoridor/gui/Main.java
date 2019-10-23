package ca.mcgill.ecse223.quoridor.gui;

import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Pane mainPane = new Pane();
		
		Button button = new Button();
		button.setText("Press me!");
		button.setOnMouseClicked(e ->
		{
			new Stage().show();
			primaryStage.close();
		});
		
		mainPane.getChildren().add(button);
		
		primaryStage.setTitle("Traian Sux");
		primaryStage.setScene(new Scene(mainPane));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
