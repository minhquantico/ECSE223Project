/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package domain;
import java.util.*;

// line 64 "../domain_model.ump"
public class Board
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Board Associations
  private List<Cell> cells;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board(Game aGame)
  {
    cells = new ArrayList<Cell>();
    if (aGame == null || aGame.getBoard() != null)
    {
      throw new RuntimeException("Unable to create Board due to aGame");
    }
    game = aGame;
  }

  public Board(int aIdForGame, GameSituation aGameSituationForGame, System aSystemForGame)
  {
    cells = new ArrayList<Cell>();
    game = new Game(aIdForGame, aGameSituationForGame, this, aSystemForGame);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Cell getCell(int index)
  {
    Cell aCell = cells.get(index);
    return aCell;
  }

  public List<Cell> getCells()
  {
    List<Cell> newCells = Collections.unmodifiableList(cells);
    return newCells;
  }

  public int numberOfCells()
  {
    int number = cells.size();
    return number;
  }

  public boolean hasCells()
  {
    boolean has = cells.size() > 0;
    return has;
  }

  public int indexOfCell(Cell aCell)
  {
    int index = cells.indexOf(aCell);
    return index;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfCellsValid()
  {
    boolean isValid = numberOfCells() >= minimumNumberOfCells() && numberOfCells() <= maximumNumberOfCells();
    return isValid;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfCells()
  {
    return 81;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCells()
  {
    return 81;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfCells()
  {
    return 81;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Cell addCell(int aRow, int aColumn)
  {
    if (numberOfCells() >= maximumNumberOfCells())
    {
      return null;
    }
    else
    {
      return new Cell(aRow, aColumn, this);
    }
  }

  public boolean addCell(Cell aCell)
  {
    boolean wasAdded = false;
    if (cells.contains(aCell)) { return false; }
    if (numberOfCells() >= maximumNumberOfCells())
    {
      return wasAdded;
    }

    Board existingBoard = aCell.getBoard();
    boolean isNewBoard = existingBoard != null && !this.equals(existingBoard);

    if (isNewBoard && existingBoard.numberOfCells() <= minimumNumberOfCells())
    {
      return wasAdded;
    }

    if (isNewBoard)
    {
      aCell.setBoard(this);
    }
    else
    {
      cells.add(aCell);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCell(Cell aCell)
  {
    boolean wasRemoved = false;
    //Unable to remove aCell, as it must always have a board
    if (this.equals(aCell.getBoard()))
    {
      return wasRemoved;
    }

    //board already at minimum (81)
    if (numberOfCells() <= minimumNumberOfCells())
    {
      return wasRemoved;
    }
    cells.remove(aCell);
    wasRemoved = true;
    return wasRemoved;
  }

  public void delete()
  {
    while (cells.size() > 0)
    {
      Cell aCell = cells.get(cells.size() - 1);
      aCell.delete();
      cells.remove(aCell);
    }
    
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
  }

}