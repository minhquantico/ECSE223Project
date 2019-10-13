package ca.mcgill.ecse223.quoridor.features.stepfiles;

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

public class InitializeBoard
{
	
	static class Controller{
		/**
		 * This method initializes the Quoridor Board for the current game
		 * 
		 * @when("The initialization of the board is initiated")
		 * @author Lenoy Christy
		 * @return Board - a Board object that is ready to be set up. 
		 */
		public  static Board initQuoridorBoard() { 
			throw new UnsupportedOperationException();
				
		}
	}
	
	/**
	 * @author Lenoy Christy
	 */
	@When("The initialization of the board is initiated")
	public void the_initialization_of_the_board_is_initiated() {
	    // Write code here that turns the phrase above into concrete actions
		Controller.initQuoridorBoard(); 
		
	    
	}
	
	/**
	 * @author Lenoy Christy
	 */
	@Then("It shall be white player to move")
	public void it_shall_be_white_player_to_move() {
//		Player currentPlayer = quoridor.getCurrentGame().getWhitePlayer();
//		QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size() getCurrentPosition().setPlayerToMove(currentPlayer);
	   assert (QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()) % 2 == 0 : "ERROR: NOT WHITE PLAYER'S TURN";
	}

	/**
	 * @author Lenoy Christy
	 */
	@Then("White's pawn shall be in its initial position")
	public void white_s_pawn_shall_be_in_its_initial_position() {
	    // Write code here that turns the phrase above into concrete actions
		assert ((QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getColumn() == 5) 
				&& (QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getRow() == 9))
				: "ERROR: WHITE PLAYER NOT IN CORRECT POS"; 
	}
	
	/**
	 * @author Lenoy Christy
	 */
	@Then("Black's pawn shall be in its initial position")
	public void black_s_pawn_shall_be_in_its_initial_position() {
	    // Write code here that turns the phrase above into concrete actions
		assert ((QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getColumn() == 5) 
				&& (QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getRow() == 1)) 
				: "ERROR: Black PLAYER NOT IN CORRECT POS";    
	}

	/**
	 * @author Lenoy Christy
	 */
	@Then("All of White's walls shall be in stock")
	public void all_of_White_s_walls_shall_be_in_stock() {
		assert QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().size() == 10 : "Error: White Player does not have 10 walls."; 	    
	}

	/**
	 * @author Lenoy Christy
	 */
	@Then("All of Black's walls shall be in stock")
	public void all_of_Black_s_walls_shall_be_in_stock() {
		assert QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().size() == 10 : "Error: Black Player does not have 10 walls.";
	}

	/**
	 * @author Lenoy Christy
	 */
	@Then("White's clock shall be counting down")
	public void white_s_clock_shall_be_counting_down() {
	    assert (isClockRunning()) : "ERROR: CLOCK NOT COUNTING DOWN";
	}

	/**
	 * @author Lenoy Christy
	 */
	@Then("It shall be shown that this is White's turn")
	public void it_shall_be_shown_that_this_is_White_s_turn() {
	    // Write code here that turns the phrase above into concrete actions
		//GUI  -- TODO FOR LATER
		throw new cucumber.api.PendingException();
	}
	//HELPER METHODS
	/**
	 * @author Lenoy Christy
	 * @return boolean
	 */
	public static boolean isClockRunning() { // CLOCK METHOD NOT YET IMPLEMENTED. TODO FOR LATER
		throw new cucumber.api.PendingException(); 
	}
	
	
}