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

  public enum MoveDirection { North, South, East, West }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PawnBehaviour State Machines
  public enum PawnSM { gameActive, gameComplete }
  public enum PawnSMGameActiveLongitudinal { Null, Longitudinal }
  public enum PawnSMGameActiveLongitudinalLongitudinal { Null, Between, SouthBorder, NorthBorder }
  public enum PawnSMGameActiveLongitudinalLongitudinalSouthBorder { Null, nearBorder, OnBorder }
  public enum PawnSMGameActiveLongitudinalLongitudinalNorthBorder { Null, nearBorder, OnBorder }
  public enum PawnSMGameActiveLatitudinal { Null, Latitudinal }
  public enum PawnSMGameActiveLatitudinalLatitudinal { Null, EastBorder, Between, WestBorder }
  public enum PawnSMGameActiveLatitudinalLatitudinalEastBorder { Null, nearBorder, OnBorder }
  public enum PawnSMGameActiveLatitudinalLatitudinalWestBorder { Null, nearBorder, OnBorder }
  private PawnSM pawnSM;
  private PawnSMGameActiveLongitudinal pawnSMGameActiveLongitudinal;
  private PawnSMGameActiveLongitudinalLongitudinal pawnSMGameActiveLongitudinalLongitudinal;
  private PawnSMGameActiveLongitudinalLongitudinalSouthBorder pawnSMGameActiveLongitudinalLongitudinalSouthBorder;
  private PawnSMGameActiveLongitudinalLongitudinalNorthBorder pawnSMGameActiveLongitudinalLongitudinalNorthBorder;
  private PawnSMGameActiveLatitudinal pawnSMGameActiveLatitudinal;
  private PawnSMGameActiveLatitudinalLatitudinal pawnSMGameActiveLatitudinalLatitudinal;
  private PawnSMGameActiveLatitudinalLatitudinalEastBorder pawnSMGameActiveLatitudinalLatitudinalEastBorder;
  private PawnSMGameActiveLatitudinalLatitudinalWestBorder pawnSMGameActiveLatitudinalLatitudinalWestBorder;

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
    setPawnSMGameActiveLongitudinalLongitudinalSouthBorder(PawnSMGameActiveLongitudinalLongitudinalSouthBorder.Null);
    setPawnSMGameActiveLongitudinalLongitudinalNorthBorder(PawnSMGameActiveLongitudinalLongitudinalNorthBorder.Null);
    setPawnSMGameActiveLatitudinal(PawnSMGameActiveLatitudinal.Null);
    setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Null);
    setPawnSMGameActiveLatitudinalLatitudinalEastBorder(PawnSMGameActiveLatitudinalLatitudinalEastBorder.Null);
    setPawnSMGameActiveLatitudinalLatitudinalWestBorder(PawnSMGameActiveLatitudinalLatitudinalWestBorder.Null);
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
    if (pawnSMGameActiveLongitudinalLongitudinalSouthBorder != PawnSMGameActiveLongitudinalLongitudinalSouthBorder.Null) { answer += "." + pawnSMGameActiveLongitudinalLongitudinalSouthBorder.toString(); }
    if (pawnSMGameActiveLongitudinalLongitudinalNorthBorder != PawnSMGameActiveLongitudinalLongitudinalNorthBorder.Null) { answer += "." + pawnSMGameActiveLongitudinalLongitudinalNorthBorder.toString(); }
    if (pawnSMGameActiveLatitudinal != PawnSMGameActiveLatitudinal.Null) { answer += "." + pawnSMGameActiveLatitudinal.toString(); }
    if (pawnSMGameActiveLatitudinalLatitudinal != PawnSMGameActiveLatitudinalLatitudinal.Null) { answer += "." + pawnSMGameActiveLatitudinalLatitudinal.toString(); }
    if (pawnSMGameActiveLatitudinalLatitudinalEastBorder != PawnSMGameActiveLatitudinalLatitudinalEastBorder.Null) { answer += "." + pawnSMGameActiveLatitudinalLatitudinalEastBorder.toString(); }
    if (pawnSMGameActiveLatitudinalLatitudinalWestBorder != PawnSMGameActiveLatitudinalLatitudinalWestBorder.Null) { answer += "." + pawnSMGameActiveLatitudinalLatitudinalWestBorder.toString(); }
    if (pawnSMGameActiveLongitudinalLongitudinalSouthBorder != PawnSMGameActiveLongitudinalLongitudinalSouthBorder.Null) { answer += "." + pawnSMGameActiveLongitudinalLongitudinalSouthBorder.toString(); }
    if (pawnSMGameActiveLongitudinalLongitudinalNorthBorder != PawnSMGameActiveLongitudinalLongitudinalNorthBorder.Null) { answer += "." + pawnSMGameActiveLongitudinalLongitudinalNorthBorder.toString(); }
    if (pawnSMGameActiveLatitudinalLatitudinalEastBorder != PawnSMGameActiveLatitudinalLatitudinalEastBorder.Null) { answer += "." + pawnSMGameActiveLatitudinalLatitudinalEastBorder.toString(); }
    if (pawnSMGameActiveLatitudinalLatitudinalWestBorder != PawnSMGameActiveLatitudinalLatitudinalWestBorder.Null) { answer += "." + pawnSMGameActiveLatitudinalLatitudinalWestBorder.toString(); }
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

  public PawnSMGameActiveLongitudinalLongitudinalSouthBorder getPawnSMGameActiveLongitudinalLongitudinalSouthBorder()
  {
    return pawnSMGameActiveLongitudinalLongitudinalSouthBorder;
  }

  public PawnSMGameActiveLongitudinalLongitudinalNorthBorder getPawnSMGameActiveLongitudinalLongitudinalNorthBorder()
  {
    return pawnSMGameActiveLongitudinalLongitudinalNorthBorder;
  }

  public PawnSMGameActiveLatitudinal getPawnSMGameActiveLatitudinal()
  {
    return pawnSMGameActiveLatitudinal;
  }

  public PawnSMGameActiveLatitudinalLatitudinal getPawnSMGameActiveLatitudinalLatitudinal()
  {
    return pawnSMGameActiveLatitudinalLatitudinal;
  }

  public PawnSMGameActiveLatitudinalLatitudinalEastBorder getPawnSMGameActiveLatitudinalLatitudinalEastBorder()
  {
    return pawnSMGameActiveLatitudinalLatitudinalEastBorder;
  }

  public PawnSMGameActiveLatitudinalLatitudinalWestBorder getPawnSMGameActiveLatitudinalLatitudinalWestBorder()
  {
    return pawnSMGameActiveLatitudinalLatitudinalWestBorder;
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
    PawnSMGameActiveLongitudinalLongitudinalSouthBorder aPawnSMGameActiveLongitudinalLongitudinalSouthBorder = pawnSMGameActiveLongitudinalLongitudinalSouthBorder;
    PawnSMGameActiveLongitudinalLongitudinalNorthBorder aPawnSMGameActiveLongitudinalLongitudinalNorthBorder = pawnSMGameActiveLongitudinalLongitudinalNorthBorder;
    PawnSMGameActiveLatitudinalLatitudinal aPawnSMGameActiveLatitudinalLatitudinal = pawnSMGameActiveLatitudinalLatitudinal;
    PawnSMGameActiveLatitudinalLatitudinalEastBorder aPawnSMGameActiveLatitudinalLatitudinalEastBorder = pawnSMGameActiveLatitudinalLatitudinalEastBorder;
    PawnSMGameActiveLatitudinalLatitudinalWestBorder aPawnSMGameActiveLatitudinalLatitudinalWestBorder = pawnSMGameActiveLatitudinalLatitudinalWestBorder;
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
          setPawnSMGameActiveLongitudinalLongitudinalSouthBorder(PawnSMGameActiveLongitudinalLongitudinalSouthBorder.nearBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(dir)&&dir.equals(MoveDirection.North)&&stepNearBorder(dir))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
        // line 60 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()-1, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinalNorthBorder(PawnSMGameActiveLongitudinalLongitudinalNorthBorder.nearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aPawnSMGameActiveLongitudinalLongitudinalSouthBorder)
    {
      case nearBorder:
        if (isLegalStep(dir)&&dir.equals(MoveDirection.South))
        {
          exitPawnSMGameActiveLongitudinalLongitudinalSouthBorder();
        // line 87 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()+1, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinalSouthBorder(PawnSMGameActiveLongitudinalLongitudinalSouthBorder.OnBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(dir)&&dir.equals(MoveDirection.North))
        {
          exitPawnSMGameActiveLongitudinal();
        // line 89 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()-1, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        break;
      case OnBorder:
        if (isLegalStep(dir)&&dir.equals(MoveDirection.North))
        {
          exitPawnSMGameActiveLongitudinalLongitudinalSouthBorder();
        // line 111 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()-1, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinalSouthBorder(PawnSMGameActiveLongitudinalLongitudinalSouthBorder.nearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aPawnSMGameActiveLongitudinalLongitudinalNorthBorder)
    {
      case nearBorder:
        if (isLegalStep(dir)&&dir.equals(MoveDirection.South))
        {
          exitPawnSMGameActiveLongitudinal();
        // line 140 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()+1, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(dir)&&dir.equals(MoveDirection.North))
        {
          exitPawnSMGameActiveLongitudinalLongitudinalNorthBorder();
        // line 142 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()-1, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinalNorthBorder(PawnSMGameActiveLongitudinalLongitudinalNorthBorder.OnBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case OnBorder:
        if (isLegalStep(dir)&&dir.equals(MoveDirection.South))
        {
          exitPawnSMGameActiveLongitudinalLongitudinalNorthBorder();
        // line 161 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()+1, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinalNorthBorder(PawnSMGameActiveLongitudinalLongitudinalNorthBorder.nearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aPawnSMGameActiveLatitudinalLatitudinal)
    {
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
          setPawnSMGameActiveLatitudinalLatitudinalEastBorder(PawnSMGameActiveLatitudinalLatitudinalEastBorder.nearBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(dir)&&dir.equals(MoveDirection.West)&&stepNearBorder(dir))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 275 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()-1);
          setPawnSMGameActiveLatitudinalLatitudinalWestBorder(PawnSMGameActiveLatitudinalLatitudinalWestBorder.nearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aPawnSMGameActiveLatitudinalLatitudinalEastBorder)
    {
      case nearBorder:
        if (isLegalStep(dir)&&dir.equals(MoveDirection.East))
        {
          exitPawnSMGameActiveLatitudinalLatitudinalEastBorder();
        // line 197 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()+1);
          setPawnSMGameActiveLatitudinalLatitudinalEastBorder(PawnSMGameActiveLatitudinalLatitudinalEastBorder.OnBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(dir)&&dir.equals(MoveDirection.West))
        {
          exitPawnSMGameActiveLongitudinal();
        // line 199 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()-1);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        break;
      case OnBorder:
        if (isLegalStep(dir)&&dir.equals(MoveDirection.West))
        {
          exitPawnSMGameActiveLatitudinalLatitudinalEastBorder();
        // line 221 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()-1);
          setPawnSMGameActiveLatitudinalLatitudinalEastBorder(PawnSMGameActiveLatitudinalLatitudinalEastBorder.nearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aPawnSMGameActiveLatitudinalLatitudinalWestBorder)
    {
      case nearBorder:
        if (isLegalStep(dir)&&dir.equals(MoveDirection.East))
        {
          exitPawnSMGameActiveLongitudinal();
        // line 321 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()+1);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(dir)&&dir.equals(MoveDirection.West))
        {
          exitPawnSMGameActiveLatitudinalLatitudinalWestBorder();
        // line 323 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()-1);
          setPawnSMGameActiveLatitudinalLatitudinalWestBorder(PawnSMGameActiveLatitudinalLatitudinalWestBorder.OnBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case OnBorder:
        if (isLegalStep(dir)&&dir.equals(MoveDirection.East))
        {
          exitPawnSMGameActiveLatitudinalLatitudinalWestBorder();
        // line 362 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()+1);
          setPawnSMGameActiveLatitudinalLatitudinalWestBorder(PawnSMGameActiveLatitudinalLatitudinalWestBorder.nearBorder);
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
    PawnSMGameActiveLongitudinalLongitudinalSouthBorder aPawnSMGameActiveLongitudinalLongitudinalSouthBorder = pawnSMGameActiveLongitudinalLongitudinalSouthBorder;
    PawnSMGameActiveLongitudinalLongitudinalNorthBorder aPawnSMGameActiveLongitudinalLongitudinalNorthBorder = pawnSMGameActiveLongitudinalLongitudinalNorthBorder;
    PawnSMGameActiveLatitudinalLatitudinal aPawnSMGameActiveLatitudinalLatitudinal = pawnSMGameActiveLatitudinalLatitudinal;
    PawnSMGameActiveLatitudinalLatitudinalEastBorder aPawnSMGameActiveLatitudinalLatitudinalEastBorder = pawnSMGameActiveLatitudinalLatitudinalEastBorder;
    PawnSMGameActiveLatitudinalLatitudinalWestBorder aPawnSMGameActiveLatitudinalLatitudinalWestBorder = pawnSMGameActiveLatitudinalLatitudinalWestBorder;
    switch (aPawnSMGameActiveLongitudinalLongitudinal)
    {
      case Between:
        if (dir1.equals(MoveDirection.North)&&dir2.equals(null)&&isLegalJump(dir1,dir2)&&!(jumpNearBorder(dir1,dir2,1))&&!(jumpOnBorder(dir1,dir2,1)))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
        // line 38 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()-2, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.South)&&dir2.equals(null)&&isLegalJump(dir1,dir2)&&!(jumpNearBorder(dir1,dir2,1))&&!(jumpOnBorder(dir1,dir2,1)))
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
        if (dir1.equals(MoveDirection.North)&&dir2.equals(null)&&isLegalJump(dir1,dir2)&&jumpNearBorder(dir1,dir2,1))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinalNorthBorder(PawnSMGameActiveLongitudinalLongitudinalNorthBorder.nearBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.South)&&dir2.equals(null)&&isLegalJump(dir1,dir2)&&jumpNearBorder(dir1,dir2,1))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinalSouthBorder(PawnSMGameActiveLongitudinalLongitudinalSouthBorder.nearBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.North)&&dir2.equals(null)&&isLegalJump(dir1,dir2)&&jumpOnBorder(dir1,dir2,1))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinalNorthBorder(PawnSMGameActiveLongitudinalLongitudinalNorthBorder.OnBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.South)&&dir2.equals(null)&&isLegalJump(dir1,dir2)&&jumpOnBorder(dir1,dir2,1))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinalSouthBorder(PawnSMGameActiveLongitudinalLongitudinalSouthBorder.OnBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.North)&&(dir2.equals(MoveDirection.East)||dir2.equals(MoveDirection.West))&&isLegalJump(dir1,dir2)&&jumpNearBorder(dir1,dir2,1))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinalNorthBorder(PawnSMGameActiveLongitudinalLongitudinalNorthBorder.nearBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.South)&&(dir2.equals(MoveDirection.West)||dir2.equals(MoveDirection.East))&&isLegalJump(dir1,dir2)&&jumpNearBorder(dir1,dir2,1))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinalSouthBorder(PawnSMGameActiveLongitudinalLongitudinalSouthBorder.nearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aPawnSMGameActiveLongitudinalLongitudinalSouthBorder)
    {
      case nearBorder:
        if (dir1.equals(MoveDirection.North)&&dir2.equals(null)&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.North)&&(dir2.equals(MoveDirection.East)||dir2.equals(MoveDirection.West))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.South)&&(dir2.equals(MoveDirection.West)||dir2.equals(MoveDirection.East))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinalLongitudinalSouthBorder();
          setPawnSMGameActiveLongitudinalLongitudinalSouthBorder(PawnSMGameActiveLongitudinalLongitudinalSouthBorder.OnBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case OnBorder:
        if (dir1.equals(MoveDirection.North)&&dir2.equals(null)&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinal();
        // line 116 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()-2, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.North)&&(dir2.equals(MoveDirection.East)||dir2.equals(MoveDirection.West))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinalLongitudinalSouthBorder();
          setPawnSMGameActiveLongitudinalLongitudinalSouthBorder(PawnSMGameActiveLongitudinalLongitudinalSouthBorder.nearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aPawnSMGameActiveLongitudinalLongitudinalNorthBorder)
    {
      case nearBorder:
        if (dir1.equals(MoveDirection.South)&&dir2.equals(null)&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.North)&&(dir2.equals(MoveDirection.East)||dir2.equals(MoveDirection.West))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinalLongitudinalNorthBorder();
          setPawnSMGameActiveLongitudinalLongitudinalNorthBorder(PawnSMGameActiveLongitudinalLongitudinalNorthBorder.OnBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.South)&&(dir2.equals(MoveDirection.West)||dir2.equals(MoveDirection.East))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinal();
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        break;
      case OnBorder:
        if (dir1.equals(MoveDirection.South)&&dir2.equals(null)&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinal();
        // line 167 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()+2, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.South)&&(dir2.equals(MoveDirection.West)||dir2.equals(MoveDirection.East))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinalLongitudinalNorthBorder();
          setPawnSMGameActiveLongitudinalLongitudinalNorthBorder(PawnSMGameActiveLongitudinalLongitudinalNorthBorder.nearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aPawnSMGameActiveLatitudinalLatitudinal)
    {
      case Between:
        if (dir2.equals(MoveDirection.West)&&dir1.equals(null)&&isLegalJump(dir1,dir2)&&!(jumpNearBorder(dir1,dir2,2))&&!(jumpOnBorder(dir1,dir2,2)))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 253 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()-2, getCurrentColumn());
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.East)&&dir1.equals(null)&&isLegalJump(dir1,dir2)&&!(jumpNearBorder(dir1,dir2,2))&&!(jumpOnBorder(dir1,dir2,2)))
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
        if (dir2.equals(MoveDirection.West)&&dir1.equals(null)&&isLegalJump(dir1,dir2)&&jumpNearBorder(dir1,dir2,2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
          setPawnSMGameActiveLatitudinalLatitudinalWestBorder(PawnSMGameActiveLatitudinalLatitudinalWestBorder.nearBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.East)&&dir1.equals(null)&&isLegalJump(dir1,dir2)&&jumpNearBorder(dir1,dir2,2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
          setPawnSMGameActiveLatitudinalLatitudinalEastBorder(PawnSMGameActiveLatitudinalLatitudinalEastBorder.nearBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.West)&&dir1.equals(null)&&isLegalJump(dir1,dir2)&&jumpOnBorder(dir1,dir2,2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
          setPawnSMGameActiveLatitudinalLatitudinalWestBorder(PawnSMGameActiveLatitudinalLatitudinalWestBorder.OnBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.East)&&dir1.equals(null)&&isLegalJump(dir1,dir2)&&jumpOnBorder(dir1,dir2,2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
          setPawnSMGameActiveLatitudinalLatitudinalEastBorder(PawnSMGameActiveLatitudinalLatitudinalEastBorder.OnBorder);
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
          setPawnSMGameActiveLatitudinalLatitudinalWestBorder(PawnSMGameActiveLatitudinalLatitudinalWestBorder.nearBorder);
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
          setPawnSMGameActiveLatitudinalLatitudinalEastBorder(PawnSMGameActiveLatitudinalLatitudinalEastBorder.nearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aPawnSMGameActiveLatitudinalLatitudinalEastBorder)
    {
      case nearBorder:
        if (dir1.equals(MoveDirection.West)&&dir2.equals(null)&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinal();
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.West)&&(dir2.equals(MoveDirection.North)||dir2.equals(MoveDirection.South))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinal();
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.East)&&(dir2.equals(MoveDirection.North)||dir2.equals(MoveDirection.South))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinalEastBorder();
          setPawnSMGameActiveLatitudinalLatitudinalEastBorder(PawnSMGameActiveLatitudinalLatitudinalEastBorder.OnBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case OnBorder:
        if (dir1.equals(MoveDirection.West)&&dir2.equals(null)&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinal();
        // line 226 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()-2, getCurrentColumn());
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.West)&&(dir1.equals(MoveDirection.North)||dir1.equals(MoveDirection.South))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinalEastBorder();
          setPawnSMGameActiveLatitudinalLatitudinalEastBorder(PawnSMGameActiveLatitudinalLatitudinalEastBorder.nearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aPawnSMGameActiveLatitudinalLatitudinalWestBorder)
    {
      case nearBorder:
        if (dir1.equals(MoveDirection.East)&&dir2.equals(null)&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinal();
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.West)&&(dir1.equals(MoveDirection.North)||dir1.equals(MoveDirection.South))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinalWestBorder();
        // line 333 "../../../../../PawnStateMachine.ump"
          if(dir1==MoveDirection.South){
                    move(getCurrentRow()+1,getCurrentColumn()-1);
            }else{
          
            
            move(getCurrentRow()-1, getCurrentColumn()-1);
              
            }
          setPawnSMGameActiveLatitudinalLatitudinalWestBorder(PawnSMGameActiveLatitudinalLatitudinalWestBorder.OnBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.East)&&(dir1.equals(MoveDirection.North)||dir1.equals(MoveDirection.South))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinal();
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
      case OnBorder:
        if (dir1.equals(MoveDirection.East)&&dir2.equals(null)&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLongitudinal();
        // line 368 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()+2, getCurrentColumn());
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.East)&&(dir1.equals(MoveDirection.North)||dir1.equals(MoveDirection.South))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinalWestBorder();
        // line 373 "../../../../../PawnStateMachine.ump"
          if(dir1==MoveDirection.South){
                    move(getCurrentRow()+1,getCurrentColumn()+1);
            }else{
          
            
            move(getCurrentRow()-1, getCurrentColumn()+1);
              
            }
          setPawnSMGameActiveLatitudinalLatitudinalWestBorder(PawnSMGameActiveLatitudinalLatitudinalWestBorder.nearBorder);
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
          setPawnSMGameActiveLatitudinalLatitudinalWestBorder(PawnSMGameActiveLatitudinalLatitudinalWestBorder.OnBorder);
          wasEventProcessed = true;
          break;
        }
        if (hasGameAsBlack())
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
          setPawnSMGameActiveLatitudinalLatitudinalEastBorder(PawnSMGameActiveLatitudinalLatitudinalEastBorder.OnBorder);
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
      case SouthBorder:
        exitPawnSMGameActiveLongitudinalLongitudinalSouthBorder();
        setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Null);
        break;
      case NorthBorder:
        exitPawnSMGameActiveLongitudinalLongitudinalNorthBorder();
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
      case SouthBorder:
        if (pawnSMGameActiveLongitudinalLongitudinalSouthBorder == PawnSMGameActiveLongitudinalLongitudinalSouthBorder.Null) { setPawnSMGameActiveLongitudinalLongitudinalSouthBorder(PawnSMGameActiveLongitudinalLongitudinalSouthBorder.nearBorder); }
        break;
      case NorthBorder:
        if (pawnSMGameActiveLongitudinalLongitudinalNorthBorder == PawnSMGameActiveLongitudinalLongitudinalNorthBorder.Null) { setPawnSMGameActiveLongitudinalLongitudinalNorthBorder(PawnSMGameActiveLongitudinalLongitudinalNorthBorder.nearBorder); }
        break;
    }
  }

  private void exitPawnSMGameActiveLongitudinalLongitudinalSouthBorder()
  {
    switch(pawnSMGameActiveLongitudinalLongitudinalSouthBorder)
    {
      case nearBorder:
        setPawnSMGameActiveLongitudinalLongitudinalSouthBorder(PawnSMGameActiveLongitudinalLongitudinalSouthBorder.Null);
        break;
      case OnBorder:
        setPawnSMGameActiveLongitudinalLongitudinalSouthBorder(PawnSMGameActiveLongitudinalLongitudinalSouthBorder.Null);
        break;
    }
  }

  private void setPawnSMGameActiveLongitudinalLongitudinalSouthBorder(PawnSMGameActiveLongitudinalLongitudinalSouthBorder aPawnSMGameActiveLongitudinalLongitudinalSouthBorder)
  {
    pawnSMGameActiveLongitudinalLongitudinalSouthBorder = aPawnSMGameActiveLongitudinalLongitudinalSouthBorder;
    if (pawnSMGameActiveLongitudinalLongitudinal != PawnSMGameActiveLongitudinalLongitudinal.SouthBorder && aPawnSMGameActiveLongitudinalLongitudinalSouthBorder != PawnSMGameActiveLongitudinalLongitudinalSouthBorder.Null) { setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.SouthBorder); }

    // entry actions and do activities
    switch(pawnSMGameActiveLongitudinalLongitudinalSouthBorder)
    {
      case OnBorder:
        // line 107 "../../../../../PawnStateMachine.ump"
        testVictory();
        break;
    }
  }

  private void exitPawnSMGameActiveLongitudinalLongitudinalNorthBorder()
  {
    switch(pawnSMGameActiveLongitudinalLongitudinalNorthBorder)
    {
      case nearBorder:
        setPawnSMGameActiveLongitudinalLongitudinalNorthBorder(PawnSMGameActiveLongitudinalLongitudinalNorthBorder.Null);
        break;
      case OnBorder:
        setPawnSMGameActiveLongitudinalLongitudinalNorthBorder(PawnSMGameActiveLongitudinalLongitudinalNorthBorder.Null);
        break;
    }
  }

  private void setPawnSMGameActiveLongitudinalLongitudinalNorthBorder(PawnSMGameActiveLongitudinalLongitudinalNorthBorder aPawnSMGameActiveLongitudinalLongitudinalNorthBorder)
  {
    pawnSMGameActiveLongitudinalLongitudinalNorthBorder = aPawnSMGameActiveLongitudinalLongitudinalNorthBorder;
    if (pawnSMGameActiveLongitudinalLongitudinal != PawnSMGameActiveLongitudinalLongitudinal.NorthBorder && aPawnSMGameActiveLongitudinalLongitudinalNorthBorder != PawnSMGameActiveLongitudinalLongitudinalNorthBorder.Null) { setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.NorthBorder); }

    // entry actions and do activities
    switch(pawnSMGameActiveLongitudinalLongitudinalNorthBorder)
    {
      case OnBorder:
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
        if (pawnSMGameActiveLatitudinalLatitudinal == PawnSMGameActiveLatitudinalLatitudinal.Null) { setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.EastBorder); }
        break;
    }
  }

  private void exitPawnSMGameActiveLatitudinalLatitudinal()
  {
    switch(pawnSMGameActiveLatitudinalLatitudinal)
    {
      case EastBorder:
        exitPawnSMGameActiveLatitudinalLatitudinalEastBorder();
        setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Null);
        break;
      case Between:
        setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Null);
        break;
      case WestBorder:
        exitPawnSMGameActiveLatitudinalLatitudinalWestBorder();
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
      case EastBorder:
        if (pawnSMGameActiveLatitudinalLatitudinalEastBorder == PawnSMGameActiveLatitudinalLatitudinalEastBorder.Null) { setPawnSMGameActiveLatitudinalLatitudinalEastBorder(PawnSMGameActiveLatitudinalLatitudinalEastBorder.nearBorder); }
        break;
      case WestBorder:
        if (pawnSMGameActiveLatitudinalLatitudinalWestBorder == PawnSMGameActiveLatitudinalLatitudinalWestBorder.Null) { setPawnSMGameActiveLatitudinalLatitudinalWestBorder(PawnSMGameActiveLatitudinalLatitudinalWestBorder.nearBorder); }
        break;
    }
  }

  private void exitPawnSMGameActiveLatitudinalLatitudinalEastBorder()
  {
    switch(pawnSMGameActiveLatitudinalLatitudinalEastBorder)
    {
      case nearBorder:
        setPawnSMGameActiveLatitudinalLatitudinalEastBorder(PawnSMGameActiveLatitudinalLatitudinalEastBorder.Null);
        break;
      case OnBorder:
        setPawnSMGameActiveLatitudinalLatitudinalEastBorder(PawnSMGameActiveLatitudinalLatitudinalEastBorder.Null);
        break;
    }
  }

  private void setPawnSMGameActiveLatitudinalLatitudinalEastBorder(PawnSMGameActiveLatitudinalLatitudinalEastBorder aPawnSMGameActiveLatitudinalLatitudinalEastBorder)
  {
    pawnSMGameActiveLatitudinalLatitudinalEastBorder = aPawnSMGameActiveLatitudinalLatitudinalEastBorder;
    if (pawnSMGameActiveLatitudinalLatitudinal != PawnSMGameActiveLatitudinalLatitudinal.EastBorder && aPawnSMGameActiveLatitudinalLatitudinalEastBorder != PawnSMGameActiveLatitudinalLatitudinalEastBorder.Null) { setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.EastBorder); }

    // entry actions and do activities
    switch(pawnSMGameActiveLatitudinalLatitudinalEastBorder)
    {
      case OnBorder:
        // line 217 "../../../../../PawnStateMachine.ump"
        testVictory();
        break;
    }
  }

  private void exitPawnSMGameActiveLatitudinalLatitudinalWestBorder()
  {
    switch(pawnSMGameActiveLatitudinalLatitudinalWestBorder)
    {
      case nearBorder:
        setPawnSMGameActiveLatitudinalLatitudinalWestBorder(PawnSMGameActiveLatitudinalLatitudinalWestBorder.Null);
        break;
      case OnBorder:
        setPawnSMGameActiveLatitudinalLatitudinalWestBorder(PawnSMGameActiveLatitudinalLatitudinalWestBorder.Null);
        break;
    }
  }

  private void setPawnSMGameActiveLatitudinalLatitudinalWestBorder(PawnSMGameActiveLatitudinalLatitudinalWestBorder aPawnSMGameActiveLatitudinalLatitudinalWestBorder)
  {
    pawnSMGameActiveLatitudinalLatitudinalWestBorder = aPawnSMGameActiveLatitudinalLatitudinalWestBorder;
    if (pawnSMGameActiveLatitudinalLatitudinal != PawnSMGameActiveLatitudinalLatitudinal.WestBorder && aPawnSMGameActiveLatitudinalLatitudinalWestBorder != PawnSMGameActiveLatitudinalLatitudinalWestBorder.Null) { setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.WestBorder); }

    // entry actions and do activities
    switch(pawnSMGameActiveLatitudinalLatitudinalWestBorder)
    {
      case OnBorder:
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
	  return isNearBorder(Controller.direction(Controller.getTile(getCurrentColumn(), getCurrentRow()),dirToInt(dir)), null);
  }
  
  // ??
  private boolean stepOnBorder(MoveDirection dir)
  {
	  return isOnBorder(Controller.direction(Controller.getTile(getCurrentColumn(), getCurrentRow()),dirToInt(dir)), null);
	  
  }
  
  private boolean jumpNearBorder(MoveDirection dir, MoveDirection dir2, int border)
  {
	  return isNearBorder(getJumpMoveTile(dir, dir2), border == 1 ? dir : dir2);
  }
  
  private Tile getJumpMoveTile(MoveDirection dir, MoveDirection dir2) {
	  Tile jumpMoveTile = Controller.getTile(getCurrentColumn(), getCurrentRow());
	  jumpMoveTile = Controller.direction(jumpMoveTile, dirToInt(dir == null ? dir2 : dir));
	  jumpMoveTile = Controller.direction(jumpMoveTile, dirToInt(dir2 == null ? dir : dir2));
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
	  if (d == null)
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
	  if (d == null)
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
  
  public void setSMTest(int row, int column){
	//assuming it is already in active state

	//longitudinal
	if(row==8){
	//—> near border South

	setPawnSMGameActiveLongitudinalLongitudinalSouthBorder(PawnSMGameActiveLongitudinalLongitudinalSouthBorder.nearBorder);
	
	}else if(row==2){

	//—>near Border North
	setPawnSMGameActiveLongitudinalLongitudinalNorthBorder(PawnSMGameActiveLongitudinalLongitudinalNorthBorder.nearBorder);

	}else if(row==1){

	//—> On border North

	setPawnSMGameActiveLongitudinalLongitudinalNorthBorder(PawnSMGameActiveLongitudinalLongitudinalNorthBorder.OnBorder);

	}else if(row==9){

	//—> On border South

	setPawnSMGameActiveLongitudinalLongitudinalSouthBorder(PawnSMGameActiveLongitudinalLongitudinalSouthBorder.OnBorder);

	}else{
	//—>is In Longitudinal.Between;

	setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);

	}



	//latitudinal

	if(column==8){
	//—> near border East
	setPawnSMGameActiveLatitudinalLatitudinalEastBorder(PawnSMGameActiveLatitudinalLatitudinalEastBorder.nearBorder);


	}else if(column==2){

	//—>near Border West

	setPawnSMGameActiveLatitudinalLatitudinalWestBorder(PawnSMGameActiveLatitudinalLatitudinalWestBorder.nearBorder);


	}else if(column==1){

	//—> On border West

	setPawnSMGameActiveLatitudinalLatitudinalWestBorder(PawnSMGameActiveLatitudinalLatitudinalWestBorder.OnBorder);

	}else if(column==9){

	//—> On border East
	setPawnSMGameActiveLatitudinalLatitudinalEastBorder(PawnSMGameActiveLatitudinalLatitudinalEastBorder.OnBorder);


	}else{
	//—>is In Latitudinal.Between;

	setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);

	}


	}

}