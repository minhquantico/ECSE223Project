package ca.mcgill.ecse223.quoridor.features.stepfiles;

import static org.junit.Assert.assertFalse;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.features.CucumberStepDefinitions;
import ca.mcgill.ecse223.quoridor.model.*;
import io.cucumber.java.After;
import io.cucumber.java.en.*;

public class RotateWall{
	static class Controller {
		/**
		 * This controller method flips the walls that are about to be placed on the board
		 * For example, walls that are about to be placed vertically will be flipped horizontally and vice versa.
		 * Used in @When("I try to flip the wall") statement in the Rotate Wall feature
		 * @author Gohar Saqib Fazal
		 * @param wallMove: Wall Move object that contains information 
		 * such as which wall is being flipped and the direction of set wall
		 */
		public static void flip_wall(WallMove wallMove) {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * @author Gohar Saqib Fazal
	 */
	@When("I try to flip the wall")
	public void i_try_to_flip_the_wall() {
		Controller.flip_wall(QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate());
	}

	/**
	 * @author Gohar Saqib Fazal
	 * @param dir: The new direction that the wall already placed on the board should be moved to
	 */
	@Then("The wall shall be rotated over the board to {string}")
	public void the_wall_shall_be_rotated_over_the_board_to(String dir) {
		if(dir.equalsIgnoreCase("vertical")) {
			assert QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().setWallDirection(Direction.Vertical);
		}
		else {
			assert QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().setWallDirection(Direction.Horizontal);
		}
	}

}
