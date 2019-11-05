package ca.mcgill.ecse223.quoridor.gui;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import ca.mcgill.ecse223.quoridor.Controller;
import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
public class SelectUsernameController {

	@FXML private ComboBox<String> comboBoxWhite;
    @FXML private ComboBox<String> comboBoxBlack;
    @FXML private Pane pane;
    @FXML private Button Next;

    @FXML
    void nextClicked(MouseEvent event) {
    	
    	
    	boolean isListedWhite = false;
    	boolean isListedBlack = false;
    	
    	String whiteUsername = comboBoxWhite.getSelectionModel().getSelectedItem();
    	String blackUsername = comboBoxBlack.getSelectionModel().getSelectedItem(); 
    	
    	ArrayList<User> list = new ArrayList<User>(QuoridorApplication.getQuoridor().getUsers());
    	
    	for(User u: list)
    	{
    		if(u.getName().equals(whiteUsername))
    			isListedWhite = true;
    		if(u.getName().equals(blackUsername))
    			isListedBlack = true;
    	}
    	
    	if(!isListedWhite)
    		ca.mcgill.ecse223.quoridor.Controller.CreateNewUsername(whiteUsername);
    	
    	if(!isListedBlack && whiteUsername != blackUsername)
    		ca.mcgill.ecse223.quoridor.Controller.CreateNewUsername(blackUsername);
    	
    	File file = new File(getClass().getClassLoader().getResource("Usernames.txt").getFile());
		try (PrintStream stream = new PrintStream(new FileOutputStream(file, true)))
		{
			System.out.println("huwhite: " + whiteUsername + ", bleck: " + blackUsername);
			if (!isListedWhite)
				stream.println(whiteUsername);
			if (!isListedBlack && !whiteUsername.equals(blackUsername))
				stream.println(blackUsername);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
    	
    	
    	Controller.SelectExistingUsername(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer(), whiteUsername);
    	Controller.SelectExistingUsername(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer(), blackUsername);

    	QuoridorApplication.setScene("thinkingScreen");
    }
    

    @FXML
    void initialize() throws FileNotFoundException
    {
    	try (Scanner input = new Scanner(new File(getClass().getClassLoader().getResource("Usernames.txt").getFile())))
    	{
			while(input.hasNextLine()) {
				String string = input.nextLine();
				System.out.println("Username: " + (string == null ? "Yes, is null" : string));
				QuoridorApplication.getQuoridor().addUser(string);
				
			}
			for (User user : QuoridorApplication.getQuoridor().getUsers())
			{
				comboBoxWhite.getItems().add(user.getName());
				comboBoxBlack.getItems().add(user.getName());
			}
			
			comboBoxWhite.getSelectionModel().select(0);
			comboBoxBlack.getSelectionModel().select(1);
		}
    }
}
