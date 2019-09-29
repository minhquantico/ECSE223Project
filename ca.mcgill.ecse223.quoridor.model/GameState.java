/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/



// line 68 "domain_model.ump"
public class GameState
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GameState Associations
  private Player turn;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GameState(Player aTurn, Game aGame)
  {
    if (!setTurn(aTurn))
    {
      throw new RuntimeException("Unable to create GameState due to aTurn");
    }
    if (aGame == null || aGame.getGameState() != null)
    {
      throw new RuntimeException("Unable to create GameState due to aGame");
    }
    game = aGame;
  }

  public GameState(Player aTurn, int aIdForGame, Board aBoardForGame, System aSystemForGame)
  {
    boolean didAddTurn = setTurn(aTurn);
    if (!didAddTurn)
    {
      throw new RuntimeException("Unable to create gameState due to turn");
    }
    game = new Game(aIdForGame, this, aBoardForGame, aSystemForGame);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Player getTurn()
  {
    return turn;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setTurn(Player aNewTurn)
  {
    boolean wasSet = false;
    if (aNewTurn != null)
    {
      turn = aNewTurn;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    turn = null;
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
  }

}