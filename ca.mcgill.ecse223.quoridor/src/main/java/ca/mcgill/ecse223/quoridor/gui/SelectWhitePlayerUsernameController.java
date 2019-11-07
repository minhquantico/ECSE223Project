	package ca.mcgill.ecse223.quoridor.gui;

	import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import ca.mcgill.ecse223.quoridor.Controller;
import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

	public class SelectWhitePlayerUsernameController {

	    @FXML private ComboBox<String> comboBoxWhite;
	    @FXML private Button next;
	    @FXML private Pane pane;
	    
	    @FXML
	    void nextClicked(ActionEvent event) throws FileNotFoundException
	    {
	    	boolean isListedWhite = false;
	    	
	    	String whiteUsername = comboBoxWhite.getSelectionModel().getSelectedItem();
	    	
	    	ArrayList<User> list = new ArrayList<User>(QuoridorApplication.getQuoridor().getUsers());
	    	
	    	for(User u: list)
	    		if(u.getName().equals(whiteUsername))
	    			isListedWhite = true;
	    	
	    	if(!isListedWhite)
	    	{
	    		ca.mcgill.ecse223.quoridor.Controller.CreateNewUsername(whiteUsername);
	    		
	    		File file = new File(getClass().getClassLoader().getResource("Usernames.txt").getFile());
				try (PrintStream stream = new PrintStream(new FileOutputStream(file, true)))
				{
					stream.println(whiteUsername);
				}
	    	}
	    	
	    	if (whiteUsername.equals("Computer"))
	    		PlayScreenController.instance.board.players[0].setComputer(true);
	    	
	    	Controller.SelectExistingUsername(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer(), whiteUsername);
	    	QuoridorApplication.setScene("BlackSelectUsername");
	    }
	    
	    @FXML
	    void initialize() throws FileNotFoundException
	    {
			try (Scanner input = new Scanner(new File(getClass().getClassLoader().getResource("Usernames.txt").getFile())))
			{
				QuoridorApplication.getQuoridor().addUser("Computer");
				while(input.hasNextLine())
					QuoridorApplication.getQuoridor().addUser(input.nextLine());
			}
			
			for (User user : QuoridorApplication.getQuoridor().getUsers())
				comboBoxWhite.getItems().add(user.getName());
			comboBoxWhite.getSelectionModel().clearAndSelect(0);
	    }
	}
