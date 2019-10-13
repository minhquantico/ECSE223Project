package ca.mcgill.ecse223.quoridor.features.stepfiles;
	
	import static org.junit.jupiter.api.Assertions.assertTrue;
	
	import java.sql.Time;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.Map;
	
	import ca.mcgill.ecse223.quoridor.Controller;
	import ca.mcgill.ecse223.quoridor.QuoridorApplication;
	import ca.mcgill.ecse223.quoridor.model.*;
	import io.cucumber.java.After;
	import io.cucumber.java.en.*;
	
	public class MoveWall
	{
		
		static class Controller {
			
			/**  
			 * 	@author David Budaghyan
			@When("I try to move the wall {string}")
			 */
			public static void moveWall(String direction) {
				//again, might need a wall parameter.
				//GUI dependent decision, i.e. we will decide later
				throw new java.lang.UnsupportedOperationException();
			}
			
		}
		
		
		//1st scenario
					
					/** @author David Budaghyan **/
					@Given("A wall move candidate exists with {string} at position \\({int}, {int})")
					public void a_wall_move_candidate_exists_with_at_position(String dir, Integer aRow, Integer aCol) {
						
						//create everything needed to create the wallMoveCandidate
						Player currentPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
						Tile targetTile = new Tile(aRow, aCol, QuoridorApplication.getQuoridor().getBoard());
						Wall wallCandidate = new Wall(0, currentPlayer);
						Game game = QuoridorApplication.getQuoridor().getCurrentGame();
						Direction wallDirection;
						if(dir.equalsIgnoreCase("vertical")) {
							wallDirection = Direction.Vertical;
						}else {
							wallDirection = Direction.Horizontal;
						}
						
						//create and set the wallMoveCandidate
						WallMove wallMoveCandidate = new WallMove(0, 1, currentPlayer, targetTile, game, wallDirection, wallCandidate);
						QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(wallMoveCandidate);
					}

					/** @author David Budaghyan **/
					@Given("The wall candidate is not at the {string} edge of the board")
					public void the_wall_candidate_is_not_at_the_edge_of_the_board(String side) {
						// GUI-related feature -- TODO for later
					    throw new cucumber.api.PendingException();
					}
					
					/** @author David Budaghyan **/
					@When("I try to move the wall {string}")
					public void i_try_to_move_the_wall(String dir) {
					    Controller.moveWall(dir);
					    // it might also be:
					    //Controller.moveWall(dir, wall)
					    //see controller
					}
					
					/** @author David Budaghyan **/
					@Then("The wall shall be moved over the board to position \\({int}, {int})")
					public void the_wall_shall_be_moved_over_the_board_to_position(Integer int1, Integer int2) {
						// GUI-related feature -- TODO for later
					    throw new cucumber.api.PendingException();
					}
					
					/** @author David Budaghyan **/
					@Then("A wall move candidate shall exist with {string} at position \\({int}, {int})")
					public void a_wall_move_candidate_shall_exist_with_at_position(String direction, Integer aRow, Integer aCol) {
					    assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getRow() == aRow);
					    assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getColumn() == aCol);
					    Direction d;
					    if("vertical".equalsIgnoreCase(direction)) {
					    	d = Direction.Vertical;
					    }else {
					    	d = Direction.Horizontal;
					    }
					    assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallDirection().equals(d));
					}					
					
					
	//2nd scenario
					/** @author David Budaghyan **/
					@Given("The wall candidate is at the {string} edge of the board")
					public void the_wall_candidate_is_at_the_edge_of_the_board(String string) {
						// GUI-related feature -- TODO for later
					    throw new cucumber.api.PendingException();
					}
					
					/** @author David Budaghyan **/
					@Then("I shall be notified that my move is illegal")
					public void i_shall_be_notified_that_my_move_is_illegal() {
						// GUI-related feature -- TODO for later
					    throw new cucumber.api.PendingException();
					}
		}
