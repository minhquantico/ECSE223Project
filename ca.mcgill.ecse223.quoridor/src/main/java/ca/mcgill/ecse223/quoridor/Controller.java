package ca.mcgill.ecse223.quoridor;

public class Controller {

<<<<<<< Updated upstream
=======
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
	 * **/
	/* This method selects an existing username from the list of users already created.
	It takes an input string that represents the username and searches the list of users to find it.
	If there is a match, the user with that username is linked to the player. If there is no match,
	the method does not link the user with the player and notifies the player that there exists no
	user with that username. */
	public static void SelectExistingUsername(String string) {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}
	
	
	/** @author Minh Quan Hoang **/
	//This method creates a new username by creating a new user and adding it to the list of users
	public static void CreateNewUsername(String string) {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}
	
	/** @author Minh Quan Hoang **/
	//Initializes a new game and changes the status of the game to initializing
	public static void InitializeNewGame() {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}
	/** @author Minh Quan Hoang **/
	//Starts the clock
	public static void StartClock() {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}
	/** @author Minh Quan Hoang **/
	// Sets the total thinking time after the game is initialized
	public static void setTotalThinkingTime() {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException();
	}
	/** @author Minh Quan Hoang **/
	//Selects the username for the black player
	public static void setBlackPlayerUsername() {
		/* The player selects a username from the list of users from the GUI or inputs a new one. 
		If he inputs a new one, then this method calls the CreateNewUserName controller method to create
		a new user with the username. Then the method calls the SelectExistingUsername controller method
		and sets the username for the black player. */ 
		throw new java.lang.UnsupportedOperationException();
	}
	/** @author Minh Quan Hoang **/
	//Selects the username for the white player
	public static void setWhitePlayerUsername() {
		//This method does the same as the method above but for the white player
		throw new java.lang.UnsupportedOperationException();
	}
	
	
	
	
	
//--------------------------------------------------------------------------------------------------------------------------
	
	//Jake's controller methods
	
	/**
	   * @author Jake Pogharian
	   * Feature: Set Total Thinking Time
	   * This method is used to set the thinking time for each player. This will dictate how much time the player has remaining. 
	   * It is called before the start of the game in order to set the initial remaining time for both players
	   * @param minutes: This int will be used to set the specific number of minutes of remaining time (thinking time) (must be less than the minute equivalent of 24 hours)
	   * @param seconds: This int will be used to set the specific number of seconds of remaining time (thinking time) for each player (must be less than the second equivalent of 24 hours)
	   */
private void setThinkingTime(int minutes, int seconds) 
{
	
	throw new java.lang.UnsupportedOperationException();
}
	

/**
 * @author Jake Pogharian
 * Feature: Drop Wall
 * This method is used to get the location over which wall is hovering from the GUI and then to return 
 * the Tile object bearing the coordinates of that area
 * @return Tile This returns sum of numA and numB.
 */
private Tile getTile() 
{
	 throw new java.lang.UnsupportedOperationException();
}



/**
 * @author Jake Pogharian
 * Feature: Drop Wall
 * This method is used to perform the act of dropping a wall. It will perform the necessary changes 
 * to both the View and the Model. For the view it will remove the wall from the hand, notify in case of invalid attempted move, etc.
 * For the model, it will register the wall move and complete the move when it is in fact valid, change whose turn it is, etc.
 * @param Tile t: This is a parameter of type Tile and will be used by the method to know where to perform the wall drop.
 */
private void dropWall(Tile t) 
{
	 throw new java.lang.UnsupportedOperationException();	
}
	
	
	
	
	
	
	
	
	
//--------------------------------------------------------------------------------------------------------------------------
		
	
	
	
	
	
	
	
	
	
	
	
	
	
>>>>>>> Stashed changes
}
