package ca.mcgill.ecse223.quoridor.gui;

import java.io.File;
import java.io.FileNotFoundException;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.Controller;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

public class SGController {

	@FXML private Pane pane;
    @FXML private Button sgButton;
    @FXML private Button lgButton;
    @FXML private Button lpButton;

    @FXML
    void startGame(ActionEvent event)
    {
    	if (!QuoridorApplication.getQuoridor().hasCurrentGame())
    		ca.mcgill.ecse223.quoridor.controller.Controller.InitializeNewGame();
    	QuoridorApplication.setScreen("WhiteSelectUsername");
    }
    
    @FXML
    void loadGame(ActionEvent event)
    {
    	FileChooser chooser = new FileChooser();
    	File f = chooser.showOpenDialog(QuoridorApplication.getPrimaryStage());
    	if (f != null)
    		try
    		{
    			Controller.loadGame(f);
    			QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.Replay);
    		}
    		catch (FileNotFoundException | Controller.InvalidPositionException ex)
    		{
    			System.err.println("Load Error: " + ex.getMessage());
    		}
    }
    
    @FXML
    void loadPosition(ActionEvent event)
    {
    	FileChooser chooser = new FileChooser();
    	File f = chooser.showOpenDialog(QuoridorApplication.getPrimaryStage());
    	if (f != null)
    		try { Controller.loadPosition(f); }
    		catch (FileNotFoundException | Controller.InvalidPositionException ex)
    		{
    			System.err.println("Load Error: " + ex.getMessage());
    		}
    }

    @FXML
    void initialize()
    {
    	ca.mcgill.ecse223.quoridor.controller.Controller.initQuoridorAndBoard();
    }
}