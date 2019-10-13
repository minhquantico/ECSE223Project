package ca.mcgill.ecse223.quoridor.features.stepfiles;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.Controller;
import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.*;
import io.cucumber.java.After;
import io.cucumber.java.en.*;

public class SwitchCurrentPlayer
{
	/**
	 * @author Lenoy Christy
	 * @param string
	 */
	@Given("The clock of {string} is running")
	public void the_clock_of_is_running(String string) {
	   if (isWhiteTurn()) { 
		   initClock(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer());
	   }
	   else initClock(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer());
	    throw new cucumber.api.PendingException();
	}
	
	/**
	 * @author Lenoy Christy
	 * @param string
	 */
	@Given("The clock of {string} is stopped")
	public void the_clock_of_is_stopped(String string) {
		if (isWhiteTurn()) { 
			   stopClock(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer());
		   }
		   else stopClock(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer());
	    throw new cucumber.api.PendingException();
	}
	
	/**
	 * @author Lenoy Christy
	 * @param string
	 */
	@When("Player {string} completes his move")
	public void player_completes_his_move(String string) {
		if (isWhiteTurn()) { 
			   Controller.endMove(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer());
		   }
		   else Controller.endMove(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer());

	    throw new cucumber.api.PendingException();
	}
	
	/**
	 * @author Lenoy Christy
	 * @param string
	 */
	@Then("The user interface shall be showing it is {string} turn")
	public void the_user_interface_shall_be_showing_it_is_turn(String string) {
		//GUI -- TODO FOR LATER
	}
	
	/**
	 * @author Lenoy Christy
	 * @param string
	 */
	@Then("The clock of {string} shall be stopped")
	public void the_clock_of_shall_be_stopped(String string) {
		if (isWhiteTurn()) {
			assert QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getRemainingTime().getTime() == 0 : "ERROR: CLOCK NOT STOPPED";
		}
		else assert QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().getRemainingTime().getTime() == 0 : "ERROR: CLOCK NOT STOPPED";
	}
	
	/**
	 * @author Lenoy Christy
	 * @param string
	 */
	@Then("The clock of {string} shall be running")
	public void the_clock_of_shall_be_running(String string) {
	    assert isClockRunning(); 
	}
	
	/**
	 * @author Lenoy Christy
	 * @param string
	 */
	@Then("The next player to move shall be {string}")
	public void the_next_player_to_move_shall_be(String string) {
		if (isWhiteTurn()) {
			assert nextPlayer().equals(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer());
		}
		else assert nextPlayer().equals(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer());
	    
	}
	
	//HELPER METHODS
	
	/**
	 * @author Lenoy Christy
	 * @return boolean
	 */
	public static boolean isWhiteTurn( ) {
		if ( (QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()) % 2 == 0) return true; 
		else return false; 
		
	}
	
	/**
	 * @author Lenoy Christy 
	 * @return Player
	 */
	public static Player nextPlayer() {
		if (isWhiteTurn()) return QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer();
		else return QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
	}
	
	/**
	 * @author Lenoy Christy
	 * @return boolean
	 */
	public static boolean isClockRunning() { // CLOCK-METHOD NOT IMPLEMENTED. TODO FOR ITERATION 3
		throw new cucumber.api.PendingException(); 
	}
	
	/**
	 * @author Lenoy Christy
	 * @param player
	 */
	public static void initClock(Player player) { // CLOCK METHOD NOT IMPLEMENTED. TODO FOR ITERATION 3
		throw new cucumber.api.PendingException(); 
	}
	
	/**
	 * @author Lenoy Christy
	 * @param player
	 */
	public static void stopClock(Player player) { // CLOCK METHOD NOT IMPLEMENTED. TODO FOR ITERATION 3
		throw new UnsupportedOperationException();
	}
	
	static class Controller {
		/**
		 * This controller method ends the move of the player passed as an argument.
		 * @author Lenoy Christy
		 * @when ("Player {string} completes his move")
		 * @param player - The player whose move it currently is.
		 * @return GamePosition - a GamePosition object with updated information on the player positions and the next player to move. 
		 */
		public static GamePosition endMove(Player player) {
			throw new UnsupportedOperationException();
		}
		
	}
}