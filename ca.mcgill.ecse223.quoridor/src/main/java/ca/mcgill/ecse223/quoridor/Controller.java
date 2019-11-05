
package ca.mcgill.ecse223.quoridor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import fxml.Board.Cell;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import fxml.PlayScreenController;
import javafx.scene.input.MouseEvent;
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

public class Controller {

//-----------------------------------------------------------------------------------------------------------

	/**
	 * @author David Budaghyan Feature: GrabWall step:("I try to grab a wall from my
	 *         stock")
	 * @param aPlayer
	 */
	// alternative: public static void grabWallFromStock(Player aPlayer, Wall aWall)
	public static void grabWallFromStock(Player aPlayer) {
		setWallMoveCandidate(1, 1, Direction.Horizontal);
	}

	/**
	 * @author David Budaghyan Feature: MoveWall
	 * 
	 */
	public static void cancelCandidate() {
		System.err.println("mother fucker");
//		 System.out.println("Candidate wall: " +
//		 QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallPlaced());
//		 System.out.println("Candidate wall: " +
//		 QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallPlaced());

		WallMove candidate = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		if (QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite())
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().addWhiteWallsInStock(candidate.getWallPlaced());
		else
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().addBlackWallsInStock(candidate.getWallPlaced());

		candidate.delete();
		QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(null);
		//System.err.println(QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate());
	}

	/**
	 * @author David Budaghyan
	 * @return boolean (true or false) depending on whether the current player has
	 *         walls in stock
	 */

	public static boolean checkCurrentPlayerStock() {
		if (QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite())
			return QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().hasWhiteWallsInStock();
		else
			return QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().hasBlackWallsInStock();
	}

	/**
	 * @author David Budaghyan Feature: MoveWall step:("I try to move the wall
	 *         {string}")
	 * @param direction
	 */
	// might need a wall parameter - depends on later decisions
	// or maybe some type of pointer to the users mouse
	// note that in that case we would return the wall aswell
	public static void moveWall(String direction) {
		throw new java.lang.UnsupportedOperationException();
	}

//---------------------------------------------------------------------------------------------------------------------------

	/**
	 * @author Lenoy Christy Feature: Initialize board step: ("The initialization of
	 *         the board is initiated") initializes the quoridor game and Board
	 */
	public static void initQuoridorAndBoard() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();

		if (!quoridor.hasBoard()) {
			Board board = new Board(quoridor);
			// Creating tiles by rows, i.e., the column index changes with every tile
			// creation
			for (int i = 1; i <= 9; i++) { // rows
				for (int j = 1; j <= 9; j++) { // columns
					board.addTile(i, j);
				}
			}
		}
	}

	/**
	 * @author Lenoy Christy Feature: SwitchCurrentPlayer step: ("Player {string}
	 *         completes his move") end the Move of the current player and get the
	 *         next player to move
	 */
	public static void endMoveGUI()
	{
		if (QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite())
		{
			PlayScreenController.instance.pane.getChildren().remove(PlayScreenController.instance.BlackPlayerImage);
			PlayScreenController.instance.pane.getChildren().add(PlayScreenController.instance.WhitePlayerImage);
		}
		else
		{
			PlayScreenController.instance.pane.getChildren().remove(PlayScreenController.instance.WhitePlayerImage);
			PlayScreenController.instance.pane.getChildren().add(PlayScreenController.instance.BlackPlayerImage);
		}
	}
	
	public static void endMove()
	{
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setPlayerToMove(
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().getNextPlayer());
	}

//--------------------------------------------------------------------------------------------------------------------------

	/**
	 * @author Traian Coza Feature: LoadPosition step:("I initiate to load a saved
	 *         game {string}") Load game in QuoridorApplication from provided file.
	 */
	public static void loadGame(File file) throws FileNotFoundException, InvalidPositionException {

		try (Scanner input = new Scanner(file)) {
			int no = 0;
			while (input.hasNext())
			{
				if (no % 2 == 0)
					input.next((no / 2 + 1) + "\\.");
				String token = input.next("[a-i][1-9][hv]?");
				int col = token.charAt(0) - 'a';
				int row = token.charAt(1) - '1';
				char or = token.length() == 2 ? '-' : token.charAt(2);

				if (or == '-') // Step move
					if (initPosValidation(getTile(col, row)))
						doPawnMove(col, row, false);
					else
						throw new InvalidPositionException("Invalid pawn move: " + token);
				else // Wall move
				{
					setWallMoveCandidate(col, row, or == 'h' ? Direction.Horizontal : Direction.Vertical);
					if (initPosValidation())
						doWallMove(false);
					else
						throw new InvalidPositionException("InvalidWallMove: " + token);
				}
				
				no++;
			}

			//PlayScreenController.instance.board.loadFromModel();
		} catch (InputMismatchException ex) {
			clearGame();
			throw new InvalidPositionException(ex.getMessage());
		}
	}

	/**
	 * @author Traian Coza
	 * @param pos
	 * @return Game Position which is the cloned game position of the position
	 *         passed into the method
	 */
	private static GamePosition cloneGamePosition(GamePosition pos) {
		GamePosition cloned = new GamePosition(pos.getId() + 1,
				new PlayerPosition(pos.getWhitePosition().getPlayer(), pos.getWhitePosition().getTile()),
				new PlayerPosition(pos.getBlackPosition().getPlayer(), pos.getBlackPosition().getTile()),
				pos.getPlayerToMove(), pos.getGame());

		for (Wall w : pos.getWhiteWallsInStock())
			cloned.addWhiteWallsInStock(w);
		for (Wall w : pos.getBlackWallsInStock())
			cloned.addBlackWallsInStock(w);
		for (Wall w : pos.getWhiteWallsOnBoard())
			cloned.addWhiteWallsOnBoard(w);
		for (Wall w : pos.getBlackWallsOnBoard())
			cloned.addBlackWallsOnBoard(w);

		return cloned;
	}

	/**
	 * @author Traian Coza Clears the game
	 */
	private static void clearGame() {

		QuoridorApplication.getQuoridor().getCurrentGame()
				.setCurrentPosition(QuoridorApplication.getQuoridor().getCurrentGame().getPosition(0));
		for (GamePosition pos : QuoridorApplication.getQuoridor().getCurrentGame().getPositions())
			QuoridorApplication.getQuoridor().getCurrentGame().removePosition(pos);
		QuoridorApplication.getQuoridor().getCurrentGame()
				.addPosition(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition());
		for (Move m : QuoridorApplication.getQuoridor().getCurrentGame().getMoves())
			QuoridorApplication.getQuoridor().getCurrentGame().removeMove(m);
	}

	/**
	 * @author Traian Coza Feature: LoadPosition step: ("The position to load is
	 *         valid") Check if current GamePosition is valid.
	 * @return true of false
	 */
	public static boolean positionIsValid() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @author Traian Coza Feature: SavePosition step: ("The user initiates to save
	 *         the game with name {string}") Save current game to provided file.
	 * @param file
	 */
	public static boolean saveGame(File file, boolean overwrite) throws FileNotFoundException {
		if (!overwrite && file.exists())
			return false;
		try (PrintStream output = new PrintStream(file)) {
			int no = 0;
			for (Move m : QuoridorApplication.getQuoridor().getCurrentGame().getMoves()) {
				if (no % 2 == 0)
					output.print(no / 2 + 1 + ".");
				output.print(' ');
				output.print('a' + m.getTargetTile().getColumn() - 1);
				output.print('1' + m.getTargetTile().getRow() - 1);
				if (m instanceof WallMove)
					output.print(((WallMove) m).getWallDirection() == Direction.Horizontal ? 'h' : 'v');
				if (no % 2 == 1)
					output.println();
			}
		}
		
		return true;
	}

	/**
	 * @author Traian Coza Feature: SavePosition step: ("The user confirms to
	 *         overwrite existing file") and ("The user cancels to overwrite
	 *         existing file") Set the overwrite option (if saveGame is called on an
	 *         existing file and overwrite is set, the file will be overwritten.
	 * @param overwrite
	 */
	public static void setOverwrite(boolean overwrite) {
		throw new java.lang.UnsupportedOperationException();
	}

//--------------------------------------------------------------------------------------------------------------------------

	/**
	 * @author Gohar Saqib Fazal
	 * @param x
	 * @param y
	 * @return Tile object at the specified coordinates
	 */
	public static Tile getTile(int x, int y) {
		return QuoridorApplication.getQuoridor().getBoard().getTile((x - 1) + (y - 1) * 9);
	}

	/**
	 * @author Gohar Saqib Fazal
	 * @param x
	 * @param y
	 * @param d
	 * @return true or false depending on whether the wall at given coordinates were
	 *         set
	 */
	public static boolean isWallSet(int x, int y, Direction d) {
		List<Wall> wallsOnBoard = new ArrayList<>(
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsOnBoard());
		wallsOnBoard
				.addAll(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsOnBoard());
		//System.err.println("Walls on board: " + wallsOnBoard.size());

		for (Wall wall : wallsOnBoard)
			if (wall.getMove().getTargetTile() == getTile(x, y))
				if (wall.getMove().getWallDirection() == d)
					return true;

		return false;
	}

	/**
	 * @author Gohar Saqib Fazal
	 * @param tile
	 * @param d
	 * @return the possible moves (tiles) that the player can move to
	 */
	public static Tile direction(Tile tile, int d) {
		switch ((d + 4) % 4) {
		case 0:
			return tile.getRow() != 1 ? getTile(tile.getColumn(), tile.getRow() - 1) : null;
		case 1:
			return tile.getColumn() != 9 ? getTile(tile.getColumn() + 1, tile.getRow()) : null;
		case 2:
			return tile.getRow() != 9 ? getTile(tile.getColumn(), tile.getRow() + 1) : null;
		case 3:
			return tile.getColumn() != 1 ? getTile(tile.getColumn() - 1, tile.getRow()) : null;
		default:
			return null;
		}
	}

	/**
	 * @author Gohar Saqib Fazal
	 * @param tile
	 * @param d
	 * @return true or false depending on whether the user's intended tile and
	 *         direction are blocked
	 */

	public static boolean isBlockedDirection(Tile tile, int d) {
		switch ((d + 4) % 4) {
		case 0:
			return tile.getRow() == 1
					|| tile.getColumn() != 1 && isWallSet(tile.getColumn() - 1, tile.getRow() - 1, Direction.Horizontal)
					|| tile.getColumn() != 9 && isWallSet(tile.getColumn(), tile.getRow() - 1, Direction.Horizontal);
		case 1:
			return tile.getColumn() == 9
					|| tile.getRow() != 1 && isWallSet(tile.getColumn(), tile.getRow() - 1, Direction.Vertical)
					|| tile.getRow() != 9 && isWallSet(tile.getColumn(), tile.getRow(), Direction.Vertical);
		case 2:
			return tile.getRow() == 9
					|| tile.getColumn() != 1 && isWallSet(tile.getColumn() - 1, tile.getRow(), Direction.Horizontal)
					|| tile.getColumn() != 9 && isWallSet(tile.getColumn(), tile.getRow(), Direction.Horizontal);
		case 3:
			return tile.getColumn() == 1
					|| tile.getRow() != 1 && isWallSet(tile.getColumn() - 1, tile.getRow() - 1, Direction.Vertical)
					|| tile.getRow() != 9 && isWallSet(tile.getColumn() - 1, tile.getRow(), Direction.Vertical);
		default:
			return false;
		}
	}

	/**
	 * @author Gohar Saqib Fazal
	 * @param tile
	 * @return true or false depending on whether or not there is a player on the
	 *         given tile
	 */
	public static boolean hasPlayer(Tile tile) {
		return (QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition()
				.getTile() == tile
				|| QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition()
						.getTile() == tile);

	}

	/**
	 * @author Gohar Saqib Fazal
	 * @return a set of Tiles that the player can move to
	 */
	public static Set<Tile> getPossibleStepMoves(Player player) {
		Tile tile = player.hasGameAsWhite() ?
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile() :
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile();
		
//		System.out.println(tile);
//		System.out.println("Up:" + direction(tile, 0));
//		System.out.println("Right: " + direction(tile, 1));
//		System.out.println("Down: " + direction(tile, 2));
//		System.out.println("Left: " + direction(tile, 3));

		Set<Tile> moves = new HashSet<>();
		for (int d = 0; d < 4; d++) {
			if (isBlockedDirection(tile, d))
				continue;
			if (!hasPlayer(direction(tile, d)))
				moves.add(direction(tile, d));
			else if (!isBlockedDirection(direction(tile, d), d) && !hasPlayer(direction(direction(tile, d), d)))
				moves.add(direction(direction(tile, d), d));
			else {
				if (!isBlockedDirection(direction(tile, d), d - 1) && !hasPlayer(direction(direction(tile, d), d - 1)))
					moves.add(direction(direction(tile, d), d - 1));
				if (!isBlockedDirection(direction(tile, d), d + 1) && !hasPlayer(direction(direction(tile, d), d + 1)))
					moves.add(direction(direction(tile, d), d + 1));
			}
		}
		
//		System.out.println("This: " + tile.getColumn() + "," + tile.getRow());
//		for (Tile t : moves)
//			System.out.println("Move: " + ((t == null) ? "null" : (t.getColumn() + "," + t.getRow())));
		
		return moves;
	}
	
	public static boolean isWinner(Player player, Tile tile)
	{
		return (player.getDestination().getDirection().equals(Direction.Horizontal) ?
				tile.getColumn() :
				tile.getRow()) == player.getDestination().getTargetNumber();
	}
	
	public static int getShortestPathLength(Player player)
	{
		GamePosition current = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		Tile playerPosition = (player.hasGameAsWhite() ? current.getWhitePosition() : current.getBlackPosition()).getTile();
		
		if (isWinner(player, playerPosition))
			return 0;
		
		class Dijkstra { int distance = -1; boolean visited = false; }
		final HashMap<Tile, Dijkstra> tilesMap = new HashMap<>();
		
		for (Tile tile : QuoridorApplication.getQuoridor().getBoard().getTiles())
			tilesMap.put(tile, new Dijkstra());
		
		Set<Tile> toVisit = getPossibleStepMoves(player);
		toVisit.forEach(t -> tilesMap.get(t).distance = 1);
		tilesMap.get(playerPosition).distance = 0;
		tilesMap.get(playerPosition).visited = true;
		
		while (!toVisit.isEmpty())
		{
			Tile min = toVisit.iterator().next();
			for (Tile t : toVisit)
				if (tilesMap.get(t).distance < tilesMap.get(min).distance)
					min = t;
			toVisit.remove(min);
			tilesMap.get(min).visited = true;
			
			if (isWinner(player, min))
				return tilesMap.get(min).distance;
			
			for (int i = 0; i < 4; i++)
				if (!isBlockedDirection(min, i) && !tilesMap.get(direction(min, i)).visited)
					if (tilesMap.get(direction(min, i)).distance == -1 || tilesMap.get(min).distance + 1 < tilesMap.get(direction(min, i)).distance)
					{
						tilesMap.get(direction(min, i)).distance = tilesMap.get(min).distance + 1;
						toVisit.add(direction(min, i));
					}
		}
		return -1;
	}
	
	/**
	 * @author Gohar Saqib Fazal This controller method valdidates the postion of
	 *         the pawn in the game Used in @When("Validation of the position is
	 *         initiated") statement in Validate Position Feature Used in @Then("The
	 *         position shall be {string}") statement in Validate Position Feature
	 * @param aTargetTile: The position at which the pawn is going to be moved in
	 *                     the current move.
	 * @return Boolean: This tells us whether the pawn position is valid or not
	 */
	public static Boolean initPosValidation(Tile aTargetTile) {
		// System.out.println("x: " + aTargetTile.getColumn() + ", y: " +
		// aTargetTile.getRow());
		for (Tile aTarget : getPossibleStepMoves(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()))
			if (aTarget == aTargetTile)
				return true;
		
		return false;

	}
	
	/**
	 * @author Gohar Saqib Fazal This controller method validates the position of
	 *         the wall in the game Used in @When("Validation of the position is
	 *         initiated") statement in Validate Position Feature Used in @Then("The
	 *         position shall be valid") statement in Validate Position Feature Used
	 *         in @Then("The position shall be invalid") statement in Validate
	 *         Position Feature
	 * @param aTargetTile: The position at which the wall is going to be placed in
	 *                     the current move.
	 * @param dir:         The direction of the wall that is going to be placed in
	 *                     the current move
	 * @return Boolean: This tells us whether the pawn position is valid or not
	 */
	public static Boolean initPosValidation()
	{
		Tile aTargetTile = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile();
		Direction dir = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallDirection();
		
		if (isWallSet(aTargetTile.getColumn(), aTargetTile.getRow(), dir))
			return false;

		if (QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite() ?
				!QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().hasWhiteWallsInStock() :
				!QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().hasBlackWallsInStock())
			return false;

		if (dir == Direction.Vertical) {
			if (aTargetTile.getRow() > 1
					&& isWallSet(aTargetTile.getColumn(), aTargetTile.getRow() - 1, Direction.Vertical))
				return false;
			if (aTargetTile.getRow() < 9 - 2
					&& isWallSet(aTargetTile.getColumn(), aTargetTile.getRow() + 1, Direction.Vertical))
				return false;
			if (isWallSet(aTargetTile.getColumn(), aTargetTile.getRow(), Direction.Horizontal))
				return false;
		} else {
			if (aTargetTile.getColumn() > 1
					&& isWallSet(aTargetTile.getColumn() - 1, aTargetTile.getRow(), Direction.Horizontal))
				return false;
			if (aTargetTile.getColumn() < 9 - 2
					&& isWallSet(aTargetTile.getColumn() + 1, aTargetTile.getRow(), Direction.Horizontal))
				return false;
			if (isWallSet(aTargetTile.getColumn(), aTargetTile.getRow(), Direction.Vertical))
				return false;
		}
		
		// Temporarily set wall
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().addWhiteWallsOnBoard(
				QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallPlaced());
		
		boolean blocked = true;
		int path;
		if ((path = getShortestPathLength(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer())) == -1)
			blocked = false;
		
		if ((path = getShortestPathLength(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer())) == -1)
			blocked = false;
		
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().removeWhiteWallsOnBoard(
				QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallPlaced());
		
		return blocked;

	}

	/**
	 * @author Gohar Saqib Fazal This controller method flips the wall that is in
	 *         the user's hand For example, walls placed vertically will be flipped
	 *         horizontally and vice versa. Used in @When("I try to flip the wall")
	 *         statement in the Rotate Wall feature
	 * @param wallMove: Wall Move object that contains information such as which
	 *                  wall is being flipped and the direction of set wall
	 */
	public static void flipWall(Rectangle wall) {
		switch ((int) wall.getRotate()) {
		case 0:
			wall.setRotate(90);
			QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate()
					.setWallDirection(Direction.Vertical);
			break;
		case 90:
			wall.setRotate(0);
			QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate()
					.setWallDirection(Direction.Horizontal);
			break;
		}
	}

//--------------------------------------------------------------------------------------------------------------------------

	/**
	 * @author Minh Quan Hoang Feature: ProvideSelectUserName Step: @When("The
	 *         player selects existing {string}")
	 * @param string: Username selected by player.
	 * @param player: player to set user. This method selects an existing username
	 *                from the list of users already created. It takes an input
	 *                string that represents the username and searches the list of
	 *                users to find it. If there is a match, the user with that
	 *                username is linked to the player. If there is no match, the
	 *                method does not link the user with the player and notifies the
	 *                player that there exists no user with that username.
	 **/

	public static void SelectExistingUsername(Player player, String username) {
		
		if (player.hasGameAsWhite()) {
			Controller.setWhitePlayerUsername(username);
		} else {
			Controller.setBlackPlayerUsername(username);
		}

	}

	/**
	 * @author Minh Quan Hoang Feature: ProvideSelectUserName Step: @When("The
	 *         player provides new user name: {string}")
	 * @param string: Username to be created. This method creates a new username by
	 *                creating a new user and adding it to the list of users
	 **/
	public static void CreateNewUsername(String username) {
		QuoridorApplication.getQuoridor().addUser(username);
	}

	/**
	 * @author Minh Quan Hoang Feature: StartNewGame Step: @When("A new game is
	 *         being initialized") Initializes a new game and changes the status of
	 *         the game to initializing
	 **/
	public static void InitializeNewGame() {
		ArrayList<Player> createUsersAndPlayers = createUsersAndPlayers("user1", "user2"); // helper method in
																							// controller
		createAndInitializeGame(createUsersAndPlayers); // helper method in controller
	}

	/**
	 * @author Minh Quan Hoang Feature: StartNewGame Step: @When("I start the
	 *         clock") Starts the clock
	 **/
	public static void StartClock(long seconds) {
		PlayScreenController.instance.board.players[0].startClock(seconds);
		PlayScreenController.instance.board.players[1].startClock(seconds);
		PlayScreenController.instance.board.players[0].stopClock();
		PlayScreenController.instance.board.players[1].stopClock();

	}
	

	/**
	 * @author Minh Quan Hoang Feature: StartNewGame Step: @When("Total thinking
	 *         time is set") Sets the total thinking time after the game is
	 *         initialized
	 **/
	public static void setTotalThinkingTime(int minutes, int seconds) {
		setThinkingTime(minutes, seconds); // overlaps with Jake's controller method
	}

	/**
	 * @author Minh Quan Hoang Feature: StartNewGame Step: @When("Black player
	 *         chooses a username") Selects the username for the black player
	 **/
	public static void setBlackPlayerUsername(String username) {
		/*
		 * The player selects a username from the list of users from the GUI or inputs a
		 * new one. If he inputs a new one, then this method calls the CreateNewUserName
		 * controller method to create a new user with the username. Then the method
		 * calls the SelectExistingUsername controller method and sets the username for
		 * the black player.
		 */
		QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().setUser(User.getWithName(username));

	}

	/**
	 * @author Minh Quan Hoang Feature: StartNewGame Step: @When("White player
	 *         chooses a username") Selects the username for the white player
	 **/
	public static void setWhitePlayerUsername(String username) {
		// This method does the same as the method above but for the white player
		QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().setUser(User.getWithName(username));
	}

//--------------------------------------------------------------------------------------------------------------------------

	// Jake's controller methods//

	/**
	 * @author Jake Pogharian Feature: Drop Wall step: when "I release the wall in
	 *         my hand" This method is used to get the location over which wall is
	 *         hovering from the GUI and then to return the Tile object bearing the
	 *         coordinates of that area
	 * @return Tile this returns the specific tile at that location.
	 */
	public static Tile getTile() {
		throw new java.lang.UnsupportedOperationException();
	}

	/**
	 * @author Jake Pogharian Feature: Drop Wall step: when "I release the wall in
	 *         my hand" This method is used to perform the act of dropping a wall.
	 *         It will perform the necessary changes to both the View and the Model.
	 *         For the view it will remove the wall from the hand, notify in case of
	 *         invalid attempted move, etc. For the model, it will register the wall
	 *         move and complete the move when it is in fact valid, change whose
	 *         turn it is, etc.
	 * @param Tile t: This is a parameter of type Tile and will be used by the
	 *             method to know where to perform the wall drop.
	 */
	public static void dropWall(int x, int y) {
		Tile target = getAppropriateWallMove(x, y);
		if (target != null)
		{
			Direction direction = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallDirection();
			setWallMoveCandidate(target.getColumn(), target.getRow(), direction);
		
			if (initPosValidation())
				doWallMove(true);
			else
				cancelCandidate();
		}
		else
			cancelCandidate();

	}

	public static Tile getAppropriateWallMove(int x, int y) {

		fxml.Board board = PlayScreenController.instance.board;
		Direction direction = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallDirection();

		int boardPaneX = 229;
		int boardPaneY = 110;
		int wallWidth = 106;
		int wallHeight = 29;

		int walli = 0;
		int wallj = 0;
		Double minDist;
		Double dist;

		int chosenXCoord = 0;
		int chosenYCoord = 0;

		int deltaX = 0;
		int deltaY = 0;

		if (direction == direction.Horizontal) {
			// check if drop location is close to horizontal wall location
			dist = (double) 1000;
			minDist = (double) 1100;
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {

					int xcoord = (int) (boardPaneX + board.hWall[i][j].getLayoutX() + wallWidth / 2);
					int ycoord = (int) (boardPaneY + board.hWall[i][j].getLayoutY() + wallHeight / 2);

					deltaX = xcoord - x;
					deltaY = ycoord - y;

					// calculate distance from mmouse click to wall position
					dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

					if (Double.compare(dist, minDist) < 0) {
						minDist = dist;
						walli = i;
						wallj = j;

						// xCoord of shortest distance wall

						chosenXCoord = xcoord;
						chosenYCoord = ycoord;
					}

					if (25 > (chosenXCoord - x) && -25 < (chosenXCoord - x) && (chosenYCoord - y) < 25
							&& (chosenYCoord - y) > -25) {

						// model element

						return getTile(i + 1, j + 1);

					}
				}
			}
		} else {
			// check if drop location is close to horizontal wall location
			dist = (double) 1000;
			minDist = (double) 1100;
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {

					int xcoord = (int) (boardPaneX + board.vWall[i][j].getLayoutX() + wallHeight / 2);
					int ycoord = (int) (boardPaneY + board.vWall[i][j].getLayoutY() + wallWidth / 2);

					deltaX = xcoord - x;
					deltaY = ycoord - y;

					// calculate distance from mmouse click to wall position
					dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

					if (Double.compare(dist, minDist) < 0) {
						minDist = dist;
						walli = i;
						wallj = j;

						// xCoord of shortest distance wall

						chosenXCoord = xcoord;
						chosenYCoord = ycoord;
					}

					if (25 > (chosenXCoord - x) && -25 < (chosenXCoord - x) && (chosenYCoord - y) < 25
							&& (chosenYCoord - y) > -25) {

						return getTile(i + 1, j + 1);

					}
				}
			}
		}
		return null;
	}

	public static boolean setWallMoveCandidate(int i, int j, Direction d) {
		if (i < 1 || i > 9-1 || j < 1 || j > 9-1)
			return false;
		
		WallMove wallMoveCandidate = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		Tile aTargetTile = getTile(i,j);
		
		if (wallMoveCandidate == null)
		{
			if (!checkCurrentPlayerStock())
				return false;
			
			Player aPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove();
			int aMoveNumber = QuoridorApplication.getQuoridor().getCurrentGame().numberOfMoves() + 1;
			int aRoundNumber = (int) Math.ceil(aMoveNumber / 2);
			Game aGame = QuoridorApplication.getQuoridor().getCurrentGame();
			
			Wall aWallPlaced;
			if (aPlayer.hasGameAsWhite())
			{
				aWallPlaced = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().get(0);
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().removeWhiteWallsInStock(aWallPlaced);
			}
			else
			{
				aWallPlaced = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsInStock().get(0);
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().removeBlackWallsInStock(aWallPlaced);
			}
			
//			for (Wall w : QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock())
//				System.out.print(w == null ? "null," : "not null,");
//			System.out.println();
//			for (Wall w : QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsInStock())
//				System.out.print(w == null ? "null," : "not null,");
//			System.out.println();
			
//			System.out.println("Wall placed: " + aWallPlaced);
			wallMoveCandidate = new WallMove(aMoveNumber, aRoundNumber, aPlayer, aTargetTile, aGame, d, aWallPlaced);
		}
		else
		{
			//throw new AssertionError("Shouldn't be here!");
			wallMoveCandidate.setTargetTile(aTargetTile);
			wallMoveCandidate.setWallDirection(d);
//			System.out.println("UPDATED");
		}
		
//		System.out.println("sat wall move candidate");
//		System.out.println("Current cand: " + QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate());
		QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(wallMoveCandidate);
		
		//System.out.println("Now: " + QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate());
		return true;
	}

	public static void doWallMove(boolean notify) {
		dropWallMoveM();
		PlayScreenController.instance.board.loadFromModel();
		if (notify)
			synchronized(PlayScreenController.instance.board) { PlayScreenController.instance.board.notify(); }
	}
	
	public static void dropWallMoveM() {
		WallMove wallMoveCandidate = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		QuoridorApplication.getQuoridor().getCurrentGame().setCurrentPosition(
				cloneGamePosition(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()));
		QuoridorApplication.getQuoridor().getCurrentGame()
				.addPosition(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition());

		if (QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()
				.hasGameAsWhite()) {
//			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
//					.removeWhiteWallsInStock(wallMoveCandidate.getWallPlaced());		// Now done in setCnadidate
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
					.addWhiteWallsOnBoard(wallMoveCandidate.getWallPlaced());
		
			
		} else {
//			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
//					.removeBlackWallsInStock(wallMoveCandidate.getWallPlaced());
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
					.addBlackWallsOnBoard(wallMoveCandidate.getWallPlaced());
		}
		
		QuoridorApplication.getQuoridor().getCurrentGame().addMove(QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate());
		QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(null);
		
		endMove();
	}
	
	public static void doPawnMove(int i, int j, boolean notify)
	{
		Player aPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove();
		int aMoveNumber = QuoridorApplication.getQuoridor().getCurrentGame().numberOfMoves() + 1;
		int aRoundNumber = (int) Math.ceil(aMoveNumber / 2);
		Game aGame = QuoridorApplication.getQuoridor().getCurrentGame();
		Tile aTargetTile = getTile(i,j);
		
		StepMove move = new StepMove(aMoveNumber, aRoundNumber, aPlayer, aTargetTile, aGame);
		
		QuoridorApplication.getQuoridor().getCurrentGame().setCurrentPosition(
				cloneGamePosition(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()));
		QuoridorApplication.getQuoridor().getCurrentGame()
				.addPosition(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition());
		
		if (aPlayer.hasGameAsWhite())
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setWhitePosition(new PlayerPosition(aPlayer, aTargetTile));
		else
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setBlackPosition(new PlayerPosition(aPlayer, aTargetTile));
		
		QuoridorApplication.getQuoridor().getCurrentGame().addMove(move);
		endMove();
		

		PlayScreenController.instance.board.loadFromModel();
		
		if (notify)
			synchronized(PlayScreenController.instance.board) { PlayScreenController.instance.board.notify(); }
	}

	/**
	 * @author Jake Pogharian Feature: setThinkingTime step: When "{int}:{int} is
	 *         set as the thinking time" This method is used to set the thinking
	 *         time for each player. This will dictate how much time the player has
	 *         remaining. It is called before the start of the game in order to set
	 *         the initial remaining time for both players
	 * @param minutes: This int will be used to set the specific number of minutes
	 *                 of remaining time (thinking time) (must be less than the
	 *                 minute equivalent of 24 hours)
	 * @param seconds: This int will be used to set the specific number of seconds
	 *                 of remaining time (thinking time) for each player (must be
	 *                 less than the second equivalent of 24 hours)
	 */
	public static void setThinkingTime(int minutes, int seconds) {
		Time time = new Time(0, minutes, seconds);
		QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().setRemainingTime(time);
		QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().setRemainingTime(time);
	}

//--------------------------------------------------------------------------------------------------------------------------

	public static class InvalidPositionException extends Exception {
		public InvalidPositionException() {
		}

		public InvalidPositionException(String message) {
			super(message);
		}

	}

	public static ArrayList<Player> createUsersAndPlayers(String userName1, String userName2) {
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
		player1.setNextPlayer(player2);
		player2.setNextPlayer(player1);

		Player[] players = { player1, player2 };

		// Create all walls. Walls with lower ID belong to player1,
		// while the second half belongs to player 2
		for (int i = 0; i < 2; i++) {
			for (int j = 1; j <= 10; j++) {
				new Wall(i * 10 + j, players[i]);
			}
		}

		ArrayList<Player> playersList = new ArrayList<Player>();
		playersList.add(player1);
		playersList.add(player2);

		return playersList;
	}

	public static void createAndInitializeGame(ArrayList<Player> players) {
		
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		// There are total 36 tiles in the first four rows and
		// indexing starts from 0 -> tiles with indices 36 and 36+8=44 are the starting
		// positions
		Tile player1StartPos = quoridor.getBoard().getTile(36);
		Tile player2StartPos = quoridor.getBoard().getTile(44);

		Game game = new Game(GameStatus.Initializing, MoveMode.PlayerMove, players.get(0), players.get(1), quoridor);

		PlayerPosition player1Position = new PlayerPosition(quoridor.getCurrentGame().getWhitePlayer(),
				player1StartPos);
		PlayerPosition player2Position = new PlayerPosition(quoridor.getCurrentGame().getBlackPlayer(),
				player2StartPos);

		GamePosition gamePosition = new GamePosition(0, player1Position, player2Position, players.get(0), game);

		// Add the walls as in stock for the players
		for (int j = 1; j <= 10; j++) {
			gamePosition.addWhiteWallsInStock(Wall.getWithId(j));
			gamePosition.addBlackWallsInStock(Wall.getWithId(j+10));
		}

		game.setCurrentPosition(gamePosition);
		}
	}

