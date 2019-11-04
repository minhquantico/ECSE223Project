package fxml;

import java.io.IOException;

import javax.jws.soap.InitParam;

import ca.mcgill.ecse223.quoridor.Controller;
import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
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
	            second = Integer.parseInt(fieldSecond.getText().toString());
	            if(minute!=0||second!=0) {
	            	 ca.mcgill.ecse223.quoridor.Controller.setTotalThinkingTime(minute, second);
	            	 long totalSeconds = (minute * 60) + second;
	            	 ca.mcgill.ecse223.quoridor.Controller.StartClock(totalSeconds);
	            	 QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.ReadyToStart);
	            	 MainController.instance.setScreen("PlayScreen");
	            	 PlayScreenController.instance.board.startGame();
	            	 QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.Running);
	            }
	           
	        } catch (NumberFormatException e1) {
	            warning.setVisible(true);
	        }
	}
	
	@FXML
	public void initialize()
	{
		fieldMinute.setText("3");
		fieldSecond.setText("0");
	}
}
