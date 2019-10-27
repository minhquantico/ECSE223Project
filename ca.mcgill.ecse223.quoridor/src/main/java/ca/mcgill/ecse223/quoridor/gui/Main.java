package ca.mcgill.ecse223.quoridor.gui;

import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		new StartMenu().show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
