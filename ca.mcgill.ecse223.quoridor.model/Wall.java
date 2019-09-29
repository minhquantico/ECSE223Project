/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package domain;

// line 3 "../domain_model.ump"
public class Wall
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Wall Attributes
  private int id;

  //Wall Associations
  private WallMove wallMove;
  private Player owner;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Wall(int aId, Player aOwner)
  {
    id = aId;
    boolean didAddOwner = setOwner(aOwner);
    if (!didAddOwner)
    {
      throw new RuntimeException("Unable to create unusedWall due to owner");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
  public WallMove getWallMove()
  {
    return wallMove;
  }

  public boolean hasWallMove()
  {
    boolean has = wallMove != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Player getOwner()
  {
    return owner;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setWallMove(WallMove aNewWallMove)
  {
    boolean wasSet = false;
    if (wallMove != null && !wallMove.equals(aNewWallMove) && equals(wallMove.getWall()))
    {
      //Unable to setWallMove, as existing wallMove would become an orphan
      return wasSet;
    }

    wallMove = aNewWallMove;
    Wall anOldWall = aNewWallMove != null ? aNewWallMove.getWall() : null;

    if (!this.equals(anOldWall))
    {
      if (anOldWall != null)
      {
        anOldWall.wallMove = null;
      }
      if (wallMove != null)
      {
        wallMove.setWall(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setOwner(Player aOwner)
  {
    boolean wasSet = false;
    //Must provide owner to unusedWall
    if (aOwner == null)
    {
      return wasSet;
    }

    //owner already at maximum (10)
    if (aOwner.numberOfUnusedWall() >= Player.maximumNumberOfUnusedWall())
    {
      return wasSet;
    }
    
    Player existingOwner = owner;
    owner = aOwner;
    if (existingOwner != null && !existingOwner.equals(aOwner))
    {
      boolean didRemove = existingOwner.removeUnusedWall(this);
      if (!didRemove)
      {
        owner = existingOwner;
        return wasSet;
      }
    }
    owner.addUnusedWall(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    WallMove existingWallMove = wallMove;
    wallMove = null;
    if (existingWallMove != null)
    {
      existingWallMove.delete();
    }
    Player placeholderOwner = owner;
    this.owner = null;
    if(placeholderOwner != null)
    {
      placeholderOwner.removeUnusedWall(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "wallMove = "+(getWallMove()!=null?Integer.toHexString(System.identityHashCode(getWallMove())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "owner = "+(getOwner()!=null?Integer.toHexString(System.identityHashCode(getOwner())):"null");
  }
}