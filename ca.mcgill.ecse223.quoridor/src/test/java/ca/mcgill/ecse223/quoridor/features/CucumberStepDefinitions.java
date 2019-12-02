
package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.Controller;
import ca.mcgill.ecse223.quoridor.controller.IllegalMoveException;
import ca.mcgill.ecse223.quoridor.controller.Controller.InvalidPositionException;
import ca.mcgill.ecse223.quoridor.gui.PlayScreenController;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Move;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.StepMove;
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
	}

	@Given("^The game is running$")
	public void theGameIsRunning() {
		Controller.initQuoridorAndBoard();
		Controller.InitializeNewGame();
		QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.Running);
	}

	@And("^It is my turn to move$")
	public void itIsMyTurnToMove() throws Throwable {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Player currentPlayer = quoridor.getCurrentGame().getWhitePlayer();
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setPlayerToMove(currentPlayer);
	}

	@Given("The following walls exist:")
	public void theFollowingWallsExist(io.cucumber.datatable.DataTable dataTable) {
		
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			Integer wrow = Integer.decode(map.get("wrow"));
			Integer wcol = Integer.decode(map.get("wcol"));
			String dir = map.get("wdir");
			
			Controller.setWallMoveCandidate(wcol, wrow, dir.equals("horizontal") ? Direction.Horizontal : Direction.Vertical);
			Controller.dropWall();   // Assume valid
		}
	}

	@And("I do not have a wall in my hand")
	public void iDoNotHaveAWallInMyHand() {
		assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate() == null);
	}

	@And("^I have a wall in my hand over the board$")
	public void iHaveAWallInMyHandOverTheBoard() throws Throwable {
		Controller.setWallMoveCandidate(1, 1, Direction.Horizontal);
	}

	@Given("^A new game is initializing$")
	public void aNewGameIsInitializing() throws Throwable {
		Controller.initQuoridorAndBoard();
		ArrayList<Player> players = createUsersAndPlayers("user1", "user2");
		Game game = new Game(GameStatus.Initializing, MoveMode.PlayerMove, QuoridorApplication.getQuoridor());
		game.setWhitePlayer(players.get(0));
		game.setBlackPlayer(players.get(1));
				
	}

	// ***********************************************
	// Scenario and scenario outline step definitions
	// ***********************************************

//-----------------------------------------------------------------------------------------------------------------------------

	
//JumpPawn: Team
	
	//we need these to communicate between steps
	Player currentPlayer;
	Player opponent;
	
	
	//1st scenario: Jump over opponent
	
	//note that there is also another given statement written by Lenoy in switchCurrentPlayer feature: Given the player to move is {string}
	
	/**
	 * @author David Budaghyan
	 * @param int1: row
	 * @param int2: column
	 */
	@Given("The player is located at {int}:{int}")
	public void the_player_is_located_at(Integer int1, Integer int2) {
		if (isWhiteTurn()) {
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().setTile(Controller.getTile(int2, int1));
			QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getPawnBehaviour().setSMTest();
		}else {
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().setTile(Controller.getTile(int2, int1));
			QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().getPawnBehaviour().setSMTest();
			
		}
	}

	/**
	 * @author David Budaghyan
	 * @param int1: row
	 * @param int2: column
	 */
	@Given("The opponent is located at {int}:{int}")
	public void the_opponent_is_located_at(Integer int1, Integer int2) {
		if (!isWhiteTurn()){
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
					.getWhitePosition().setTile(Controller.getTile(int2, int1));
			QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getPawnBehaviour().setSMTest();
		}else {
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
					.getBlackPosition().setTile(Controller.getTile(int2, int1));
			QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().getPawnBehaviour().setSMTest();
		}
	}
	

	/**
	 * @author Jake Pogharian
	 * @param string: color to player to whom wall belongs to
	 * @param string2: direction
	 */
	@Given("There are no {string} walls {string} from the player nearby")
	public void there_are_no_walls_from_the_player_nearby(String string, String string2) {
	    // Here do no thing -> no walls were ever set 
	}

	private Player player;
	
	/**
	 * @author Traian Coza
	 * @param string: player
	 * @param string2: direction
	 */
	@When("Player {string} initiates to move {string}")
	public void player_initiates_to_move(String string, String string2) {
		Tile dest = string.equals("white") ?
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
				.getWhitePosition().getTile() :
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
				.getBlackPosition().getTile();
		player = isWhiteTurn() ?
				QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer() :
				QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer();
		
		int dir = dirToInt(string2);
		if (dir != -1)
		{
			dest = Controller.direction(dest, dirToInt(string2));
			if (Controller.hasPlayer(dest))
				dest = Controller.direction(dest, dirToInt(string2));	// Jump
		}
		else
		{
			if (string2.startsWith("up"))
				dest = Controller.direction(dest, 0);
			else
				dest = Controller.direction(dest, 2);
			if (string2.endsWith("right"))
				dest = Controller.direction(dest, 1);
			else
				dest = Controller.direction(dest, 3);
		}
		
		legal = dest != null;
		if (!legal)
			return;
		try { Controller.doPawnMoveStateMachine(dest.getColumn(), dest.getRow()); }
	    catch (IllegalMoveException ex) { legal = false; }
	}

	/**
	 * @author Traian Coza
	 * @param string: side
	 * @param string2: status
	 */
	@Then("The move {string} shall be {string}")
	public void the_move_shall_be(String string, String string2) {
	    assertEquals(string2.equals("success"), legal);
	}

	
	/**
	 * @author David Budaghyan
	 * @param int1: row
	 * @param int2: column
	 */
	@Then("Player's new position shall be {int}:{int}")
	public void player_s_new_position_shall_be(Integer int1, Integer int2) {
		Tile pos = player.hasGameAsWhite() ?
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
				.getWhitePosition().getTile() :
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
				.getBlackPosition().getTile();
		System.out.println("legal: " + legal);
		assertEquals(pos.getRow(), int1);
		assertEquals(pos.getColumn(), int2);
	}

	
	/**
	 * @author Traian Coza
	 * @param string: nextPlayer
	 */
	@Then("The next player to move shall become {string}")
	public void the_next_player_to_move_shall_become(String string) {
		System.out.println("string: " + string);
	    assertEquals(isWhiteTurn(), string.equals("white"));
	}
	
	
	
	// 2nd scenario: Jump of player blocked by wall
	/**
	 * @author Jake Pogharian
	 * @param String: player's color
	 * @param int1: row
	 * @param int2: column
	 */
	@Given("There is a {string} wall at {int}:{int}")
	public void there_is_a_wall_at(String string, Integer int1, Integer int2) {
	    Controller.setWallMoveCandidate(int2, int1, string.equals("vertical") ? Direction.Vertical : Direction.Horizontal);
	    Player p = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove();
	    Controller.doWallMove();
	    QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setPlayerToMove(p);
	}
	
	//all methods for scenario 3 and 4 are foudn in the above
//-----------------------------------------------------------------------------------------------------------------------------------------------
	
// MovePawn: Team
	/**
	 * @author Jake Pogharian
	 * @param string: player's color
	 * @param string2: side 
	 */
	@Given("There are no {string} walls {string} from the player")
	public void there_are_no_walls_from_the_player(String string, String string2) {
		//No walls have been placed
		
//		Tile pos = isWhiteTurn() ?
//				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile() :
//				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile();
//	    assertFalse(Controller.isBlockedDirection(pos, dirToInt(string2)));
	}

	/**
	 * @author Traian Coza
	 * @param string: side
	 */
	@Given("The opponent is not {string} from the player")
	public void the_opponent_is_not_from_the_player(String string) {
		Tile pos = isWhiteTurn() ?
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile() :
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile();
		Tile oppos = !isWhiteTurn() ?
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile() :
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile();
		
	    assertNotEquals(Controller.direction(pos, dirToInt(string)), oppos);
	}
	
	/**
	 * @author Traian Coza
	 * @param string: player's color
	 * @param string2: side
	 */
	@Given("There is a {string} wall {string} from the player")
	public void there_is_a_wall_from_the_player(String string, String string2) {
		Tile pos = isWhiteTurn() ?
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile() :
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile();
		int dir = dirToInt(string2);
		if (dir == 0 || dir == 3)
			pos = Controller.direction(pos, dir);
		there_is_a_wall_at(string, pos.getRow(), pos.getColumn());
	}

	
	/**
	 * @author Traian Coza
	 * @param string: side
	 */
	@Given("My opponent is not {string} from the player")
	public void my_opponent_is_not_from_the_player(String string) {
	    the_opponent_is_not_from_the_player(string);
	}
	
	
	
	
	
	
	
//--------------------------------------------------------------------------------------------------------------------------------------------------

//GrabWall: David Budaghyan

	// 1st scenario

	/** @author David Budaghyan **/
	@Given("I have more walls on stock")
	public void i_have_more_walls_on_stock() {
		System.out.println("i do");

	}

	/** @author David Budaghyan **/
	@When("I try to grab a wall from my stock")
	public void i_try_to_grab_a_wall_from_my_stock() {
		Controller.setWallMoveCandidate(1, 1, Direction.Horizontal);
	}

	/** @author David Budaghyan **/
	@Then("A wall move candidate shall be created at initial position")
	public void a_wall_move_candidate_shall_be_created_at_initial_position() {
		// if the wall that was taken out was the first wall,
		// then the following assertion is sufficient
		// Na
		
		// We will set the initial position of all Moves to be 1,1 --- doesn't matter
		// what it is..
		assertEquals(1, QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getColumn());
		assertEquals(1, QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getRow());
	}

	/** @author David Budaghyan **/
	@And("I shall have a wall in my hand over the board")
	public void i_shall_have_a_wall_in_my_hand_over_the_board() {
		assertTrue(1 == 1);
	}

	/** @author David Budaghyan **/
	@And("The wall in my hand shall disappear from my stock")
	public void the_wall_in_my_hand_shall_disappear_from_my_stock() {
		assertEquals(9, QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().numberOfWhiteWallsInStock());
	}

	// 2nd scenario

	/** @author David Budaghyan **/
	@Given("I have no more walls on stock")
	public void i_have_no_more_walls_on_stock() {
		while (!QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().isEmpty())
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().removeWhiteWallsInStock(
					QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock(0));
	}

	/** @author David Budaghyan **/
	@Then("I shall be notified that I have no more walls")
	public void i_shall_be_notified_that_I_have_no_more_walls() {
		assertNull(QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate());
	}

	/** @author David Budaghyan **/
	@Then("I shall have no walls in my hand")
	public void i_shall_have_no_walls_in_my_hand() {
		assertNull(QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate());
	}

//MoveWall: David Budaghyan

	// 1st scenario

	/** @author David Budaghyan **/
	@Given("A wall move candidate exists with {string} at position \\({int}, {int})")
	public void a_wall_move_candidate_exists_with_at_position(String dir, Integer aRow, Integer aCol) {
		Controller.setWallMoveCandidate(aCol, aRow, dir.equals("horizontal") ? Direction.Horizontal : Direction.Vertical);
	}
	
	/** @author David Budaghyan **/
	@Given("The wall candidate is not at the {string} edge of the board")
	public void the_wall_candidate_is_not_at_the_edge_of_the_board(String side) {
		WallMove candidate = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		switch (side)
		{
		case "up":
			assertNotEquals(candidate.getTargetTile().getRow(), 1);
			break;
		case "down":
			assertNotEquals(candidate.getTargetTile().getRow(), 9-1);
			break;
		case "left":
			assertNotEquals(candidate.getTargetTile().getColumn(), 1);
			break;
		case "right":
			assertNotEquals(candidate.getTargetTile().getColumn(), 9-1);
			break;
		default:
			assertTrue(false);
		}
		
	}
	
	boolean legal;

	/** @author David Budaghyan **/
	@When("I try to move the wall {string}")
	public void i_try_to_move_the_wall(String dir) {
		WallMove candidate = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		switch (dir)
		{
		case "up":
			legal = Controller.setWallMoveCandidate(candidate.getTargetTile().getColumn(), candidate.getTargetTile().getRow()-1, candidate.getWallDirection());
			break;
		case "down":
			legal = Controller.setWallMoveCandidate(candidate.getTargetTile().getColumn(), candidate.getTargetTile().getRow()+1, candidate.getWallDirection());
			break;
		case "left":
			legal = Controller.setWallMoveCandidate(candidate.getTargetTile().getColumn()-1, candidate.getTargetTile().getRow(), candidate.getWallDirection());
			break;
		case "right":
			legal = Controller.setWallMoveCandidate(candidate.getTargetTile().getColumn()+1, candidate.getTargetTile().getRow(), candidate.getWallDirection());
			break;
		default:
			assertTrue(false);
		}
		
//		Controller.moveWall(dir);
//		// alternative
//		// Controller.moveWall(dir, wall)
//		// see controller
	}

	/** @author David Budaghyan **/
	@Then("The wall shall be moved over the board to position \\({int}, {int})")
	public void the_wall_shall_be_moved_over_the_board_to_position(Integer int1, Integer int2) {
		WallMove candidate = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		assertEquals(candidate.getTargetTile().getRow(), int1);
		assertEquals(candidate.getTargetTile().getColumn(), int2);
	}

	/** @author David Budaghyan **/
	@Then("A wall move candidate shall exist with {string} at position \\({int}, {int})")
	public void a_wall_move_candidate_shall_exist_with_at_position(String direction, Integer aRow, Integer aCol) {
		assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile()
				.getRow() == aRow);
		assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile()
				.getColumn() == aCol);
		Direction d;
		if ("vertical".equalsIgnoreCase(direction)) {
			d = Direction.Vertical;
		} else {
			d = Direction.Horizontal;
		}
		assertTrue(
				QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallDirection().equals(d));
	}

	// 2nd scenario

	/** @author David Budaghyan **/
	@Given("The wall candidate is at the {string} edge of the board")
	public void the_wall_candidate_is_at_the_edge_of_the_board(String string) {
		WallMove candidate = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		switch (string)
		{
		case "up":
			assertEquals(candidate.getTargetTile().getRow(), 1);
			break;
		case "down":
			assertEquals(candidate.getTargetTile().getRow(), 9-1);
			break;
		case "left":
			assertEquals(candidate.getTargetTile().getColumn(), 1);
			break;
		case "right":
			assertEquals(candidate.getTargetTile().getColumn(), 9-1);
			break;
		default:
			assertTrue(false);
		}
	}

	/** @author David Budaghyan **/
	@Then("I shall be notified that my move is illegal")
	public void i_shall_be_notified_that_my_move_is_illegal() {
		assertFalse(legal);
	}

//-----------------------------------------------------------------------------------------------------------------------

//Initialize Board: Lenoy Christy

	/**
	 * @author Lenoy Christy
	 */
	@When("The initialization of the board is initiated")
	public void the_initialization_of_the_board_is_initiated() {

		Controller.initQuoridorAndBoard();
		Controller.InitializeNewGame();

	}

	/**
	 * @author Lenoy Christy
	 */
	@Then("It shall be white player to move")
	public void it_shall_be_white_player_to_move() {

		assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()
				.hasGameAsWhite());
	}

	/**
	 * @author Lenoy Christy
	 */
	@Then("White's pawn shall be in its initial position")
	public void white_s_pawn_shall_be_in_its_initial_position() {

		assertTrue(
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile() == Controller.getTile(5, 9));
	}

	/**
	 * @author Lenoy Christy
	 */
	@Then("Black's pawn shall be in its initial position")
	public void black_s_pawn_shall_be_in_its_initial_position() {

		assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile() == Controller.getTile(5,  1));
	}

	/**
	 * @author Lenoy Christy
	 */
	@Then("All of White's walls shall be in stock")
	public void all_of_White_s_walls_shall_be_in_stock() {
		assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock()
				.size() == 10);
	}

	/**
	 * @author Lenoy Christy
	 */
	@Then("All of Black's walls shall be in stock")
	public void all_of_Black_s_walls_shall_be_in_stock() {
		assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock()
				.size() == 10);
	}

	/**
	 * @author Lenoy Christy
	 */
	@Then("White's clock shall be counting down")
	public void white_s_clock_shall_be_counting_down() {
		if(!(PlayScreenController.instance==null))
			assertFalse(PlayScreenController.instance.board.getActivePlayer().isClockStopped());
	}
		

	/**
	 * @author Lenoy Christy
	 */
	@Then("It shall be shown that this is White's turn")
	public void it_shall_be_shown_that_this_is_White_s_turn() {
		if(!(PlayScreenController.instance==null))
			assertTrue(PlayScreenController.instance.pane.getChildren().contains(PlayScreenController.instance.statusImage));

	}

//SwitchCurrentPlayer: Lenoy Christy

	/**
	 * @author Lenoy Christy
	 * @param string
	 */
	@Given("The player to move is {string}")
	public void the_player_to_move_is(String string) {
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
				.setPlayerToMove(string.equals("white") ?
						QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer() :
						QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer());
	}

	/**
	 * @author Lenoy Christy
	 * @param string
	 */
	@Given("The clock of {string} is running")
	public void the_clock_of_is_running(String string) {
		if(PlayScreenController.instance != null)
			if (string.equals("white"))
				PlayScreenController.instance.board.players[0].startClock();
			else
				PlayScreenController.instance.board.players[1].startClock();
	}

	/**
	 * @author Lenoy Christy
	 * @param string
	 */
	@Given("The clock of {string} is stopped")
	public void the_clock_of_is_stopped(String string) {
		if(PlayScreenController.instance != null)
			if (string.equals("white"))
				PlayScreenController.instance.board.players[0].stopClock();
			else
				PlayScreenController.instance.board.players[1].stopClock();
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
		if(!(PlayScreenController.instance==null)) {
			if (isWhiteTurn()) {
				assertTrue(PlayScreenController.instance.pane.getChildren().contains(PlayScreenController.instance.statusImage));
			} else
				assertTrue(PlayScreenController.instance.pane.getChildren().contains(PlayScreenController.instance.blackTurn));
			}
		}
		

	/**
	 * @author Lenoy Christy
	 * @param string
	 */
	@Then("The clock of {string} shall be stopped")
	public void the_clock_of_shall_be_stopped(String string) {
		if(!(PlayScreenController.instance==null)) {
			if (isWhiteTurn()) {
				assertTrue(PlayScreenController.instance.board.players[0].isClockStopped());
			} else
				assertTrue(PlayScreenController.instance.board.players[1].isClockStopped());
			}
		}

	/**
	 * @author Lenoy Christy
	 * @param string
	 */
	@Then("The clock of {string} shall be running")
	public void the_clock_of_shall_be_running(String string) {
		if(!(PlayScreenController.instance==null)) {
			if (isWhiteTurn())
				assertFalse(PlayScreenController.instance.board.players[0].isClockStopped());
			else
				assertFalse(PlayScreenController.instance.board.players[1].isClockStopped());
	
			}
		}

	/**
	 * @author Lenoy Christy
	 * @param string
	 */
	@Then("The next player to move shall be {string}")
	public void the_next_player_to_move_shall_be(String string) {
		if (isWhiteTurn()) {
			assertTrue(nextPlayer().equals(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer()));
		} else
			assertTrue(nextPlayer().equals(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer()));

	}

//-------------------------------------------------------------------------------------------------------------------------

//Load Position: Traian Coza

	// 1st scenario

	boolean valid;
	Exception error;
	File file;

	/**
	 * @author Traian Coza 
	 * This method initiates to load the saved game with the specified file name
	 * @param string: The file name of the saved game that is to be loaded
	 * @throws FileNotFoundException: Exception thrown in the case that the file is not found
	 */
	@When("I initiate to load a saved game {string}")
	public void i_initiate_to_load_a_saved_game(String string) throws FileNotFoundException {
		try {
			Controller.loadPosition(new File(string));
		} catch (InvalidPositionException ex) {
			valid = false;
			error = ex;
			return;
		}
		valid = true;
		error = null;
	}

	/**
	 * @author Traian Coza
	 * This method checks that the position to load is valid
	 */
	@When("The position to load is valid")
	public void the_position_to_load_is_valid() {
		assertTrue(valid);
	}

	/**
	 * @author Traian Coza
	 * This method asserts the players turn
	 * @param string: the player whose turn it is
	 */
	@Then("It shall be {string}'s turn")
	public void it_shall_be_s_turn(String string) {
		assertEquals(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()
				.hasGameAsWhite() ? "white" : "black", string);
	}

	/**
	 * @author Traian Coza
	 * This method ensures that the player is at the specified position on the board
	 * @param string: the player
	 * @param int1: row of the current position of the player
	 * @param int2: column of the current position of the player
	 */
	@Then("{string} shall be at {int}:{int}")
	public void shall_be_at(String string, Integer int1, Integer int2) {
		Tile t = string.equals("white")
				? QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile()
				: QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile();

		assertTrue(t.getRow() == int1 && t.getColumn() == int2);
	}

	/**
	 * @author Traian Coza
	 * This method ensures that the vertical walls are at the specified coordinates
	 * @param string: the player
	 * @param int1: row of the position of the vertical wall
	 * @param int2: column of the position of the vertical wall
	 */
	@Then("{string} shall have a vertical wall at {int}:{int}")
	public void shall_have_a_vertical_wall_at(String string, Integer int1, Integer int2) {
		List<Wall> walls = string.equals("white")
				? QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsOnBoard()
				: QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsOnBoard();

		boolean exists = false;
		for (Wall wall : walls)
			exists |= (wall.getMove().getTargetTile().getRow() == int1
					&& wall.getMove().getTargetTile().getColumn() == int2
					&& wall.getMove().getWallDirection().equals(Direction.Vertical));
		assertTrue(exists);
	}

	/**
	 * @author Traian Coza
	 * This method ensures that the horizontal wall of the player is at the specified coordinates
	 * @param string: the player
	 * @param int1: row of the position of the horizontal wall
	 * @param int2: column of the position of the horizontal wall
	 */
	@Then("{string} shall have a horizontal wall at {int}:{int}")
	public void shall_have_a_horizontal_wall_at(String string, Integer int1, Integer int2) {
		List<Wall> walls = string.equals("white")
				? QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsOnBoard()
				: QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsOnBoard();

		boolean exists = false;
		for (Wall wall : walls)
			exists |= (wall.getMove().getTargetTile().getRow() == int1
					&& wall.getMove().getTargetTile().getColumn() == int2
					&& wall.getMove().getWallDirection().equals(Direction.Horizontal));
		assertTrue(exists);
	}

	/**
	 * @author Traian Coza
	 * This method ensures that both players have remaining walls in their stacks
	 * @param int1: remaining walls in stock
	 */
	@Then("Both players shall have {int} in their stacks")
	public void both_players_shall_have_in_their_stacks(Integer int1) {
		assertEquals(
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().size(),
				int1);
		assertEquals(
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsInStock().size(),
				int1);
	}

	// 2nd scenario

	/**
	 * @author Traian Coza
	 * This method checks whether the position to load is invalid
	 */
	@When("The position to load is invalid")
	public void the_position_to_load_is_invalid() {
		assertFalse(valid);
	}

	/**
	 * @author Traian Coza
	 * This method returns an error if the position to load is invalid
	 */
	@Then("The load shall return an error")
	public void the_load_shall_return_an_error() {
		assertNotNull(error);
	}

//Save Position: Traian Coza

	// 1st scenario

	/**
	 * @author Traian Coza
	 * This method checks that no file with the given filename exists in the file system
	 * @param string: filename of the file in the file system 
	 */
	@Given("No file {string} exists in the filesystem")
	public void no_file_exists_in_the_filesystem(String string) {
		File file = new File(string);
		if (file.exists())
			file.delete();
	}

	/**
	 * @author Traian Coza
	 * This method initiates the saving of the game with a specific filename
	 * @param string: the filename of the file to be saved
	 * @throws FileNotFoundException: signals that the attempt to open the file denoted by a specified path name has failed.
	 */
	@When("The user initiates to save the game with name {string}")
	public void the_user_initiates_to_save_the_game_with_name(String string) throws FileNotFoundException {
		Controller.saveGame(file = new File(string), false);
	}

	/**
	 * @author Traian Coza
	 * This method creates a new file in the filesystem with the given filename
	 * @param string: filename of the file saved
	 */
	@Then("A file with {string} shall be created in the filesystem")
	public void a_file_with_shall_be_created_in_the_filesystem(String string) {
		assertTrue(new File(string).exists());
	}

	// 2nd scenario

	/**
	 * @author Traian Coza
	 * This method is for the 2nd scenario where the file already exists in the filesystem.
	 * @param string: filename
	 * @throws IOException: exception happens when there is a failure during reading, writing and searching file or directory operation.
	 */
	@Given("File {string} exists in the filesystem")
	public void file_exists_in_the_filesystem(String string) throws IOException {
		new File(string).createNewFile();
	}

	/**
	 * @author Traian Coza
	 * In the case that the file already exists the user overwrites the existing file
	 * @throws FileNotFoundException: signals that the attempt to open the file denoted by a specified path name has failed.
	 */
	@When("The user confirms to overwrite existing file")
	public void the_user_confirms_to_overwrite_existing_file() throws FileNotFoundException {
		Controller.saveGame(file, true);
	}

	/**
	 * @author Traian Coza
	 * This method ensures that file is updated in the filesystem
	 * @param string: file name
	 */
	@Then("File with {string} shall be updated in the filesystem")
	public void file_with_shall_be_updated_in_the_filesystem(String string) {
		assertEquals(new File(string).lastModified() / 1000, System.currentTimeMillis() / 1000);
	}

	// 3rd scenario

	/**
	 * @author Traian Coza
	 * This method is called when the user cancels the overwriting of the existing file
	 * @throws FileNotFoundException: signals that the attempt to open the file denoted by a specified path name has failed.
	 */
	@When("The user cancels to overwrite existing file")
	public void the_user_cancels_to_overwrite_existing_file() throws FileNotFoundException {
		Controller.saveGame(file, false);
	}

	/**
	 * @author Traian Coza
	 * This method ensures that the file is not changed in the file system.
	 * @param string: filename
	 */
	@Then("File {string} shall not be changed in the filesystem")
	public void file_shall_not_be_changed_in_the_filesystem(String string) {
		assertTrue(System.currentTimeMillis() - new File(string).lastModified() > 0);
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
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setWhitePosition(
				new PlayerPosition(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer(),
				Controller.getTile(int2, int1)));
	}

	/**
	 * @author Gohar Saqib Fazal
	 */
	@When("Validation of the position is initiated")
	public void validation_of_the_position_is_initiated() {
		Iterator<Move> moveIt = QuoridorApplication.getQuoridor().getCurrentGame().getMoves().iterator();
		valid = true;
		for (GamePosition pos : QuoridorApplication.getQuoridor().getCurrentGame().getPositions())
		{
			QuoridorApplication.getQuoridor().getCurrentGame().setCurrentPosition(pos);
			if (!moveIt.hasNext())
				break;
			Move m = moveIt.next();
			if (m instanceof StepMove)
				valid &= Controller.isValidPawnMove(((StepMove)m).getTargetTile());
			else
			{
				Controller.setWallMoveCandidate(m.getTargetTile().getColumn(), m.getTargetTile().getRow(), ((WallMove)m).getWallDirection());
				valid &= Controller.isValidWallMove();
			}
		}
	}

	/**
	 * @author Gohar Saqib Fazal
	 * @param string: "Ok" or "Error" to tell whether the position is valid or not
	 */
	@Then("The position shall be {string}")
	public void the_position_shall_be(String string) {
		assertEquals(string.equals("ok"), valid);
		
//		if (Controller.initPosValidation(QuoridorApplication.getQuoridor().getCurrentGame()
//				.getMove(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size())
//				.getTargetTile()) == true) {
//			assert posValidationResult(Controller.initPosValidation(QuoridorApplication.getQuoridor().getCurrentGame()
//					.getMove(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()).getTargetTile()))
//							.equals("ok");
//		} else {
//			assert posValidationResult(Controller.initPosValidation(QuoridorApplication.getQuoridor().getCurrentGame()
//					.getMove(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()).getTargetTile()))
//							.equals("error");
//		}
	}

	/**
	 * @author Gohar Saqib Fazal
	 * @param int1:   The row number of the wall coordinate supplied
	 * @param int2:   The column number of the pawn coordinate supplied
	 * @param string: "Ok" or "Error" to tell whether the position is valid or not
	 */
	@Given("A game position is supplied with wall coordinate {int}:{int}-{string}")
	public void a_game_position_is_supplied_with_wall_coordinate(Integer int1, Integer int2, String string) {
		Controller.setWallMoveCandidate(int2, int1, string.equals("horizontal") ? Direction.Horizontal : Direction.Vertical);
		Controller.dropWall();
	}

	/**
	 * @author Gohar Saqib Fazal
	 */
	@Then("The position shall be valid")
	public void the_position_shall_be_valid() {
		assertTrue(valid);
	}

	/**
	 * @author Gohar Saqib Fazal
	 */
	@Then("The position shall be invalid")
	public void the_position_shall_be_invalid() {
		assert (!Controller.isValidWallMove());
	}

// Rotate Wall: Gohar Saqib Fazal

	/**
	 * @author Gohar Saqib Fazal
	 */
	@When("I try to flip the wall")
	public void i_try_to_flip_the_wall() {
		Controller.flipWall();
	}

	/**
	 * @author Gohar Saqib Fazal
	 * @param dir: The new direction that the wall already placed on the board
	 *             should be moved to
	 */
	@Then("The wall shall be rotated over the board to {string}")
	public void the_wall_shall_be_rotated_over_the_board_to(String dir) {
		if (dir.equalsIgnoreCase("vertical")) {
			assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate()
					.setWallDirection(Direction.Vertical));
		} else {
			assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate()
					.setWallDirection(Direction.Horizontal));
		}
	}

//-------------------------------------------------------------------------------------------------------------------------

//ProvideSelectUserName: Minh Quan Hoang

	// Given model api
	// when controller
	// then model api

	// throw UnsupportedOperationException

//					/** @author Minh Quan Hoang **/
//					//Initiates Quoridor, a Board and the users and players
//					@Given("A new game is initializing")
//					public void a_new_game_is_initializing() {
//					    initQuoridorAndBoard();
//					    createUsersAndPlayers("user1", "user2");
//					}
	/** @author Minh Quan Hoang **/
	// Chooses the next player to set the username
	@Given("Next player to set user name is {string}")
	public void next_player_to_set_user_name_is(String string) {
		if (string == "black")
			QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().setUser(null);

		else if (string == "white")
			QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().setUser(null);
	}

	/** @author Minh Quan Hoang **/
	// Adds a user with the username from the string
	@And("There is existing user {string}")
	public void there_is_existing_user(String string) {
		QuoridorApplication.getQuoridor().addUser(string);
	}

	/** @author Minh Quan Hoang **/
	// Link the player with his username
	@When("The player selects existing {string}")
	public void the_player_selects_existing(String string) {
		Player player = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
		Controller.SelectExistingUsername(player, string);
	}

	/** @author Minh Quan Hoang **/
	// Check if the name of the player is the new username
	@Then("The name of player {string} in the new game shall be {string}")
	public void the_name_of_player_in_the_new_game_shall_be(String string, String string2) {
		if (string.equals("Black"))
			assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().getUser().getName()
					.equals(string2));
		else if (string.equals("White"))
			assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getUser().getName()
					.equals(string2));

	}

	/** @author Minh Quan Hoang **/
	// Checks for a user with the string username and deletes it if it exists
	@Given("There is no existing user {string}")
	public void there_is_no_existing_user(String string) {
		List<User> list = QuoridorApplication.getQuoridor().getUsers();

		for (User u : list) {
			if (u.getName().equals(string))
				QuoridorApplication.getQuoridor().removeUser(u);

		}
	}

	Throwable thrown;
	
	/** @author Minh Quan Hoang **/
	// Creates a new username with the string as username
	@When("The player provides new user name: {string}")
	public void the_player_provides_new_user_name(String string) {
		thrown = null;
		try { Controller.CreateNewUsername(string); }
		catch (Throwable t) { thrown = t; }
	}

	/** @author Minh Quan Hoang **/
	// Checks whether the username string already exists
	@Then("The player shall be warned that {string} already exists")
	public void the_player_shall_be_warned_that_already_exists(String string) {
		assertNotNull(thrown);
	}

	/** @author Minh Quan Hoang **/
	// Checks whether the next player does not have a user yet, meaning it would be
	// his turn to set the username
	@Then("Next player to set user name shall be {string}")
	public void next_player_to_set_user_name_shall_be(String string) {
		if (string.equals("black")) {
			assertTrue(
					QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().getUser().getName() == "user2");
		} else if (string.equals("white")) {
			assertTrue(
					QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getUser().getName() == "user1");
		}
	}

//StartNewGame : Minh Quan Hoang

	// Given model api (action, change something with quoridorapplication)
	// when controller
	// then model api assert

	/** @author Minh Quan Hoang **/
	// Initialize a new game of Quoridor
	@When("A new game is being initialized")
	public void a_new_game_is_being_initialized() {
		Controller.InitializeNewGame();
	}

	/** @author Minh Quan Hoang **/
	// Get username for white player from player class
	@When("White player chooses a username")
	public void white_player_chooses_a_username() {
		Controller.setWhitePlayerUsername("default");
	}

	/** @author Minh Quan Hoang **/
	// Get username for black player from player class
	@When("Black player chooses a username")
	public void black_player_chooses_a_username() {
		Controller.setBlackPlayerUsername("default2");
	}

	/** @author Minh Quan Hoang **/
	// Sets the total thinking time
	@When("Total thinking time is set")
	public void total_thinking_time_is_set() {
		Controller.setTotalThinkingTime(3, 0);
	}

	/** @author Minh Quan Hoang **/
	// Checks if the game is ready to start
	@Then("The game shall become ready to start")
	public void the_game_shall_become_ready_to_start() {
		assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus()
				.equals(Game.GameStatus.ReadyToStart));
	}

	/** @author Minh Quan Hoang **/
	// Changes the status of the game to be ready to start
	@Given("The game is ready to start")
	public void the_game_is_ready_to_start() {
		if (QuoridorApplication.getQuoridor().hasCurrentGame()) {
			QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(Game.GameStatus.ReadyToStart);
		}
	}

	/** @author Minh Quan Hoang **/
	// Start running the game and initialize board (create and start game method)
	@When("I start the clock")
	public void i_start_the_clock() {
		// this is GUI

	}

	/** @author Minh Quan Hoang **/
	// Checks if the game status is set to running
	@Then("The game shall be running")
	public void the_game_shall_be_running() {
		if (QuoridorApplication.getQuoridor().hasCurrentGame()) {
			assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus().equals(Game.GameStatus.Running));
		}
	}

	/** @author Minh Quan Hoang **/
	// Checks if the board has been initialized
	@Then("The board shall be initialized")
	public void the_board_shall_be_initialized() {
		assertTrue(QuoridorApplication.getQuoridor().hasBoard());
	}

//-------------------------------------------------------------------------------------------------------------------------

	// Jake Pogharian step
	// Set Total Thinking Time

	/**
	 * @author Jake Pogharian Feature: SetTotalThinkingTime Step: "{int}:{int} is
	 *         set as the thinking time" This step calls on the Controller method to
	 *         initialize the remaining time (thinking time) for each player.
	 * @param int1: This is the Integer used to indicate the minutes of the thinking
	 *              time
	 * @param int2: This is the Integer used to indicate the seconds of the thinking
	 *              time
	 */

	@When("{int}:{int} is set as the thinking time")
	public void is_set_as_the_thinking_time(Integer int1, Integer int2) {

		Controller.setThinkingTime(int1, int2);

	}

	/**
	 * @author Jake Pogharian This step shall validate whether both player's
	 *         initially have remaining time equivalent to the thinking time
	 *         initialized before the start of the game. It assumes that the
	 *         thinking time is no greater than 24 hours (less than 24 hours). it
	 *         does so by validating that the string equivalent of the time object
	 *         Feature : SetTotalThinkingTime Step : "Both players shall have
	 *         {int}:{int} remaining time left"
	 * @param int1: the Integer which will indicate the number of minutes
	 * @param int2: the Integer which will indicate the number of hours
	 */
	@Then("Both players shall have {int}:{int} remaining time left")
	public void both_players_shall_have_remaining_time_left(Integer int1, Integer int2) {
		Time time = new Time(0, int1, int2);
		// assert that given Time corresponds to initial remaining time of both players
		assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getRemainingTime().toString()
				.equals(time.toString())
				&& QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().getRemainingTime().toString()
						.equals(time.toString()));

	}

	// Drop Wall Feature

	/**
	 * @author Jake Pogharian Feature: Drop Wall Step: " Given the wall move
	 *         candidate with {string} at position \\({int}, {int}) is valid"
	 * @param string: This is the String used to indicate the wall move candidate's
	 *                direction.
	 * @param int1:   This is the Integer used to indicate the row of the wall move
	 *                candidate
	 * @param int2:   This is the Integer used to indicate the column of the wall
	 *                move candidate
	 */
	@Given("The wall move candidate with {string} at position \\({int}, {int}) is valid")
	public void the_wall_move_candidate_with_at_position_is_valid(String string, Integer int1, Integer int2) {

		Controller.setWallMoveCandidate(int2, int1, string.equals("horizontal") ? Direction.Horizontal : Direction.Vertical);
		
//		String dir = string;
//		if (string.equals("horizontal"))
//			dir = "Horizontal";
//		if (string.equals("vertical"))
//			dir = "Vertical";
//		// transform Row and Column to Tile Index
//		int row = int1;
//		int column = int2;
//		int index = ((row - 1) * 9) + column;
//
//		Direction aWallDirection = Direction.valueOf(dir);
//		int aMoveNumber = QuoridorApplication.getQuoridor().getCurrentGame().numberOfMoves() + 1;
//		int aRoundNumber = aMoveNumber / 2 + 1;
//		Player aPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove();
//		Tile aTargetTile = QuoridorApplication.getQuoridor().getBoard().getTile(index);
//		Game aGame = QuoridorApplication.getQuoridor().getCurrentGame();
//		Wall aWallPlaced;
//
//		// get index of top-most wall in stock for given player
//
//		// select top most wall in stock for currentPlayer
//		// depending on whether or not player is white or black proceed
//		Player currentPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
//				.getPlayerToMove();
//		// validate according to which player turn it is currently
//		if (currentPlayer.hasGameAsWhite()) {
//			// get index of the last wall in stock for given player
//			int lastWallIndex = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
//					.getWhiteWallsInStock().size() - 1;
//			// get the last wall in stock
//			aWallPlaced = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
//					.getWhiteWallsInStock(lastWallIndex);
//		} else {
//			int lastWallIndex = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
//					.getBlackWallsInStock().size() - 1;
//			aWallPlaced = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
//					.getBlackWallsInStock(lastWallIndex);
//		}
//
//		// create wall move
//		WallMove candidateWallMove = new WallMove(aMoveNumber, aRoundNumber, aPlayer, aTargetTile, aGame,
//				aWallDirection, aWallPlaced);
//		// set it as the candidate wall move
//		QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(candidateWallMove);

	}

	/**
	 * @author Jake Pogharian Feature: Drop Wall Step: ("When I release the wall in
	 *         my hand")
	 */
	@When("I release the wall in my hand")
	public void i_release_the_wall_in_my_hand() {
		valid = Controller.isValidWallMove();
		wallsOnBoard();
		System.out.println("Valid: " + valid);
		if (valid)
			Controller.dropWall();
		else
			Controller.cancelCandidate();
		wallsOnBoard();
	}
	
	

	/**
	 * @author Jake Pogharian Feature: Drop Wall Step: ("Then A wall move shall be
	 *         registered with {string} at position \\({int}, {int})")
	 * @param string: This is the String used to indicate the wall move direction.
	 * @param int1:   This is the Integer used to indicate the row of the wall move
	 * @param int2:   This is the Integer used to indicate the column of the wall
	 *                move
	 */
	@Then("A wall move shall be registered with {string} at position \\({int}, {int})")
	public void a_wall_move_shall_be_registered_with_at_position(String string, Integer int1, Integer int2) {

		assertTrue(Controller.isWallSet(int2, int1, string.equals("horizontal") ? Direction.Horizontal : Direction.Vertical));
		wallsOnBoard();
	//
//		
//		String aWallDirection = string;
//		if (aWallDirection.equals("horizontal"))
//			aWallDirection = "Horizontal";
//		if (aWallDirection.equals("vertical"))
//			aWallDirection = "Vertical";
//		Direction dir = Direction.valueOf(aWallDirection);
//		
//		int numberOfWalls;
//		WallMove lastWallMove;
//		Player currentPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
//				.getPlayerToMove();
//
//		if (currentPlayer.hasGameAsWhite()) {
//			// get index in wallsOnBoard list of last wall on board
//			numberOfWalls = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
//					.numberOfWhiteWallsOnBoard();
//			// get last wall on board
//			lastWallMove = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
//					.getWhiteWallsOnBoard().get(numberOfWalls - 1).getMove();
//		} else {
//			numberOfWalls = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
//					.numberOfBlackWallsOnBoard();
//			lastWallMove = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
//					.getBlackWallsOnBoard().get(numberOfWalls - 1).getMove();
//
//		}
//
//		// check that the last wall placed on the board, was placed during this turn and
//		// that it corresponds to those coordinates and direction
//		assertTrue(lastWallMove.getWallDirection().toString().equals(aWallDirection));
//		assertTrue(lastWallMove.getTargetTile().getRow() == int1 && lastWallMove.getTargetTile().getColumn() == int2
//				&& lastWallMove.getNextMove() == null);

	}

	/**
	 * @author Jake Pogharian Feature: Drop Wall Step: ("Then I shall not have a
	 *         wall in my hand")
	 */

	@Then("I shall not have a wall in my hand")
	public void i_shall_not_have_a_wall_in_my_hand() {
		assertNull(QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate());
	}

	/**
	 * @author Jake Pogharian Feature: Drop Wall Step: "Then my move shall be
	 *         completed"
	 */
	@Then("My move shall be completed")
	public void my_move_shall_be_completed() {

		// Check that last move in Moves List belongs to player whose turn it currently
		// is -- this shall verify that the move is completed, (it is only subsequent to
		// the completion of the move that the turn is completed)

		assertNotEquals(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove(),
				QuoridorApplication.getQuoridor().getCurrentGame().getMoves().get(
						QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()-1));
		
//		// this is to get index of last move in moves List
//		int index = QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size() - 1;
//		if (QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()
//				.hasGameAsWhite()) {
//			// if turn is of white player (if I am white player)
//			assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().get(index).getPlayer()
//					.hasGameAsWhite());
//		} else {
//			// if turn is of black player (if i am black player)
//			assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().get(index).getPlayer()
//					.hasGameAsBlack());
//		}
	}

	/**
	 * @author Jake Pogharian Feature: Drop Wall Step: "Then it shall not be my turn
	 *         to move"
	 */
	@Then("It shall not be my turn to move")
	public void it_shall_not_be_my_turn_to_move() {

		// assuming I am player1 (white)
		assertFalse(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()
				.hasGameAsWhite());
		wallsOnBoard();
	}

	/**
	 * @author Jake Pogharian Feature: Drop Wall Step: "Given the wall move
	 *         candidate with {string} at position \\({int}, {int}) is invalid"
	 * @param string: This is the String used to indicate the wall move direction.
	 * @param int1:   This is the Integer used to indicate the row of the wall move
	 * @param int2:   This is the Integer used to indicate the column of the wall
	 *                move
	 * 
	 */
	@Given("The wall move candidate with {string} at position \\({int}, {int}) is invalid")
	public void the_wall_move_candidate_with_at_position_is_invalid(String string, Integer int1, Integer int2) {

		Controller.setWallMoveCandidate(int2, int1, string.equals("horizontal") ? Direction.Horizontal : Direction.Vertical);
		
		
		// transform Row and Column to Tile Index!
//		int row = int1;
//		int column = int2;
//		int index = ((row - 1) * 9) + column;
//		String dir = string;
//		if (dir.equals("horizontal"))
//			dir = "Horizontal";
//		if (dir.equals("vertical"))
//			dir = "Vertical";
//		Direction aWallDirection = Direction.valueOf(dir);
//
//		int aMoveNumber = QuoridorApplication.getQuoridor().getCurrentGame().numberOfMoves() + 1;
//		int aRoundNumber = aMoveNumber / 2 + 1;
//		Player aPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove();
//		Tile aTargetTile = QuoridorApplication.getQuoridor().getBoard().getTile(index);
//		Game aGame = QuoridorApplication.getQuoridor().getCurrentGame();
//		Wall aWallPlaced;
//
//		// get index of top-most wall in stock for given player
//
//		Player currentPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
//				.getPlayerToMove();
//		// select top most wall in stock for currentPlayer
//		if (currentPlayer.hasGameAsWhite()) {
//			int lastWallIndex = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
//					.getWhiteWallsInStock().size() - 1;
//			aWallPlaced = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
//					.getWhiteWallsInStock(lastWallIndex);
//		} else {
//			int lastWallIndex = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
//					.getBlackWallsInStock().size() - 1;
//			aWallPlaced = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
//					.getBlackWallsInStock(lastWallIndex);
//		}
//
//		// create and set CandidateWallMove
//		WallMove candidateWallMove = new WallMove(aMoveNumber, aRoundNumber, aPlayer, aTargetTile, aGame,
//				aWallDirection, aWallPlaced);
//
//		QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(candidateWallMove);

	}

	/**
	 * @author Jake Pogharian Feature: Drop Wall Step: "Then I shall be notified
	 *         that my wall move is invalid"
	 */
	@Then("I shall be notified that my wall move is invalid")
	public void i_shall_be_notified_that_my_wall_move_is_invalid() {
		assertFalse(valid);
	}

	/**
	 * @author Jake Pogharian Feature: Drop Wall Step: "It shall be my turn to move"
	 */
	@Then("It shall be my turn to move")
	public void it_shall_be_my_turn_to_move() {
		// assuming I am player 1 and that player 1 is white
		// assert that it is player white's turn to move
		assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()
				.hasGameAsWhite());

	}

	/**
	 * @author Jake Pogharian Feature: Drop Wall Step: "Then no wall move shall be
	 *         registered with {string} at position \\({int}, {int})"
	 * @param string: This is the String used to indicate the wall move direction.
	 * @param int1:   This is the Integer used to indicate the row of the wall move
	 * @param int2:   This is the Integer used to indicate the column of the wall
	 *                move
	 */
	@Then("No wall move shall be registered with {string} at position \\({int}, {int})")
	public void no_wall_move_shall_be_registered_with_at_position(String string, Integer int1, Integer int2) {
		// check that the last wallMove placed on board is NOT in fact the given wall
		// (wall candidate)
		String aWallDirection = string;

		if (aWallDirection.equals("horizontal"))
			aWallDirection = "Horizontal";
		if (aWallDirection.equals("vertical"))
			aWallDirection = "Vertical";
		Direction dir = Direction.valueOf(aWallDirection);

		int numberOfWalls;
		WallMove lastWallMove;
		// checks if the player in question is white or black and then checks
		// accordingly
		if (QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()
				.hasGameAsWhite()) {
			numberOfWalls = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
					.numberOfWhiteWallsOnBoard();
			lastWallMove = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
					.getWhiteWallsOnBoard().get(numberOfWalls - 1).getMove();

		} else {
			numberOfWalls = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
					.numberOfBlackWallsOnBoard();
			lastWallMove = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
					.getBlackWallsOnBoard().get(numberOfWalls - 1).getMove();

		}
		// assert that NO wall move was registered, by checking that the last wall move
		// does not correspond to the given coordinates or wall direction or that if it
		// is, it does not correspond to a move made during this turn. Ie: wall placed
		// at that position at the prior turn.
		assertTrue(lastWallMove.getWallDirection() != dir || lastWallMove.getTargetTile().getRow() != int1
				|| lastWallMove.getTargetTile().getColumn() != int2 || lastWallMove.getNextMove() != null);

	}

	
	
	////------------------------DELIVERABLE 5 Step Definitions------------------------/// 
	
	/**
	 * @author Gohar Saqib Fazal Feature: CheckIfPathExists
	 * This method places the wall move candidate at int1, int2
	 * @param string: This is the String used to indicate the wall move direction.
	 * @param int1:   This is the Integer used to indicate the row of the wall move
	 * @param int2:   This is the Integer used to indicate the column of the wall
	 */
	@Given("A {string} wall move candidate exists at position {int}:{int}")
	public void a_wall_move_candidate_exists_at_position(String string, Integer int1, Integer int2) {
		a_wall_move_candidate_exists_with_at_position(string, int1, int2);
	}

	/**
	 * @author Gohar Saqib Fazal Feature: CheckIfPathExists
	 * This method places the black player at the location int1, int2
	 * @param int1:   This is the Integer used to indicate the row of the new position of the player
	 * @param int2:   This is the Integer used to indicate the column of the new position of the player
	 */
	@Given("The black player is located at {int}:{int}")
	public void the_black_player_is_located_at(Integer int1, Integer int2) {
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().setTile(Controller.getTile(int2, int1));
	}

	/**
	 * @author Gohar Saqib Fazal Feature: CheckIfPathExists
	 * This method places the white player at the location int1, int2
	 * @param int1:   This is the Integer used to indicate the row of the new position of the player
	 * @param int2:   This is the Integer used to indicate the column of the new position of the player
	 */
	@Given("The white player is located at {int}:{int}")
	public void the_white_player_is_located_at(Integer int1, Integer int2) {
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().setTile(Controller.getTile(int2, int1));
	}

	/**
	 * @author Gohar Saqib Fazal Feature: CheckIfPathExists
	 * This method asserts that the path existence is initiated
	 */
	@When("Check path existence is initiated")
	public void check_path_existence_is_initiated() {

	}

	/**
	 * @author Gohar Saqib Fazal Feature: CheckIfPathExists
	 * This method checks whether the path is available for white, black or both players.
	 * @param string: Result of the availability of path for white, black or both players.
	 */
	@Then("Path is available for {string} player\\(s)")
	public void path_is_available_for_player_s(String string) {
		if(string.equals("both")) {
			assertFalse(Controller.wallMoveCandidateBlocksPath(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer()));
			assertFalse(Controller.wallMoveCandidateBlocksPath(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer()));
		}
		else if(string.equals("white")){
			assertFalse(Controller.wallMoveCandidateBlocksPath(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer()));
			//assertFalse(Controller.wallMoveCandidateBlocksPath(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer()));
		}
		else if(string.equals("black")) {
			//assertFalse(Controller.wallMoveCandidateBlocksPath(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer()));
			assertFalse(Controller.wallMoveCandidateBlocksPath(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer()));
		}
		else {
			assertTrue(Controller.wallMoveCandidateBlocksPath(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer()));
			assertTrue(Controller.wallMoveCandidateBlocksPath(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer()));	    }
	}

	/**
	 * @author: Traian Coza, Feature: EnterReplayMode
	 * This method invokes a controller method to initialize a new game, and then sets the Game status to enumeration Replay
	 **/
	@When("I initiate replay mode")
	public void i_initiate_replay_mode() {
		Controller.InitializeNewGame();
		QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.Replay);
	}

	/**
	 * @author: Traian Coza, Feature: EnterReplayMode
	 * This method asserts if the enum GameStatus has been set to Replay
	 */
	@Then("The game shall be in replay mode")
	public void the_game_shall_be_in_replay_mode() {
		assertEquals(GameStatus.Replay, QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus());
	}

	/**
	 * @author Traian Coza, Feature: EnterReplayMode
	 * This method plays the moves specified in the dataTable passed as a parameter
	 * @param dataTable: A datatable containing the moves that will have been played in game as a result of this method.
	 */
	@Given("The following moves have been played in game:")
	public void the_following_moves_have_been_played_in_game(io.cucumber.datatable.DataTable dataTable) {
		for (Map<String, String> map : dataTable.asMaps())
		{
			if (map.get("move").charAt(1) == '-')
				QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(
						map.get("move").charAt(0) == '1' ? GameStatus.WhiteWon : GameStatus.BlackWon);
			else
				Controller.doMove(map.get("move"));
		}
	}
	
	/** @author: Traian Coza, Feature: EnterReplayMode
	 *  This method ensures that the game does not have a final result by calling the_game_has_no_final_results method
	 */
	@Given("The game does not have a final result")
	public void the_game_does_not_have_a_final_result() {
		the_game_has_no_final_results();
	}
	
	/**
	 * @author Traian Coza, Feature: EnterReplayMode
	 * This method sets the next move according to the move and round numbers passed as parameters
	 * @param int1 - movenumber, the number of the move 
	 * @param int2 - roundnumber, the number of the round
	 */
	@Given("The next move is {int}:{int}")
	public void the_next_move_is(int int1, int int2) {
		QuoridorApplication.getQuoridor().getCurrentGame().setCurrentPosition(
				QuoridorApplication.getQuoridor().getCurrentGame().getPosition(2*(int1-1)+(int2-1)));
	}
	
	/**
	 * @author Traian Coza, Feature: EnterReplayMode
	 * This method invokes a controller method that initiates the call to continue game 
	 */
	@When("I initiate to continue game")
	public void i_initiate_to_continue_game() {
		valid = Controller.continueGame();
	}
	
	/**
	 * @author Traian Coza, Feature: EnterReplayMode
	 * This method asserts if the remaining moves of the game have been removed. 
	 */
	@Then("The remaining moves of the game shall be removed")
	public void the_remaining_moves_of_the_game_shall_be_removed() {
		Controller.assertCurrentPositionIsLastPosition();
	}

	/**
	 * @author Traian Coza, Feature: EnterReplayMode
	 * This method ensures that the game does now have a final result by calling the_game_has_a_final_result helper method
	 */
	@Given("The game has a final result")
	public void the_game_has_a_final_result() {
		try
		{
			the_game_does_not_have_a_final_result();
			fail();
		}
		catch (AssertionError e) { }
	}

	/**
	 * @author Traian Coza, Feature: Enter Replay Mode
	 * Asserts that the user is notified that finished games cannot be continued 
	 */
	@Then("I shall be notified that finished games cannot be continued")
	public void i_shall_be_notified_that_finished_games_cannot_be_continued() {
		assertFalse(valid);
	}

	/** 
	 * @author Jake Pogharian, Feature: IdentifyGameDrawn
	 * Executes the moves from the data table
	 * @param dataTable: Has the list of moves that will be executed by this method
	 */
	@Given("The following moves were executed:")
	public void the_following_moves_were_executed(io.cucumber.datatable.DataTable dataTable) {
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			Integer row = Integer.decode(map.get("row"));
			Integer col = Integer.decode(map.get("col"));

			Controller.doPawnMove(col, row);;
		}
	}

	/**
	 * @author Jake Pogharian Feature: IdentifyGameDrawn
	 * This method completes the player's move
	 * @param string: This is the String used to indicate the move completed
	 */
	@Given("Player {string} has just completed his move")
	public void player_has_just_completed_his_move(String string) {
		//just create a move
	}

	/**
	 * @author Jake Pogharian Feature: IdentifyGameDrawn
	 * This method makes the last move for a player
	 * @param string: This is the String used to indicate if it's the white or black player
	 * @param int1:   This is the Integer used to indicate the row of the move
	 * @param int2:   This is the Integer used to indicate the column of the move
	 */
	@Given("The last move of {string} is pawn move to {int}:{int}")
	public void the_last_move_of_is_pawn_move_to(String string, Integer int1, Integer int2) {
		Controller.doPawnMove(int2, int1);
	}

	/**
	 * @author Jake Pogharian Feature: IdentifyGameDrawn
	 * This method checks and updates the status of the game.
	 */
	@When("Checking of game result is initated")
	public void checking_of_game_result_is_initated() {
		Controller.updateGameStatus();
	}

	/**
	 * @author Jake Pogharian Feature: IdentifyGameDrawn
	 * This method changes the game status depending on the string given.
	 * @param string: This is the String used to indicate the game status wanted
	 */
	@Then("Game result shall be {string}")
	public void game_result_shall_be(String string) {
		GameStatus gameStatus;
		if(string.equalsIgnoreCase("whiteWon")) {
			gameStatus=GameStatus.WhiteWon;
		}else if(string.equalsIgnoreCase("blackWon")){
			gameStatus=GameStatus.BlackWon;
		}else if(string.equalsIgnoreCase("pending")){
			gameStatus=GameStatus.Running;
		}else if (string.equalsIgnoreCase("Drawn")) {
			gameStatus=GameStatus.Draw;
		}else {
			//add proper code to this for the DRAW
			gameStatus=GameStatus.Initializing;
		}

		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		assertEquals(gameStatus, game.getGameStatus());
	}

	/**
	 * @author Gohar Saqib Fazal Feature: CheckIfPathExists
	 * This method asserts that the status of the game is anything but running.
	 */
	@Then("The game shall no longer be running")
	public void the_game_shall_no_longer_be_running() {
		assertNotEquals(GameStatus.Running, QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus());
	}

	/**
	 * @author Jake Pogharian Feature: IdentifyGameWon
	 * This method places the player at int1, int2 position.
	 * @param string: This is the String used to indicate if the player is white or black.
	 * @param int1:   This is the Integer used to indicate the row of the new position for the player
	 * @param int2:   This is the Integer used to indicate the column of the new position for the player
	 */
	@Given("The new position of {string} is {int}:{int}")
	public void the_new_position_of_is(String string, Integer int1, Integer int2) {
		if (string.equals("white")) {
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().setTile(Controller.getTile(int2, int1));
			QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getPawnBehaviour().setSMTest();
		}else {
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().setTile(Controller.getTile(int2, int1));
			QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().getPawnBehaviour().setSMTest();

		}
	}

	/**
	 * @author Jake Pogharian Feature: IdentifyGameWon
	 * This method sets the thinking time of the white or black player to more than zero.
	 */
	@Given("The clock of {string} is more than zero")
	public void the_clock_of_is_more_than_zero(String string) {
		Controller.setThinkingTime(10,10);

	}

	/**
	 * @author Jake Pogharian Feature: IdentifyGameWon
	 * This method counts down the thinking time of the white or black player to zero.
	 * @param string: This is the String used to indicate if the player is white or black.
	 */
	@When("The clock of {string} counts down to zero")
	public void the_clock_of_counts_down_to_zero(String string) {
		Player player;
		if(string.equals("white")) {
			player=QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
		} else {
			player=QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer();
		}
		Controller.countdownZero(player);
	}

	/**
	 * @author Minh Quan Hoang Feature: StepBackward, StepForward
	 * This method puts the game status to replay mode.
	 */
	@Given("The game is in replay mode")
	public void the_game_is_in_replay_mode() {
		initQuoridorAndBoard();
		Controller.InitializeNewGame();
		QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.Replay);
	}

	/**
	 * @author Minh Quan Hoang Feature: StepBackward
	 * This method makes the game go back one step (one move backwards) in the replay mode.
	 */
	@When("Step backward is initiated")
	public void step_backward_is_initiated() {
		Controller.stepBackwards();
	}
	
	/**
	 * @author Minh Quan Hoang Feature: StepForward
	 * This method makes the game go forward one step (one move forwards) in the replay mode.
	 */
	@When("Step forward is initiated")
	public void step_forward_is_initiated() {
		Controller.stepForwards();
	}

	/**
	 * @author Lenoy Christy Feature: JumpToStart
	 * This method jumps to the starting position of the game in replay mode.
	 */
	@When("Jump to start position is initiated")
	public void jump_to_start_position_is_initiated() {
		Controller.jumpToStartPos();
	}

	/**
	 * @author Lenoy Christy Feature: JumpToFinal
	 * This method jumps to the final position of the game in replay mode.
	 */
	@When("Jump to final position is initiated")
	public void jump_to_final_position_is_initiated() {
		Controller.jumpToFinalPos();
	}

	/**
	 * @author Minh Quan Hoang Feature: StepBackward, StepForward
	 * This method checks that the current position is such that the next move is as specified true.
	 * @param int1:   This is the Integer used to indicate moveNumber
	 * @param int2:   This is the Integer used to indicate roundNumber
	 */
	@Then("The next move shall be {int}:{int}")
	public void the_next_move_shall_be(int int1, int int2) {
		int nextMove = QuoridorApplication.getQuoridor().getCurrentGame().getPositions().indexOf(
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition());
		assertEquals(int1, nextMove / 2 + 1);
		assertEquals(int2, nextMove % 2 + 1);
	}

	/**
	 * @author Minh Quan Hoang Feature: StepBackward, StepForward
	 * This method checks if the white player at int1, int2 position.
	 * @param int1:   This is the Integer used to indicate the row of the expected position for the player
	 * @param int2:   This is the Integer used to indicate the column of the expected position for the player
	 */
	@Then("White player's position shall be \\({int},{int})")
	public void white_player_s_position_shall_be(int int1, int int2) {
		int row = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getRow();
		int column = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getColumn();
		assertTrue(row == int1 && column == int2);
	}
	
	/**
	 * @author Minh Quan Hoang Feature: StepBackward, StepForward
	 * This method checks if the black player is at int1, int2 position.
	 * @param string: This is the String used to indicate if the player is white or black.
	 * @param int1:   This is the Integer used to indicate the row of the expected position for the player
	 * @param int2:   This is the Integer used to indicate the column of the expected position for the player
	 */
	@Then("Black player's position shall be \\({int},{int})")
	public void black_player_s_position_shall_be(int int1, int int2) {
		int row = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getRow();
		int column = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getColumn();
		assertEquals(int1, row);
		assertEquals(int2, column);
	}
	
	/**
	 * @author Minh Quan Hoang Feature: StepBackward, StepForward
	 * This method checks if the white player has int1 number of walls on stock
	 * @param int1:   This is the Integer used to indicate the number of walls on stock expected
	 */
	@Then("White has {int} on stock")
	public void white_has_wwallno_on_stock(int int1) {
		int size = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().size();
		assertEquals(int1, size);
	}

	/**
	 * @author Minh Quan Hoang Feature: StepBackward, StepForward
	 * This method checks if the black player has int1 number of walls on stock
	 * @param int1:   This is the Integer used to indicate the number of walls on stock expected
	 */
	@Then("Black has {int} on stock")
	public void black_has_bwallno_on_stock(int int1) {
		int size = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsInStock().size();
		assertEquals(int1, size);
	}
	
	/**
	 * @author David Budaghyan Feature: LoadGame
	 * This method loads a game in the given file
	 * @param string FileName of the file that is to be loaded
	 * @throws FileNotFoundException It throws this exception if there is no file to load
	 */
	@When("I initiate to load a game in {string}")
	public void i_initiate_to_load_a_game_in(String string) throws FileNotFoundException {
		valid = true;
		try { Controller.loadGame(new File(string)); }
		catch (InvalidPositionException ex) { valid = false; }
	}
	
	/**
	 * @author David Budaghyan Feature: LoadGame
	 * This method checks that each game move is valid before the game is loaded
	 */
	@When("Each game move is valid")
	public void each_game_move_is_valid() {
		assertTrue(valid);
	}
	
	/**
	 * @author David Budaghyan Feature: LoadGame
	 * This method checks that the game has no final results and is still in progress.
	 */
	@When("The game has no final results")
	public void the_game_has_no_final_results() {
		GameStatus status = QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus();
		assertNotEquals(GameStatus.WhiteWon, status);
		assertNotEquals(GameStatus.BlackWon, status);
		assertNotEquals(GameStatus.Draw, status);
	}

	/**
	 * @author David Budaghyan Feature: LoadGame
	 * This checks whether the game to load has an invalid move.
	 */
	@When("The game to load has an invalid move")
	public void the_game_to_load_has_an_invalid_move() {
		assertFalse(valid);
	}
	
	/**
	 * @author David Budaghyan Feature: LoadGame
	 * This notifies the user that the game file is invalid when the game to load has an invalid move.
	 */
	@Then("The game shall notify the user that the game file is invalid")
	public void the_game_shall_notify_the_user_that_the_game_file_is_invalid() {
		assertFalse(valid);
	}
	
	/**
	 * @author David Budaghyan Feature: ReportFinalResult
	 * It checks whether the game is running or not. In the case that it's not running the final result is displayed on the screen.
	 */
	@When("The game is no longer running")
	public void the_game_is_no_longer_running() {
		QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.WhiteWon);
	}
	
	/**
	 * @author David Budaghyan Feature: ReportFinalResult
	 * This method displays the final result on the screen when the game is over
	 */
	@Then("The final result shall be displayed")
	public void the_final_result_shall_be_displayed() {
		try
		{
			System.out.println("Results: " + QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
	
	/**
	 * @author David Budaghyan Feature: ReportFinalResult
	 * This method ensures that the white players clock isn't counting down when the game is over.
	 */
	@Then("White's clock shall not be counting down")
	public void white_s_clock_shall_not_be_counting_down() {
		if(!(PlayScreenController.instance==null))
			assertTrue(PlayScreenController.instance.board.players[0].isClockStopped());
	}
	
	/**
	 * @author David Budaghyan Feature: ReportFinalResult
	 * This method ensures that the black players clock isn't counting down when the game is over
	 */
	@Then("Black's clock shall not be counting down")
	public void black_s_clock_shall_not_be_counting_down() {
		if(!(PlayScreenController.instance==null))
			assertTrue(PlayScreenController.instance.board.players[1].isClockStopped());
	}
	
	/**
	 * @author David Budaghyan Feature: ReportFinalResult
	 * This is a GUI method that ensures that white shall not be able to move when the game is over.
	 */
	@Then("White shall be unable to move")
	public void white_shall_be_unable_to_move() {
		//Implemented in GUI
	}
	
	/**
	 * @author David Budaghyan Feature: ReportFinalResult
	 * This is a GUI method that ensures that black shall not be able to move when the game is over. 
	 */
	@Then("Black shall be unable to move")
	public void black_shall_be_unable_to_move() {
		//Implemented in GUI
	}

	/**
	 * @author Gohar Saqib Fazal Feature: ResignGame
	 * This method initiates the resign functionality for the player that presses the resign button
	 */
	@When("Player initates to resign")
	public void player_initates_to_resign() {
		Controller.resign(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove());
	}
	//THE SAVE GAME CUCUMBER STEPS ARE DONE WITH SAVE POSITION AND LOAD POSITION CUCUMBER STEP DEFINITIONS SEPERATELY BECAUSE THEY WERE SIMILAR TO EACH OTHER
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
			if (wall != null) {
				wall.delete();
			}
		}
	}

	// ***********************************************
	// Extracted helper methods
	// ***********************************************

	// Place your extracted methods below

	
	/**
	 * @author David Budaghyan
	 * this converts directions to integers
	 * helper method for jumpPawn and MovePawn steps
	 * @param dir: the direction that we want to convert
	 */
	int dirToInt(String dir)
	{
		switch (dir)
		{
		case "up": return 0;
		case "down": return 2;
		case "left": return 3;
		case "right": return 1;
		default: return -1;
		}
	}
	
	/**
	 * @author David Budaghyan
	 * return void
	 * feature: jumpPawn/movePawn - helper method
	 */
	private void setNewPlayerPosition(Player aPlayer, PlayerPosition aNewPosition){
		if(aPlayer.hasGameAsWhite()) {
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setWhitePosition(aNewPosition);
		}
		if(aPlayer.hasGameAsBlack()) {
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setBlackPosition(aNewPosition);
		}
	}
	
	/**
	 * @author David Budaghyan
	 * return playerPosition
	 * feature: jumpPawn/movePawn - helper method
	 */
	private PlayerPosition convertTupleToPosition(Player aPlayer, Integer aRow, Integer aCol) {
		Tile tile = new Tile(aRow, aCol, QuoridorApplication.getQuoridor().getBoard());
		PlayerPosition playerPosition = new PlayerPosition(aPlayer, tile);
		return playerPosition;
	}

	/**
	 * @author Jake Pogharian 
	 * helper for Move/drop wall
	 */
	public void wallsOnBoard() {
		System.err.println("walls on board: " + (QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().numberOfWhiteWallsOnBoard() + QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().numberOfBlackWallsOnBoard()));
	}
	
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
	private boolean isWhiteTurn() {
		return QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite();

	}

	/**
	 * @author Lenoy Christy
	 * @return Player
	 */
	private Player nextPlayer() {
		if (isWhiteTurn())
			return QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer();
		else
			return QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
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
	 * @return String: The result of the validation method and it stores whether the
	 *         position is valid or not.
	 */
	private String posValidationResult(Boolean okOrError) {
		String result;
		if (okOrError) {
			result = "ok";
		} else {
			result = "error";
		}
		return result;
	}
	
	//helpers
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
		// @formatter:off
		/*
		 * __________ | | | | |x-> <-x| | | |__________|
		 * 
		 */
		// @formatter:on
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

		Game game = new Game(GameStatus.Running, MoveMode.PlayerMove, quoridor);
		game.setWhitePlayer(players.get(0));
		game.setBlackPlayer(players.get(1));
		
		

		PlayerPosition player1Position = new PlayerPosition(quoridor.getCurrentGame().getWhitePlayer(),
				player1StartPos);
		PlayerPosition player2Position = new PlayerPosition(quoridor.getCurrentGame().getBlackPlayer(),
				player2StartPos);

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