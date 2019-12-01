package ca.mcgill.ecse223.quoridor.gui;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;

public class HelpController {

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu GameRules;

    @FXML
    private Menu Network;

    @FXML
    private Button Back;

    @FXML
    private Menu Computer;

    @FXML
    private Pane pane;

    @FXML
    private Menu BasicControls;

    @FXML
    void BackButton(ActionEvent event) {
    	QuoridorApplication.setScreen("StartGame");
    }

    @FXML
    void ClickGameRules(ActionEvent event) {

    }

    @FXML
    void ClickBasicControls(ActionEvent event) {

    }

    @FXML
    void ClickComputer(ActionEvent event) {

    }

    @FXML
    void ClickNetwork(ActionEvent event) {

    }

}
