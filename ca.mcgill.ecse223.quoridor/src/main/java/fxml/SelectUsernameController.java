package fxml;
import java.net.URL;
import java.util.ResourceBundle;



import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
public class SelectUsernameController {

    @FXML
    private ComboBox<?> comboBoxWhite;

    @FXML
    private ComboBox<?> comboBoxBlack;

    @FXML
    private Pane pane;
    
    @FXML
    private Button Next;
    

    @FXML
    void nextClicked(ActionEvent event) {

    }

    @FXML
    void nextButtonClick(ActionEvent event) {
    	MainController.instance.setScreen("thinkingScreen");
    }
}
