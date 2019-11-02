package ca.mcgill.ecse223.quoridor.gui;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RotateWall_Demo extends Stage {
	@FXML
    private ImageView image;
    
    
    private static RotateWall_Demo instance;
    public static RotateWall_Demo create()
    {
    	try
    	{
    		Scene s = new Scene(FXMLLoader.load(Main.instance.getClass().getClassLoader().getResource("RotateWall_Demo.fxml")));
    		instance.setScene(s);
    		return instance;
    	}
    	catch (IOException ex) { ex.printStackTrace(); }
    	return null;
    }

    public RotateWall_Demo()
    {
    	instance = this;
    	System.out.println("imagae is: " + image);
    }

    
    public void arrowRotation(KeyEvent event){
    	if(event.getCode().equals(KeyCode.R))
    		image.setRotate(90);
    }
    
    public void initialize() {
    	
    	image.setImage(new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/7/7c/Javafx-layout-classes.svg/1000px-Javafx-layout-classes.svg.png"));
    	
    	System.out.println("image: " + this.image);
    	pane.requestFocus();
    } 
}
