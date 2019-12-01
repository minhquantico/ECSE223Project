package ca.mcgill.ecse223.quoridor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.mcgill.ecse223.quoridor.model.Quoridor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class QuoridorApplication extends Application {

	private static Quoridor quoridor;
	
	private static Stage primaryStage;
	private static HashMap<String, Screen> screens = new HashMap<>();
	
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
		
	    addScreen("StartGame", "StartGame.fxml", "All.css", "StartGame.css");
	    addScreen("WhiteSelectUsername", "SelectWhitePlayerUsername.fxml", "All.css", "SelectWhitePlayerUsername.css");
	    addScreen("BlackSelectUsername", "SelectBlackPlayerUsername.fxml", "All.css", "SelectBlackPlayerUsername.css");
	    addScreen("ThinkingScreen", "ThinkingTime.fxml", "All.css", "ThinkingTime.css");
	    addScreen("PlayScreen", "PlayScreen.fxml", "PlayScreen.css");
	    addScreen("Help", "Help.fxml", "Help.css");
	    setScreen("StartGame");
	    
	    primaryStage.setOnHidden(e -> System.exit(0));
	    primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public void addScreen(String name, String fxml, String... css) throws IOException
		{ screens.put(name, new Screen(fxml, css)); }
    
    public static void setScreen(String name)
    {
    	try { screens.get(name).setScreen(); }
    	catch (IOException ex) { System.err.println(ex.getMessage()); }
    }
    public static Stage getPrimaryStage() { return primaryStage; }
	public static void main(String[] args) { launch(args); }
	
	private static class Screen
	{
		private FXMLLoader loader;
		private List<String> css;
		private Scene scene;
		
		public Screen(String fxml, String... css)
		{
			this.loader = new FXMLLoader(QuoridorApplication.class.getClassLoader().getResource(fxml));
			this.css = new ArrayList<>();
			for (String style : css)
				this.css.add(QuoridorApplication.class.getClassLoader().getResource(style).toExternalForm());
			this.scene = null;
		}
		
		public void setScreen() throws IOException
		{
			if (this.scene == null)
			{
				this.scene = new Scene(loader.load());
				this.scene.getStylesheets().addAll(css);
			}
			QuoridorApplication.primaryStage.setScene(this.scene);
		}
	}
}
