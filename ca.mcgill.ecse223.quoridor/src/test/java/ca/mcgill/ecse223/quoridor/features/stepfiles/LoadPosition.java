package ca.mcgill.ecse223.quoridor.features.stepfiles;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.Controller;
import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import java.lang.UnsupportedOperationException;
import ca.mcgill.ecse223.quoridor.model.*;
import io.cucumber.java.After;
import io.cucumber.java.en.*;

public class LoadPosition
{
	@When("I initiate to load a saved game {string}")
	public void i_initiate_to_load_a_saved_game(String string) {
	    Controller.loadGame(new File(string));
	}

	@When("The position to load is valid")
	public void the_position_to_load_is_valid() {
		assertTrue(Controller.positionIsValid());
	}

	@Then("It shall be {string}'s turn")
	public void it_shall_be_s_turn(String string) {
		assertEquals(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size() % 2 == 0, string.equals("white"));
	}

	@Then("{string} shall be at {int}:{int}")
	public void shall_be_at(String string, Integer int1, Integer int2) {
	    Tile t = string.equals("white") ?
	    		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile() :
	    		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile();
	    
	    assertTrue(t.getRow() == int1 && t.getColumn() == int2);
	}

	@Then("{string} shall have a vertical wall at {int}:{int}")
	public void shall_have_a_vertical_wall_at(String string, Integer int1, Integer int2) {
		List<Wall> walls = string.equals("white") ?
	    		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsOnBoard() :
	    		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsOnBoard();
	    
	    boolean exists = false;
		for (Wall wall : walls)
			exists |= (
					wall.getMove().getTargetTile().getRow() == int1 &&
					wall.getMove().getTargetTile().getColumn() == int2 &&
					wall.getMove().getWallDirection().equals(Direction.Vertical));
		assertTrue(exists);
	}

	@Then("{string} shall have a horizontal wall at {int}:{int}")
	public void shall_have_a_horizontal_wall_at(String string, Integer int1, Integer int2) {
		List<Wall> walls = string.equals("white") ?
	    		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsOnBoard() :
	    		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsOnBoard();
	    
	    boolean exists = false;
		for (Wall wall : walls)
			exists |= (
					wall.getMove().getTargetTile().getRow() == int1 &&
					wall.getMove().getTargetTile().getColumn() == int2 &&
					wall.getMove().getWallDirection().equals(Direction.Horizontal));
		assertTrue(exists);
	}

	@Then("Both players shall have {int} in their stacks")
	public void both_players_shall_have_in_their_stacks(Integer int1) {
		assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().size() == int1);
		assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsInStock().size() == int1);
	}

	@When("The position to load is invalid")
	public void the_position_to_load_is_invalid() {
	    assertFalse(Controller.positionIsValid());
	}

	@Then("The load shall return an error")
	public void the_load_shall_return_an_error() {
	    assertTrue(false);
	}
	
	static class Controller
	{
		public static void loadGame(File file)
		{		
			throw new UnsupportedOperationException();
		}

		public static boolean positionIsValid() {
			// TODO Auto-generated method stub
			return false;
		}
	}
}
