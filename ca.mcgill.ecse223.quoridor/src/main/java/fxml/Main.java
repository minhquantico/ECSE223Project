package fxml;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
		
		
		//instantiate the main controller for all the screens
		 MainController mainContainer = new MainController();
	   mainContainer.loadScreen("StartGame", "resources/StartGame.fxml",  "resources/StartGame.css");
	   mainContainer.loadScreen("thinkingScreen", "resources/Scene.fxml",  "resources/Scene.css");
	   mainContainer.loadScreen("SelectUsername", "resources/SelectUsername.fxml",  "resources/SelectUsername.css");
	   mainContainer.loadScreen("PlayScreen", "resources/PlayScreen.fxml",  "resources/PlayScreen.css");
	  
	     
	    mainContainer.setScreen("StartGame");
		mainContainer.show();
		//this is true --> System.out.println(MainController.instance==mainContainer);
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
