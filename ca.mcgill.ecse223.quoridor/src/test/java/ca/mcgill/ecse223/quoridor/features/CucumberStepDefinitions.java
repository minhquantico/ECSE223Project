package ca.mcgill.ecse223.quoridor.features;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import io.cucumber.java.After;
import io.cucumber.java.en.*;

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
		// GUI-related feature -- TODO for later
	}
	
	@And("^I have a wall in my hand over the board$")
	public void iHaveAWallInMyHandOverTheBoard() throws Throwable {
		// GUI-related feature -- TODO for later
	}
	
	// ***********************************************
	// Scenario and scenario outline step definitions
	// ***********************************************

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
	
	@When("I initiate to load a saved game quoridor_test_game_{int}.dat")
	public void i_initiate_to_load_a_saved_game_quoridor_test_game__dat(Integer int1) {
	    // Write code here tha-
	    throw new cucumber.api.PendingException();
	}

	@When("The position is valid")
	public void the_position_is_valid() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("It is player's turn")
	public void it_is_player_s_turn() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("player is at {int}:{int}")
	public void player_is_at(Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("white is at {int}:{int}")
	public void white_is_at(Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("player has a vertical wall at {int}:{int}")
	public void player_has_a_vertical_wall_at(Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("white has a horizontal wall at {int}:{int}")
	public void white_has_a_horizontal_wall_at(Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("Both players have {int} in their stacks")
	public void both_players_have_in_their_stacks(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("I initiate to load a saved game quoridor_test_game_invalid_pawn.dat")
	public void i_initiate_to_load_a_saved_game_quoridor_test_game_invalid_pawn_dat() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("The position is invalid")
	public void the_position_is_invalid() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The load returns error")
	public void the_load_returns_error() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("No file save_game_test.dat exists in the filesystem")
	public void no_file_save_game_test_dat_exists_in_the_filesystem() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("The user initiates to save the game with name save_game_test.dat")
	public void the_user_initiates_to_save_the_game_with_name_save_game_test_dat() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("A file with save_game_test.dat is created in the filesystem")
	public void a_file_with_save_game_test_dat_is_created_in_the_filesystem() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("File save_game_test.dat exists in the filesystem")
	public void file_save_game_test_dat_exists_in_the_filesystem() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("The user confirms to overwrite existing file")
	public void the_user_confirms_to_overwrite_existing_file() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("File with save_game_test.dat is updated in the filesystem")
	public void file_with_save_game_test_dat_is_updated_in_the_filesystem() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("The user cancels to overwrite existing file")
	public void the_user_cancels_to_overwrite_existing_file() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("File save_game_test.dat is not changed in the filesystem")
	public void file_save_game_test_dat_is_not_changed_in_the_filesystem() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
	
	/////////////////////////////////////////////
	
	@Given("A new game is initializing")
	public void a_new_game_is_initializing() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("Next player to set user name is white")
	public void next_player_to_set_user_name_is_white() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("There is existing user Daniel")
	public void there_is_existing_user_Daniel() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("The player selects existing Daniel")
	public void the_player_selects_existing_Daniel() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The name of player white in the new game shall be Daniel")
	public void the_name_of_player_white_in_the_new_game_shall_be_Daniel() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("Next player to set user name is black")
	public void next_player_to_set_user_name_is_black() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("There is existing user Marton")
	public void there_is_existing_user_Marton() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("The player selects existing Marton")
	public void the_player_selects_existing_Marton() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The name of player black in the new game shall be Marton")
	public void the_name_of_player_black_in_the_new_game_shall_be_Marton() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("There is no existing user Rijul")
	public void there_is_no_existing_user_Rijul() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("The player provides new user name: Rijul")
	public void the_player_provides_new_user_name_Rijul() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The name of player white in the new game shall be Rijul")
	public void the_name_of_player_white_in_the_new_game_shall_be_Rijul() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("There is no existing user Hyacinth")
	public void there_is_no_existing_user_Hyacinth() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("The player provides new user name: Hyacinth")
	public void the_player_provides_new_user_name_Hyacinth() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The name of player black in the new game shall be Hyacinth")
	public void the_name_of_player_black_in_the_new_game_shall_be_Hyacinth() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("The player provides new user name: Daniel")
	public void the_player_provides_new_user_name_Daniel() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The player shall be warned that Daniel already exists")
	public void the_player_shall_be_warned_that_Daniel_already_exists() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("Next player to set user name is white")
	public void next_player_to_set_user_name_is_white() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("The player provides new user name: Marton")
	public void the_player_provides_new_user_name_Marton() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The player shall be warned that Marton already exists")
	public void the_player_shall_be_warned_that_Marton_already_exists() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@And("Next player to set user name is black")
	public void next_player_to_set_user_name_is_black() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
	
	//feature 2
	
	@When("A new game is initializing")
	public void a_new_game_is_initializing() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("White player chooses a username")
	public void white_player_chooses_a_username() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("Black player chooses a username")
	public void black_player_chooses_a_username() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("Total thinking time is set")
	public void total_thinking_time_is_set() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The game is ready to start")
	public void the_game_is_ready_to_start() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("The game is ready to start")
	public void the_game_is_ready_to_start() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("I start the clock")
	public void i_start_the_clock() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The board is initialized")
	public void the_board_is_initialized() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
	
	///////////////////////////////////////////// Gohar 
	
	    //Rotate Wall
		//Rotate Wall
		//Rotate Wall
		//Rotate Wall
		@Then("I have a wall in my hand over the board")
		public void i_have_a_wall_in_my_hand_over_the_board() {
		    // Write code here that turns the phrase above into concrete actions
			throw new cucumber.api.PendingException();
		}

		@Given("A wall move candidate exists with vertical at position \\({int}, {int})")
		public void a_wall_move_candidate_exists_with_vertical_at_position(Integer int1, Integer int2) {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Given("A wall move candidate exists with horizontal at position \\({int}, {int})")
		public void a_wall_move_candidate_exists_with_horizontal_at_position(Integer int1, Integer int2) {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@When("I try to flip the wall")
		public void i_try_to_flip_the_wall() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("The wall shall be rotated over the board to vertical")
		public void the_wall_shall_be_rotated_over_the_board_to_vertical() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("The wall shall be rotated over the board to horizontal")
		public void the_wall_shall_be_rotated_over_the_board_to_horizontal() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("A wall move candidate shall exist with vertical at position \\({int}, {int})")
		public void a_wall_move_candidate_shall_exist_with_vertical_at_position(Integer int1, Integer int2) {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("A wall move candidate shall exist with horizontal at position \\({int}, {int})")
		public void a_wall_move_candidate_shall_exist_with_horizontal_at_position(Integer int1, Integer int2) {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}
		//Validate Position
		//Validate Position
		//Validate Position
		//Validate Position
		@Given("A game position is supplied with pawn coordinate {int}:{int}")
		public void a_game_position_is_supplied_with_pawn_coordinate(Integer int1, Integer int2) {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@When("Validation of the position is initiated")
		public void validation_of_the_position_is_initiated() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("The position is ok")
		public void the_position_is_ok() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("The position is error")
		public void the_position_is_error() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Given("A game position is supplied with wall coordinate {int}:{int}-horizontal")
		public void a_game_position_is_supplied_with_wall_coordinate_horizontal(Integer int1, Integer int2) {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Given("A game position is supplied with wall coordinate {int}:{int}-vertical")
		public void a_game_position_is_supplied_with_wall_coordinate_vertical(Integer int1, Integer int2) {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("The position is valid")
		public void the_position_is_valid() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("The position is invalid")
		public void the_position_is_invalid() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}
		
		//...................
		//...................
		// Initialize Board Feature - LENOY
		// ..................
		//...................
		
		@When("The initialization of the board is initiated")
		public void the_initialization_of_the_board_is_initiated() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("It is white player to move")
		public void it_is_white_player_to_move() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("White's pawn is in its initial position")
		public void white_s_pawn_is_in_its_initial_position() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("Black's pawn is in its initial position")
		public void black_s_pawn_is_in_its_initial_position() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("All of White's walls are in stock")
		public void all_of_White_s_walls_are_in_stock() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("All of Black's walls are in stock")
		public void all_of_Black_s_walls_are_in_stock() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("White's clock is counting down")
		public void white_s_clock_is_counting_down() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("It is shown that this is White's turn")
		public void it_is_shown_that_this_is_White_s_turn() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}
		
		//.......................
		//.......................
		// Switch Current Player - LENOY
		//.......................
		//.......................
		
		@Given("The player to move is white")
		public void the_player_to_move_is_white() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Given("The clock of white is running")
		public void the_clock_of_white_is_running() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Given("The clock of black is stopped")
		public void the_clock_of_black_is_stopped() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@When("Player white completes his move")
		public void player_white_completes_his_move() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("The clock of white is stopped")
		public void the_clock_of_white_is_stopped() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("The clock of black is running")
		public void the_clock_of_black_is_running() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("The player to move is black")
		public void the_player_to_move_is_black() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("The user interface is showing it is black's turn")
		public void the_user_interface_is_showing_it_is_black_s_turn() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Given("The player to move is black")
		public void the_player_to_move_is_black1() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Given("The clock of black is running")
		public void the_clock_of_black_is_running1() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Given("The clock of white is stopped")
		public void the_clock_of_white_is_stopped1() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@When("Player black completes his move")
		public void player_black_completes_his_move() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("The clock of black is stopped")
		public void the_clock_of_black_is_stopped1() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("The clock of white is running")
		public void the_clock_of_white_is_running1() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("The player to move is white")
		public void the_player_to_move_is_white1() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}

		@Then("The user interface is showing it is white's turn")
		public void the_user_interface_is_showing_it_is_white_s_turn() {
		    // Write code here that turns the phrase above into concrete actions
		    throw new cucumber.api.PendingException();
		}
}
