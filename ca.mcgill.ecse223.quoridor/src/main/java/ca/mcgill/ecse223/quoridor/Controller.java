package ca.mcgill.ecse223.quoridor;

import java.io.File;

import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Tile;
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
	 * @return Board - a Board object that is ready to be set up. 
	 */
	public static Board initQuoridorBoard() { 
		throw new java.lang.UnsupportedOperationException();
			
	}
	
	/**
	 * @author Lenoy Christy
	 * Feature: SwitchCurrentPlayer
	 * step: ("Player {string} completes his move")
	 * @param player - The player whose move it currently is.
	 * @return GamePosition - a GamePosition object with updated information on the player positions and the next player to move. 
	 */
	public static GamePosition endMove(Player player) {
		throw new java.lang.UnsupportedOperationException();
	}
	
	
//--------------------------------------------------------------------------------------------------------------------------

	
	/**
	 * @author Traian Coza
	 * Feature: LoadPosition
	 * step:("I initiate to load a saved game {string}")
	 *  Load game in QuoridorApplication from provided file.
	 */
	public static void loadGame(File file)
	{		
		throw new java.lang.UnsupportedOperationException();
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
	public static void saveGame(File file) {
		throw new java.lang.UnsupportedOperationException();
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
	public static void flip_wall(WallMove wallMove) {
		throw new java.lang.UnsupportedOperationException();
	}
	
	
	
	
	
	
//--------------------------------------------------------------------------------------------------------------------------
	
	/** @author Minh Quan Hoang 
	 * Feature: ProvideSelectUserName
	 * Step: @When("The player selects existing {string}")
	 * @param string: Username selected by player.
	 * This method selects an existing username from the list of users already created.
	 * It takes an input string that represents the username and searches the list of users to find it.
	 * If there is a match, the user with that username is linked to the player. If there is no match,
	 * the method does not link the user with the player and notifies the player that there exists no
	 * user with that username. **/
	public static void SelectExistingUsername(String string) {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	/** @author Minh Quan Hoang 
	 * Feature: ProvideSelectUserName
	 * Step: @When("The player provides new user name: {string}")
	 * @param string: Username to be created.
	 * This method creates a new username by creating a new user
	 * and adding it to the list of users **/
	public static void CreateNewUsername(String string) {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}
	
	/** @author Minh Quan Hoang 
	 * Feature: StartNewGame
	 * Step: @When("A new game is being initialized")
	 * Initializes a new game and changes the status of the game to initializing **/
	public static void InitializeNewGame() {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	/** @author Minh Quan Hoang 
	 * Feature: StartNewGame
	 * Step: @When("I start the clock")
	 * Starts the clock **/
	public static void StartClock() {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	/** @author Minh Quan Hoang 
	 * Feature: StartNewGame
	 * Step: @When("Total thinking time is set")
	 * Sets the total thinking time after the game is initialized **/
	public static void setTotalThinkingTime() {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}

	/** @author Minh Quan Hoang 
	 * Feature: StartNewGame
	 * Step: @When("Black player chooses a username")
	 * Selects the username for the black player **/
	public static void setBlackPlayerUsername() {
		/* The player selects a username from the list of users from the GUI or inputs a new one. 
		If he inputs a new one, then this method calls the CreateNewUserName controller method to create
		a new user with the username. Then the method calls the SelectExistingUsername controller method
		and sets the username for the black player. */ 
		throw new java.lang.UnsupportedOperationException();
	}

	/** @author Minh Quan Hoang 
	 * Feature: StartNewGame 
	 * Step: @When("White player chooses a username")
	 * Selects the username for the white player **/
	public static void setWhitePlayerUsername() {
		//This method does the same as the method above but for the white player
		throw new java.lang.UnsupportedOperationException();
	}

	
	
//--------------------------------------------------------------------------------------------------------------------------
	
	//Jake's controller methods//
	
/**
 * @author Jake Pogharian
 * Feature: Drop Wall
 * step:  when "I release the wall in my hand"
 * This method is used to get the location over which wall is hovering from the GUI and then to return 
 * the Tile object bearing the coordinates of that area
 * @return Tile This returns sum of numA and numB.
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
public static void dropWall(Tile t) 
{
	 throw new java.lang.UnsupportedOperationException();	
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

throw new java.lang.UnsupportedOperationException();
}
	
	
	
	
//--------------------------------------------------------------------------------------------------------------------------
}	
	

