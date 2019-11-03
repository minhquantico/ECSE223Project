package fxml;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/* The controller class must be mentioned in SceneBuilder to use with an .fxml file. */
public class ThinkingScreenController 
{
	/* @FXML annotated fields will be populated with the SceneBuilder components with matching fx:id */
	@FXML
	private Pane pane;
	@FXML
	private Button button;
	@FXML
	private TextField fieldMinute;
	@FXML
	private TextField fieldSecond;
	@FXML 
	//set this as visible upon invalid input
	private Label warning;
	
	/* @FXML annotated methods can be set in SceneBuilder to handle events. */
	boolean test = false;
	@FXML
	public void buttonClicked(ActionEvent e) { 
		
		int minute;
		int second;
		
		
		 try {
	            minute = Integer.parseInt(fieldMinute.getText().toString());
	            second =Integer.parseInt(fieldSecond.getText().toString());
	            if(minute!=0||second!=0) {
	            	ca.mcgill.ecse223.quoridor.Controller.setThinkingTime(minute, second);
	            	//ca.mcgill.ecse223.quoridor.Controller.startClock();
	            	 MainController.instance.setScreen("PlayScreen");
	            	 PlayScreenController.instance.board.startGame();
	            }
	           
	        } catch (NumberFormatException e1) {
	            warning.setVisible(true);
	        }
	}
	

}
