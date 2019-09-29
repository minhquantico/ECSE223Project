/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/


import java.util.*;

// line 8 "domain_model.ump"
public class Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private int thinkingTime;
  private int id;

  //Player Associations
  private List<Wall> usedWall;
  private List<Wall> unusedWall;
  private Game game;
  private List<Move> pawnMoves;
  private Move currentMove;
  private Cell currentCell;
  private User user;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(int aThinkingTime, int aId, Game aGame, Cell aCurrentCell, User aUser)
  {
    thinkingTime = aThinkingTime;
    id = aId;
    usedWall = new ArrayList<Wall>();
    unusedWall = new ArrayList<Wall>();
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create player due to game");
    }
    pawnMoves = new ArrayList<Move>();
    boolean didAddCurrentCell = setCurrentCell(aCurrentCell);
    if (!didAddCurrentCell)
    {
      throw new RuntimeException("Unable to create occupant due to currentCell");
    }
    boolean didAddUser = setUser(aUser);
    if (!didAddUser)
    {
      throw new RuntimeException("Unable to create player due to user");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setThinkingTime(int aThinkingTime)
  {
    boolean wasSet = false;
    thinkingTime = aThinkingTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public int getThinkingTime()
  {
    return thinkingTime;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetMany */
  public Wall getUsedWall(int index)
  {
    Wall aUsedWall = usedWall.get(index);
    return aUsedWall;
  }

  public List<Wall> getUsedWall()
  {
    List<Wall> newUsedWall = Collections.unmodifiableList(usedWall);
    return newUsedWall;
  }

  public int numberOfUsedWall()
  {
    int number = usedWall.size();
    return number;
  }

  public boolean hasUsedWall()
  {
    boolean has = usedWall.size() > 0;
    return has;
  }

  public int indexOfUsedWall(Wall aUsedWall)
  {
    int index = usedWall.indexOf(aUsedWall);
    return index;
  }
  /* Code from template association_GetMany */
  public Wall getUnusedWall(int index)
  {
    Wall aUnusedWall = unusedWall.get(index);
    return aUnusedWall;
  }

  public List<Wall> getUnusedWall()
  {
    List<Wall> newUnusedWall = Collections.unmodifiableList(unusedWall);
    return newUnusedWall;
  }

  public int numberOfUnusedWall()
  {
    int number = unusedWall.size();
    return number;
  }

  public boolean hasUnusedWall()
  {
    boolean has = unusedWall.size() > 0;
    return has;
  }

  public int indexOfUnusedWall(Wall aUnusedWall)
  {
    int index = unusedWall.indexOf(aUnusedWall);
    return index;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetMany */
  public Move getPawnMove(int index)
  {
    Move aPawnMove = pawnMoves.get(index);
    return aPawnMove;
  }

  public List<Move> getPawnMoves()
  {
    List<Move> newPawnMoves = Collections.unmodifiableList(pawnMoves);
    return newPawnMoves;
  }

  public int numberOfPawnMoves()
  {
    int number = pawnMoves.size();
    return number;
  }

  public boolean hasPawnMoves()
  {
    boolean has = pawnMoves.size() > 0;
    return has;
  }

  public int indexOfPawnMove(Move aPawnMove)
  {
    int index = pawnMoves.indexOf(aPawnMove);
    return index;
  }
  /* Code from template association_GetOne */
  public Move getCurrentMove()
  {
    return currentMove;
  }

  public boolean hasCurrentMove()
  {
    boolean has = currentMove != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Cell getCurrentCell()
  {
    return currentCell;
  }
  /* Code from template association_GetOne */
  public User getUser()
  {
    return user;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUsedWall()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfUsedWall()
  {
    return 10;
  }
  /* Code from template association_AddUnidirectionalOptionalN */
  public boolean addUsedWall(Wall aUsedWall)
  {
    boolean wasAdded = false;
    if (usedWall.contains(aUsedWall)) { return false; }
    if (numberOfUsedWall() < maximumNumberOfUsedWall())
    {
      usedWall.add(aUsedWall);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean removeUsedWall(Wall aUsedWall)
  {
    boolean wasRemoved = false;
    if (usedWall.contains(aUsedWall))
    {
      usedWall.remove(aUsedWall);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalOptionalN */
  public boolean setUsedWall(Wall... newUsedWall)
  {
    boolean wasSet = false;
    ArrayList<Wall> verifiedUsedWall = new ArrayList<Wall>();
    for (Wall aUsedWall : newUsedWall)
    {
      if (verifiedUsedWall.contains(aUsedWall))
      {
        continue;
      }
      verifiedUsedWall.add(aUsedWall);
    }

    if (verifiedUsedWall.size() != newUsedWall.length || verifiedUsedWall.size() > maximumNumberOfUsedWall())
    {
      return wasSet;
    }

    usedWall.clear();
    usedWall.addAll(verifiedUsedWall);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUsedWallAt(Wall aUsedWall, int index)
  {  
    boolean wasAdded = false;
    if(addUsedWall(aUsedWall))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsedWall()) { index = numberOfUsedWall() - 1; }
      usedWall.remove(aUsedWall);
      usedWall.add(index, aUsedWall);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUsedWallAt(Wall aUsedWall, int index)
  {
    boolean wasAdded = false;
    if(usedWall.contains(aUsedWall))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsedWall()) { index = numberOfUsedWall() - 1; }
      usedWall.remove(aUsedWall);
      usedWall.add(index, aUsedWall);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUsedWallAt(aUsedWall, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUnusedWall()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfUnusedWall()
  {
    return 10;
  }
  /* Code from template association_AddOptionalNToOne */
  public Wall addUnusedWall(int aId)
  {
    if (numberOfUnusedWall() >= maximumNumberOfUnusedWall())
    {
      return null;
    }
    else
    {
      return new Wall(aId, this);
    }
  }

  public boolean addUnusedWall(Wall aUnusedWall)
  {
    boolean wasAdded = false;
    if (unusedWall.contains(aUnusedWall)) { return false; }
    if (numberOfUnusedWall() >= maximumNumberOfUnusedWall())
    {
      return wasAdded;
    }

    Player existingOwner = aUnusedWall.getOwner();
    boolean isNewOwner = existingOwner != null && !this.equals(existingOwner);
    if (isNewOwner)
    {
      aUnusedWall.setOwner(this);
    }
    else
    {
      unusedWall.add(aUnusedWall);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUnusedWall(Wall aUnusedWall)
  {
    boolean wasRemoved = false;
    //Unable to remove aUnusedWall, as it must always have a owner
    if (!this.equals(aUnusedWall.getOwner()))
    {
      unusedWall.remove(aUnusedWall);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUnusedWallAt(Wall aUnusedWall, int index)
  {  
    boolean wasAdded = false;
    if(addUnusedWall(aUnusedWall))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUnusedWall()) { index = numberOfUnusedWall() - 1; }
      unusedWall.remove(aUnusedWall);
      unusedWall.add(index, aUnusedWall);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUnusedWallAt(Wall aUnusedWall, int index)
  {
    boolean wasAdded = false;
    if(unusedWall.contains(aUnusedWall))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUnusedWall()) { index = numberOfUnusedWall() - 1; }
      unusedWall.remove(aUnusedWall);
      unusedWall.add(index, aUnusedWall);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUnusedWallAt(aUnusedWall, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    //Must provide game to player
    if (aGame == null)
    {
      return wasSet;
    }

    //game already at maximum (2)
    if (aGame.numberOfPlayers() >= Game.maximumNumberOfPlayers())
    {
      return wasSet;
    }
    
    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      boolean didRemove = existingGame.removePlayer(this);
      if (!didRemove)
      {
        game = existingGame;
        return wasSet;
      }
    }
    game.addPlayer(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPawnMoves()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Move addPawnMove(Cell aCell)
  {
    return new Move(this, aCell);
  }

  public boolean addPawnMove(Move aPawnMove)
  {
    boolean wasAdded = false;
    if (pawnMoves.contains(aPawnMove)) { return false; }
    Player existingPlayer = aPawnMove.getPlayer();
    boolean isNewPlayer = existingPlayer != null && !this.equals(existingPlayer);
    if (isNewPlayer)
    {
      aPawnMove.setPlayer(this);
    }
    else
    {
      pawnMoves.add(aPawnMove);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePawnMove(Move aPawnMove)
  {
    boolean wasRemoved = false;
    //Unable to remove aPawnMove, as it must always have a player
    if (!this.equals(aPawnMove.getPlayer()))
    {
      pawnMoves.remove(aPawnMove);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPawnMoveAt(Move aPawnMove, int index)
  {  
    boolean wasAdded = false;
    if(addPawnMove(aPawnMove))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPawnMoves()) { index = numberOfPawnMoves() - 1; }
      pawnMoves.remove(aPawnMove);
      pawnMoves.add(index, aPawnMove);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePawnMoveAt(Move aPawnMove, int index)
  {
    boolean wasAdded = false;
    if(pawnMoves.contains(aPawnMove))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPawnMoves()) { index = numberOfPawnMoves() - 1; }
      pawnMoves.remove(aPawnMove);
      pawnMoves.add(index, aPawnMove);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPawnMoveAt(aPawnMove, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setCurrentMove(Move aNewCurrentMove)
  {
    boolean wasSet = false;
    currentMove = aNewCurrentMove;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setCurrentCell(Cell aNewCurrentCell)
  {
    boolean wasSet = false;
    if (aNewCurrentCell == null)
    {
      //Unable to setCurrentCell to null, as occupant must always be associated to a currentCell
      return wasSet;
    }
    
    Player existingOccupant = aNewCurrentCell.getOccupant();
    if (existingOccupant != null && !equals(existingOccupant))
    {
      //Unable to setCurrentCell, the current currentCell already has a occupant, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Cell anOldCurrentCell = currentCell;
    currentCell = aNewCurrentCell;
    currentCell.setOccupant(this);

    if (anOldCurrentCell != null)
    {
      anOldCurrentCell.setOccupant(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setUser(User aUser)
  {
    boolean wasSet = false;
    //Must provide user to player
    if (aUser == null)
    {
      return wasSet;
    }

    //user already at maximum (2)
    if (aUser.numberOfPlayers() >= User.maximumNumberOfPlayers())
    {
      return wasSet;
    }
    
    User existingUser = user;
    user = aUser;
    if (existingUser != null && !existingUser.equals(aUser))
    {
      boolean didRemove = existingUser.removePlayer(this);
      if (!didRemove)
      {
        user = existingUser;
        return wasSet;
      }
    }
    user.addPlayer(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    usedWall.clear();
    for(int i=unusedWall.size(); i > 0; i--)
    {
      Wall aUnusedWall = unusedWall.get(i - 1);
      aUnusedWall.delete();
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removePlayer(this);
    }
    for(int i=pawnMoves.size(); i > 0; i--)
    {
      Move aPawnMove = pawnMoves.get(i - 1);
      aPawnMove.delete();
    }
    currentMove = null;
    Cell existingCurrentCell = currentCell;
    currentCell = null;
    if (existingCurrentCell != null)
    {
      existingCurrentCell.setOccupant(null);
    }
    User placeholderUser = user;
    this.user = null;
    if(placeholderUser != null)
    {
      placeholderUser.removePlayer(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "thinkingTime" + ":" + getThinkingTime()+ "," +
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentMove = "+(getCurrentMove()!=null?Integer.toHexString(System.identityHashCode(getCurrentMove())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentCell = "+(getCurrentCell()!=null?Integer.toHexString(System.identityHashCode(getCurrentCell())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "user = "+(getUser()!=null?Integer.toHexString(System.identityHashCode(getUser())):"null");
  }
}