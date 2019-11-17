/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.controller;
import java.util.List;
import java.util.ArrayList;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.*;
import java.sql.Time;

// line 7 "../../../../../PawnStateMachine.ump"
public class PawnBehaviour
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum MoveDirection { North, South, East, West, Null }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PawnBehaviour State Machines
  public enum PawnSM { gameActive, gameComplete }
  public enum PawnSMGameActiveLongitudinal { Null, Longitudinal }
  public enum PawnSMGameActiveLongitudinalLongitudinal { Null, Between, SouthBordernearBorder, SouthBorderOnBorder, NorthBordernearBorder, NorthBorderOnBorder }
  public enum PawnSMGameActiveLatitudinal { Null, Latitudinal }
  public enum PawnSMGameActiveLatitudinalLatitudinal { Null, EastBordernearBorder, EastBorderOnBorder, Between, WestBordernearBorder, WestBorderOnBorder }
  private PawnSM pawnSM;
  private PawnSMGameActiveLongitudinal pawnSMGameActiveLongitudinal;
  private PawnSMGameActiveLongitudinalLongitudinal pawnSMGameActiveLongitudinalLongitudinal;
  private PawnSMGameActiveLatitudinal pawnSMGameActiveLatitudinal;
  private PawnSMGameActiveLatitudinalLatitudinal pawnSMGameActiveLatitudinalLatitudinal;

  //PawnBehaviour Associations
  private Game currentGame;
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PawnBehaviour(Player aPlayer)
  {
    if (aPlayer == null || aPlayer.getPawnBehaviour() != null)
    {
      throw new RuntimeException("Unable to create PawnBehaviour due to aPlayer");
    }
    player = aPlayer;
    setPawnSMGameActiveLongitudinal(PawnSMGameActiveLongitudinal.Null);
    setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Null);
    setPawnSMGameActiveLatitudinal(PawnSMGameActiveLatitudinal.Null);
    setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Null);
    setPawnSM(PawnSM.gameActive);
  }

  public PawnBehaviour(Time aRemainingTimeForPlayer, User aUserForPlayer, Destination aDestinationForPlayer)
  {
    player = new Player(aRemainingTimeForPlayer, aUserForPlayer, aDestinationForPlayer, this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getPawnSMFullName()
  {
    String answer = pawnSM.toString();
    if (pawnSMGameActiveLongitudinal != PawnSMGameActiveLongitudinal.Null) { answer += "." + pawnSMGameActiveLongitudinal.toString(); }
    if (pawnSMGameActiveLongitudinalLongitudinal != PawnSMGameActiveLongitudinalLongitudinal.Null) { answer += "." + pawnSMGameActiveLongitudinalLongitudinal.toString(); }
    if (pawnSMGameActiveLatitudinal != PawnSMGameActiveLatitudinal.Null) { answer += "." + pawnSMGameActiveLatitudinal.toString(); }
    if (pawnSMGameActiveLatitudinalLatitudinal != PawnSMGameActiveLatitudinalLatitudinal.Null) { answer += "." + pawnSMGameActiveLatitudinalLatitudinal.toString(); }
    return answer;
  }

  public PawnSM getPawnSM()
  {
    return pawnSM;
  }

  public PawnSMGameActiveLongitudinal getPawnSMGameActiveLongitudinal()
  {
    return pawnSMGameActiveLongitudinal;
  }

  public PawnSMGameActiveLongitudinalLongitudinal getPawnSMGameActiveLongitudinalLongitudinal()
  {
    return pawnSMGameActiveLongitudinalLongitudinal;
  }

  public PawnSMGameActiveLatitudinal getPawnSMGameActiveLatitudinal()
  {
    return pawnSMGameActiveLatitudinal;
  }

  public PawnSMGameActiveLatitudinalLatitudinal getPawnSMGameActiveLatitudinalLatitudinal()
  {
    return pawnSMGameActiveLatitudinalLatitudinal;
  }

  public boolean gameCompleted()
  {
    boolean wasEventProcessed = false;
    
    PawnSM aPawnSM = pawnSM;
    switch (aPawnSM)
    {
      case gameActive:
        exitPawnSM();
        setPawnSM(PawnSM.gameComplete);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean stepMove(MoveDirection dir)
  {
    boolean wasEventProcessed = false;
    
    PawnSMGameActiveLongitudinalLongitudinal aPawnSMGameActiveLongitudinalLongitudinal = pawnSMGameActiveLongitudinalLongitudinal;
    PawnSMGameActiveLatitudinalLatitudinal aPawnSMGameActiveLatitudinalLatitudinal = pawnSMGameActiveLatitudinalLatitudinal;
    switch (aPawnSMGameActiveLongitudinalLongitudinal)
    {
      case Between:
        if (isLegalStep(dir)&&dir.equals(MoveDirection.South)&&!(stepNearBorder(dir))&&!(stepOnBorder(dir)))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
        // line 30 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()+1, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(dir)&&dir.equals(MoveDirection.North)&&!(stepNearBorder(dir))&&!(stepOnBorder(dir)))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
        // line 32 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()-1, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(dir)&&dir.equals(MoveDirection.South)&&stepNearBorder(dir))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
        // line 58 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()+1, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.SouthBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(dir)&&dir.equals(MoveDirection.North)&&stepNearBorder(dir))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
        // line 60 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()-1, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.NorthBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case SouthBordernearBorder:
        if (isLegalStep(dir)&&dir.equals(MoveDirection.South))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
        // line 87 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()+1, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.SouthBorderOnBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(dir)&&dir.equals(MoveDirection.North))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
        // line 89 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()-1, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        break;
      case SouthBorderOnBorder:
        if (isLegalStep(dir)&&dir.equals(MoveDirection.North))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
        // line 111 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()-1, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.SouthBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case NorthBordernearBorder:
        if (isLegalStep(dir)&&dir.equals(MoveDirection.South))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
        // line 140 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()+1, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(dir)&&dir.equals(MoveDirection.North))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
        // line 142 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()-1, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.NorthBorderOnBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case NorthBorderOnBorder:
        if (isLegalStep(dir)&&dir.equals(MoveDirection.South))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
        // line 161 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()+1, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.NorthBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aPawnSMGameActiveLatitudinalLatitudinal)
    {
      case EastBordernearBorder:
        if (isLegalStep(dir)&&dir.equals(MoveDirection.East))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 197 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()+1);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.EastBorderOnBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(dir)&&dir.equals(MoveDirection.West))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 199 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()-1);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        break;
      case EastBorderOnBorder:
        if (isLegalStep(dir)&&dir.equals(MoveDirection.West))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 221 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()-1);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.EastBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case Between:
        if (isLegalStep(dir)&&dir.equals(MoveDirection.East)&&!(stepNearBorder(dir))&&!(stepOnBorder(dir)))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 245 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()+1);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(dir)&&dir.equals(MoveDirection.West)&&!(stepNearBorder(dir))&&!(stepOnBorder(dir)))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 247 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()-1);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(dir)&&dir.equals(MoveDirection.East)&&stepNearBorder(dir))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 273 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()+1);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.EastBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(dir)&&dir.equals(MoveDirection.West)&&stepNearBorder(dir))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 275 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()-1);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.WestBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case WestBordernearBorder:
        if (isLegalStep(dir)&&dir.equals(MoveDirection.East))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 321 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()+1);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(dir)&&dir.equals(MoveDirection.West))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 323 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()-1);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.WestBorderOnBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case WestBorderOnBorder:
        if (isLegalStep(dir)&&dir.equals(MoveDirection.East))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 362 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()+1);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.WestBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean jumpMove(MoveDirection dir1,MoveDirection dir2)
  {
    boolean wasEventProcessed = false;
    
    PawnSMGameActiveLongitudinalLongitudinal aPawnSMGameActiveLongitudinalLongitudinal = pawnSMGameActiveLongitudinalLongitudinal;
    PawnSMGameActiveLatitudinalLatitudinal aPawnSMGameActiveLatitudinalLatitudinal = pawnSMGameActiveLatitudinalLatitudinal;
    switch (aPawnSMGameActiveLongitudinalLongitudinal)
    {
      case Between:
        if (dir1.equals(MoveDirection.North)&&dir2.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2)&&!(jumpNearBorder(dir1,dir2,1))&&!(jumpOnBorder(dir1,dir2,1)))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
        // line 38 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()-2, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.South)&&dir2.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2)&&!(jumpNearBorder(dir1,dir2,1))&&!(jumpOnBorder(dir1,dir2,1)))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
        // line 40 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()+2, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.North)&&(dir2.equals(MoveDirection.East)||dir2.equals(MoveDirection.West))&&isLegalJump(dir1,dir2)&&!(jumpNearBorder(dir1,dir2,1))&&!(jumpOnBorder(dir1,dir2,1)))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.South)&&(dir2.equals(MoveDirection.West)||dir2.equals(MoveDirection.East))&&isLegalJump(dir1,dir2)&&!(jumpNearBorder(dir1,dir2,1))&&!(jumpOnBorder(dir1,dir2,1)))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.North)&&dir2.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2)&&jumpNearBorder(dir1,dir2,1))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.NorthBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.South)&&dir2.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2)&&jumpNearBorder(dir1,dir2,1))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.SouthBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.North)&&dir2.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2)&&jumpOnBorder(dir1,dir2,1))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.NorthBorderOnBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.South)&&dir2.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2)&&jumpOnBorder(dir1,dir2,1))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.SouthBorderOnBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.North)&&(dir2.equals(MoveDirection.East)||dir2.equals(MoveDirection.West))&&isLegalJump(dir1,dir2)&&jumpNearBorder(dir1,dir2,1))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.NorthBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.South)&&(dir2.equals(MoveDirection.West)||dir2.equals(MoveDirection.East))&&isLegalJump(dir1,dir2)&&jumpNearBorder(dir1,dir2,1))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.SouthBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case SouthBordernearBorder:
        if (dir1.equals(MoveDirection.North)&&dir2.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.North)&&(dir2.equals(MoveDirection.East)||dir2.equals(MoveDirection.West))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.South)&&(dir2.equals(MoveDirection.West)||dir2.equals(MoveDirection.East))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.SouthBorderOnBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case SouthBorderOnBorder:
        if (dir1.equals(MoveDirection.North)&&dir2.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
        // line 116 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()-2, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.North)&&(dir2.equals(MoveDirection.East)||dir2.equals(MoveDirection.West))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.SouthBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case NorthBordernearBorder:
        if (dir1.equals(MoveDirection.South)&&dir2.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.North)&&(dir2.equals(MoveDirection.East)||dir2.equals(MoveDirection.West))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.NorthBorderOnBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.South)&&(dir2.equals(MoveDirection.West)||dir2.equals(MoveDirection.East))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        break;
      case NorthBorderOnBorder:
        if (dir1.equals(MoveDirection.South)&&dir2.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
        // line 167 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()+2, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.South)&&(dir2.equals(MoveDirection.West)||dir2.equals(MoveDirection.East))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.NorthBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aPawnSMGameActiveLatitudinalLatitudinal)
    {
      case EastBordernearBorder:
        if (dir1.equals(MoveDirection.West)&&dir2.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.West)&&(dir2.equals(MoveDirection.North)||dir2.equals(MoveDirection.South))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.East)&&(dir2.equals(MoveDirection.North)||dir2.equals(MoveDirection.South))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.EastBorderOnBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case EastBorderOnBorder:
        if (dir1.equals(MoveDirection.West)&&dir2.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 226 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()-2, getCurrentColumn());
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.West)&&(dir1.equals(MoveDirection.North)||dir1.equals(MoveDirection.South))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.EastBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case Between:
        if (dir2.equals(MoveDirection.West)&&dir1.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2)&&!(jumpNearBorder(dir1,dir2,2))&&!(jumpOnBorder(dir1,dir2,2)))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 253 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()-2, getCurrentColumn());
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.East)&&dir1.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2)&&!(jumpNearBorder(dir1,dir2,2))&&!(jumpOnBorder(dir1,dir2,2)))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 255 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()+2, getCurrentColumn());
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.West)&&(dir1.equals(MoveDirection.North)||dir1.equals(MoveDirection.South))&&isLegalJump(dir1,dir2)&&!(jumpNearBorder(dir1,dir2,1))&&!(jumpOnBorder(dir1,dir2,2)))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.East)&&(dir1.equals(MoveDirection.North)||dir1.equals(MoveDirection.South))&&isLegalJump(dir1,dir2)&&!(jumpNearBorder(dir1,dir2,1))&&!(jumpOnBorder(dir1,dir2,2)))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.West)&&dir1.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2)&&jumpNearBorder(dir1,dir2,2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.WestBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.East)&&dir1.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2)&&jumpNearBorder(dir1,dir2,2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.EastBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.West)&&dir1.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2)&&jumpOnBorder(dir1,dir2,2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.WestBorderOnBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.East)&&dir1.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2)&&jumpOnBorder(dir1,dir2,2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.EastBorderOnBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.West)&&(dir1.equals(MoveDirection.North)||dir1.equals(MoveDirection.South))&&isLegalJump(dir1,dir2)&&jumpNearBorder(dir1,dir2,1))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 290 "../../../../../PawnStateMachine.ump"
          if(dir1==MoveDirection.South){
                    move(getCurrentRow()+1,getCurrentColumn()-1);
            }else{
          
            move(getCurrentRow()-1, getCurrentColumn()-1);    
            }
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.WestBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.East)&&(dir1.equals(MoveDirection.North)||dir1.equals(MoveDirection.South))&&isLegalJump(dir1,dir2)&&jumpNearBorder(dir1,dir2,1))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 299 "../../../../../PawnStateMachine.ump"
          if(dir1==MoveDirection.South){
                    move(getCurrentRow()+1,getCurrentColumn()+1);
            }else{
          
            move(getCurrentRow()-1, getCurrentColumn()+1);    
            }
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.EastBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case WestBordernearBorder:
        if (dir1.equals(MoveDirection.East)&&dir2.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.West)&&(dir1.equals(MoveDirection.North)||dir1.equals(MoveDirection.South))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 333 "../../../../../PawnStateMachine.ump"
          if(dir1==MoveDirection.South){
                    move(getCurrentRow()+1,getCurrentColumn()-1);
            }else{
          
            
            move(getCurrentRow()-1, getCurrentColumn()-1);
              
            }
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.WestBorderOnBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.East)&&(dir1.equals(MoveDirection.North)||dir1.equals(MoveDirection.South))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 346 "../../../../../PawnStateMachine.ump"
          if(dir1==MoveDirection.South){
                    move(getCurrentRow()+1,getCurrentColumn()+1);
            }else{
          
            
            move(getCurrentRow()-1, getCurrentColumn()+1);
              
            }
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        break;
      case WestBorderOnBorder:
        if (dir1.equals(MoveDirection.East)&&dir2.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 368 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()+2, getCurrentColumn());
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.East)&&(dir1.equals(MoveDirection.North)||dir1.equals(MoveDirection.South))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 373 "../../../../../PawnStateMachine.ump"
          if(dir1==MoveDirection.South){
                    move(getCurrentRow()+1,getCurrentColumn()+1);
            }else{
          
            
            move(getCurrentRow()-1, getCurrentColumn()+1);
              
            }
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.WestBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean initialize()
  {
    boolean wasEventProcessed = false;
    
    PawnSMGameActiveLatitudinal aPawnSMGameActiveLatitudinal = pawnSMGameActiveLatitudinal;
    switch (aPawnSMGameActiveLatitudinal)
    {
      case Latitudinal:
        if (hasGameAsWhite())
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.WestBorderOnBorder);
          wasEventProcessed = true;
          break;
        }
        if (hasGameAsBlack())
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.EastBorderOnBorder);
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
        exitPawnSMGameActiveLongitudinal();
        exitPawnSMGameActiveLatitudinal();
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
        if (pawnSMGameActiveLongitudinal == PawnSMGameActiveLongitudinal.Null) { setPawnSMGameActiveLongitudinal(PawnSMGameActiveLongitudinal.Longitudinal); }
        if (pawnSMGameActiveLatitudinal == PawnSMGameActiveLatitudinal.Null) { setPawnSMGameActiveLatitudinal(PawnSMGameActiveLatitudinal.Latitudinal); }
        break;
      case gameComplete:
        // line 395 "../../../../../PawnStateMachine.ump"
        displayResults();
        break;
    }
  }

  private void exitPawnSMGameActiveLongitudinal()
  {
    switch(pawnSMGameActiveLongitudinal)
    {
      case Longitudinal:
        exitPawnSMGameActiveLongitudinalLongitudinal();
        setPawnSMGameActiveLongitudinal(PawnSMGameActiveLongitudinal.Null);
        break;
    }
  }

  private void setPawnSMGameActiveLongitudinal(PawnSMGameActiveLongitudinal aPawnSMGameActiveLongitudinal)
  {
    pawnSMGameActiveLongitudinal = aPawnSMGameActiveLongitudinal;
    if (pawnSM != PawnSM.gameActive && aPawnSMGameActiveLongitudinal != PawnSMGameActiveLongitudinal.Null) { setPawnSM(PawnSM.gameActive); }

    // entry actions and do activities
    switch(pawnSMGameActiveLongitudinal)
    {
      case Longitudinal:
        if (pawnSMGameActiveLongitudinalLongitudinal == PawnSMGameActiveLongitudinalLongitudinal.Null) { setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between); }
        break;
    }
  }

  private void exitPawnSMGameActiveLongitudinalLongitudinal()
  {
    switch(pawnSMGameActiveLongitudinalLongitudinal)
    {
      case Between:
        setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Null);
        break;
      case SouthBordernearBorder:
        setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Null);
        break;
      case SouthBorderOnBorder:
        setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Null);
        break;
      case NorthBordernearBorder:
        setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Null);
        break;
      case NorthBorderOnBorder:
        setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Null);
        break;
    }
  }

  private void setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal aPawnSMGameActiveLongitudinalLongitudinal)
  {
    pawnSMGameActiveLongitudinalLongitudinal = aPawnSMGameActiveLongitudinalLongitudinal;
    if (pawnSMGameActiveLongitudinal != PawnSMGameActiveLongitudinal.Longitudinal && aPawnSMGameActiveLongitudinalLongitudinal != PawnSMGameActiveLongitudinalLongitudinal.Null) { setPawnSMGameActiveLongitudinal(PawnSMGameActiveLongitudinal.Longitudinal); }

    // entry actions and do activities
    switch(pawnSMGameActiveLongitudinalLongitudinal)
    {
      case SouthBorderOnBorder:
        // line 107 "../../../../../PawnStateMachine.ump"
        testVictory();
        break;
      case NorthBorderOnBorder:
        // line 159 "../../../../../PawnStateMachine.ump"
        testVictory();
        break;
    }
  }

  private void exitPawnSMGameActiveLatitudinal()
  {
    switch(pawnSMGameActiveLatitudinal)
    {
      case Latitudinal:
        exitPawnSMGameActiveLatitudinalLatitudinal();
        setPawnSMGameActiveLatitudinal(PawnSMGameActiveLatitudinal.Null);
        break;
    }
  }

  private void setPawnSMGameActiveLatitudinal(PawnSMGameActiveLatitudinal aPawnSMGameActiveLatitudinal)
  {
    pawnSMGameActiveLatitudinal = aPawnSMGameActiveLatitudinal;
    if (pawnSM != PawnSM.gameActive && aPawnSMGameActiveLatitudinal != PawnSMGameActiveLatitudinal.Null) { setPawnSM(PawnSM.gameActive); }

    // entry actions and do activities
    switch(pawnSMGameActiveLatitudinal)
    {
      case Latitudinal:
        if (pawnSMGameActiveLatitudinalLatitudinal == PawnSMGameActiveLatitudinalLatitudinal.Null) { setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.EastBordernearBorder); }
        break;
    }
  }

  private void exitPawnSMGameActiveLatitudinalLatitudinal()
  {
    switch(pawnSMGameActiveLatitudinalLatitudinal)
    {
      case EastBordernearBorder:
        setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Null);
        break;
      case EastBorderOnBorder:
        setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Null);
        break;
      case Between:
        setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Null);
        break;
      case WestBordernearBorder:
        setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Null);
        break;
      case WestBorderOnBorder:
        setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Null);
        break;
    }
  }

  private void setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal aPawnSMGameActiveLatitudinalLatitudinal)
  {
    pawnSMGameActiveLatitudinalLatitudinal = aPawnSMGameActiveLatitudinalLatitudinal;
    if (pawnSMGameActiveLatitudinal != PawnSMGameActiveLatitudinal.Latitudinal && aPawnSMGameActiveLatitudinalLatitudinal != PawnSMGameActiveLatitudinalLatitudinal.Null) { setPawnSMGameActiveLatitudinal(PawnSMGameActiveLatitudinal.Latitudinal); }

    // entry actions and do activities
    switch(pawnSMGameActiveLatitudinalLatitudinal)
    {
      case EastBorderOnBorder:
        // line 217 "../../../../../PawnStateMachine.ump"
        testVictory();
        break;
      case WestBorderOnBorder:
        // line 360 "../../../../../PawnStateMachine.ump"
        testVictory();
        break;
    }
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
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setCurrentGame(Game aNewCurrentGame)
  {
    boolean wasSet = false;
    currentGame = aNewCurrentGame;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    currentGame = null;
    Player existingPlayer = player;
    player = null;
    if (existingPlayer != null)
    {
      existingPlayer.delete();
    }
  }
  private int getCurrentRow()
  {
	  if(player.hasGameAsWhite())
		  return QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getRow();
	  else
		  return QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getRow();
	  
  }
  
  private int getCurrentColumn()
  {
	  if(player.hasGameAsWhite())
		  return QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getColumn();
	  else
		  return QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getColumn();
  }
  
  private boolean stepNearBorder(MoveDirection dir)
  {
	  return isNearBorder(Controller.direction(Controller.getTile(getCurrentColumn(), getCurrentRow()),dirToInt(dir)), MoveDirection.Null);
  }
  
  // ??
  private boolean stepOnBorder(MoveDirection dir)
  {
	  return isOnBorder(Controller.direction(Controller.getTile(getCurrentColumn(), getCurrentRow()),dirToInt(dir)), MoveDirection.Null);
	  
  }
  
  private boolean jumpNearBorder(MoveDirection dir, MoveDirection dir2, int border)
  {
	  return isNearBorder(getJumpMoveTile(dir, dir2), border == 1 ? dir : dir2);
  }
  
  private Tile getJumpMoveTile(MoveDirection dir, MoveDirection dir2) {
	  Tile jumpMoveTile = Controller.getTile(getCurrentColumn(), getCurrentRow());
	  jumpMoveTile = Controller.direction(jumpMoveTile, dirToInt(dir == MoveDirection.Null ? dir2 : dir));
	  jumpMoveTile = Controller.direction(jumpMoveTile, dirToInt(dir2 == MoveDirection.Null ? dir : dir2));
	  return jumpMoveTile;
  }
  
  // ??
  private boolean jumpOnBorder(MoveDirection dir, MoveDirection dir2, int border)
  {
	  return isOnBorder(getJumpMoveTile(dir, dir2), border == 1 ? dir : dir2);
  }
  
  private boolean isLegalStep(MoveDirection dir)
  {
	  try
	  {
		  return Controller.initPosValidation(
				  Controller.direction(
				  Controller.getTile(getCurrentColumn(), getCurrentRow()),
				  dirToInt(dir)));
	  }
	  catch (NullPointerException ex) { return false; }
  }
  
  private boolean isLegalJump(MoveDirection dir, MoveDirection dir2)
  {
	  try { return Controller.initPosValidation(getJumpMoveTile(dir, dir2)); }
	  catch (NullPointerException ex) { return false; }
  }
  
  private void move(int row, int col)
  {
	  Controller.doPawnMove(col, row);
  }
  
  private boolean hasGameAsWhite() { return player.hasGameAsWhite(); }
  private boolean hasGameAsBlack() { return player.hasGameAsBlack(); }
  
  private boolean testVictory()
  {
	  Tile tile = Controller.getTile(getCurrentColumn(), getCurrentRow());
	  if(player.hasGameAsWhite()) {
		   if(tile.getColumn() == 9) {
		   return true;
		   }
		   else {
		   return false;
		   }
	  }
	  
	  else if(player.hasGameAsBlack()) {
		  if(tile.getColumn() == 1) {
			  return true;
		  }
		  else {
			  return false;
		  }
	  }
	  else return false;
	  
  }
  
  private void displayResults()
  {
	  throw new UnsupportedOperationException("Implement me!");
  }
  
  private int dirToInt(MoveDirection dir) {
	  switch(dir) {
	  	case North: return 0;
	  	case East: return 1; 
	  	case South: return 2; 
	  	case West: return 3;
	  }
	  return -1;
  }
  
  private boolean isNearBorder(Tile tile, MoveDirection d) {
	  if (d == MoveDirection.Null)
		  return (tile.getRow() == 2 || tile.getRow() == 8 || tile.getColumn() == 2 || tile.getColumn() == 8 );
	  else 
		  switch(d) {
		  	case North: 
		  		return(tile.getRow() == 2);
		  	case East: 
		  		return(tile.getColumn() == 8);
		  	case South: 
		  		return(tile.getRow() == 8);
		  	case West: 
		  		return(tile.getColumn() == 2);
		  }
	  return false;
	  
  }
  
  private boolean isOnBorder(Tile tile, MoveDirection d) {
	  if (d == MoveDirection.Null)
		  return (tile.getRow() == 1 || tile.getRow() == 9 || tile.getColumn() == 1 || tile.getColumn() == 9 );
	  else 
		  switch(d) {
		  	case North: 
		  		return(tile.getRow() == 1);
		  	case East: 
		  		return(tile.getColumn() == 9);
		  	case South: 
		  		return(tile.getRow() == 9);
		  	case West: 
		  		return(tile.getColumn() == 1);
		  }
	  return false;
	  
  } 
  
//  public void setSMTest(int row, int column){
//	//assuming it is already in active state
//
//	//longitudinal
//	if(row==8){
//	//—> near border South
//
//	setPawnSMGameActiveLongitudinalLongitudinalSouthBorder(PawnSMGameActiveLongitudinalLongitudinalSouthBorder.nearBorder);
//	
//	}else if(row==2){
//
//	//—>near Border North
//	setPawnSMGameActiveLongitudinalLongitudinalNorthBorder(PawnSMGameActiveLongitudinalLongitudinalNorthBorder.nearBorder);
//
//	}else if(row==1){
//
//	//—> On border North
//
//	setPawnSMGameActiveLongitudinalLongitudinalNorthBorder(PawnSMGameActiveLongitudinalLongitudinalNorthBorder.OnBorder);
//
//	}else if(row==9){
//
//	//—> On border South
//
//	setPawnSMGameActiveLongitudinalLongitudinalSouthBorder(PawnSMGameActiveLongitudinalLongitudinalSouthBorder.OnBorder);
//
//	}else{
//	//—>is In Longitudinal.Between;
//
//	setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
//
//	}
//
//
//
//	//latitudinal
//
//	if(column==8){
//	//—> near border East
//	setPawnSMGameActiveLatitudinalLatitudinalEastBorder(PawnSMGameActiveLatitudinalLatitudinalEastBorder.nearBorder);
//
//
//	}else if(column==2){
//
//	//—>near Border West
//
//	setPawnSMGameActiveLatitudinalLatitudinalWestBorder(PawnSMGameActiveLatitudinalLatitudinalWestBorder.nearBorder);
//
//
//	}else if(column==1){
//
//	//—> On border West
//
//	setPawnSMGameActiveLatitudinalLatitudinalWestBorder(PawnSMGameActiveLatitudinalLatitudinalWestBorder.OnBorder);
//
//	}else if(column==9){
//
//	//—> On border East
//	setPawnSMGameActiveLatitudinalLatitudinalEastBorder(PawnSMGameActiveLatitudinalLatitudinalEastBorder.OnBorder);
//
//
//	}else{
//	//—>is In Latitudinal.Between;
//
//	setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
//
//	}
//
//
//	}
}