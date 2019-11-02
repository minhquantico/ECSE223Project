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
	    void clickedNext(ActionEvent event) {
	    	MainController.instance.setScreen("thinkingScreen");

	    }
	    
	    @FXML
	    void initialize()
	    {
	    	try {
	    		File file = new File(getClass().getClassLoader().getResource("Usernames.txt").getFile());
				Scanner input = new Scanner(file);
				while(input.hasNext()) {
				comboBoxWhite.getItems().add(input.nextLine());
				}
				File file2 = new File(getClass().getClassLoader().getResource("Usernames.txt").getFile());
				Scanner input2 = new Scanner(file2);
				while(input2.hasNext()) {
				comboBoxBlack.getItems().add(input2.nextLine());
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
}
