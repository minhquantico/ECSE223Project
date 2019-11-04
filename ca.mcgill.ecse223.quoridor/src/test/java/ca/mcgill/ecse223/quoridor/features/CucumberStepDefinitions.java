package ca.mcgill.ecse223.quoridor.features;


import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;


import static org.junit.jupiter.api.Assertions.assertFalse;


import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;






import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.Controller;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import fxml.PlayScreenController;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
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
	public void theGameIsNotRunning() {
		initQuoridorAndBoard();
		createUsersAndPlayers("user1", "user2");
	}

	@Given("^The game is running$")
	public void theGameIsRunning() {
		initQuoridorAndBoard();
		ArrayList<Player> createUsersAndPlayers = createUsersAndPlayers("user1", "user2");
		createAndStartGame(createUsersAndPlayers);
	}

	@And("^It is my turn to move$")
	public void itIsMyTurnToMove() throws Throwable {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Player currentPlayer = quoridor.getCurrentGame().getWhitePlayer();
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setPlayerToMove(currentPlayer);
	}

	@Given("The following walls exist:")
	public void theFollowingWallsExist(io.cucumber.datatable.DataTable dataTable) {
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
	public void iDoNotHaveAWallInMyHand() {
		assertFalse(PlayScreenController.isWallInHand);
	}
	
	@And("^I have a wall in my hand over the board$")
	public void iHaveAWallInMyHandOverTheBoard() throws Throwable {
		assertTrue(PlayScreenController.isWallInHand);
	}
	
	@Given("^A new game is initializing$")
	public void aNewGameIsInitializing() throws Throwable {
		initQuoridorAndBoard();
		ArrayList<Player> players = createUsersAndPlayers("user1", "user2");
		new Game(GameStatus.Initializing, MoveMode.PlayerMove, players.get(0), players.get(1), QuoridorApplication.getQuoridor());
	}

	// ***********************************************
	// Scenario and scenario outline step definitions
	// ***********************************************
	
//-------------------------------------------------------------------------------------------------------------------------
	
//GrabWall: David Budaghyan
	
			//1st scenario
			
			  
			 	/** @author David Budaghyan **/
				@Given("I have more walls on stock")
				public void i_have_more_walls_on_stock() {
					System.out.println("i do");
				    
				}
				
				/** @author David Budaghyan **/
				@When("I try to grab a wall from my stock")
				public void i_try_to_grab_a_wall_from_my_stock() {
					Player currentPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
				    Controller.grabWallFromStock(currentPlayer);
				}
				
				/** @author David Budaghyan **/
				@Then("A wall move candidate shall be created at initial position")
				public void a_wall_move_candidate_shall_be_created_at_initial_position() {
					//if the wall that was taken out was the first wall,
					// then the following assertion is sufficient
					assertEquals(0, QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallPlaced().getId());
					
					//We will set the initial position of all Moves to be 1,1  --- doesn't matter what it is..
					assertEquals(1, QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getColumn());
					assertEquals(1, QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getRow());
				}
				
				/** @author David Budaghyan **/
				@And("I shall have a wall in my hand over the board")
				public void i_shall_have_a_wall_in_my_hand_over_the_board() {
					assertTrue(PlayScreenController.isWallInHand);
				}
				
				/** @author David Budaghyan **/
				@And("The wall in my hand shall disappear from my stock")
				public void the_wall_in_my_hand_shall_disappear_from_my_stock() {
				
					assertEquals(9, QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().numberOfWhiteWallsInStock());
				}
				


				
			//2nd scenario
				
				/** @author David Budaghyan **/
				@Given("I have no more walls on stock")
				public void i_have_no_more_walls_on_stock() {
				    QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().clear();
				    QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsInStock().clear();
				}
				
				/** @author David Budaghyan **/
				@Then("I shall be notified that I have no more walls")
				public void i_shall_be_notified_that_I_have_no_more_walls() {
					assertTrue(PlayScreenController.noMoreWalls);
				}
				
				/** @author David Budaghyan **/
				@Then("I shall have no walls in my hand")
				public void i_shall_have_no_walls_in_my_hand() {
					assertFalse(PlayScreenController.isWallInHand);
				}

				
				
				

//MoveWall: David Budaghyan
	
	
			//1st scenario
				
				/** @author David Budaghyan **/
				@Given("A wall move candidate exists with {string} at position \\({int}, {int})")
				public void a_wall_move_candidate_exists_with_at_position(String dir, Integer aRow, Integer aCol) {
					
					//create everything needed to create the wallMoveCandidate
					Player currentPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
					Tile targetTile = new Tile(aRow, aCol, QuoridorApplication.getQuoridor().getBoard());
					Wall wallCandidate = new Wall(0, currentPlayer);
					// alternative: use the wall field of this class instead of creating a new wall (see above)
					
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
				    // alternative
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
	
//-----------------------------------------------------------------------------------------------------------------------

				
//Initialize Board: Lenoy Christy
				
				/**
				 * @author Lenoy Christy
				 */
				@When("The initialization of the board is initiated")
				public void the_initialization_of_the_board_is_initiated() {
				    
					Controller.initQuoridorAndBoard(); 
					
				    
				}
				
				/**
				 * @author Lenoy Christy
				 */
				@Then("It shall be white player to move")
				public void it_shall_be_white_player_to_move() {
                   
				   assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size() % 2 == 0);
				}

				/**
				 * @author Lenoy Christy
				 */
				@Then("White's pawn shall be in its initial position")
				public void white_s_pawn_shall_be_in_its_initial_position() {
				    
					assertTrue((QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getColumn() == 5) 
							&& (QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getRow() == 9));
				}
				
				/**
				 * @author Lenoy Christy
				 */
				@Then("Black's pawn shall be in its initial position")
				public void black_s_pawn_shall_be_in_its_initial_position() {
				   
					assertTrue((QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getColumn() == 5) 
							&& (QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getRow() == 1));    
				}

				/**
				 * @author Lenoy Christy
				 */
				@Then("All of White's walls shall be in stock")
				public void all_of_White_s_walls_shall_be_in_stock() {
					assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().size() == 10); 	    
				}

				/**
				 * @author Lenoy Christy
				 */
				@Then("All of Black's walls shall be in stock")
				public void all_of_Black_s_walls_shall_be_in_stock() {
					assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().size() == 10);
				}

				/**
				 * @author Lenoy Christy
				 */
				@Then("White's clock shall be counting down")
				public void white_s_clock_shall_be_counting_down() {
				    assertFalse(PlayScreenController.instance.board.players[0].isClockStopped());
				}

				/**
				 * @author Lenoy Christy
				 */
				@Then("It shall be shown that this is White's turn")
				public void it_shall_be_shown_that_this_is_White_s_turn() {
					assertTrue(PlayScreenController.instance.pane.getChildren().contains(PlayScreenController.instance.WhitePlayerImage));
	
				}	
	

				
//SwitchCurrentPlayer: Lenoy Christy
				
				/**
				 * @author Lenoy Christy
				 * @param string
				 */
				@Given("The player to move is {string}")
				public void the_player_to_move_is(String string)
				{
					if (isWhiteTurn())
						QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setPlayerToMove(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer());
					else QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setPlayerToMove(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer());

					
				}
				/**
				 * @author Lenoy Christy
				 * @param string
				 */
				@Given("The clock of {string} is running")
				public void the_clock_of_is_running(String string) {
				   if (isWhiteTurn()) { 
					   PlayScreenController.instance.board.players[0].startClock();
				   }
				   else PlayScreenController.instance.board.players[1].startClock();
				    
				}
				
				/**
				 * @author Lenoy Christy
				 * @param string
				 */
				@Given("The clock of {string} is stopped")
				public void the_clock_of_is_stopped(String string) {
					if (isWhiteTurn()) { 
						   PlayScreenController.instance.board.players[0].stopClock();
					   }
					   else PlayScreenController.instance.board.players[1].stopClock();
				    
				}
				
				/**
				 * @author Lenoy Christy
				 * @param string
				 */
				@When("Player {string} completes his move")
				public void player_completes_his_move(String string) {
					Controller.endMove();
				}
				
				/**
				 * @author Lenoy Christy
				 * @param string
				 */
				@Then("The user interface shall be showing it is {string} turn")
				public void the_user_interface_shall_be_showing_it_is_turn(String string) {
					if(isWhiteTurn()) {
						assertTrue(PlayScreenController.instance.pane.getChildren().contains(PlayScreenController.instance.WhitePlayerImage));
					}
					else assertTrue(PlayScreenController.instance.pane.getChildren().contains(PlayScreenController.instance.BlackPlayerImage));
				}
				
				/**
				 * @author Lenoy Christy
				 * @param string
				 */
				@Then("The clock of {string} shall be stopped")
				public void the_clock_of_shall_be_stopped(String string) {
					if (isWhiteTurn()) {
						assertTrue(PlayScreenController.instance.board.players[0].isClockStopped()); 
					}
					else assertTrue(PlayScreenController.instance.board.players[0].isClockStopped()); 
				}
				
				/**
				 * @author Lenoy Christy
				 * @param string
				 */
				@Then("The clock of {string} shall be running")
				public void the_clock_of_shall_be_running(String string) {
					if(isWhiteTurn()) 
						assertFalse(PlayScreenController.instance.board.players[0].isClockStopped());
					else assertFalse(PlayScreenController.instance.board.players[1].isClockStopped());
				    
				}
				
				/**
				 * @author Lenoy Christy
				 * @param string
				 */
				@Then("The next player to move shall be {string}")
				public void the_next_player_to_move_shall_be(String string) {
					if (isWhiteTurn()) {
						assertTrue(nextPlayer().equals(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer()));
					}
					else assertTrue(nextPlayer().equals(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer()));
				    
				}
				
				

				
//-------------------------------------------------------------------------------------------------------------------------


//Load Position: Traian Coza
				
			//1st scenario
				
				/* Traian Coza */
				@When("I initiate to load a saved game {string}")
				public void i_initiate_to_load_a_saved_game(String string) {
				    Controller.loadGame(new File(string));
				}

				/* Traian Coza */
				@When("The position to load is valid")
				public void the_position_to_load_is_valid() {
					assertTrue(Controller.positionIsValid());
				}

				/* Traian Coza */
				@Then("It shall be {string}'s turn")
				public void it_shall_be_s_turn(String string) {
					assertEquals(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size() % 2 == 0, string.equals("white"));
				}

				/* Traian Coza */
				@Then("{string} shall be at {int}:{int}")
				public void shall_be_at(String string, Integer int1, Integer int2) {
				    Tile t = string.equals("white") ?
				    		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile() :
				    		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile();
				    
				    assertTrue(t.getRow() == int1 && t.getColumn() == int2);
				}

				/* Traian Coza */
				@Then("{string} shall have a vertical wall at {int}:{int}")
				public void shall_have_a_vertical_wall_at(String string, Integer int1, Integer int2) {
					List<Wall> walls = string.equals("white") ?
				    		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsOnBoard() :
				    		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsOnBoard();
				    
				    boolean exists = false;
					for (Wall wall : walls)
						exists |= (
								wall.getMove().getTargetTile().getRow() == int1 &&
								wall.getMove().getTargetTile().getColumn() == int2 &&
								wall.getMove().getWallDirection().equals(Direction.Vertical));
					assertTrue(exists);
				}

				/* Traian Coza */
				@Then("{string} shall have a horizontal wall at {int}:{int}")
				public void shall_have_a_horizontal_wall_at(String string, Integer int1, Integer int2) {
					List<Wall> walls = string.equals("white") ?
				    		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsOnBoard() :
				    		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsOnBoard();
				    
				    boolean exists = false;
					for (Wall wall : walls)
						exists |= (
								wall.getMove().getTargetTile().getRow() == int1 &&
								wall.getMove().getTargetTile().getColumn() == int2 &&
								wall.getMove().getWallDirection().equals(Direction.Horizontal));
					assertTrue(exists);
				}

				/* Traian Coza */
				@Then("Both players shall have {int} in their stacks")
				public void both_players_shall_have_in_their_stacks(Integer int1) {
					assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().size() == int1);
					assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsInStock().size() == int1);
				}
				
				
			//2nd scenario
				
				/* Traian Coza */
				@When("The position to load is invalid")
				public void the_position_to_load_is_invalid() {
				    assertFalse(Controller.positionIsValid());
				}

				/* Traian Coza */
				@Then("The load shall return an error")
				public void the_load_shall_return_an_error() {
				    assertTrue(false);
				}
	

				
				
//Save Position: Traian Coza
				
			//1st scenario
				
				/* Traian Coza */
				@Given("No file {string} exists in the filesystem")
				public void no_file_exists_in_the_filesystem(String string) {
				    File file = new File(string);
				    if (file.exists())
				    	file.delete();
				}

				/* Traian Coza */
				@When("The user initiates to save the game with name {string}")
				public void the_user_initiates_to_save_the_game_with_name(String string) {
				    Controller.saveGame(new File(string));
				}

				/* Traian Coza */
				@Then("A file with {string} shall be created in the filesystem")
				public void a_file_with_shall_be_created_in_the_filesystem(String string) {
				    assertTrue(new File(string).exists());
				}
				
			//2nd scenario

				/* Traian Coza */
				@Given("File {string} exists in the filesystem")
				public void file_exists_in_the_filesystem(String string) throws IOException {
				    new File(string).createNewFile();
				}

				/* Traian Coza */
				@When("The user confirms to overwrite existing file")
				public void the_user_confirms_to_overwrite_existing_file() {
				    Controller.setOverwrite(true);
				}

				/* Traian Coza */
				@Then("File with {string} shall be updated in the filesystem")
				public void file_with_shall_be_updated_in_the_filesystem(String string) {
					assertEquals(new File(string).lastModified() / 1000, System.currentTimeMillis() / 1000);
				}
	
				
			//3rd scenario

				/* Traian Coza */
				@When("The user cancels to overwrite existing file")
				public void the_user_cancels_to_overwrite_existing_file() {
				    Controller.setOverwrite(false);
				}

				/* Traian Coza */
				@Then("File {string} shall not be changed in the filesystem")
				public void file_shall_not_be_changed_in_the_filesystem(String string) {
					assertNotEquals(new File(string).lastModified() / 1000, System.currentTimeMillis() / 1000);
				}

//-------------------------------------------------------------------------------------------------------------------------

				
//ValidatePosition: Gohar Saqib Fazal
				
			
				
				/**
				 * @author Gohar Saqib Fazal
				 * @param int1: The row number of the pawn coordinate supplied
				 * @param int2: The column number of the pawn coordinate supplied
				 */
				@Given("A game position is supplied with pawn coordinate {int}:{int}")
				public void a_game_position_is_supplied_with_pawn_coordinate(Integer int1, Integer int2) {
					Tile tile = new Tile(int1, int2, QuoridorApplication.getQuoridor().getBoard());
					QuoridorApplication.getQuoridor().getCurrentGame().getMove(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()).setTargetTile(tile);
				}

				/**
				 * @author Gohar Saqib Fazal
				 */
				@When("Validation of the position is initiated")
				public void validation_of_the_position_is_initiated() {
					if(QuoridorApplication.getQuoridor().getCurrentGame().hasWallMoveCandidate()) {
						Controller.initPosValidation(QuoridorApplication.getQuoridor().getCurrentGame().getMove(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()).getTargetTile(), QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallDirection());
					}
					else {
						Controller.initPosValidation(QuoridorApplication.getQuoridor().getCurrentGame().getMove(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()).getTargetTile());
					}
				}

				/**
				 * @author Gohar Saqib Fazal
				 * @param string: "Ok" or "Error" to tell whether the position is valid or not
				 */
				@Then("The position shall be {string}")
				public void the_position_shall_be(String string) {
					if (Controller.initPosValidation(QuoridorApplication.getQuoridor().getCurrentGame().getMove(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()).getTargetTile()) == true){
						assert posValidationResult(Controller.initPosValidation(QuoridorApplication.getQuoridor().getCurrentGame().getMove(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()).getTargetTile())).equals("ok"); 
					}
					else {
						assert posValidationResult(Controller.initPosValidation(QuoridorApplication.getQuoridor().getCurrentGame().getMove(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()).getTargetTile())).equals("error");	
					}
				}

				/**
				 * @author Gohar Saqib Fazal
				 * @param int1: The row number of the wall coordinate supplied
				 * @param int2: The column number of the pawn coordinate supplied
				 * @param string: "Ok" or "Error" to tell whether the position is valid or not
				 */
				@Given("A game position is supplied with wall coordinate {int}:{int}-{string}")
				public void a_game_position_is_supplied_with_wall_coordinate(Integer int1, Integer int2, String string) {
					Tile tile = new Tile(int1, int2, QuoridorApplication.getQuoridor().getBoard());
					QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().setTargetTile(tile);			
					if (string.equalsIgnoreCase("Vertical")) {
						QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().setWallDirection(Direction.Vertical);
					}
					else {
						QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().setWallDirection(Direction.Horizontal);
					}
				}

				/**
				 * @author Gohar Saqib Fazal
				 */
				@Then("The position shall be valid")
				public void the_position_shall_be_valid() {
					assert Controller.initPosValidation(QuoridorApplication.getQuoridor().getCurrentGame().getMove(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()).getTargetTile(), QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallDirection()):"ERROR: The position is invalid";
				}

				/**
				 * @author Gohar Saqib Fazal
				 */
				@Then("The position shall be invalid")
				public void the_position_shall_be_invalid() {
					assert (! Controller.initPosValidation(QuoridorApplication.getQuoridor().getCurrentGame().getMove(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()).getTargetTile(), QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallDirection()));
				}
			
				
// Rotate Wall: Gohar Saqib Fazal
				
				/**
				 * @author Gohar Saqib Fazal
				 */
				@When("I try to flip the wall")
				public void i_try_to_flip_the_wall() {
					Controller.flipWall(PlayScreenController.instance.wall);
				}

				/**
				 * @author Gohar Saqib Fazal
				 * @param dir: The new direction that the wall already placed on the board should be moved to
				 */
				@Then("The wall shall be rotated over the board to {string}")
				public void the_wall_shall_be_rotated_over_the_board_to(String dir) {
					if(dir.equalsIgnoreCase("vertical")) {
						assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().setWallDirection(Direction.Vertical));
					}
					else {
						assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().setWallDirection(Direction.Horizontal));
					}
				}
	
//-------------------------------------------------------------------------------------------------------------------------
	
//ProvideSelectUserName: Minh Quan Hoang
				
				//Given model api
				//when controller
				//then model api

				// throw UnsupportedOperationException

				

//					/** @author Minh Quan Hoang **/
//					//Initiates Quoridor, a Board and the users and players
//					@Given("A new game is initializing")
//					public void a_new_game_is_initializing() {
//					    initQuoridorAndBoard();
//					    createUsersAndPlayers("user1", "user2");
//					}
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
					public void the_player_selects_existing(Player player, String string) {
					    Controller.SelectExistingUsername(player, string);
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
					    assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().getUser().getName() == "user2");
						}
					    else if(string.equals("white")){
					    assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getUser().getName() == "user1");
					    }
					}
				
				
//StartNewGame : Minh Quan Hoang
					
		//Given model api (action, change something with quoridorapplication)
		//when controller
		//then model api assert
				
					/** @author Minh Quan Hoang **/
					//Initialize a new game of Quoridor
					@When("A new game is being initialized")
					public void a_new_game_is_being_initialized() {
					    Controller.InitializeNewGame();
					}
					/** @author Minh Quan Hoang **/
					//Get username for white player from player class
					@When("White player chooses a username")
					public void white_player_chooses_a_username(String username) {
					    Controller.setWhitePlayerUsername(username);
					}
					/** @author Minh Quan Hoang **/
					//Get username for black player from player class
					@When("Black player chooses a username")
					public void black_player_chooses_a_username(String username) {
					    Controller.setBlackPlayerUsername(username);
					}
					/** @author Minh Quan Hoang **/
					//Sets the total thinking time
					@When("Total thinking time is set")
					public void total_thinking_time_is_set(int minutes, int seconds) {
						Controller.setTotalThinkingTime(minutes, seconds);
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
					public void i_start_the_clock(long time) {
					    Controller.StartClock(time);
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
				
				
				
				
	
	
//-------------------------------------------------------------------------------------------------------------------------
	
					//Jake Pogharian step 
					//Set Total Thinking Time
					
					
					
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
											
						Controller.setThinkingTime(int1, int2);

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
					
					
					//Drop Wall Feature
					
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
						
						Tile t = Controller.getTile();
						Controller.dropWall(t);
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
					
	
	
	
//-------------------------------------------------------------------------------------------------------------------------

	
	
	
	


//-------------------------------------------------------------------------------------------------------------------------


	// ***********************************************
	// Clean up
	// ***********************************************

	// After each scenario, the test model is discarded
	@After
	public void tearDown() {
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
	
	
	
	/**
	 * @author Lenoy Christy
	 * @return boolean
	 */
	private boolean isClockRunning() { // CLOCK METHOD NOT YET IMPLEMENTED. TODO FOR LATER
		throw new cucumber.api.PendingException(); 
	}
	

	/**
	 * @author Lenoy Christy
	 * @return boolean
	 */
	private boolean isWhiteTurn( ){
		if ( (QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()) % 2 == 0) return true; 
		else return false; 
		
	}
	
	/**
	 * @author Lenoy Christy 
	 * @return Player
	 */
	private Player nextPlayer() {
		if (isWhiteTurn()) return QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer();
		else return QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
	}
	
	
	/**
	 * @author Lenoy Christy
	 * @param player
	 */
	private void initClock(Player player) { // CLOCK METHOD NOT IMPLEMENTED. TODO FOR ITERATION 3
		throw new cucumber.api.PendingException(); 
	}
	
	/**
	 * @author Lenoy Christy
	 * @param player
	 */
	private void stopClock(Player player) { // CLOCK METHOD NOT IMPLEMENTED. TODO FOR ITERATION 3
		throw new cucumber.api.PendingException();
	}
	
	
	/**
	 * @author Gohar Saqib Fazal
	 * @param okOrError: A boolean value
	 * @return String: The result of the validation method and it stores whether the position is valid or not. 
	 */
	private String posValidationResult(Boolean okOrError){
		String result; 
		if(okOrError) {
			result = "ok";
		}
		else {
			result = "error";
		}
		return result;	
	}
	
	
	
	private void initQuoridorAndBoard() {
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

	private ArrayList<Player> createUsersAndPlayers(String userName1, String userName2) {
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

	private void createAndStartGame(ArrayList<Player> players) {
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

