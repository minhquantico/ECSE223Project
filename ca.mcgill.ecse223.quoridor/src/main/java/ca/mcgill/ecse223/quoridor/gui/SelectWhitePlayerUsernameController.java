	package ca.mcgill.ecse223.quoridor.gui;

	import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.Controller;
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
	    		ca.mcgill.ecse223.quoridor.controller.Controller.CreateNewUsername(whiteUsername);
	    		
	    		try (PrintStream stream =
						new PrintStream(
								new FileOutputStream(
										new File(
												QuoridorApplication.class.getClassLoader().getResource("Usernames.txt").toURI().getPath()), true)))
				{
					stream.println(whiteUsername);
				}
				catch (URISyntaxException ex) { System.err.println(ex.getMessage()); }
	    	}
	    	
	    	Controller.SelectExistingUsername(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer(), whiteUsername);
	    	QuoridorApplication.setScreen("BlackSelectUsername");
	    }
	    
	    @FXML
	    void initialize() throws FileNotFoundException
	    {
			try (Scanner input = new Scanner(new File(QuoridorApplication.class.getClassLoader().getResource("Usernames.txt").toURI().getPath())))
			{
				QuoridorApplication.getQuoridor().addUser("Computer");
				while(input.hasNextLine())
					QuoridorApplication.getQuoridor().addUser(input.nextLine());
			}
			catch (URISyntaxException ex) { System.err.println(ex.getMessage()); }
			
			for (User user : QuoridorApplication.getQuoridor().getUsers())
				comboBoxWhite.getItems().add(user.getName());
			comboBoxWhite.getSelectionModel().clearAndSelect(0);
	    }
	}
