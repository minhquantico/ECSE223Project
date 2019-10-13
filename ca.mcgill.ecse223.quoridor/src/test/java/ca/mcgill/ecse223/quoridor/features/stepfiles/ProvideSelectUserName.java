package ca.mcgill.ecse223.quoridor.features.stepfiles;

import static org.junit.Assert.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.Controller;
import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.features.CucumberStepDefinitions;
import ca.mcgill.ecse223.quoridor.model.*;
import io.cucumber.java.After;
import io.cucumber.java.en.*;

//Given model api
//when controller
//then model api

// throw UnsupportedOperationException

public class ProvideSelectUserName { 

	/** @author Minh Quan Hoang **/
	//Initiates Quoridor, a Board and the users and players
	@Given("A new game is initializing")
	public void a_new_game_is_initializing() {
	    CucumberStepDefinitions.initQuoridorAndBoard();
	    CucumberStepDefinitions.createUsersAndPlayers("user1", "user2");
	}
	/** @author Minh Quan Hoang **/
	//Chooses the next player to set the username
	@Given("Next player to set user name is {string}")
	public void next_player_to_set_user_name_is(String string) {
		if(string == "black")
	    QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().setUser(null);
		
		else if(string == "white")
		QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().setUser(null);
	}
	/** @author Minh Quan Hoang **/
	//Adds a user with the username from the string
	@And("There is existing user {string}")
	public void there_is_existing_user(String string) {
		QuoridorApplication.getQuoridor().addUser(string);
	}
	/** @author Minh Quan Hoang **/
	//Link the player with his username
	@When("The player selects existing {string}")
	public void the_player_selects_existing(String string) {
	    Controller.SelectExistingUsername(string);
	}
	/** @author Minh Quan Hoang **/
	//Check if the name of the player is the new username
	@Then("The name of player {string} in the new game shall be {string}")
	public void the_name_of_player_in_the_new_game_shall_be(String string, String string2) {
		if(string.equals("black"))
		assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().getUser().getName().equals(string2));
		else if(string.equals("white"))
		assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getUser().getName().equals(string2));
	
	}
	/** @author Minh Quan Hoang **/
	//Checks for a user with the string username and deletes it if it exists
	@Given("There is no existing user {string}")
	public void there_is_no_existing_user(String string) {
		List<User> list = QuoridorApplication.getQuoridor().getUsers();
		
		for(User u: list) {
			if(u.getName().equals(string))
				QuoridorApplication.getQuoridor().removeUser(u);
	    
		}
	}
	/** @author Minh Quan Hoang **/
	//Creates a new username with the string as username
	@When("The player provides new user name: {string}")
	public void the_player_provides_new_user_name(String string) {
	    Controller.CreateNewUsername(string);
	}
	/** @author Minh Quan Hoang **/
	//Checks whether the username string already exists
	@Then("The player shall be warned that {string} already exists")
	public void the_player_shall_be_warned_that_already_exists(String string) {
		List<User> list = QuoridorApplication.getQuoridor().getUsers();
		
		boolean exists = false;
		for(User u: list) {
			exists = exists | u.getName().equals(string);
	    
		}
		assertTrue(exists);
	}
	/** @author Minh Quan Hoang **/
	//Checks whether the next player does not have a user yet, meaning it would be his turn to set the username
	@Then("Next player to set user name shall be {string}")
	public void next_player_to_set_user_name_shall_be(String string) {
		if(string.equals("black")) {
	    assertNull(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().getUser());
		}
	    else if(string.equals("white")){
	    assertNull(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getUser());
	    }
	}

	private static class Controller{
		/** @author Minh Quan Hoang **/
		/* This method selects an existing username from the list of users already created.
		It takes an input string that represents the username and searches the list of users to find it.
		If there is a match, the user with that username is linked to the player. If there is no match,
		the method does not link the user with the player and notifies the player that there exists no
		user with that username. */
		public static void SelectExistingUsername(String string) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}
		/** @author Minh Quan Hoang **/
		//This method creates a new username by creating a new user and adding it to the list of users
		public static void CreateNewUsername(String string) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}
		
	}
	
}
