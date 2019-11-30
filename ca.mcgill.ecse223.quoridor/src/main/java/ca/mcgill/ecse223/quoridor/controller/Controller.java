
package ca.mcgill.ecse223.quoridor.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.PawnBehaviour.MoveDirection;
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
import javafx.scene.shape.Rectangle;

public class Controller {

	public static void detectDraw()
	{	
		List<Move> moves = QuoridorApplication.getQuoridor().getCurrentGame().getMoves();
		if (moves.size() == 0)
			return;
		
		Move current = moves.get(moves.size()-1);
		int i = moves.size() - 2;
		while (i > 0)
		{
			Move prev = null;
			for (; i >= 0; i--)
				if (moveEquals(moves.get(i), current))
				{
					prev = moves.get(i);
					break;
				}
			if (prev == null)
				break;
			
			int distance = moves.size()-1 - i;
			if (distance > i)
				break;
			
			boolean draw = true;
			for (int j = i - distance; i < moves.size()-1; i++, j++)
				if (!moveEquals(moves.get(i), moves.get(j)))
					draw = false;	// No draw
			i = distance - moves.size() + 1;
			if (!draw)
				continue;
			
			// Draw detected!
			QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.Draw);
			for (Move m : moves)
				System.out.println(m.getTargetTile().getColumn() + ", " + m.getTargetTile().getRow());
			
			break;
		}
	}
	
	public static boolean moveEquals(Move move1, Move move2)
	{
		boolean equals = move1.getTargetTile() == move2.getTargetTile();
		equals &= move1.getClass().equals(move2.getClass());
		if (!equals) return false;
		if (move1 instanceof WallMove)
			return ((WallMove)move1).getWallDirection().equals(((WallMove)move2).getWallDirection());
		return true;
	}
	
	public static boolean isIPAdress(String ip)
	{
		try { InetAddress.getByName(ip); return true; }
		catch (UnknownHostException ex) { return false; }
	}
	
	/**
	 * @author David Budaghyan Feature: GrabWall 
	 * step:("I try to grab a wall from my stock")
	 * @param aPlayer
	 */
	public static void grabWallFromStock(Player aPlayer) {
		setWallMoveCandidate(null, Direction.Horizontal);
	}

	/**
	 * @author David Budaghyan Feature: MoveWall
	 * 
	 */
	public static void cancelCandidate() {
		
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
	public static void updateStatusGUI()
	{
		switch (QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus())
		{
		case Initializing:
		case ReadyToStart:
		case Running:
			if (QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite())
			{
				PlayScreenController.instance.statusImage.setImage(PlayScreenController.whiteTurn);
				PlayScreenController.instance.resign.setDisable(!PlayScreenController.instance.board.players[0].isUser());
			}
			else
			{
				PlayScreenController.instance.statusImage.setImage(PlayScreenController.blackTurn);
				PlayScreenController.instance.resign.setDisable(!PlayScreenController.instance.board.players[1].isUser());
			}
			break;
		case WhiteWon:
			PlayScreenController.instance.statusImage.setImage(PlayScreenController.whiteWon);
			break;
		case BlackWon:
			PlayScreenController.instance.statusImage.setImage(PlayScreenController.blackWon);
			break;
		case Draw:
			PlayScreenController.instance.statusImage.setImage(PlayScreenController.draw);
			break;
		case Replay:
			if (!updateGameStatus())
				QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.Running);
			updateStatusGUI();
			QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.Replay);
			break;
		}
		
		PlayScreenController.instance.updateWallCount();
	}
	/**
	 * @author Lenoy Christy This will end the move of a player and make necessary changes to the model
	 */
	public static void endMove()
	{
		try
		{
			if (PlayScreenController.instance.board.isWaitingForMove())
				synchronized(PlayScreenController.instance.board) { PlayScreenController.instance.board.notify(); }
		}
		catch (NullPointerException ex) { /* Do nothing */ }
		
		updateGameStatus();
		
		GameStatus status = QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus();
		if (status != GameStatus.WhiteWon &&  status != GameStatus.BlackWon && status != GameStatus.Draw)
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setPlayerToMove(
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().getNextPlayer());
	}
	
	public static void jumpToStartPos() {
		GamePosition startPos = QuoridorApplication.getQuoridor().getCurrentGame().getPosition(0);
		QuoridorApplication.getQuoridor().getCurrentGame().setCurrentPosition(startPos);
		
	}
	
	public static void jumpToFinalPos() {
		int max = QuoridorApplication.getQuoridor().getCurrentGame().numberOfPositions()-1;
		GamePosition finalPos = QuoridorApplication.getQuoridor().getCurrentGame().getPosition(max);
		QuoridorApplication.getQuoridor().getCurrentGame().setCurrentPosition(finalPos);
		
	}
	
	public static boolean continueGame()
	{
		if (QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus() != GameStatus.Replay)
		{
			QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.Replay);
			return false;
		}
		GamePosition current = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		jumpToFinalPos();
		boolean changed = Controller.updateGameStatus();
		QuoridorApplication.getQuoridor().getCurrentGame().setCurrentPosition(current);
		if (changed)
			return false;
		QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.Running);
		int index = QuoridorApplication.getQuoridor().getCurrentGame().indexOfPosition(current);
		index++;
		while (index < QuoridorApplication.getQuoridor().getCurrentGame().numberOfPositions())
		{
			QuoridorApplication.getQuoridor().getCurrentGame().getPosition(index).delete();
			QuoridorApplication.getQuoridor().getCurrentGame().getMove(index-1).delete();
		}
		return true;
	}

//--------------------------------------------------------------------------------------------------------------------------

	/**
	 * @author Traian Coza Feature: LoadPosition step:("I initiate to load a saved
	 *         game {string}") Load game in QuoridorApplication from provided file.
	 */
	public static void loadGame(File file) throws FileNotFoundException, InvalidPositionException {
		try (Scanner input = new Scanner(file)) {
			if (QuoridorApplication.getQuoridor().hasCurrentGame())
				QuoridorApplication.getQuoridor().getCurrentGame().delete();
			Controller.InitializeNewGame();
			
			int no = 0;
			while (input.hasNext())
			{
				if (no % 2 == 0)
					input.next((no / 2 + 1) + "\\.");
				String token = input.next("[a-i][1-9][hv]?");
				if (!doMove(token))
					throw new InvalidPositionException("Invalid move: " + token);
				
				no++;
			}
			
			jumpToStartPos();
		}
		catch (InputMismatchException ex)
		{
			QuoridorApplication.getQuoridor().getCurrentGame().delete();
			throw new InvalidPositionException(ex.getMessage());
		}
	}
	
	public static boolean doMove(String move)
	{
		int col = move.charAt(0) - 'a' + 1;
		int row = move.charAt(1) - '1' + 1;
		char or = move.length() == 2 ? '-' : move.charAt(2);
		if (or == '-') // Step move
			if (isValidPawnMove(getTile(col, row)))
				doPawnMoveStateMachine(col, row);
			else
				return false;
		else // Wall move
		{
			setWallMoveCandidate(col, row, or == 'h' ? Direction.Horizontal : Direction.Vertical);
			if (isValidWallMove())
				doWallMove();
			else
				return false;
		}
		
		return true;
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
				output.print(' ' + moveToToken(m));
				if (no++ % 2 == 1)
					output.println();
			}
		}
		
		return true;
	}
	
	public static boolean savePosition(File file, boolean overwrite) throws FileNotFoundException {
		if (!overwrite && file.exists())
			return false;
		
		try (PrintStream output = new PrintStream(file)) {
			String whiteString = "W: ";
			String blackString = "B: ";

			whiteString += tileToToken(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile());
			blackString += tileToToken(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile());
			
			for (Wall w : QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsOnBoard())
				whiteString += ", " + moveToToken(w.getMove());
			for (Wall w : QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsOnBoard())
				blackString += ", " + moveToToken(w.getMove());
			
			if (QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite())
			{
				output.println(whiteString);
				output.println(blackString);
			}
			else
			{
				output.println(blackString);
				output.println(whiteString);
			}
		}
		
		return true;
	}
	
	private static String tileToToken(Tile t) { return "" + (char)('a' + t.getColumn()-1) + (char)('1' + t.getRow()-1); }
	public static String moveToToken(Move m) { return tileToToken(m.getTargetTile()) + (m instanceof WallMove ? ((WallMove)m).getWallDirection() == Direction.Horizontal ? "h" : "v" : ""); }
	
	/**
	 * @author Traian Coza Feature: LoadGamePosition. This loads the 
	 * game Position from a file inputted by the user.It reads in values line by line corresponding to respective player.
	 * @param file
	 */
	public static void loadPosition(File file) throws FileNotFoundException, InvalidPositionException {
		boolean whiteToMove = true;
		Scanner whiteScan;
		Scanner blackScan;
		try (Scanner fileScan = new Scanner(file))
		{
			whiteScan = new Scanner(fileScan.nextLine());
			blackScan = new Scanner(fileScan.nextLine());
		}
		
		if (QuoridorApplication.getQuoridor().hasCurrentGame())
			QuoridorApplication.getQuoridor().getCurrentGame().delete();
		Controller.InitializeNewGame();
		
		GamePosition curpos = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		
		try
		{
			if (whiteScan.next("[WB]:").equals("B:"))
			{
				whiteToMove = false;
				Scanner temp = whiteScan;
				whiteScan = blackScan;
				blackScan = temp;
				whiteScan.next("W:");
			}
			else
				blackScan.next("B:");

			// huwhite
			String[] tokens = whiteScan.nextLine().substring(1).split(", *");
			
			Player player = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
			char or = '\0';
			int col = tokens[0].charAt(0) - 'a' + 1;
			int row = tokens[0].charAt(1) - '1' + 1;
			
			if (col < 1 || col > 9 || row < 1 || row > 9)
				throw new InvalidPositionException();
			
			curpos.setWhitePosition(new PlayerPosition(player, getTile(col, row)));
			
			for (int i = 1; i < tokens.length; i++)
			{
				col = tokens[i].charAt(0) - 'a' + 1;
				row = tokens[i].charAt(1) - '1' + 1;
				or = tokens[i].charAt(2);
				
				Wall w = curpos.getWhiteWallsInStock(0);
				new WallMove(0, 0, player, getTile(col, row), QuoridorApplication.getQuoridor().getCurrentGame(),
						or == 'h' ? Direction.Horizontal : Direction.Vertical, w);
				QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(w.getMove());
				if (!isValidWallMove())
					throw new InvalidPositionException();
				
				curpos.addWhiteWallsOnBoard(w);
				curpos.removeWhiteWallsInStock(w);
				
				QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(null);
			}
			
			// Bleck
			tokens = blackScan.nextLine().substring(1).split(", *");
			
			player = QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer();
			or = '\0';
			col = tokens[0].charAt(0) - 'a' + 1;
			row = tokens[0].charAt(1) - '1' + 1;
			
			if (col < 1 || col > 9 || row < 1 || row > 9)
				throw new InvalidPositionException();
			
			curpos.setBlackPosition(new PlayerPosition(player, getTile(col, row)));
			if (curpos.getWhitePosition().getTile() == curpos.getBlackPosition().getTile())
				throw new InvalidPositionException();
			
			for (int i = 1; i < tokens.length; i++)
			{
				col = tokens[i].charAt(0) - 'a' + 1;
				row = tokens[i].charAt(1) - '1' + 1;
				or = tokens[i].charAt(2);
				
				Wall w = curpos.getBlackWallsInStock(0);
				new WallMove(0, 0, player, getTile(col, row), QuoridorApplication.getQuoridor().getCurrentGame(),
						or == 'h' ? Direction.Horizontal : Direction.Vertical, w);
				QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(w.getMove());
				if (!isValidWallMove())
					throw new InvalidPositionException();
				
				curpos.addBlackWallsOnBoard(w);
				curpos.removeBlackWallsInStock(w);
				
				QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(null);
			}
			
			// Set to move
			curpos.setPlayerToMove(whiteToMove ? player.getNextPlayer() : player);
			player.getPawnBehaviour().setSMTest();
			player.getNextPlayer().getPawnBehaviour().setSMTest();
		}
		catch (ArrayIndexOutOfBoundsException | InputMismatchException ex)
		{
			QuoridorApplication.getQuoridor().getCurrentGame().delete();
			throw new InvalidPositionException("!!");
		}
		
		whiteScan.close();
		blackScan.close();
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
		int index = (x - 1) + (y - 1) * 9;
		if (index < QuoridorApplication.getQuoridor().getBoard().numberOfTiles())
			return QuoridorApplication.getQuoridor().getBoard().getTile(index);
		else
			return null;
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
	
	/**
	 * @author Gohar Saqib Fazal
	 * @return boolean : identifies if player has won
	 * @param  Tile tile: is the tile at which player finds himself
	 * @param  Player for which we are validating win
	 */
	
	public static boolean isWinner(Player player, Tile tile)
	{
		return (player.getDestination().getDirection().equals(Direction.Horizontal) ?
				tile.getColumn() : tile.getRow()) == player.getDestination().getTargetNumber();
	}
	/**
	 * @author Gohar Saqib Fazal
	 * @return int : refers to the length of  the given path
	 * @param  Player: player refers to the player concerned
	 */
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
	
	public static boolean wallMoveCandidateBlocksPath(Player player)
	{
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().addWhiteWallsOnBoard(
				QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallPlaced());
		
		boolean blocked = getShortestPathLength(player) == -1;
		
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().removeWhiteWallsOnBoard(
				QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallPlaced());
		
		return blocked;
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
	public static Boolean isValidPawnMove(Tile aTargetTile) {
		//System.out.println("Target move: " + aTargetTile.getColumn() + ", " + aTargetTile.getRow());
		//System.out.println("Player to move: " + (QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite() ? "white" : "black"));
		
		for (Tile aTarget : getPossibleStepMoves(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()))
		{
			//System.out.println("Possible move: " + aTarget.getColumn() + ", " + aTarget.getRow());
			if (aTarget == aTargetTile)
				return true;
		}
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
	public static Boolean isValidWallMove()
	{
		Tile aTargetTile = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile();
		Direction dir = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallDirection();
		
		if (isWallSet(aTargetTile.getColumn(), aTargetTile.getRow(), dir))
			return false;
		
//		if (QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite() ?
//				!QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().hasWhiteWallsInStock() :
//				!QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().hasBlackWallsInStock())
//			return false;

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
		
		// Temporarily set wall TODO
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().addWhiteWallsOnBoard(
				QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallPlaced());
		
		if (wallMoveCandidateBlocksPath(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer()))
			return false;
		if (wallMoveCandidateBlocksPath(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer()))
			return false;
		
		return true;

	}

	/**
	 * @author Gohar Saqib Fazal This controller method flips the wall that is in
	 *         the user's hand For example, walls placed vertically will be flipped
	 *         horizontally and vice versa. Used in @When("I try to flip the wall")
	 *         statement in the Rotate Wall feature
	 * @param wallMove: Wall Move object that contains information such as which
	 *                  wall is being flipped and the direction of set wall
	 */
	public static void flipWall()
	{
		setWallMoveCandidate(null,
				QuoridorApplication.getQuoridor().
				getCurrentGame().getWallMoveCandidate().
				getWallDirection() == Direction.Horizontal ?
				Direction.Vertical : Direction.Horizontal);
	}
	
	public static void resign() { resign(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()); }
	public static void resign(Player player) {
		QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(player.hasGameAsWhite() ? GameStatus.BlackWon : GameStatus.WhiteWon);
		endMove();
	}
	
	public static void notRunning() {
		QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.Initializing);
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
	public static void StartClock() {
		int seconds = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getRemainingTime().getMinutes() * 60;
		seconds += QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getRemainingTime().getSeconds();
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
		QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.ReadyToStart);
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
	 *         my hand" This method is used to perform the act of dropping a wall.
	 *         It will perform the necessary changes to both the View and the Model.
	 *         For the view it will remove the wall from the hand, notify in case of
	 *         invalid attempted move, etc. For the model, it will register the wall
	 *         move and complete the move when it is in fact valid, change whose
	 *         turn it is, etc.
	 * @param int x: This is a parameter of type Tile and will be used by the
	 *             method to know where to perform the wall drop.
	 *             
	 * @param int y: this the Y coordinate of the wall to be dropped
	 */
	public static void dropWall()
	{
		if (isValidWallMove())
			doWallMove();
		else
			cancelCandidate();
	}
	
	/**
	 * @author Jake Pogharian Feature: Drop Wall step: when "I release the wall in
	 *         my hand" This method is used to perform the act of dropping a wall.
	 *         it will get the corresponding tile for the wallMove
	 * @param int x:This is the X coordinate of the desired wall move
	 *        int y: This is the Y coordinate of the desired Wall move
	 */

	
	
	public static boolean setWallMoveCandidate(int i, int j, Direction d)
	{
		if (i < 1 || i > 9-1 || j < 1 || j > 9-1)
			return false;
		return setWallMoveCandidate(getTile(i, j), d);
	}
	
	/**
	 * @author Jake Pogharian Feature: Drop Wall step: when "I release the wall in
	 *         my hand" This method is used to set the wallMove candidate.
	 * @param int i:This is the i (column) coordinate of the desired wall move
	 * @param int j: This is the j (row) coordinate of the desired Wall move
	 */
	public static boolean setWallMoveCandidate(Tile tile, Direction d)
	{
		WallMove wallMoveCandidate = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		
		if (wallMoveCandidate == null)
		{
			if (!checkCurrentPlayerStock())
				return false;
			
			Player aPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove();
			int aMoveNumber = QuoridorApplication.getQuoridor().getCurrentGame().numberOfMoves() / 2 + 1;
			int aRoundNumber = QuoridorApplication.getQuoridor().getCurrentGame().numberOfMoves() % 2 + 1;
			Game aGame = QuoridorApplication.getQuoridor().getCurrentGame();
			
			if (tile == null) tile = getTile(1, 1);
			if (d == null) d = Direction.Horizontal;
			
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
			
			wallMoveCandidate = new WallMove(aMoveNumber, aRoundNumber, aPlayer, tile, aGame, d, aWallPlaced);
		}
		else
		{
			if (tile != null)
				wallMoveCandidate.setTargetTile(tile);
			if (d != null)
				wallMoveCandidate.setWallDirection(d);
		}
		
		QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(wallMoveCandidate);
		
		return true;
	}
	
	/**
	 * @author Jake Pogharian Feature: Drop Wall step: when "I release the wall in
	 *         my hand" This method is used to execute the wall move drop. Does approprate changes
	 * @param boolean notify:This is used to either allow or not allow the GUI elements to be initiated (useful for gherkin)
	 * 
	 */
	public static void doWallMove() {
		assertCurrentPositionIsLastPosition();
		
		WallMove wallMoveCandidate = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		GamePosition prevPosition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		QuoridorApplication.getQuoridor().getCurrentGame().setCurrentPosition(
				cloneGamePosition(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()));
		QuoridorApplication.getQuoridor().getCurrentGame()
				.addPosition(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition());

		if (QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()
				.hasGameAsWhite())
		{
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
					.addWhiteWallsOnBoard(wallMoveCandidate.getWallPlaced());
			prevPosition.addWhiteWallsInStock(wallMoveCandidate.getWallPlaced());
		}
		else
		{
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
					.addBlackWallsOnBoard(wallMoveCandidate.getWallPlaced());
			prevPosition.addBlackWallsInStock(wallMoveCandidate.getWallPlaced());
		}
		
		QuoridorApplication.getQuoridor().getCurrentGame().addMove(QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate());
		QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(null);
		
		endMove();
	}
	
	public static void assertCurrentPositionIsLastPosition() throws AssertionError
	{
		GamePosition currentPosition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		int currentPositionIndex = QuoridorApplication.getQuoridor().getCurrentGame().getPositions().indexOf(currentPosition);
		int lastPostionIndex = QuoridorApplication.getQuoridor().getCurrentGame().numberOfPositions()-1;
		if (currentPositionIndex != lastPostionIndex)
			throw new AssertionError("Current position is not last position!");
	}
	
	public static void doPawnMove(int i, int j)
	{
		assertCurrentPositionIsLastPosition();
		
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
	}
	/**
	 *  This method is used to get the current player's corresponding state machine.
	 */
	public static PawnBehaviour getCurrentStateMachine()
	{
		return QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().getPawnBehaviour();
	}
	
	/**
	 *  This method is used to do a pawnMove by calling on the corresponding state machine event. It will see if it is a jumpMove,
	 *  if so, it will call the jumpMove(dir1, dir2) event, if it sees that it is a step move, 
	 *  it will call the stepMove(dir1) event. It will internally convert the tile coordinates into a direction.
	 * @param int i:This is the i (column) coordinate of the desired pawn move
	 * @param int j: This is the j (row) coordinate of the desired pawn move
	 *
	 */
	public static void doPawnMoveStateMachine(int x, int y)
	{
		PawnBehaviour sm = getCurrentStateMachine();
		Tile pos = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite() ?
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile() :
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile();
		
		int dx = x - pos.getColumn();
		int dy = y - pos.getRow();
		
//		System.out.println("White: " + QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite());
//		System.out.println("dx: " + dx + ", dy:" + dy);
		sm.calledLegal = false;

		if (Math.abs(dx + dy) == 1)	// Step move
			if (dx == 0)
				sm.stepMove(dy > 0 ? MoveDirection.South : MoveDirection.North);
			else
				sm.stepMove(dx > 0 ? MoveDirection.East : MoveDirection.West);
		else				// Jump move
			sm.jumpMove(
					dy > 0 ? MoveDirection.South : dy < 0 ? MoveDirection.North : MoveDirection.Null,
					dx > 0 ? MoveDirection.East : dx < 0 ? MoveDirection.West : MoveDirection.Null);
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
		@SuppressWarnings("deprecation")
		Time time = new Time(0, minutes, seconds);
		QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().setRemainingTime(time);
		QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().setRemainingTime(time);
	}

	
	
	public static void countdownZero(Player player) {
		
		//assumes player passed as parameters is the one with zero time left
			//other player is victorious
		if(player.hasGameAsWhite()) {
			QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.BlackWon);
		}else {
			QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.WhiteWon);
		}
		
	}
	
	/** Checks if WhiteWon, BlackWon or Draw. Returns true if status changed */
	public static boolean updateGameStatus() {
		GameStatus initalStatus = QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus();
		
		Player player = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
		if(isWinner(player, QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile()))
			QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.WhiteWon);
		
		player = player.getNextPlayer();
		if(isWinner(player, QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile()))
			QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.BlackWon);
		
		detectDraw();
		
		return initalStatus != QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus();
	}
	
//--------------------------------------------------------------------------------------------------------------------------

	public static class InvalidPositionException extends Exception
	{
		public InvalidPositionException() {}
		public InvalidPositionException(String message) { super(message); }
	}

	public static ArrayList<Player> createUsersAndPlayers(String userName1, String userName2) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		User user1 = User.hasWithName(userName1) ? User.getWithName(userName1) : quoridor.addUser(userName1);
		User user2 = User.hasWithName(userName2) ? User.getWithName(userName2) : quoridor.addUser(userName2);

		int thinkingTime = 180;

		// Players are assumed to start on opposite sides and need to make progress
		// horizontally to get to the other side
		// @formatter:off
		/*
		 * __________ | | | | |x-> <-x| | | |__________|			<------------ This
		 * 															<------------ Is bullshit
		 * 
		 */
		// @formatter:on
		Player player1 = new Player(new Time(thinkingTime), user1, 1, Direction.Vertical);
		Player player2 = new Player(new Time(thinkingTime), user2, 9, Direction.Vertical);
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
		// positions		<----------- Such bullshit, pissed off
		Tile player1StartPos = getTile(5, 9);
		Tile player2StartPos = getTile(5, 1);

		Game game = new Game(GameStatus.Initializing, MoveMode.PlayerMove, quoridor);
		game.setWhitePlayer(players.get(0));
		game.setBlackPlayer(players.get(1));

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
		game.getWhitePlayer().getPawnBehaviour().initialize();
		game.getBlackPlayer().getPawnBehaviour().initialize();
		game.getWhitePlayer().getPawnBehaviour().setSMTest();
		game.getBlackPlayer().getPawnBehaviour().setSMTest();
		
	}

	public static void stepBackwards() {
		GamePosition pos = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		int currentIndex = QuoridorApplication.getQuoridor().getCurrentGame().getPositions().indexOf(pos);
		int previousIndex = currentIndex == 0 ? 0 : currentIndex - 1;
		GamePosition newPos = QuoridorApplication.getQuoridor().getCurrentGame().getPosition(previousIndex);
		QuoridorApplication.getQuoridor().getCurrentGame().setCurrentPosition(newPos);
		
	}

	public static void stepForwards() {
		GamePosition pos = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		int currentIndex = QuoridorApplication.getQuoridor().getCurrentGame().getPositions().indexOf(pos);
		int max = QuoridorApplication.getQuoridor().getCurrentGame().numberOfPositions()-1;
		int nextIndex = currentIndex == max ? currentIndex : currentIndex + 1;
		GamePosition newPos = QuoridorApplication.getQuoridor().getCurrentGame().getPosition(nextIndex);
		QuoridorApplication.getQuoridor().getCurrentGame().setCurrentPosition(newPos);
	}
}