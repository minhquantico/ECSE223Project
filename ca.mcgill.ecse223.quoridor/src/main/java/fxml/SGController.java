package fxml;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;

import java.io.File;
import java.io.FileNotFoundException;
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

import javafx.scene.control.Button;

public class SGController {

	@FXML
	private Pane pane;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button sgButton;

    @FXML
    private Button lgButton;

    @FXML
    void buttonClicked(ActionEvent event) {

    	System.out.println("it's in!!!");
		MainController.instance.setScreen("SelectUsername");
		

    }

    @FXML
    void initialize() {
    	ca.mcgill.ecse223.quoridor.Controller.initQuoridorAndBoard();
    	ca.mcgill.ecse223.quoridor.Controller.InitializeNewGame();
        assert sgButton != null : "fx:id=\"sgButton\" was not injected: check your FXML file 'StartGame.fxml'.";
        assert lgButton != null : "fx:id=\"lgButton\" was not injected: check your FXML file 'StartGame.fxml'.";

    }
}




