package ca.mcgill.ecse223.quoridor;

import java.io.IOException;
import java.util.HashMap;

import ca.mcgill.ecse223.quoridor.model.Quoridor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class QuoridorApplication extends Application {

	private static Quoridor quoridor;
	private static Stage primaryStage;
	private static HashMap<String, Scene> scenes = new HashMap<>();
	
	public static Quoridor getQuoridor()
	{
		if (quoridor == null)
			quoridor = new Quoridor();
 		return quoridor;
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		QuoridorApplication.primaryStage = primaryStage;
		
		Thread.setDefaultUncaughtExceptionHandler((t, ex) ->
		{
			while (ex.getCause() != null)		// Get to root cause
				ex = ex.getCause();
			
			System.err.println(ex);		// Message
			for (StackTraceElement e : ex.getStackTrace())
				if (e.toString().contains("ca.mcgill.ecse223.quoridor"))	// Stack trace only in package
					System.err.println("\t" + e);
		});
		
	    loadScreen("StartGame", "StartGame.fxml",  "StartGame.css");
	    loadScreen("WhiteSelectUsername", "SelectWhitePlayerUsername.fxml", null);
	    loadScreen("BlackSelectUsername", "SelectBlackPlayerUsername.fxml", null);
	    loadScreen("ThinkingScreen", "Scene.fxml",  "Scene.css");
	    loadScreen("PlayScreen", "PlayScreen.fxml",  "PlayScreen.css");
	    
	    setScene("StartGame");
	    primaryStage.setOnHidden(e -> System.exit(0));
		primaryStage.show();
	}
	
	public boolean loadScreen(String name, String resource, String resourceCSS) throws IOException
    { 	
        Scene scene = new Scene(FXMLLoader.load(QuoridorApplication.class.getClassLoader().getResource(resource)));
		if(resourceCSS != null)
			scene.getStylesheets().add(QuoridorApplication.class.getClassLoader().getResource(resourceCSS).toExternalForm());
        scenes.put(name,scene);
        return true;
    }
    
    public static void setScene(String name) { primaryStage.setScene(scenes.get(name)); }
    public static Stage getPrimaryStage() { return primaryStage; }
	
	public static void main(String[] args)
	{
		launch(args);
	}

}
