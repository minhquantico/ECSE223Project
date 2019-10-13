package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.Controller;
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
import io.cucumber.java.After;
import io.cucumber.java.en.*;

public class CucumberStepDefinitions {

	// might need this, see below
	// Wall wall;
	
	
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
	    throw new cucumber.api.PendingException();
	}
	
	@And("^I have a wall in my hand over the board$")
	public static void iHaveAWallInMyHandOverTheBoard() throws Throwable {
		// GUI-related feature -- TODO for later
	    throw new cucumber.api.PendingException();
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
	
	
	

//GrabWall	
	
	//1st scenario
	
		@Given("I have more walls on stock")
		public void i_have_more_walls_on_stock() {
		    
		}
		
		@When("I try to grab a wall from my stock")
		public void i_try_to_grab_a_wall_from_my_stock() {
			Player currentPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
		    Controller.grabWallFromStock(currentPlayer);
		    
		    // might need to do the following based on how grabWallFromStock is implemented:
		    // wall = Controller.grabWallFromStock(currentPlayer, aWall);
		}
		
		@Then("A wall move candidate shall be created at initial position")
		public void a_wall_move_candidate_shall_be_created_at_initial_position() {
			//if the wall that was taken out was the first wall,
			// then the following assertion is sufficient
			assertEquals(0, QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallPlaced().getId());
			
			//again, the alternative to the above would be the following:
			// assertEquals(wall.getId(), QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallPlaced().getId());
			
			//FOR ME: not sure what initial position is, and what attribute of wallMove should be equal to it
			assertEquals(initialPosition, QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile() )
		}
		
		@And("I shall have a wall in my hand over the board")
		public void i_shall_have_a_wall_in_my_hand_over_the_board() {
			// GUI-related feature -- TODO for later
			throw new cucumber.api.PendingException();
		}
		
		@And("The wall in my hand shall disappear from my stock")
		public void the_wall_in_my_hand_shall_disappear_from_my_stock() {
		
			assertEquals(9, QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().numberOfWhiteWallsInStock());
			//or, if we have the wall that was grabbed:
			//assertFalse(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().contains(wall));
		}
		

		
	//2nd scenario
		
		@Given("I have no more walls on stock")
		public void i_have_no_more_walls_on_stock() {
			Player currentPlayer =  QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
		    List<Wall> whiteWallsInStock = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock();
		    whiteWallsInStock = new ArrayList<Wall>();
		}
		
		@Then("I shall be notified that I have no more walls")
		public void i_shall_be_notified_that_I_have_no_more_walls() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}
		
		@Then("I shall have no walls in my hand")
		public void i_shall_have_no_walls_in_my_hand() {
			// GUI-related feature -- TODO for later
		    throw new cucumber.api.PendingException();
		}
	

//MoveWall
		
		public class MoveWall
		{
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

			
			@Given("The wall candidate is not at the {string} edge of the board")
			public void the_wall_candidate_is_not_at_the_edge_of_the_board(String side) {
				// GUI-related feature -- TODO for later
			    throw new cucumber.api.PendingException();
			}

			@When("I try to move the wall {string}")
			public void i_try_to_move_the_wall(String direction) {
			    Controller.moveWall(direction);
			    // it might also be:
			    //Controller.moveWall(direction, wall)
			    //see controller
			}

			@Then("The wall shall be moved over the board to position \\({int}, {int})")
			public void the_wall_shall_be_moved_over_the_board_to_position(Integer int1, Integer int2) {
			    // Write code here that turns the phrase above into concrete actions
			    throw new cucumber.api.PendingException();
			}

			@Then("A wall move candidate shall exist with {string} at position \\({int}, {int})")
			public void a_wall_move_candidate_shall_exist_with_at_position(String string, Integer int1, Integer int2) {
			    // Write code here that turns the phrase above into concrete actions
			    throw new cucumber.api.PendingException();
			}

			
			
			@Given("The wall candidate is at the {string} edge of the board")
			public void the_wall_candidate_is_at_the_edge_of_the_board(String string) {
			    // Write code here that turns the phrase above into concrete actions
			    throw new cucumber.api.PendingException();
			}

			@Then("I shall be notified that my move is illegal")
			public void i_shall_be_notified_that_my_move_is_illegal() {
			    // Write code here that turns the phrase above into concrete actions
			    throw new cucumber.api.PendingException();
			}
		}
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * TODO Insert your missing step definitions here
	 * 
	 * Call the methods of the controller that will manipulate the model once they
	 * are implemented
	 * 
	 */

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
