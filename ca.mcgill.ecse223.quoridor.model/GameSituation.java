/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package domain;

// line 70 "../domain_model.ump"
public class GameSituation
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GameSituation Associations
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GameSituation(Game aGame)
  {
    if (aGame == null || aGame.getGameSituation() != null)
    {
      throw new RuntimeException("Unable to create GameSituation due to aGame");
    }
    game = aGame;
  }

  public GameSituation(int aIdForGame, Board aBoardForGame, System aSystemForGame)
  {
    game = new Game(aIdForGame, this, aBoardForGame, aSystemForGame);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }

  public void delete()
  {
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
  }

}