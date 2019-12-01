package ca.mcgill.ecse223.quoridor.gui;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class HelpController {

    @FXML
    private ImageView networkImage;

    @FXML
    private Button computerButton;

    @FXML
    private Button GameRules;

    @FXML
    private Button controlsButton;

    @FXML
    private ImageView computerImage;

    @FXML
    private Button Back;

    @FXML
    private ImageView basicControlImage;

    @FXML
    private Button networkButton;

    @FXML
    private Pane pane;

    @FXML
    void BackButton(ActionEvent event) {
    	QuoridorApplication.setScreen("StartGame");
    }

    @FXML
    void gameRulesClick(ActionEvent event) {
    	System.out.println("button pressed mna");
    	computerImage.setVisible(true);
    	basicControlImage.setVisible(false);
    }

    @FXML
    void networkClick(ActionEvent event) {
    	
    }

    @FXML
    void controlsClick(ActionEvent event) {

    }

    @FXML
    void computerClick(ActionEvent event) {
    	computerImage.setVisible(true);
    }

}
