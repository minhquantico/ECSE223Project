package ca.mcgill.ecse223.quoridor.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import ca.mcgill.ecse223.quoridor.Controller;
import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

public class SGController {

	@FXML private Pane pane;
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Button sgButton;
    @FXML private Button lgButton;

    @FXML
    void buttonClicked(ActionEvent event)
    {
    	QuoridorApplication.setScene("WhiteSelectUsername");
    }
    
    @FXML
    void loadGame(ActionEvent event)
    {
    	FileChooser chooser = new FileChooser();
    	File f = chooser.showOpenDialog(QuoridorApplication.getPrimaryStage());
    	if (f != null)
    		try { Controller.loadGame(f); }
    		catch (FileNotFoundException | Controller.InvalidPositionException ex)
    		{
    			System.err.println("Load Error: " + ex.getMessage());
    		}
    }

    @FXML
    void initialize()
    {
    	ca.mcgill.ecse223.quoridor.Controller.initQuoridorAndBoard();
    	ca.mcgill.ecse223.quoridor.Controller.InitializeNewGame();
    }
}




