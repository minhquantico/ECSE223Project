package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.Controller;
import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import cucumber.api.PendingException;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CucumberStepDefinitions {
	

	// ***********************************************
	// Background step definitions
	// ***********************************************

 
	@Given("^The game is not running$")
	public static void theGameIsNotRunning() {
		initQuoridorAndBoard();
		createUsersAndPlayers("user1", "user2");
	}

	@Given("^The game is running$")
	public static void theGameIsRunning() {
		initQuoridorAndBoard();
		ArrayList<Player> createUsersAndPlayers = createUsersAndPlayers("user1", "user2");
		createAndStartGame(createUsersAndPlayers);
	}

	@And("^It is my turn to move$")
	public static void itIsMyTurnToMove() throws Throwable {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Player currentPlayer = quoridor.getCurrentGame().getWhitePlayer();
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setPlayerToMove(currentPlayer);
	}

	@Given("The following walls exist:")
	public static void theFollowingWallsExist(io.cucumber.datatable.DataTable dataTable) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		// keys: wrow, wcol, wdir
		Player[] players = { quoridor.getCurrentGame().getWhitePlayer(), quoridor.getCurrentGame().getBlackPlayer() };
		int playerIdx = 0;
		int wallIdxForPlayer = 0;
		for (Map<String, String> map : valueMaps) {
			Integer wrow = Integer.decode(map.get("wrow"));
			Integer wcol = Integer.decode(map.get("wcol"));
			// Wall to place
			// Walls are placed on an alternating basis wrt. the owners
			//Wall wall = Wall.getWithId(playerIdx * 10 + wallIdxForPlayer);
			Wall wall = players[playerIdx].getWall(wallIdxForPlayer); // above implementation sets wall to null

			String dir = map.get("wdir");

			Direction direction;
			switch (dir) {
			case "horizontal":
				direction = Direction.Horizontal;
				break;
			case "vertical":
				direction = Direction.Vertical;
				break;
			default:
				throw new IllegalArgumentException("Unsupported wall direction was provided");
			}
			new WallMove(0, 1, players[playerIdx], quoridor.getBoard().getTile((wrow - 1) * 9 + wcol - 1), quoridor.getCurrentGame(), direction, wall);
			if (playerIdx == 0) {
				quoridor.getCurrentGame().getCurrentPosition().removeWhiteWallsInStock(wall);
				quoridor.getCurrentGame().getCurrentPosition().addWhiteWallsOnBoard(wall);
			} else {
				quoridor.getCurrentGame().getCurrentPosition().removeBlackWallsInStock(wall);
				quoridor.getCurrentGame().getCurrentPosition().addBlackWallsOnBoard(wall);
			}
			wallIdxForPlayer = wallIdxForPlayer + playerIdx;
			playerIdx++;
			playerIdx = playerIdx % 2;
		}
		System.out.println();

	}

	@And("I do not have a wall in my hand")
	public static void iDoNotHaveAWallInMyHand() {
		// GUI-related feature -- TODO for later
	}
	
	@And("^I have a wall in my hand over the board$")
	public static void iHaveAWallInMyHandOverTheBoard() throws Throwable {
		// GUI-related feature -- TODO for later
	}
	
	// ***********************************************
	// Scenario and scenario outline step definitions
	// ***********************************************

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//////////////////////for DROP WALL ////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////
	
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
		// Tile t = Controller.getTile(row, col);
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
	
	
	
////////MUST COMPLETE THIS STEP DEFINITION!!!!!!!
	
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////FEATURE 2: SET TOTAL THINKING TIME////////////////////////////////////////////
	
	
	@When("{int}:{int} is set as the thinking time")
	public void is_set_as_the_thinking_time(Integer int1, Integer int2) {
		throw new PendingException();
		//Controller.InitializeThinkingTime(int1, int2);

	}

	@Then("Both players shall have {int}:{int} remaining time left")
	public void both_players_shall_have_remaining_time_left(Integer int1, Integer int2) {
		Time time = new Time(0, int1, int2);
		assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().getRemainingTime().toString().equals(time.toString()) && QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().getNextPlayer().getRemainingTime().toString().equals(time.toString()));

	}
	
	
	
	
	
	
	//Controller.InitializeThinkingTime(int1, int2);
	// Tile t = Controller.getTile(row, col);
			// Controller.dropWall(t);
	
	
	
	
	
	
	
	
	
	
	
<<<<<<< Updated upstream
	
	
=======
	//Step: Drop Wall - Jake Pogharian 
					
					/**
					   *  @author Jake Pogharian
					   *  Feature: Drop Wall
					   *  Step: " Given the wall move candidate with {string} at position \\({int}, {int}) is valid"
					   *  @param string: This is the String used to indicate the wall move candidate's direction. 
					   *  @param int1: This is the Integer used to indicate the row of the wall move candidate 
					   *  @param int2: This is the Integer used to indicate the column of the wall move candidate
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
						//validate according to which player turn it is currently
						if(currentPlayer.hasGameAsWhite()) {
							//get index of the last wall in stock for given player
							int lastWallIndex = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().size() - 1;
							//get the last wall in stock
							aWallPlaced=QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock(lastWallIndex);
						}else {
							int lastWallIndex = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsInStock().size() - 1;
							aWallPlaced=QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsInStock(lastWallIndex);	
						}
						
						//create wall move 
						WallMove candidateWallMove = new WallMove(aMoveNumber, aRoundNumber, aPlayer, aTargetTile, aGame, aWallDirection, aWallPlaced);
					   //set it as the candidate wall move
						QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(candidateWallMove);
						
					}
					/**
					   *  @author Jake Pogharian
					   *  Feature: Drop Wall
					   *  Step: ("When I release the wall in my hand")
					*/

					@When("I release the wall in my hand")
					public void i_release_the_wall_in_my_hand() {
						throw new PendingException();
						// To implement
						// Tile t = Controller.getTile();
						// Controller.dropWall(t);
					}


					/**
					   *  @author Jake Pogharian
					   *  Feature: Drop Wall
					   *  Step: ("Then A wall move shall be registered with {string} at position \\({int}, {int})")
					   *  @param string: This is the String used to indicate the wall move direction. 
					   *  @param int1: This is the Integer used to indicate the row of the wall move  
					   *  @param int2: This is the Integer used to indicate the column of the wall move 
					*/
					@Then("A wall move shall be registered with {string} at position \\({int}, {int})")
					public void a_wall_move_shall_be_registered_with_at_position(String string, Integer int1, Integer int2) {

						String aWallDirection = string;
						Direction dir = Direction.valueOf(aWallDirection);
						
					    int numberOfWalls;
						WallMove lastWallMove;
						Player currentPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove();
					
						if(currentPlayer.hasGameAsWhite()) {
							//get index in wallsOnBoard list of last wall on board
							numberOfWalls = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().numberOfWhiteWallsOnBoard();
							//get last wall on board
							lastWallMove=QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsOnBoard().get(numberOfWalls - 1).getMove();
						} else {
							numberOfWalls = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().numberOfBlackWallsOnBoard();
							lastWallMove=QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsOnBoard().get(numberOfWalls - 1).getMove();
							
						}
						
						//check that the last wall placed on the board, was placed during this turn and that it corresponds to those coordinates and direction
						assertTrue(lastWallMove.getWallDirection()==dir && lastWallMove.getTargetTile().getRow()==int1 && lastWallMove.getTargetTile().getColumn()==int2 && lastWallMove.getNextMove()==null);	

						
					}

					
					
					
					/**
					   *  @author Jake Pogharian
					   *  Feature: Drop Wall
					   *  Step: ("Then I shall not have a wall in my hand")
					*/
					
					@Then("I shall not have a wall in my hand")
					public void i_shall_not_have_a_wall_in_my_hand() {
					    
						//this is a GUI element 
					    throw new cucumber.api.PendingException();
					}
					
					
					/**
					   *  @author Jake Pogharian
					   *  Feature: Drop Wall
					   *  Step: "Then my move shall be completed"
					*/
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

					/**
					   *  @author Jake Pogharian
					   *  Feature: Drop Wall
					   *  Step: "Then it shall not be my turn to move"
					*/
					@Then("It shall not be my turn to move")
					public void it_shall_not_be_my_turn_to_move() {
					 
						//assuming I am player1 (white)
						assertFalse(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite());
					}

					/**
					   *  @author Jake Pogharian
					   *  Feature: Drop Wall
					   *  Step: "Given the wall move candidate with {string} at position \\({int}, {int}) is invalid"
					   *  @param string: This is the String used to indicate the wall move direction. 
					   *  @param int1: This is the Integer used to indicate the row of the wall move  
					   *  @param int2: This is the Integer used to indicate the column of the wall move
					   *  
					*/
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
					
					/**
					   *  @author Jake Pogharian
					   *  Feature: Drop Wall
					   *  Step: "Then I shall be notified that my wall move is invalid"
					*/
					@Then("I shall be notified that my wall move is invalid")
					public void i_shall_be_notified_that_my_wall_move_is_invalid() {
					    // this is a GUI element, therefore do not implement
					    throw new cucumber.api.PendingException();
					}

					/**
					   *  @author Jake Pogharian
					   *  Feature: Drop Wall
					   *  Step: "It shall be my turn to move"
					*/
					@Then("It shall be my turn to move")
					public void it_shall_be_my_turn_to_move() {
					    //assuming I am player 1 and that player 1 is white
						//assert that it is player white's turn to move
								 assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite());
					 
					}
					
					/**
					   *  @author Jake Pogharian
					   *  Feature: Drop Wall
					   *  Step: "Then no wall move shall be registered with {string} at position \\({int}, {int})"
					   *  @param string: This is the String used to indicate the wall move direction. 
					   *  @param int1: This is the Integer used to indicate the row of the wall move  
					   *  @param int2: This is the Integer used to indicate the column of the wall move
					*/
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
							//assert that NO wall move was registered, by checking that the last wall move does not correspond to the given coordinates or wall direction or that if it is, it does not correspond to a move made during this turn. Ie: wall placed at that position at the prior turn.
						assertTrue(lastWallMove.getWallDirection()!=dir || lastWallMove.getTargetTile().getRow()!=int1 || lastWallMove.getTargetTile().getColumn()!=int2 || lastWallMove.getNextMove()!=null) ;	

					    
					}
		
>>>>>>> Stashed changes
	

	//Step: Set Total Thinking Time - Jake Pogharian 
	
<<<<<<< Updated upstream

	
	
	
	/*
	 * TODO Insert your missing step definitions here
	 * 
	 * Call the methods of the controller that will manipulate the model once they
	 * are implemented
	 * 
	 */
=======
					
					/**
					   *  @author Jake Pogharian
					   *  Feature: SetTotalThinkingTime
					   *  Step: "{int}:{int} is set as the thinking time"
					   *  This step calls on the Controller method to initialize the remaining time (thinking time) for each player.
					   *  @param int1: This is the Integer used to indicate the minutes of the thinking time
					   *  @param int2: This is the Integer used to indicate the seconds of the thinking time 
					*/
					
					@When("{int}:{int} is set as the thinking time")
					public void is_set_as_the_thinking_time(Integer int1, Integer int2) {
						throw new PendingException();
						//To implement
						//Controller.InitializeThinkingTime(int1, int2);

					}
					
					/**
					   *  @author Jake Pogharian
					   *  This step shall validate whether both player's initially have remaining time equivalent to the thinking time
					   *  initialized before the start of the game. It assumes that the thinking time is no greater than 24 hours (less than 24 hours).
					   *  it does so by validating that the string equivalent of the time object 
					   *  Feature : SetTotalThinkingTime
					   *  Step : "Both players shall have {int}:{int} remaining time left"
					   *  @param int1: the Integer which will indicate the number of minutes
					   *  @param int2: the Integer which will indicate the number of hours
					*/
					@Then("Both players shall have {int}:{int} remaining time left")
					public void both_players_shall_have_remaining_time_left(Integer int1, Integer int2) {
						Time time = new Time(0, int1, int2);
						//assert that given Time corresponds to initial remaining time of both players
						assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().getRemainingTime().toString().equals(time.toString()) && QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().getNextPlayer().getRemainingTime().toString().equals(time.toString()));

					}

//-------------------------------------------------------------------------------------------------------------------------
>>>>>>> Stashed changes

	// ***********************************************
	// Clean up
	// ***********************************************

	// After each scenario, the test model is discarded
	@After
	public static void tearDown() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		// Avoid null pointer for step definitions that are not yet implemented.
		if (quoridor != null) {
			quoridor.delete();
			quoridor = null;
		}

		for (int i = 0; i < 20; i++) {
			Wall wall = Wall.getWithId(i);
			if(wall != null) {
				wall.delete();
			}
		}
	}

	// ***********************************************
	// Extracted helper methods
	// ***********************************************

	// Place your extracted methods below

	public static void initQuoridorAndBoard() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Board board = new Board(quoridor);
		// Creating tiles by rows, i.e., the column index changes with every tile
		// creation
		for (int i = 1; i <= 9; i++) { // rows
			for (int j = 1; j <= 9; j++) { // columns
				board.addTile(i, j);
			}
		}
	}

	public static ArrayList<Player> createUsersAndPlayers(String userName1, String userName2) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		User user1 = quoridor.addUser(userName1);
		User user2 = quoridor.addUser(userName2);

		int thinkingTime = 180;

		// Players are assumed to start on opposite sides and need to make progress
		// horizontally to get to the other side
		//@formatter:off
		/*
		 *  __________
		 * |          |
		 * |          |
		 * |x->    <-x|
		 * |          |
		 * |__________|
		 * 
		 */
		//@formatter:on
		Player player1 = new Player(new Time(thinkingTime), user1, 9, Direction.Horizontal);
		Player player2 = new Player(new Time(thinkingTime), user2, 1, Direction.Horizontal);

		Player[] players = { player1, player2 };

		// Create all walls. Walls with lower ID belong to player1,
		// while the second half belongs to player 2
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 10; j++) {
				new Wall(i * 10 + j, players[i]);
			}
		}
		
		ArrayList<Player> playersList = new ArrayList<Player>();
		playersList.add(player1);
		playersList.add(player2);
		
		return playersList;
	}

	public static void createAndStartGame(ArrayList<Player> players) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		// There are total 36 tiles in the first four rows and
		// indexing starts from 0 -> tiles with indices 36 and 36+8=44 are the starting
		// positions
		Tile player1StartPos = quoridor.getBoard().getTile(36);
		Tile player2StartPos = quoridor.getBoard().getTile(44);
		
		Game game = new Game(GameStatus.Running, MoveMode.PlayerMove, players.get(0), players.get(1), quoridor);

		PlayerPosition player1Position = new PlayerPosition(quoridor.getCurrentGame().getWhitePlayer(), player1StartPos);
		PlayerPosition player2Position = new PlayerPosition(quoridor.getCurrentGame().getBlackPlayer(), player2StartPos);

		GamePosition gamePosition = new GamePosition(0, player1Position, player2Position, players.get(0), game);

		// Add the walls as in stock for the players
		for (int j = 0; j < 10; j++) {
			Wall wall = Wall.getWithId(j);
			gamePosition.addWhiteWallsInStock(wall);
		}
		for (int j = 0; j < 10; j++) {
			Wall wall = Wall.getWithId(j + 10);
			gamePosition.addBlackWallsInStock(wall);
		}

		game.setCurrentPosition(gamePosition);
	}
}
