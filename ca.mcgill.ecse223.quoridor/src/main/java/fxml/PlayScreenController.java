package fxml;


import java.net.URL;
import java.util.ResourceBundle;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class PlayScreenController {

    @FXML
    private Button EndTurnLabel;

    @FXML
    private Label wallLabel;

    @FXML
    private ImageView WhitePlayerImage;

    @FXML
    private Pane pane;

    @FXML
    private Label timeLabel;

    @FXML
    private Button SaveGameLabel;

    @FXML
    private ImageView BlackPlayerImage;

    @FXML
    private Pane boardPane;

    @FXML
    void buttonClickedEndTurn(ActionEvent event) {

    }

    @FXML
    void buttonClickedSaveGame(ActionEvent event) {

    }

}
