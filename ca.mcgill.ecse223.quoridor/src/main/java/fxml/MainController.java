package fxml;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController extends Stage {
	
	
	private HashMap<String, Scene> scenes = new HashMap<>();
	
	
	@FXML
	public void initialize() {}
	
	/* This static variable will contain the single instance of this class. */
	public static MainController instance = null;
	public MainController() { MainController.instance = this; }
	
    public boolean loadScreen(String name, String resource, String resourceCSS) {
        try {
            //FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Scene scene = new Scene(FXMLLoader.load(ThinkingScreenController.class.getResource(resource)));
			scene.getStylesheets().add(ThinkingScreenController.class.getResource(resourceCSS).toExternalForm());
            scenes.put(name,scene);
            return true;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
            
        }
    }
    
    public void setScreen(String name) {
    	Scene scene = scenes.get(name);
    	MainController.instance.setScene(scene);
    }

}
