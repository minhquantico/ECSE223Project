/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.controller;
import java.util.*;
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

  private boolean __autotransition6__()
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

  private boolean __autotransition5__()
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
        if (isLegalStepMove())
        {
          exitPawnSMGameActive();
          setPawnSMGameActiveMoving(PawnSMGameActiveMoving.stepMove);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalStepMove()))
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
        if (isLegalJumpMove())
        {
          exitPawnSMGameActive();
          setPawnSMGameActiveMoving(PawnSMGameActiveMoving.jumpMove);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalJumpMove()))
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
        __autotransition6__();
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
        getPossibleMoves();
        __autotransition5__();
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

}