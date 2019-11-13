package ca.mcgill.ecse223.quoridor.gui;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/* The controller class must be mentioned in SceneBuilder to use with an .fxml file. */
public class ThinkingScreenController 
{
	@FXML private Pane pane;
	@FXML private Button button;
	@FXML private TextField fieldMinute;
	@FXML private TextField fieldSecond;
	@FXML private Label warning;
	
	@FXML
	public void buttonClicked(ActionEvent e)
	{
		int minute = Integer.parseInt(fieldMinute.getText());
		int second = Integer.parseInt(fieldSecond.getText());
		
		if(minute != 0 || second != 0)
		{
			ca.mcgill.ecse223.quoridor.controller.Controller.setTotalThinkingTime(minute, second);
			long totalSeconds = (minute * 60) + second;
			ca.mcgill.ecse223.quoridor.controller.Controller.StartClock(totalSeconds);
			QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.ReadyToStart);
			QuoridorApplication.setScene("PlayScreen");
			PlayScreenController.instance.board.startGame();
			QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.Running);
		}
	}
	
	public void checkTime()
	{
		try
		{
			Integer.parseInt(fieldMinute.getText());
			Integer.parseInt(fieldSecond.getText());
			
			warning.setVisible(false);
			button.setDisable(false);
		}
		catch (NumberFormatException ex)
		{
			warning.setVisible(true);
			button.setDisable(true);
		}
	}
	
	@FXML
	public void initialize()
	{
		fieldMinute.textProperty().addListener(e -> checkTime());
		fieldSecond.textProperty().addListener(e -> checkTime());
	}
}
