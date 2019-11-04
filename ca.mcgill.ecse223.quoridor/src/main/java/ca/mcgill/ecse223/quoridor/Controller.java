
package ca.mcgill.ecse223.quoridor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashSet;
import java.sql.Time;
import java.util.ArrayList;
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
	 * @author David Budaghyan
	 * Feature: GrabWall
	 * step:("I try to grab a wall from my stock")
	 *  @param aPlayer
	*/
	//alternative: public static void grabWallFromStock(Player aPlayer, Wall aWall)
	public static void grabWallFromStock(Player aPlayer) {
		// alternative: grabWallFromStock(Player aPlayer, Wall aWall)
		throw new java.lang.UnsupportedOperationException();
		
		//if we do change to alternative, then we can also return the aWall parameter.
	}
	
	
	
	/**  
	 * @author David Budaghyan
	 * Feature: MoveWall
	 * step:("I try to move the wall {string}")
	 *  @param direction
	 */
	// might need a wall parameter - depends on later decisions
	//or maybe some type of pointer to the users mouse
	//note that in that case we would return the wall aswell
	public static void moveWall(String direction) {
		throw new java.lang.UnsupportedOperationException();
	}


//---------------------------------------------------------------------------------------------------------------------------

	
	/**
	 *  @author Lenoy Christy
	 * Feature: Initialize board
	 * step: ("The initialization of the board is initiated") 
	 */
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
	
	/**
	 * @author Lenoy Christy
	 * Feature: SwitchCurrentPlayer
	 * step: ("Player {string} completes his move") 
	 */
	public static void endMove() {
		if (PlayScreenController.instance.board.activePlayer == 0) {
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setPlayerToMove(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer());
			PlayScreenController.instance.pane.getChildren().remove(PlayScreenController.instance.WhitePlayerImage);
			PlayScreenController.instance.pane.getChildren().add(PlayScreenController.instance.BlackPlayerImage);
		}	
		else {
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setPlayerToMove(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer());
			PlayScreenController.instance.pane.getChildren().remove(PlayScreenController.instance.BlackPlayerImage);
			PlayScreenController.instance.pane.getChildren().add(PlayScreenController.instance.WhitePlayerImage);
		}
	}
	
	
//--------------------------------------------------------------------------------------------------------------------------

	
	/**
	 * @author Traian Coza
	 * Feature: LoadPosition
	 * step:("I initiate to load a saved game {string}")
	 *  Load game in QuoridorApplication from provided file.
	 */
	public static void loadGame(File file) throws FileNotFoundException, InvalidPositionException
	{
		initQuoridorAndBoard();
		InitializeNewGame();
		
		
		try (Scanner input = new Scanner(file))
		{
			int no = 0;
			GamePosition current = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
			while (input.hasNext())
			{
				input.next((no/2+1) + "\\.");
				String token = input.next("[a-i][1-9][hv]?");
				int col = token.charAt(0) - 'a';
				int row = token.charAt(1) - '1';
				char or = token.length() == 2 ? '-' : token.charAt(2);
				
				current = cloneGamePosition(current);
				
				Player player = no % 2 == 0 ?
						QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer() :
						QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer();
				Tile target = QuoridorApplication.getQuoridor().getBoard().getTile(row*9+col);
				Move move;
				if (or == '-')	// Step move
				{
					if (!initPosValidation(target))
						throw new InvalidPositionException();
					
					move = new StepMove(no, no/2+1, player, target, QuoridorApplication.getQuoridor().getCurrentGame());

					if (player.hasGameAsWhite())
						current.setWhitePosition(new PlayerPosition(player, target));
					else
						current.setBlackPosition(new PlayerPosition(player, target));
				}
				else		// Wall move
				{
					if (!initPosValidation(target, or == 'h' ? Direction.Horizontal : Direction.Vertical))
						throw new InvalidPositionException();
					
					Wall w;
					if (player.hasGameAsWhite())
					{
						w = current.getWhiteWallsInStock(0);
						current.removeWhiteWallsInStock(w);
						current.addWhiteWallsOnBoard(w);
					}
					else
					{
						w = current.getBlackWallsInStock(0);
						current.removeBlackWallsInStock(w);
						current.addBlackWallsOnBoard(w);
					}
					
					move = new WallMove(no, no/2+1, player, target,
							QuoridorApplication.getQuoridor().getCurrentGame(),
							or == 'h' ? Direction.Horizontal : Direction.Vertical,
							w);
				}
				
				QuoridorApplication.getQuoridor().getCurrentGame().addMove(move);
				QuoridorApplication.getQuoridor().getCurrentGame().addPosition(current);
				QuoridorApplication.getQuoridor().getCurrentGame().setCurrentPosition(current);
				QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setPlayerToMove(player.getNextPlayer());
				no++;
			}
		}
	}
	
	private static GamePosition cloneGamePosition(GamePosition pos)
	{
		GamePosition cloned = new GamePosition(pos.getId()+1, pos.getWhitePosition(), pos.getBlackPosition(), pos.getPlayerToMove(), pos.getGame());

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
	 * @author Traian Coza
	 * Feature: LoadPosition
	 * step: ("The position to load is valid")
	 * Check if current GamePosition is valid.
	 * @return true of false
	 */
	public static boolean positionIsValid() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * @author Traian Coza
	 * Feature: SavePosition
	 * step: ("The user initiates to save the game with name {string}")
	 * Save current game to provided file.
	 * @param file
	 */
	public static void saveGame(File file) throws FileNotFoundException
	{
		try (PrintStream output = new PrintStream(file))
		{
			int no = 0;
			for (Move m : QuoridorApplication.getQuoridor().getCurrentGame().getMoves())
			{
				if (no % 2 == 0)
					output.print(no/2+1 + ".");
				output.print(' ');
				output.print('a' + m.getTargetTile().getColumn()-1);
				output.print('1' + m.getTargetTile().getRow()-1);
				if (m instanceof WallMove)
					output.print(((WallMove)m).getWallDirection() == Direction.Horizontal ? 'h' : 'v');
				if (no % 2 == 1)
					output.println();
			}
		}
	}

	/**
	 * @author Traian Coza
	 * Feature: SavePosition
	 * step: ("The user confirms to overwrite existing file") and ("The user cancels to overwrite existing file")
	 * Set the overwrite option (if saveGame is called on an existing file and overwrite is set, the file will be overwritten.
	 * @param overwrite
	 */
	public static void setOverwrite(boolean overwrite) {
		throw new java.lang.UnsupportedOperationException();
	}
	
	
//--------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * @author Gohar Saqib Fazal
	 * This controller method valdidates the postion of the pawn in the game
	 * Used in @When("Validation of the position is initiated") statement in Validate Position Feature
	 * Used in @Then("The position shall be {string}") statement in Validate Position Feature
	 * @param aTargetTile: The position at which the pawn is going to be moved in the current move.
	 * @return Boolean: This tells us whether the pawn position is valid or not
	 */
	public static Boolean initPosValidation (Tile aTargetTile) {
		throw new java.lang.UnsupportedOperationException();
//		Quoridor model = null;
//		try {
//			model = new Quoridor();
//		} catch(Exception e) {
//			System.out.println(e.getMessage());
//		}
//		if(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().equals(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer()) && QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().equals(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer())) {
//			return true;
//		}
//		else {
//			return false;
//		}
//		return true;
		//assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().equals(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer()));
	
		// = PlayScreenController.instance.board.players[PlayScreenController.instance.board.activePlayer].getPossibleMoves();
		
				
	}


	/**
	 * @author Gohar Saqib Fazal 
	 * This controller method validates the position of the wall in the game
	 * Used in @When("Validation of the position is initiated") statement in Validate Position Feature
	 * Used in @Then("The position shall be valid") statement in Validate Position Feature
	 * Used in @Then("The position shall be invalid") statement in Validate Position Feature
	 * @param aTargetTile: The position at which the wall is going to be placed in the current move.
	 * @param dir: The direction of the wall that is going to be placed in the current move
	 * @return Boolean: This tells us whether the pawn position is valid or not
	 */
	public static Boolean initPosValidation (Tile aTargetTile, Direction dir) { 
		throw new java.lang.UnsupportedOperationException();
	}
	
	
		
	/**
	 * @author Gohar Saqib Fazal 
	 * This controller method flips the wall that is in the user's hand
	 * For example, walls placed vertically will be flipped horizontally and vice versa.
	 * Used in @When("I try to flip the wall") statement in the Rotate Wall feature
	 * @param wallMove: Wall Move object that contains information 
	 * such as which wall is being flipped and the direction of set wall
	 */
	public static void flip_wall(Rectangle wall) {
		switch ((int)wall.getRotate())
		{
		case 0: wall.setRotate(90); QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().setWallDirection(Direction.Vertical); break;
		case 90: wall.setRotate(0); QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().setWallDirection(Direction.Horizontal); break;
		default:
			wall.setRotate(0); QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().setWallDirection(Direction.Horizontal);
		}
	}
	
	
	
	
	
	
//--------------------------------------------------------------------------------------------------------------------------
	
	/** @author Minh Quan Hoang 
	 * Feature: ProvideSelectUserName
	 * Step: @When("The player selects existing {string}")
	 * @param string: Username selected by player.
	 * @param player: player to set user.
	 * This method selects an existing username from the list of users already created.
	 * It takes an input string that represents the username and searches the list of users to find it.
	 * If there is a match, the user with that username is linked to the player. If there is no match,
	 * the method does not link the user with the player and notifies the player that there exists no
	 * user with that username. **/

	public static void SelectExistingUsername(Player player, String username) {
		if(player.hasGameAsWhite()) {
		Controller.setWhitePlayerUsername(username);
		}
		else {
		Controller.setBlackPlayerUsername(username);
		}

	}

	/** @author Minh Quan Hoang 
	 * Feature: ProvideSelectUserName
	 * Step: @When("The player provides new user name: {string}")
	 * @param string: Username to be created.
	 * This method creates a new username by creating a new user
	 * and adding it to the list of users **/
	public static void CreateNewUsername(String username) {
		QuoridorApplication.getQuoridor().addUser(username);
	}
	
	/** @author Minh Quan Hoang 
	 * Feature: StartNewGame
	 * Step: @When("A new game is being initialized")
	 * Initializes a new game and changes the status of the game to initializing **/
	public static void InitializeNewGame() {
		ArrayList<Player> createUsersAndPlayers = createUsersAndPlayers("user1","user2"); //helper method in controller
		createAndInitializeGame(createUsersAndPlayers); //helper method in controller
	}


	/** @author Minh Quan Hoang 
	 * Feature: StartNewGame
	 * Step: @When("I start the clock")
	 * Starts the clock **/
	public static void StartClock(long seconds) {
		PlayScreenController.instance.board.players[0].startClock(seconds);
	}

	/** @author Minh Quan Hoang 
	 * Feature: StartNewGame
	 * Step: @When("Total thinking time is set")
	 * Sets the total thinking time after the game is initialized **/
	public static void setTotalThinkingTime(int minutes, int seconds) {
		setThinkingTime(minutes, seconds); //overlaps with Jake's controller method
	}

	/** @author Minh Quan Hoang 
	 * Feature: StartNewGame
	 * Step: @When("Black player chooses a username")
	 * Selects the username for the black player **/
	public static void setBlackPlayerUsername(String username) {
		/* The player selects a username from the list of users from the GUI or inputs a new one. 
		If he inputs a new one, then this method calls the CreateNewUserName controller method to create
		a new user with the username. Then the method calls the SelectExistingUsername controller method
		and sets the username for the black player. */ 
		QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().setUser(User.getWithName(username));
		
	}

	/** @author Minh Quan Hoang 
	 * Feature: StartNewGame 
	 * Step: @When("White player chooses a username")
	 * Selects the username for the white player **/
	public static void setWhitePlayerUsername(String username) {
		//This method does the same as the method above but for the white player
		QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().setUser(User.getWithName(username));
	}

	
	
//--------------------------------------------------------------------------------------------------------------------------
	
	//Jake's controller methods//
	
/**
 * @author Jake Pogharian
 * Feature: Drop Wall
 * step:  when "I release the wall in my hand"
 * This method is used to get the location over which wall is hovering from the GUI and then to return 
 * the Tile object bearing the coordinates of that area
 * @return Tile this returns the specific tile at that location.
 */
public static Tile getTile() 
{
	 throw new java.lang.UnsupportedOperationException();
}



/**
 * @author Jake Pogharian
 * Feature: Drop Wall
 * step: when "I release the wall in my hand"
 * This method is used to perform the act of dropping a wall. It will perform the necessary changes 
 * to both the View and the Model. For the view it will remove the wall from the hand, notify in case of invalid attempted move, etc.
 * For the model, it will register the wall move and complete the move when it is in fact valid, change whose turn it is, etc.
 * @param Tile t: This is a parameter of type Tile and will be used by the method to know where to perform the wall drop.
 */
public static void dropWall(fxml.Board board, int x, int y, Direction direction) {
	
	int boardPaneX=229;
	int boardPaneY=110;
	int wallWidth=106;
	int wallHeight=29;
	
	int walli=0;
	int wallj=0;
	Double minDist;
	Double dist;
	
	int chosenXCoord=0;
	int chosenYCoord=0;
	
	int deltaX=0;
	int deltaY=0;
	
	if(direction==direction.Horizontal) {
		//check if drop location is close to horizontal wall location
		dist=(double)1000;
		minDist=(double)1100;
		for(int i=0; i<8;i++) {
			for(int j=0; j<8; j++) {
				
				
				
				int xcoord =(int)(boardPaneX+board.getWallX(i,j,'h')+wallWidth/2);
				int ycoord=(int)(boardPaneY+board.getWallY(i,j,'h')+wallHeight/2);
		
				
				deltaX=xcoord-x;
				deltaY=ycoord-y;
				
				//calculate distance from mmouse click to wall position
				dist=Math.sqrt(deltaX*deltaX+deltaY*deltaY);
				
				if(Double.compare(dist,minDist)<0) { 
					minDist=dist;
					walli=i;
					wallj=j;
					
					//xCoord of shortest distance wall
					
					chosenXCoord=xcoord;
					chosenYCoord=ycoord;
				}
				
				if(25>(chosenXCoord - x) && -25<(chosenXCoord-x) && (chosenYCoord-y)<25 && (chosenYCoord-y)>-25) {
					board.setVisible(i,j,'h');
			
					return;
				
				}
			}
		}
	}else {
		//check if drop location is close to horizontal wall location
		dist=(double)1000;
		minDist=(double)1100;
		for(int i=0; i<8;i++) {
			for(int j=0; j<8; j++) {
				
				
				
				int xcoord =(int)(boardPaneX+board.getWallX(i,j,'v')+wallHeight/2);
				int ycoord=(int)(boardPaneY+board.getWallY(i,j,'v')+wallWidth/2);
				
				deltaX=xcoord-x;
				deltaY=ycoord-y;
				
				//calculate distance from mmouse click to wall position
				dist=Math.sqrt(deltaX*deltaX+deltaY*deltaY);
				
				if(Double.compare(dist,minDist)<0) { 
					minDist=dist;
					walli=i;
					wallj=j;
					
					//xCoord of shortest distance wall
					
					chosenXCoord=xcoord;
					chosenYCoord=ycoord;
				}
				
				if(25>(chosenXCoord - x) && -25<(chosenXCoord-x) && (chosenYCoord-y)<25 && (chosenYCoord-y)>-25) {
					board.setVisible(i, j,'v');
					return;
				
				}
			}
		}}
}

/**
 * @author Jake Pogharian
 * Feature: setThinkingTime
 * step: When "{int}:{int} is set as the thinking time"
 * This method is used to set the thinking time for each player. This will dictate how much time the player has remaining. 
 * It is called before the start of the game in order to set the initial remaining time for both players
 * @param minutes: This int will be used to set the specific number of minutes of remaining time (thinking time) (must be less than the minute equivalent of 24 hours)
 * @param seconds: This int will be used to set the specific number of seconds of remaining time (thinking time) for each player (must be less than the second equivalent of 24 hours)
 */
public static void setThinkingTime(int minutes, int seconds) 
{
	Time time = new Time(0, minutes, seconds);
	QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().setRemainingTime(time);
	QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().setRemainingTime(time);
}
	
	
	
	
//--------------------------------------------------------------------------------------------------------------------------
	

static class InvalidPositionException extends Exception
{
	public InvalidPositionException() {}
	public InvalidPositionException(String message) { super(message); }
	
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

public static void createAndInitializeGame(ArrayList<Player> players) {
	Quoridor quoridor = QuoridorApplication.getQuoridor();
	// There are total 36 tiles in the first four rows and
	// indexing starts from 0 -> tiles with indices 36 and 36+8=44 are the starting
	// positions
	Tile player1StartPos = quoridor.getBoard().getTile(36);
	Tile player2StartPos = quoridor.getBoard().getTile(44);
	
	Game game = new Game(GameStatus.Initializing, MoveMode.PlayerMove, players.get(0), players.get(1), quoridor);

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