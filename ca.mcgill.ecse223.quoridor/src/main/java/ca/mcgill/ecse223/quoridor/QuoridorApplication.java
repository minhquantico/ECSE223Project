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
			boolean printedTrace = false;
			for (StackTraceElement e : ex.getStackTrace())
				if (e.toString().contains("ca.mcgill.ecse223.quoridor") && (printedTrace = true))
					System.err.println("\t" + e);		// Stack trace only in package
			if (!printedTrace)
				ex.printStackTrace();
		});
		
	    loadScreen("StartGame", "StartGame.fxml", "All.css", "StartGame.css");
	    loadScreen("WhiteSelectUsername", "SelectWhitePlayerUsername.fxml", "All.css", "SelectWhitePlayerUsername.css");
	    loadScreen("BlackSelectUsername", "SelectBlackPlayerUsername.fxml", "All.css", "SelectBlackPlayerUsername.css");
	    loadScreen("ThinkingScreen", "ThinkingTime.fxml", "All.css", "ThinkingTime.css");
	    loadScreen("PlayScreen", "PlayScreen.fxml", "PlayScreen.css");
	    setScene("StartGame");
	    
	    primaryStage.setOnHidden(e -> System.exit(0));
	    primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public boolean loadScreen(String name, String fxml, String... css) throws IOException
    {
        Scene scene = new Scene(FXMLLoader.load(QuoridorApplication.class.getClassLoader().getResource(fxml)));
        for (String style : css)
        	scene.getStylesheets().add(QuoridorApplication.class.getClassLoader().getResource(style).toExternalForm());
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
