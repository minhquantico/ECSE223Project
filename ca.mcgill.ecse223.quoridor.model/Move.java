/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package domain;

// line 31 "../domain_model.ump"
public class Move
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Move Associations
  private Move nextMove;
  private Player player;
  private Move previousMove;
  private Cell cell;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Move(Player aPlayer, Cell aCell)
  {
    boolean didAddPlayer = setPlayer(aPlayer);
    if (!didAddPlayer)
    {
      throw new RuntimeException("Unable to create pawnMove due to player");
    }
    if (!setCell(aCell))
    {
      throw new RuntimeException("Unable to create Move due to aCell");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Move getNextMove()
  {
    return nextMove;
  }

  public boolean hasNextMove()
  {
    boolean has = nextMove != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }
  /* Code from template association_GetOne */
  public Move getPreviousMove()
  {
    return previousMove;
  }

  public boolean hasPreviousMove()
  {
    boolean has = previousMove != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Cell getCell()
  {
    return cell;
  }
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setNextMove(Move aNewNextMove)
  {
    boolean wasSet = false;
    if (aNewNextMove == null)
    {
      Move existingNextMove = nextMove;
      nextMove = null;
      
      if (existingNextMove != null && existingNextMove.getPreviousMove() != null)
      {
        existingNextMove.setPreviousMove(null);
      }
      wasSet = true;
      return wasSet;
    }

    Move currentNextMove = getNextMove();
    if (currentNextMove != null && !currentNextMove.equals(aNewNextMove))
    {
      currentNextMove.setPreviousMove(null);
    }

    nextMove = aNewNextMove;
    Move existingPreviousMove = aNewNextMove.getPreviousMove();

    if (!equals(existingPreviousMove))
    {
      aNewNextMove.setPreviousMove(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setPlayer(Player aPlayer)
  {
    boolean wasSet = false;
    if (aPlayer == null)
    {
      return wasSet;
    }

    Player existingPlayer = player;
    player = aPlayer;
    if (existingPlayer != null && !existingPlayer.equals(aPlayer))
    {
      existingPlayer.removePawnMove(this);
    }
    player.addPawnMove(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setPreviousMove(Move aNewPreviousMove)
  {
    boolean wasSet = false;
    if (aNewPreviousMove == null)
    {
      Move existingPreviousMove = previousMove;
      previousMove = null;
      
      if (existingPreviousMove != null && existingPreviousMove.getNextMove() != null)
      {
        existingPreviousMove.setNextMove(null);
      }
      wasSet = true;
      return wasSet;
    }

    Move currentPreviousMove = getPreviousMove();
    if (currentPreviousMove != null && !currentPreviousMove.equals(aNewPreviousMove))
    {
      currentPreviousMove.setNextMove(null);
    }

    previousMove = aNewPreviousMove;
    Move existingNextMove = aNewPreviousMove.getNextMove();

    if (!equals(existingNextMove))
    {
      aNewPreviousMove.setNextMove(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCell(Cell aNewCell)
  {
    boolean wasSet = false;
    if (aNewCell != null)
    {
      cell = aNewCell;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    if (nextMove != null)
    {
      nextMove.setPreviousMove(null);
    }
    Player placeholderPlayer = player;
    this.player = null;
    if(placeholderPlayer != null)
    {
      placeholderPlayer.removePawnMove(this);
    }
    if (previousMove != null)
    {
      previousMove.setNextMove(null);
    }
    cell = null;
  }

}