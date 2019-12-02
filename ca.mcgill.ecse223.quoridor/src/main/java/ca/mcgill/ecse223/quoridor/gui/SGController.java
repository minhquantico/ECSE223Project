package ca.mcgill.ecse223.quoridor.gui;

import java.io.File;
import java.io.FileNotFoundException;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.Controller;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class SGController {

	@FXML private Pane pane;
    @FXML private Button sgButton;
    @FXML private Button lgButton;
    @FXML private Button lpButton;

    
    @FXML
    private Button helpButton;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    void helpButton(ActionEvent event) {
    	QuoridorApplication.setScreen("Help");
    }
    
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
    		try { Controller.loadGame(f); 
    		errorLabel.setText("VALID LOAD GAME");
			errorLabel.setTextFill(Color.GREEN);
    		}
    		catch (FileNotFoundException | Controller.InvalidPositionException ex)
    		{
    			
    			errorLabel.setText("INVALID LOAD GAME: "+ ex.getMessage());
    			errorLabel.setTextFill(Color.RED);
    		}
    }
    
    @FXML
    void loadPosition(ActionEvent event)
    {
    	FileChooser chooser = new FileChooser();
    	File f = chooser.showOpenDialog(QuoridorApplication.getPrimaryStage());
    	if (f != null)
    		try { Controller.loadPosition(f); 
    		errorLabel.setText("VALID LOAD POSITION");
			errorLabel.setTextFill(Color.GREEN);
    		}
    		catch (FileNotFoundException | Controller.InvalidPositionException ex)
    		{
    			errorLabel.setText("INVALID LOAD POSITION ERROR: "+ ex.getMessage());
    			errorLabel.setTextFill(Color.RED);
    		}
    }

    @FXML
    void initialize()
    {
    	ca.mcgill.ecse223.quoridor.controller.Controller.initQuoridorAndBoard();
    }
}