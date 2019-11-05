	package ca.mcgill.ecse223.quoridor.gui;

	import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import ca.mcgill.ecse223.quoridor.Controller;
import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class SelectBlackPlayerUsernameController {

    @FXML private ComboBox<String> comboBoxBlack;
    @FXML private Button Next;
    @FXML private Pane pane;
    
    @FXML
    void nextClicked(MouseEvent event) {
    	
    	boolean isNull = false;
    	if(comboBoxBlack.getSelectionModel().getSelectedItem() == null) {
    		System.out.println("Nooooo");
    		isNull = true;
    	}
    	
    	if(!isNull) {
    	boolean isListedBlack = false;
    	String blackUsername = comboBoxBlack.getSelectionModel().getSelectedItem(); 
    	
    	
    	ArrayList<User> list = new ArrayList<User>(QuoridorApplication.getQuoridor().getUsers());
    	
    	for(User u: list) {
    		if(u.getName().equals(blackUsername)) {
    			isListedBlack = true;
    		}
    		
    	}
    	
    	
    	if(!isListedBlack) {
    		ca.mcgill.ecse223.quoridor.Controller.CreateNewUsername(blackUsername);
    		
    		File file = new File(getClass().getClassLoader().getResource("Usernames.txt").getFile());

			try (PrintStream stream = new PrintStream(new FileOutputStream(file, true))) {
				stream.println(blackUsername);
			} catch (IOException e) {
				e.printStackTrace();
				}
    		
    		}
    	
    	Controller.SelectExistingUsername(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer(), blackUsername);

    	QuoridorApplication.setScene("ThinkingScreen");
    	}
    }
    
    @FXML
    void initialize()
    {
		for (User user : QuoridorApplication.getQuoridor().getUsers())
		{
			comboBoxBlack.getItems().add(user.getName());
		}
		comboBoxBlack.getSelectionModel().select(1);
    }
	    
}