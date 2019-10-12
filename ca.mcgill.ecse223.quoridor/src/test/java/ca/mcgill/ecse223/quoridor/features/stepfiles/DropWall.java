package ca.mcgill.ecse223.quoridor.features.stepfiles;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.*;
import cucumber.api.PendingException;
import io.cucumber.java.After;
import io.cucumber.java.en.*;



public static class Controller{
	
	
	
	/**
	   * @author Jake Pogharian
	   * This method is used to get the location over which wall is hovering from the GUI and then to return 
	   * the Tile object bearing the coordinates of that area
	   * @return Tile This returns sum of numA and numB.
	   */
	private Tile getTile() 
	{
		 throw new java.lang.UnsupportedOperationException();
	}
	
	
	
	/**
	   *  @author Jake Pogharian
	   * This method is used to perform the act of dropping a wall. It will perform the necessary changes 
	   * to both the View and the Model. For the view it will remove the wall from the hand, notify in case of invalid attempted move, etc.
	   * For the model, it will register the wall move and complete the move when it is in fact valid, change whose turn it is, etc.
	   * @param Tile t: This is a parameter of type Tile and will be used by the method to know where to perform the wall drop.
	   */
	private void dropWall(Tile t) 
	{
		 throw new java.lang.UnsupportedOperationException();	
	}
	
}




public class DropWall
{
	/**
	   *  @author Jake Pogharian
	*/
	@Given("The wall move candidate with {string} at position \\({int}, {int}) is valid")
	public void the_wall_move_candidate_with_at_position_is_valid(String string, Integer int1, Integer int2) {
		
		
		String dir = string;
		//transform Row and Column to Tile Index
		int row = int1;
		int column = int2;
		int index= ((row -1)*9)+column;
		
		Direction aWallDirection = Direction.valueOf(dir);
		int aMoveNumber=QuoridorApplication.getQuoridor().getCurrentGame().numberOfMoves()+1;
		int aRoundNumber=aMoveNumber/2+1;
		Player aPlayer=QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove();
		Tile aTargetTile=QuoridorApplication.getQuoridor().getBoard().getTile(index);
		Game aGame=QuoridorApplication.getQuoridor().getCurrentGame();
		Wall aWallPlaced;
		
		//get index of top-most wall in stock for given player

		
		//select top most wall in stock for currentPlayer
		//depending on whether or not player is white or black proceed
		Player currentPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove();
		if(currentPlayer.hasGameAsWhite()) {
			int lastWallIndex = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().size() - 1;
			aWallPlaced=QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock(lastWallIndex);
		}else {
			int lastWallIndex = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsInStock().size() - 1;
			aWallPlaced=QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsInStock(lastWallIndex);	
		}
		
		//create and set CandidateWallMove 
		WallMove candidateWallMove = new WallMove(aMoveNumber, aRoundNumber, aPlayer, aTargetTile, aGame, aWallDirection, aWallPlaced);
	   
		QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(candidateWallMove);
		
	}

	@When("I release the wall in my hand")
	public void i_release_the_wall_in_my_hand() {
		throw new PendingException();
		// To implement
		// Tile t = Controller.getTile();
		// Controller.dropWall(t);
	}


	
	@Then("A wall move shall be registered with {string} at position \\({int}, {int})")
	public void a_wall_move_shall_be_registered_with_at_position(String string, Integer int1, Integer int2) {

		String aWallDirection = string;
		Direction dir = Direction.valueOf(aWallDirection);
		
	    int numberOfWalls;
		WallMove lastWallMove;
		Player currentPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove();
	
		if(currentPlayer.hasGameAsWhite()) {
			numberOfWalls = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().numberOfWhiteWallsOnBoard();
			lastWallMove=QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsOnBoard().get(numberOfWalls - 1).getMove();
		} else {
			numberOfWalls = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().numberOfBlackWallsOnBoard();
			lastWallMove=QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsOnBoard().get(numberOfWalls - 1).getMove();
			
		}
		
		assertTrue(lastWallMove.getWallDirection()==dir && lastWallMove.getTargetTile().getRow()==int1 && lastWallMove.getTargetTile().getColumn()==int2 && lastWallMove.getNextMove()==null);	

		
	}

	
	
	
	
	
	@Then("I shall not have a wall in my hand")
	public void i_shall_not_have_a_wall_in_my_hand() {
	    
		//this is a GUI element 
	    throw new cucumber.api.PendingException();
	}
	
	
	
	@Then("My move shall be completed")
	public void my_move_shall_be_completed() {
		
		//Check that last move in Moves List belongs to player whose turn it currently is -- this shall verify that the move is completed, (it is only subsequent to the completion of the move that the turn is completed)
		
		//this is to get index of last move in moves List
		int index= QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size() -1;
		if(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite()) {
			//if turn is of white player (if I am white player)
			assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().get(index).getPlayer().hasGameAsWhite());
		} else {
			//if turn is of black player (if i am black player)
			assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().get(index).getPlayer().hasGameAsBlack());
		}
	}


	@Then("It shall not be my turn to move")
	public void it_shall_not_be_my_turn_to_move() {
	 
		//assuming I am player1 (white)
		assertFalse(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite());
	}

	@Given("The wall move candidate with {string} at position \\({int}, {int}) is invalid")
	public void the_wall_move_candidate_with_at_position_is_invalid(String string, Integer int1, Integer int2) {
		//transform Row and Column to Tile Index!
				int row = int1;
				int column = int2;
				int index= ((row -1)*9)+column;
				String dir = string;
				Direction aWallDirection = Direction.valueOf(dir);
				int aMoveNumber=QuoridorApplication.getQuoridor().getCurrentGame().numberOfMoves()+1;
				int aRoundNumber=aMoveNumber/2+1;
				Player aPlayer=QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove();
				Tile aTargetTile=QuoridorApplication.getQuoridor().getBoard().getTile(index);
				Game aGame=QuoridorApplication.getQuoridor().getCurrentGame();
				Wall aWallPlaced;
				
				//get index of top-most wall in stock for given player

				Player currentPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove();
				//select top most wall in stock for currentPlayer
				if(currentPlayer.hasGameAsWhite()) {
					int lastWallIndex = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().size() - 1;
					aWallPlaced=QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock(lastWallIndex);
				}else {
					int lastWallIndex = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsInStock().size() - 1;
					aWallPlaced=QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsInStock(lastWallIndex);	
				}
				
				

				//create and set CandidateWallMove 
				WallMove candidateWallMove = new WallMove(aMoveNumber, aRoundNumber, aPlayer, aTargetTile, aGame, aWallDirection, aWallPlaced);
			   
				QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(candidateWallMove);
		    

		
	}

	@Then("I shall be notified that my wall move is invalid")
	public void i_shall_be_notified_that_my_wall_move_is_invalid() {
	    // this is a GUI element, therefore do not implement
	    throw new cucumber.api.PendingException();
	}

	@Then("I shall have a wall in my hand over the board")
	public void i_shall_have_a_wall_in_my_hand_over_the_board() {
	    // This is a GUI element, therefore implement in next deliverable 
	    throw new cucumber.api.PendingException();
	}

	@Then("It shall be my turn to move")
	public void it_shall_be_my_turn_to_move() {
	    //assuming I am player 1 and that player 1 is white
				 assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite());
	 
	}

	@Then("No wall move shall be registered with {string} at position \\({int}, {int})")
	public void no_wall_move_shall_be_registered_with_at_position(String string, Integer int1, Integer int2) {
		//check that the last wallMove placed on board is NOT in fact the given wall (wall candidate)
		
		
		String aWallDirection = string;
		//get enum representing the direction of the wall
		Direction dir = Direction.valueOf(aWallDirection);

		int numberOfWalls;
		WallMove lastWallMove;	
		//checks if the player in question is white or black and then checks accordingly 
			if(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite()) {
				numberOfWalls = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().numberOfWhiteWallsOnBoard();
				lastWallMove=QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsOnBoard().get(numberOfWalls - 1).getMove();
				
			} else {
				numberOfWalls = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().numberOfBlackWallsOnBoard();
				lastWallMove=QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsOnBoard().get(numberOfWalls - 1).getMove();
				
			}
			
		assertTrue(lastWallMove.getWallDirection()!=dir || lastWallMove.getTargetTile().getRow()!=int1 || lastWallMove.getTargetTile().getColumn()!=int2 || lastWallMove.getNextMove()!=null) ;	

	    
	}
	
}
