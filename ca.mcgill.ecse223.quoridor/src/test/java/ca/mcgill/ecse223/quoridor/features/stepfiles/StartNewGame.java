package ca.mcgill.ecse223.quoridor.features.stepfiles;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.features.CucumberStepDefinitions;
import ca.mcgill.ecse223.quoridor.model.*;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import io.cucumber.java.After;
import io.cucumber.java.en.*;

//Given model api (action, change something with quoridorapplication)
//when controller
//then model api assert

public class StartNewGame
{
	
	/** @author Minh Quan Hoang **/
	//Initialize a new game of Quoridor
	@When("A new game is being initialized")
	public void a_new_game_is_being_initialized() {
	    Controller.InitializeNewGame();
	}
	/** @author Minh Quan Hoang **/
	//Get username for white player from player class
	@When("White player chooses a username")
	public void white_player_chooses_a_username() {
	    Controller.setWhitePlayerUsername();
	}
	/** @author Minh Quan Hoang **/
	//Get username for black player from player class
	@When("Black player chooses a username")
	public void black_player_chooses_a_username() {
	    Controller.setBlackPlayerUsername();
	}
	/** @author Minh Quan Hoang **/
	//Sets the total thinking time
	@When("Total thinking time is set")
	public void total_thinking_time_is_set() {
		Controller.setTotalThinkingTime();
	}
	/** @author Minh Quan Hoang **/
	//Checks if the game is ready to start
	@Then("The game shall become ready to start")
	public void the_game_shall_become_ready_to_start() {
	    assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus().equals(Game.GameStatus.ReadyToStart));
	}
	/** @author Minh Quan Hoang **/
	//Changes the status of the game to be ready to start
	@Given("The game is ready to start")
	public void the_game_is_ready_to_start() {
	    QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(Game.GameStatus.ReadyToStart);
	    
	}
	/** @author Minh Quan Hoang **/
	//Start running the game and initialize board (create and start game method)
	@When("I start the clock")
	public void i_start_the_clock() {
	    Controller.StartClock();
	}
	/** @author Minh Quan Hoang **/
	//Checks if the game status is set to running
	@Then("The game shall be running")
	public void the_game_shall_be_running() {
	    assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus().equals(Game.GameStatus.Running));
	}
	/** @author Minh Quan Hoang **/
	//Checks if the board has been initialized
	@Then("The board shall be initialized")
	public void the_board_shall_be_initialized() {
	    assertTrue(QuoridorApplication.getQuoridor().hasBoard());
	}
	
	private static class Controller{
		/** @author Minh Quan Hoang **/
		//Initializes a new game and changes the status of the game to initializing
		public static void InitializeNewGame() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}
		/** @author Minh Quan Hoang **/
		//Starts the clock
		public static void StartClock() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}
		/** @author Minh Quan Hoang **/
		// Sets the total thinking time after the game is initialized
		public static void setTotalThinkingTime() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}
		/** @author Minh Quan Hoang **/
		//Selects the username for the black player
		public static void setBlackPlayerUsername() {
			/* The player selects a username from the list of users from the GUI or inputs a new one. 
			If he inputs a new one, then this method calls the CreateNewUserName controller method to create
			a new user with the username. Then the method calls the SelectExistingUsername controller method
			and sets the username for the black player. */ 
			throw new UnsupportedOperationException();
		}
		/** @author Minh Quan Hoang **/
		//Selects the username for the white player
		public static void setWhitePlayerUsername() {
			//This method does the same as the method above but for the white player
			throw new UnsupportedOperationException();
		}
		
	}

}
