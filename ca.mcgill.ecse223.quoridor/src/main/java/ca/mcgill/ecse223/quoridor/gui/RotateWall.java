package ca.mcgill.ecse223.quoridor.gui;

import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//import ca.mcgill.ecse223.quoridor.gui.Main.Delta;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RotateWall extends Stage{
	@FXML
    Pane pane;
    @FXML
    ImageView image;
    
    private double x;
    private double y;
    
    private static RotateWall instance;
    public static RotateWall create()
    {
    	try
    	{
    		Scene s = new Scene(FXMLLoader.load(Main.instance.getClass().getClassLoader().getResource("RotateWall.fxml")));
    		instance.setScene(s);
    		return instance;
    	}
    	catch (IOException ex) { ex.printStackTrace(); }
    	return null;
    }

    public RotateWall()
    {
    	instance = this;
    }

    @FXML
    public void arrowRotation(KeyEvent event){
    	//System.out.println("Yos");
        if(event.getCode().equals(KeyCode.R))
            image.setRotate(image.getRotate() + 90);
    }

	@FXML
    public void initialize() {
        Image img = new Image(getClass().getClassLoader().getResourceAsStream("Wall.png"));
        image.setImage(img);
        
        image.setOnMousePressed( e -> {
              x = e.getSceneX();
              y = e.getSceneY();
              image.getScene().setCursor(Cursor.NONE);
            });
          image.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
              image.getScene().setCursor(Cursor.DEFAULT);
            }
          });
          image.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
            	double deltaX = mouseEvent.getSceneX() - x ;
                double deltaY = mouseEvent.getSceneY() - y ;
                image.setX(image.getX() + deltaX);
                image.setY(image.getY() + deltaY);
                x = mouseEvent.getSceneX() ;
                y = mouseEvent.getSceneY() ;
                pane.requestFocus();
            }
          });
          image.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
              if (!mouseEvent.isPrimaryButtonDown()) {
                image.getScene().setCursor(Cursor.DEFAULT);
              }
            }
          });
          image.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
              if (!mouseEvent.isPrimaryButtonDown()) {
                image.getScene().setCursor(Cursor.DEFAULT);
              }
            }
          });
        }
       
    }

