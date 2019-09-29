/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package domain;

// line 40 "../domain_model.ump"
public class Cell
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Cell Attributes
  private int row;
  private int column;

  //Cell Associations
  private Player occupant;
  private Board board;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cell(int aRow, int aColumn, Board aBoard)
  {
    row = aRow;
    column = aColumn;
    boolean didAddBoard = setBoard(aBoard);
    if (!didAddBoard)
    {
      throw new RuntimeException("Unable to create cell due to board");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRow(int aRow)
  {
    boolean wasSet = false;
    row = aRow;
    wasSet = true;
    return wasSet;
  }

  public boolean setColumn(int aColumn)
  {
    boolean wasSet = false;
    column = aColumn;
    wasSet = true;
    return wasSet;
  }

  public int getRow()
  {
    return row;
  }

  public int getColumn()
  {
    return column;
  }
  /* Code from template association_GetOne */
  public Player getOccupant()
  {
    return occupant;
  }

  public boolean hasOccupant()
  {
    boolean has = occupant != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setOccupant(Player aNewOccupant)
  {
    boolean wasSet = false;
    if (occupant != null && !occupant.equals(aNewOccupant) && equals(occupant.getCurrentCell()))
    {
      //Unable to setOccupant, as existing occupant would become an orphan
      return wasSet;
    }

    occupant = aNewOccupant;
    Cell anOldCurrentCell = aNewOccupant != null ? aNewOccupant.getCurrentCell() : null;

    if (!this.equals(anOldCurrentCell))
    {
      if (anOldCurrentCell != null)
      {
        anOldCurrentCell.occupant = null;
      }
      if (occupant != null)
      {
        occupant.setCurrentCell(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setBoard(Board aBoard)
  {
    boolean wasSet = false;
    //Must provide board to cell
    if (aBoard == null)
    {
      return wasSet;
    }

    //board already at maximum (81)
    if (aBoard.numberOfCells() >= Board.maximumNumberOfCells())
    {
      return wasSet;
    }
    
    Board existingBoard = board;
    board = aBoard;
    if (existingBoard != null && !existingBoard.equals(aBoard))
    {
      boolean didRemove = existingBoard.removeCell(this);
      if (!didRemove)
      {
        board = existingBoard;
        return wasSet;
      }
    }
    board.addCell(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Player existingOccupant = occupant;
    occupant = null;
    if (existingOccupant != null)
    {
      existingOccupant.delete();
    }
    Board placeholderBoard = board;
    this.board = null;
    if(placeholderBoard != null)
    {
      placeholderBoard.removeCell(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "row" + ":" + getRow()+ "," +
            "column" + ":" + getColumn()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "occupant = "+(getOccupant()!=null?Integer.toHexString(System.identityHashCode(getOccupant())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "board = "+(getBoard()!=null?Integer.toHexString(System.identityHashCode(getBoard())):"null");
  }
}