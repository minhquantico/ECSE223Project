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
        // line 235 "../../../../../PawnStateMachine.ump"
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
        // line 266 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()+1);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(dir)&&dir.equals(MoveDirection.West)&&!(stepNearBorder(dir))&&!(stepOnBorder(dir)))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 268 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()-1);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(dir)&&dir.equals(MoveDirection.East)&&stepNearBorder(dir))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 308 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()+1);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.EastBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(dir)&&dir.equals(MoveDirection.West)&&stepNearBorder(dir))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 310 "../../../../../PawnStateMachine.ump"
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
        // line 356 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()+1);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (isLegalStep(dir)&&dir.equals(MoveDirection.West))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 358 "../../../../../PawnStateMachine.ump"
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
        // line 397 "../../../../../PawnStateMachine.ump"
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
        // line 66 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()-2, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.NorthBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.South)&&dir2.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2)&&jumpNearBorder(dir1,dir2,1))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
        // line 68 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()+2, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.SouthBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.North)&&dir2.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2)&&jumpOnBorder(dir1,dir2,1))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
        // line 70 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()-2, getCurrentColumn());
          setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.NorthBorderOnBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir1.equals(MoveDirection.South)&&dir2.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2)&&jumpOnBorder(dir1,dir2,1))
        {
          exitPawnSMGameActiveLongitudinalLongitudinal();
        // line 72 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()+2, getCurrentColumn());
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
        // line 95 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()-2, getCurrentColumn());
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
        // line 148 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()+2,getCurrentColumn());
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
        if (dir2.equals(MoveDirection.West)&&dir1.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 205 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()-2);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.West)&&(dir1.equals(MoveDirection.North)||dir1.equals(MoveDirection.South))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 209 "../../../../../PawnStateMachine.ump"
          if(dir1==MoveDirection.South){
                    move(getCurrentRow()+1,getCurrentColumn()-1);
            }else{
          
            move(getCurrentRow()-1, getCurrentColumn()-1);    
            }
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.East)&&(dir1.equals(MoveDirection.North)||dir1.equals(MoveDirection.South))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 218 "../../../../../PawnStateMachine.ump"
          if(dir1==MoveDirection.South){
                    move(getCurrentRow()+1,getCurrentColumn()+1);
            }else{
          
            move(getCurrentRow()-1, getCurrentColumn()+1);    
            }
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.EastBorderOnBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case EastBorderOnBorder:
        if (dir2.equals(MoveDirection.West)&&dir1.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 240 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow()-2, getCurrentColumn());
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.West)&&(dir1.equals(MoveDirection.North)||dir1.equals(MoveDirection.South))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 246 "../../../../../PawnStateMachine.ump"
          if(dir1==MoveDirection.South){
                    move(getCurrentRow()+1,getCurrentColumn()-1);
            }else{
          
            move(getCurrentRow()-1, getCurrentColumn()-1);    
            }
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.EastBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      case Between:
        if (dir2.equals(MoveDirection.West)&&dir1.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2)&&!(jumpNearBorder(dir1,dir2,2))&&!(jumpOnBorder(dir1,dir2,2)))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 274 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()-2);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.East)&&dir1.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2)&&!(jumpNearBorder(dir1,dir2,2))&&!(jumpOnBorder(dir1,dir2,2)))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 276 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()+2);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.West)&&(dir1.equals(MoveDirection.North)||dir1.equals(MoveDirection.South))&&isLegalJump(dir1,dir2)&&!(jumpNearBorder(dir1,dir2,1))&&!(jumpOnBorder(dir1,dir2,2)))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 280 "../../../../../PawnStateMachine.ump"
          if(dir1==MoveDirection.South){
                    move(getCurrentRow()+1,getCurrentColumn()-1);
            }else{
          
            move(getCurrentRow()-1, getCurrentColumn()-1);    
            }
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.East)&&(dir1.equals(MoveDirection.North)||dir1.equals(MoveDirection.South))&&isLegalJump(dir1,dir2)&&!(jumpNearBorder(dir1,dir2,1))&&!(jumpOnBorder(dir1,dir2,2)))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 289 "../../../../../PawnStateMachine.ump"
          if(dir1==MoveDirection.South){
                    move(getCurrentRow()+1,getCurrentColumn()+1);
            }else{
          
            move(getCurrentRow()-1, getCurrentColumn()+1);    
            }
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.West)&&dir1.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2)&&jumpNearBorder(dir1,dir2,2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 316 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()-2);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.WestBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.East)&&dir1.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2)&&jumpNearBorder(dir1,dir2,2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 318 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()+2);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.EastBordernearBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.West)&&dir1.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2)&&jumpOnBorder(dir1,dir2,2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 320 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()-2);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.WestBorderOnBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.East)&&dir1.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2)&&jumpOnBorder(dir1,dir2,2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 322 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()+2);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.EastBorderOnBorder);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.West)&&(dir1.equals(MoveDirection.North)||dir1.equals(MoveDirection.South))&&isLegalJump(dir1,dir2)&&jumpNearBorder(dir1,dir2,1))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 325 "../../../../../PawnStateMachine.ump"
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
        // line 334 "../../../../../PawnStateMachine.ump"
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
        if (dir2.equals(MoveDirection.East)&&dir1.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 364 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()+2);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.West)&&(dir1.equals(MoveDirection.North)||dir1.equals(MoveDirection.South))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 368 "../../../../../PawnStateMachine.ump"
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
        // line 381 "../../../../../PawnStateMachine.ump"
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
        if (dir2.equals(MoveDirection.East)&&dir1.equals(MoveDirection.Null)&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 403 "../../../../../PawnStateMachine.ump"
          move(getCurrentRow(), getCurrentColumn()+2);
          setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);
          wasEventProcessed = true;
          break;
        }
        if (dir2.equals(MoveDirection.East)&&(dir1.equals(MoveDirection.North)||dir1.equals(MoveDirection.South))&&isLegalJump(dir1,dir2))
        {
          exitPawnSMGameActiveLatitudinalLatitudinal();
        // line 408 "../../../../../PawnStateMachine.ump"
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
        // line 430 "../../../../../PawnStateMachine.ump"
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
        // line 231 "../../../../../PawnStateMachine.ump"
        testVictory();
        break;
      case WestBorderOnBorder:
        // line 395 "../../../../../PawnStateMachine.ump"
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
  /**
	 * @author Jake Pogharian
	 * method is used to get row of player
	 *This method is used to get the corresponding player's current Row
	 */
  private int getCurrentRow()
  {
	  if(player.hasGameAsWhite())
		  return QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getRow();
	  else
		  return QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getRow();
	  
  }
  /**
	 * @author Jake Pogharian
	 * method is used to get column of player
	 *This method is used to get the corresponding player's current Column
	 */
  private int getCurrentColumn()
  {
	  if(player.hasGameAsWhite())
		  return QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getColumn();
	  else
		  return QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getColumn();
  }
  
  /**
	 * @author Traian Coze
	 *@param MoveDirection which is the direction in which the step is taken
	 *@param boolean which is if the step will end up near the border it returns true.
	 */
  private boolean stepNearBorder(MoveDirection dir)
  {
	 
	  return isNearBorder(Controller.direction(Controller.getTile(getCurrentColumn(), getCurrentRow()),dirToInt(dir)), dir);
  }
  /**
 	 * @author Traian Coze
 	 *@param MoveDirection which is the direction in which the step is taken
 	 *@param boolean which is if the step will end up on the border it returns true.
 	 */
  private boolean stepOnBorder(MoveDirection dir)
  {
	  return isOnBorder(Controller.direction(Controller.getTile(getCurrentColumn(), getCurrentRow()),dirToInt(dir)), dir);
	  
  }
  /**
	 * @author Jake Pogharian
	 * Methode is used to see if the jump will end up near a given border 
	 *@param MoveDirection dir1 which is one of the jump move's directions
	 *@param MoveDirection dir 2is the diagonal direction of the jump move
	 *@param int border says which direction we're checking the border for, dir1 or dir2
	 *@param boolean which is if the jump will end up near the border it returns true.
	 */
  private boolean jumpNearBorder(MoveDirection dir, MoveDirection dir2, int border)
  {
	  return isNearBorder(getJumpMoveTile(dir, dir2), border == 1 ? dir : dir2);
  }
  /**
 	 * @author Traian Coze
 	 * method is used to get tile corresponding to a given jump move
 	 *@param MoveDirection dir1 which is one of the jump move's directions
 	 *@param MoveDirection dir2 is the diagonal direction of the jump move
 	 *@param Tile which is the jump move's corresponding tile
 	 */
  private Tile getJumpMoveTile(MoveDirection dir, MoveDirection dir2) {
	  Tile jumpMoveTile = Controller.getTile(getCurrentColumn(), getCurrentRow());
	  jumpMoveTile = Controller.direction(jumpMoveTile, dirToInt(dir == MoveDirection.Null ? dir2 : dir));
	  jumpMoveTile = Controller.direction(jumpMoveTile, dirToInt(dir2 == MoveDirection.Null ? dir : dir2));
	  return jumpMoveTile;
  }
  
  /**
 	 * @author Traian Coze
 	 * This method is used to check if the jump will end up on a given border
 	 *@param MoveDirection dir1 which is one of the jump move's directions
 	 *@param MoveDirection dir 2is the diagonal direction of the jump move
 	 *@param int border says which direction we're checking the border for, dir1 or dir2
 	 *@param boolean which is if the jump will end up on the border it returns true.
 	 */
  private boolean jumpOnBorder(MoveDirection dir, MoveDirection dir2, int border)
  {
	  return isOnBorder(getJumpMoveTile(dir, dir2), border == 1 ? dir : dir2);
  }
  
  public boolean calledLegal;
  /**
	 * @author David Budaghyan
	 * Guard method used to check if is legal step
	 *@param MoveDirection dir1 which is one of the jump move's directions
	 *@param boolean return true if legal
	 */
  private boolean isLegalStep(MoveDirection dir)
  {
	  if (calledLegal)
		  return true;
	  calledLegal = true;
	  
	  boolean ret = false;
	  try
	  {
		  ret = Controller.initPosValidation(
				  Controller.direction(
				  Controller.getTile(getCurrentColumn(), getCurrentRow()),
				  dirToInt(dir)));
	  }
	  catch (NullPointerException ex) { }
	  
	  if (!ret)
		  throw new IllegalMoveException(player, dir);
	  
	  return ret;
  }
  
  /**
 	 * @author Traian Coza
 	 * Guard method used to check if is legal jump
 	 *@param MoveDirection dir1 which is one of the jump move's directions
 	 *@param MoveDirection dir2 which is one of the jump move's directions
 	 */
  private boolean isLegalJump(MoveDirection dir, MoveDirection dir2)
  {
	  if (calledLegal)
		  return true;
	  calledLegal = true;
	  
	  boolean ret = false;
	  try { ret =  Controller.initPosValidation(getJumpMoveTile(dir, dir2)); }
	  catch (NullPointerException ex) { }
	  if (!ret)
		  throw new IllegalMoveException(player, dir, dir2);
	  return ret;
	  
  }
  
  /**
	 * @author Jake Pogharian
	 *method used to move the pawn 
	 *@param int col refers to the column
	 *@param int row refers to the row
	 */
  private void move(int row, int col)
  {
	  Controller.doPawnMove(col, row);
  }
  
  
  /**
	 * @author Jake Pogharian
	 *just returns which player it is
	 */
  private boolean hasGameAsWhite() { return player.hasGameAsWhite(); }
  
  /**
 	 * @author Jake Pogharian
 	 *just returns which player it is
 	 */
  private boolean hasGameAsBlack() { return player.hasGameAsBlack(); }
  
  
  
  /**
	 * @author Jake Pogharian
	 *method used to test if won, called at final position
	 */
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
  
  /**
 	 * @author Jake Pogharian
 	 *unimplemented method that should display results
 	 */
  private void displayResults()
  {
	  throw new UnsupportedOperationException("Implement me!");
  }
  
  /**
	 * @author David Budaghyan
	 *method that converts directions to integers 
	 *@param dir refers to the move direction
	 */
  private int dirToInt(MoveDirection dir) {
	  switch(dir) {
	  	case North: return 0;
	  	case East: return 1; 
	  	case South: return 2; 
	  	case West: return 3;
	  }
	  return -1;
  }
  
  /**
	 * @author David Budaghyan
	 *method that checks if the given tile is near a border.
	 *if move direction is null then check all cases. if we have a move direction, check the corresponding case
	 *@param tile is the tile we want to inquire about,
	 *@param direction is the direction of the move
	 *method is used for checking move legality
	 *@return boolean
	 */
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
  /**
 	 * @author David Budaghyan
 	 *method that checks if the given tile is near a border.
 	 *if move direction is null then check all cases. if we have a move direction, check the corresponding case
 	 *@param tile is the tile we want to inquire about,
 	 *@param direction is the direction of the move
 	 *method is used for checking move legality
 	 *@return boolean
 	 */
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
  
  /**
	 * @author Jake Pogharian
	 * This method is used for the testing scenarios in the gherkin. ONLY USED FOR GHERKIN TESTS.
	 * sets state machine to correct initial state when given player's starting coordinates
	 *@param int row corresponding int for given row
	 *@param int column corresponding int for given column
	 */
  public void setSMTest(int row, int column){
	//assuming it is already in active state

	//longitudinal
	if(row==8){
	//—> near border South

	setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.SouthBordernearBorder);
	
	}else if(row==2){

	//—>near Border North
	setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.NorthBordernearBorder);

	}else if(row==1){

	//—> On border North

	setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.NorthBorderOnBorder);

	}else if(row==9){

	//—> On border South

	setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.SouthBorderOnBorder);

	}else{
	//—>is In Longitudinal.Between;

	setPawnSMGameActiveLongitudinalLongitudinal(PawnSMGameActiveLongitudinalLongitudinal.Between);

	}
	//latitudinal

	if(column==8){
	//—> near border East
	setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.EastBordernearBorder);


	}else if(column==2){

	//—>near Border West

	setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.WestBordernearBorder);


	}else if(column==1){

	//—> On border West

	setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.WestBorderOnBorder);

	}else if(column==9){

	//—> On border East
	setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.EastBorderOnBorder);


	}else{
	//—>is In Latitudinal.Between;

	setPawnSMGameActiveLatitudinalLatitudinal(PawnSMGameActiveLatitudinalLatitudinal.Between);

	}


	}

}