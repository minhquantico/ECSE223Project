package ca.mcgill.ecse223.quoridor.features.stepfiles;

import static org.junit.Assert.assertEquals;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.Controller;
import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.*;
import io.cucumber.java.After;
import io.cucumber.java.en.*;

public class GrabWall
{
	
	//GrabWall	
	
	static class Controller{
		/**  
		 * 	@author David Budaghyan
		@When("I try to grab a wall from my stock")
		*/
		public static void grabWallFromStock(Player aPlayer) {
			//not sure if we need aWall parameter. Depends on GUI
			// alternative: grabWallFromStock(Player aPlayer, Wall aWall)
			//based on our current GUI prediction, there is no need for it in this method, but might need to change!
			throw new java.lang.UnsupportedOperationException();
			
			//if we do change, then we can also return the aWall parameter.
		}
	}
	
	
	
		//1st scenario
		
		  
		 	/** @author David Budaghyan **/
			@Given("I have more walls on stock")
			public void i_have_more_walls_on_stock() {
				//WTF DO I DO HERE COLISS
			    
			}
			
			/** @author David Budaghyan **/
			@When("I try to grab a wall from my stock")
			public void i_try_to_grab_a_wall_from_my_stock() {
				
				Player currentPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
			    Controller.grabWallFromStock(currentPlayer);
			    
			    // might need to do the following based on how grabWallFromStock is implemented:
			    // wall = Controller.grabWallFromStock(currentPlayer, aWall);
			}
			
			/** @author David Budaghyan **/
			@Then("A wall move candidate shall be created at initial position")
			public void a_wall_move_candidate_shall_be_created_at_initial_position() {
				//if the wall that was taken out was the first wall,
				// then the following assertion is sufficient
				assertEquals(0, QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallPlaced().getId());
				
				//again, the alternative to the above would be the following:
				// assertEquals(wall.getId(), QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallPlaced().getId());
				
				//We will set the initial position of all Moves to be 1,1  --- doesn't matter what it is..
				assertEquals(1, QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getColumn());
				assertEquals(1, QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getRow());
			}
			
			/** @author David Budaghyan **/
			@And("I shall have a wall in my hand over the board")
			public void i_shall_have_a_wall_in_my_hand_over_the_board() {
				// GUI-related feature -- TODO for later
				throw new cucumber.api.PendingException();
			}
			
			/** @author David Budaghyan **/
			@And("The wall in my hand shall disappear from my stock")
			public void the_wall_in_my_hand_shall_disappear_from_my_stock() {
			
				assertEquals(9, QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().numberOfWhiteWallsInStock());
				//or, if we have the wall that was grabbed:
				//assertFalse(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().contains(wall));
			}
			

			
		//2nd scenario
			
			/** @author David Budaghyan **/
			@Given("I have no more walls on stock")
			public void i_have_no_more_walls_on_stock() {
				//Note that "I" is the white player.
				//We get the stock and initialize it to a new Array of size 0
			    List<Wall> whiteWallsInStock = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock();
			    whiteWallsInStock = new ArrayList<Wall>();
			}
			
			/** @author David Budaghyan **/
			@Then("I shall be notified that I have no more walls")
			public void i_shall_be_notified_that_I_have_no_more_walls() {
				// GUI-related feature -- TODO for later
			    throw new cucumber.api.PendingException();
			}
			
			/** @author David Budaghyan **/
			@Then("I shall have no walls in my hand")
			public void i_shall_have_no_walls_in_my_hand() {
				// GUI-related feature -- TODO for later
			    throw new cucumber.api.PendingException();
			}
}


	
