package ca.mcgill.ecse223.quoridor;

import ca.mcgill.ecse223.quoridor.model.Quoridor;
import fxml.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

public class QuoridorApplication extends Application {

	private static Quoridor quoridor;

	public static Quoridor getQuoridor() {
		if (quoridor == null)
			quoridor = new Quoridor();
 		return quoridor;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//instantiate the main controller for all the screens
				MainController mainContainer = new MainController();
			    mainContainer.loadScreen("StartGame", "resources/StartGame.fxml",  "resources/StartGame.css");
			    mainContainer.loadScreen("thinkingScreen", "resources/Scene.fxml",  "resources/Scene.css");
			   //mainContainer.loadScreen("SelectUsername", "resources/SelectUsername.fxml",  "resources/SelectUsername.css");
			    mainContainer.loadScreen("PlayScreen", "resources/PlayScreen.fxml",  "resources/PlayScreen.css");
			    mainContainer.loadScreen("WhiteSelectUsername", "resources/SelectWhitePlayerUsername.fxml", null);
			    mainContainer.loadScreen("BlackSelectUsername", "resources/SelectBlackPlayerUsername.fxml", null);
			    mainContainer.instance.setOnHidden(e -> System.exit(0));
			    mainContainer.setScreen("StartGame");
				mainContainer.show();
				//this is true --> System.out.println(MainController.instance==mainContainer);
		
	}
	public static void main(String[] args)
	{
		launch(args);
	}

}
