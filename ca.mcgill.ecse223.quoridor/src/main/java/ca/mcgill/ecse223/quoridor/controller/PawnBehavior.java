/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.controller;
import java.util.*;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.*;

// line 6 "../../../../../PawnStateMachine.ump"
public class PawnBehavior
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum MoveDirection { East, South, West, North }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PawnBehavior State Machines
  public enum PawnSM { gameActive, gameOver }
  public enum PawnSMGameActive { Null, unmoved, moving }
  public enum PawnSMGameActiveUnmoved { Null, unchecked, checked }
  public enum PawnSMGameActiveMoving { Null, stepMove, jumpMove }
  private PawnSM pawnSM;
  private PawnSMGameActive pawnSMGameActive;
  private PawnSMGameActiveUnmoved pawnSMGameActiveUnmoved;
  private PawnSMGameActiveMoving pawnSMGameActiveMoving;

  //PawnBehavior Associations
  private Game currentGame;
  private Player player;
  private List<Tile> PossibleMoves;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PawnBehavior()
  {
    PossibleMoves = new ArrayList<Tile>();
    setPawnSMGameActive(PawnSMGameActive.Null);
    setPawnSMGameActiveUnmoved(PawnSMGameActiveUnmoved.Null);
    setPawnSMGameActiveMoving(PawnSMGameActiveMoving.Null);
    setPawnSM(PawnSM.gameActive);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getPawnSMFullName()
  {
    String answer = pawnSM.toString();
    if (pawnSMGameActive != PawnSMGameActive.Null) { answer += "." + pawnSMGameActive.toString(); }
    if (pawnSMGameActiveUnmoved != PawnSMGameActiveUnmoved.Null) { answer += "." + pawnSMGameActiveUnmoved.toString(); }
    if (pawnSMGameActiveMoving != PawnSMGameActiveMoving.Null) { answer += "." + pawnSMGameActiveMoving.toString(); }
    return answer;
  }

  public PawnSM getPawnSM()
  {
    return pawnSM;
  }

  public PawnSMGameActive getPawnSMGameActive()
  {
    return pawnSMGameActive;
  }

  public PawnSMGameActiveUnmoved getPawnSMGameActiveUnmoved()
  {
    return pawnSMGameActiveUnmoved;
  }

  public PawnSMGameActiveMoving getPawnSMGameActiveMoving()
  {
    return pawnSMGameActiveMoving;
  }

  public boolean gameCompleted()
  {
    boolean wasEventProcessed = false;
    
    PawnSM aPawnSM = pawnSM;
    switch (aPawnSM)
    {
      case gameActive:
        exitPawnSM();
        setPawnSM(PawnSM.gameOver);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean nextPlayerTurn()
  {
    boolean wasEventProcessed = false;
    
    PawnSMGameActive aPawnSMGameActive = pawnSMGameActive;
    switch (aPawnSMGameActive)
    {
      case unmoved:
        exitPawnSMGameActive();
        setPawnSMGameActive(PawnSMGameActive.unmoved);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private boolean __autotransition16__()
  {
    boolean wasEventProcessed = false;
    
    PawnSMGameActive aPawnSMGameActive = pawnSMGameActive;
    switch (aPawnSMGameActive)
    {
      case moving:
        exitPawnSMGameActive();
        setPawnSMGameActive(PawnSMGameActive.unmoved);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private boolean __autotransition15__()
  {
    boolean wasEventProcessed = false;
    
    PawnSMGameActiveUnmoved aPawnSMGameActiveUnmoved = pawnSMGameActiveUnmoved;
    switch (aPawnSMGameActiveUnmoved)
    {
      case unchecked:
        exitPawnSMGameActiveUnmoved();
        setPawnSMGameActiveUnmoved(PawnSMGameActiveUnmoved.checked);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean doStepMove(MoveDirection direction)
  {
    boolean wasEventProcessed = false;
    
    PawnSMGameActiveUnmoved aPawnSMGameActiveUnmoved = pawnSMGameActiveUnmoved;
    switch (aPawnSMGameActiveUnmoved)
    {
      case checked:
        if (isLegalStep(direction))
        {
          exitPawnSMGameActive();
          setPawnSMGameActiveMoving(PawnSMGameActiveMoving.stepMove);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStep(direction)))
        {
          exitPawnSMGameActiveUnmoved();
        // line 28 "../../../../../PawnStateMachine.ump"
          illegalMove();
          setPawnSMGameActiveUnmoved(PawnSMGameActiveUnmoved.checked);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean doJumpMove(MoveDirection direction,MoveDirection direction2)
  {
    boolean wasEventProcessed = false;
    
    PawnSMGameActiveUnmoved aPawnSMGameActiveUnmoved = pawnSMGameActiveUnmoved;
    switch (aPawnSMGameActiveUnmoved)
    {
      case checked:
        if (isLegalJump(direction,direction2))
        {
          exitPawnSMGameActive();
          setPawnSMGameActiveMoving(PawnSMGameActiveMoving.jumpMove);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalJump(direction,direction2)))
        {
          exitPawnSMGameActiveUnmoved();
        // line 29 "../../../../../PawnStateMachine.ump"
          illegalMove();
          setPawnSMGameActiveUnmoved(PawnSMGameActiveUnmoved.checked);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void exitPawnSM()
  {
    switch(pawnSM)
    {
      case gameActive:
        exitPawnSMGameActive();
        break;
    }
  }

  private void setPawnSM(PawnSM aPawnSM)
  {
    pawnSM = aPawnSM;

    // entry actions and do activities
    switch(pawnSM)
    {
      case gameActive:
        if (pawnSMGameActive == PawnSMGameActive.Null) { setPawnSMGameActive(PawnSMGameActive.unmoved); }
        break;
    }
  }

  private void exitPawnSMGameActive()
  {
    switch(pawnSMGameActive)
    {
      case unmoved:
        exitPawnSMGameActiveUnmoved();
        setPawnSMGameActive(PawnSMGameActive.Null);
        break;
      case moving:
        exitPawnSMGameActiveMoving();
        // line 42 "../../../../../PawnStateMachine.ump"
        completeTurn();
        setPawnSMGameActive(PawnSMGameActive.Null);
        break;
    }
  }

  private void setPawnSMGameActive(PawnSMGameActive aPawnSMGameActive)
  {
    pawnSMGameActive = aPawnSMGameActive;
    if (pawnSM != PawnSM.gameActive && aPawnSMGameActive != PawnSMGameActive.Null) { setPawnSM(PawnSM.gameActive); }

    // entry actions and do activities
    switch(pawnSMGameActive)
    {
      case unmoved:
        if (pawnSMGameActiveUnmoved == PawnSMGameActiveUnmoved.Null) { setPawnSMGameActiveUnmoved(PawnSMGameActiveUnmoved.unchecked); }
        break;
      case moving:
        if (pawnSMGameActiveMoving == PawnSMGameActiveMoving.Null) { setPawnSMGameActiveMoving(PawnSMGameActiveMoving.stepMove); }
        __autotransition16__();
        break;
    }
  }

  private void exitPawnSMGameActiveUnmoved()
  {
    switch(pawnSMGameActiveUnmoved)
    {
      case unchecked:
        setPawnSMGameActiveUnmoved(PawnSMGameActiveUnmoved.Null);
        break;
      case checked:
        setPawnSMGameActiveUnmoved(PawnSMGameActiveUnmoved.Null);
        break;
    }
  }

  private void setPawnSMGameActiveUnmoved(PawnSMGameActiveUnmoved aPawnSMGameActiveUnmoved)
  {
    pawnSMGameActiveUnmoved = aPawnSMGameActiveUnmoved;
    if (pawnSMGameActive != PawnSMGameActive.unmoved && aPawnSMGameActiveUnmoved != PawnSMGameActiveUnmoved.Null) { setPawnSMGameActive(PawnSMGameActive.unmoved); }

    // entry actions and do activities
    switch(pawnSMGameActiveUnmoved)
    {
      case unchecked:
        // line 20 "../../../../../PawnStateMachine.ump"
        generatePossibleMoves();
        __autotransition15__();
        break;
    }
  }

  private void exitPawnSMGameActiveMoving()
  {
    switch(pawnSMGameActiveMoving)
    {
      case stepMove:
        setPawnSMGameActiveMoving(PawnSMGameActiveMoving.Null);
        break;
      case jumpMove:
        setPawnSMGameActiveMoving(PawnSMGameActiveMoving.Null);
        break;
    }
  }

  private void setPawnSMGameActiveMoving(PawnSMGameActiveMoving aPawnSMGameActiveMoving)
  {
    pawnSMGameActiveMoving = aPawnSMGameActiveMoving;
    if (pawnSMGameActive != PawnSMGameActive.moving && aPawnSMGameActiveMoving != PawnSMGameActiveMoving.Null) { setPawnSMGameActive(PawnSMGameActive.moving); }
  }
  /* Code from template association_GetOne */
  public Game getCurrentGame()
  {
    return currentGame;
  }

  public boolean hasCurrentGame()
  {
    boolean has = currentGame != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }

  public boolean hasPlayer()
  {
    boolean has = player != null;
    return has;
  }
  /* Code from template association_GetMany */
  public Tile getPossibleMove(int index)
  {
    Tile aPossibleMove = PossibleMoves.get(index);
    return aPossibleMove;
  }

  public List<Tile> getPossibleMoves()
  {
    List<Tile> newPossibleMoves = Collections.unmodifiableList(PossibleMoves);
    return newPossibleMoves;
  }

  public int numberOfPossibleMoves()
  {
    int number = PossibleMoves.size();
    return number;
  }

  public boolean hasPossibleMoves()
  {
    boolean has = PossibleMoves.size() > 0;
    return has;
  }

  public int indexOfPossibleMove(Tile aPossibleMove)
  {
    int index = PossibleMoves.indexOf(aPossibleMove);
    return index;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setCurrentGame(Game aNewCurrentGame)
  {
    boolean wasSet = false;
    currentGame = aNewCurrentGame;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setPlayer(Player aNewPlayer)
  {
    boolean wasSet = false;
    player = aNewPlayer;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPossibleMoves()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addPossibleMove(Tile aPossibleMove)
  {
    boolean wasAdded = false;
    if (PossibleMoves.contains(aPossibleMove)) { return false; }
    PossibleMoves.add(aPossibleMove);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePossibleMove(Tile aPossibleMove)
  {
    boolean wasRemoved = false;
    if (PossibleMoves.contains(aPossibleMove))
    {
      PossibleMoves.remove(aPossibleMove);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPossibleMoveAt(Tile aPossibleMove, int index)
  {  
    boolean wasAdded = false;
    if(addPossibleMove(aPossibleMove))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPossibleMoves()) { index = numberOfPossibleMoves() - 1; }
      PossibleMoves.remove(aPossibleMove);
      PossibleMoves.add(index, aPossibleMove);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePossibleMoveAt(Tile aPossibleMove, int index)
  {
    boolean wasAdded = false;
    if(PossibleMoves.contains(aPossibleMove))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPossibleMoves()) { index = numberOfPossibleMoves() - 1; }
      PossibleMoves.remove(aPossibleMove);
      PossibleMoves.add(index, aPossibleMove);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPossibleMoveAt(aPossibleMove, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    currentGame = null;
    player = null;
    PossibleMoves.clear();
  }
  
  private void generatePossibleMoves()
  {
	  this.PossibleMoves.clear();
	  for (Tile t : getPossiblePawnMoves(getCurrentGame().getCurrentPosition().getPlayerToMove()))
		  this.PossibleMoves.add(t);
  }

  private boolean isLegalStep(MoveDirection d)
  {
	  
  }
  
	/**
	 * @author Gohar Saqib Fazal
	 * @return a set of Tiles that the player can move to
	 */
	public Set<Tile> getPossiblePawnMoves(Player player) {
		Tile tile = player.hasGameAsWhite() ?
				getCurrentGame().getCurrentPosition().getWhitePosition().getTile() :
				getCurrentGame().getCurrentPosition().getBlackPosition().getTile();
		
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
		
		return moves;
	}
	
	/**
	 * @author Gohar Saqib Fazal
	 * @param tile
	 * @param d
	 * @return the possible moves (tiles) that the player can move to
	 */
	public Tile direction(Tile tile, int d) {
		switch ((d + 4) % 4) {
		case 0:
			return tile.getRow() != 1 ? Controller.getTile(tile.getColumn(), tile.getRow() - 1) : null;
		case 1:
			return tile.getColumn() != 9 ? Controller.getTile(tile.getColumn() + 1, tile.getRow()) : null;
		case 2:
			return tile.getRow() != 9 ? Controller.getTile(tile.getColumn(), tile.getRow() + 1) : null;
		case 3:
			return tile.getColumn() != 1 ? Controller.getTile(tile.getColumn() - 1, tile.getRow()) : null;
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

	public boolean isBlockedDirection(Tile tile, int d) {
		switch ((d + 4) % 4) {
		case 0:
			return tile.getRow() == 1
					|| tile.getColumn() != 1 && Controller.isWallSet(tile.getColumn() - 1, tile.getRow() - 1, Direction.Horizontal)
					|| tile.getColumn() != 9 && Controller.isWallSet(tile.getColumn(), tile.getRow() - 1, Direction.Horizontal);
		case 1:
			return tile.getColumn() == 9
					|| tile.getRow() != 1 && Controller.isWallSet(tile.getColumn(), tile.getRow() - 1, Direction.Vertical)
					|| tile.getRow() != 9 && Controller.isWallSet(tile.getColumn(), tile.getRow(), Direction.Vertical);
		case 2:
			return tile.getRow() == 9
					|| tile.getColumn() != 1 && Controller.isWallSet(tile.getColumn() - 1, tile.getRow(), Direction.Horizontal)
					|| tile.getColumn() != 9 && Controller.isWallSet(tile.getColumn(), tile.getRow(), Direction.Horizontal);
		case 3:
			return tile.getColumn() == 1
					|| tile.getRow() != 1 && Controller.isWallSet(tile.getColumn() - 1, tile.getRow() - 1, Direction.Vertical)
					|| tile.getRow() != 9 && Controller.isWallSet(tile.getColumn() - 1, tile.getRow(), Direction.Vertical);
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
	public boolean hasPlayer(Tile tile) {
		return (QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition()
				.getTile() == tile
				|| QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition()
						.getTile() == tile);

	}
	
	/**
	 * @author Gohar Saqib Fazal
	 * @return boolean : identifies if player has won
	 * @param  Tile tile: is the tile at which player finds himself
	 * @param  Player for which we are validating win
	 */
	
	public boolean isWinner(Player player, Tile tile)
	{
		return (player.getDestination().getDirection().equals(Direction.Horizontal) ?
				tile.getColumn() :
				tile.getRow()) == player.getDestination().getTargetNumber();
	}
	/**
	 * @author Gohar Saqib Fazal
	 * @return int : refers to the length of  the given path
	 * @param  Player: player refers to the player concerned
	 */
	public int getShortestPathLength(Player player)
	{
		GamePosition current = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		Tile playerPosition = (player.hasGameAsWhite() ? current.getWhitePosition() : current.getBlackPosition()).getTile();
		
		if (isWinner(player, playerPosition))
			return 0;
		
		class Dijkstra { int distance = -1; boolean visited = false; }
		final HashMap<Tile, Dijkstra> tilesMap = new HashMap<>();
		
		for (Tile tile : QuoridorApplication.getQuoridor().getBoard().getTiles())
			tilesMap.put(tile, new Dijkstra());
		
		Set<Tile> toVisit = QuoridorApplication.psm.getPossiblePawnMoves(player);
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
}