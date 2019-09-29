/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/



// line 47 "domain_model.ump"
public class WallMove extends Move
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Orientation { Vertical, Horizontal }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WallMove Associations
  private Wall wall;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WallMove(Player aPlayer, Cell aCell, Wall aWall)
  {
    super(aPlayer, aCell);
    boolean didAddWall = setWall(aWall);
    if (!didAddWall)
    {
      throw new RuntimeException("Unable to create wallMove due to wall");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Wall getWall()
  {
    return wall;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setWall(Wall aNewWall)
  {
    boolean wasSet = false;
    if (aNewWall == null)
    {
      //Unable to setWall to null, as wallMove must always be associated to a wall
      return wasSet;
    }
    
    WallMove existingWallMove = aNewWall.getWallMove();
    if (existingWallMove != null && !equals(existingWallMove))
    {
      //Unable to setWall, the current wall already has a wallMove, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Wall anOldWall = wall;
    wall = aNewWall;
    wall.setWallMove(this);

    if (anOldWall != null)
    {
      anOldWall.setWallMove(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Wall existingWall = wall;
    wall = null;
    if (existingWall != null)
    {
      existingWall.setWallMove(null);
    }
    super.delete();
  }

}