package ca.mcgill.ecse223.quoridor.features.stepfiles;

import static org.junit.Assert.assertTrue;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.features.stepfiles.LoadPosition.Controller;
import ca.mcgill.ecse223.quoridor.model.*;
import io.cucumber.java.After;
import io.cucumber.java.en.*;

public class ValidatePosition{

	static class Controller{

		/**
		 * This controller method valdidates the postion of the pawn in the game
		 * Used in @When("Validation of the position is initiated") statement in Validate Position Feature
		 * Used in @Then("The position shall be {string}") statement in Validate Position Feature
		 * @author Gohar Saqib Fazal
		 * @param aTargetTile: The position at which the pawn is going to be moved in the current move.
		 * @return Boolean: This tells us whether the pawn position is valid or not
		 */
		static Boolean initPosValidation (Tile aTargetTile) {
			throw new UnsupportedOperationException();
		}

		/**
		 * This controller method validates the position of the wall in the game
		 * Used in @When("Validation of the position is initiated") statement in Validate Position Feature
		 * Used in @Then("The position shall be valid") statement in Validate Position Feature
		 * Used in @Then("The position shall be invalid") statement in Validate Position Feature
		 * @author Gohar Saqib Fazal 
		 * @param aTargetTile: The position at which the wall is going to be placed in the current move.
		 * @param dir: The direction of the wall that is going to be placed in the current move
		 * @return Boolean: This tells us whether the pawn position is valid or not
		 */
		static Boolean initPosValidation (Tile aTargetTile, Direction dir) { 
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * @author Gohar Saqib Fazal
	 * @param int1: The row number of the pawn coordinate supplied
	 * @param int2: The column number of the pawn coordinate supplied
	 */
	@Given("A game position is supplied with pawn coordinate {int}:{int}")
	public void a_game_position_is_supplied_with_pawn_coordinate(Integer int1, Integer int2) {
		Tile tile = new Tile(int1, int2, QuoridorApplication.getQuoridor().getBoard());
		QuoridorApplication.getQuoridor().getCurrentGame().getMove(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()).setTargetTile(tile);
	}

	/**
	 * @author Gohar Saqib Fazal
	 */
	@When("Validation of the position is initiated")
	public void validation_of_the_position_is_initiated() {
		if(QuoridorApplication.getQuoridor().getCurrentGame().hasWallMoveCandidate()) {
			Controller.initPosValidation(QuoridorApplication.getQuoridor().getCurrentGame().getMove(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()).getTargetTile(), QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallDirection());
		}
		else {
			Controller.initPosValidation(QuoridorApplication.getQuoridor().getCurrentGame().getMove(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()).getTargetTile());
		}
	}

	/**
	 * @author Gohar Saqib Fazal
	 * @param string: "Ok" or "Error" to tell whether the position is valid or not
	 */
	@Then("The position shall be {string}")
	public void the_position_shall_be(String string) {
		if (Controller.initPosValidation(QuoridorApplication.getQuoridor().getCurrentGame().getMove(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()).getTargetTile()) == true){
			assert posValidationResult(Controller.initPosValidation(QuoridorApplication.getQuoridor().getCurrentGame().getMove(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()).getTargetTile())).equals("ok"); 
		}
		else {
			assert posValidationResult(Controller.initPosValidation(QuoridorApplication.getQuoridor().getCurrentGame().getMove(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()).getTargetTile())).equals("error");	
		}
	}

	/**
	 * @author Gohar Saqib Fazal
	 * @param int1: The row number of the wall coordinate supplied
	 * @param int2: The column number of the pawn coordinate supplied
	 * @param string: "Ok" or "Error" to tell whether the position is valid or not
	 */
	@Given("A game position is supplied with wall coordinate {int}:{int}-{string}")
	public void a_game_position_is_supplied_with_wall_coordinate(Integer int1, Integer int2, String string) {
		Tile tile = new Tile(int1, int2, QuoridorApplication.getQuoridor().getBoard());
		QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().setTargetTile(tile);			
		if (string.equalsIgnoreCase("Vertical")) {
			QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().setWallDirection(Direction.Vertical);
		}
		else {
			QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().setWallDirection(Direction.Horizontal);
		}
	}

	/**
	 * @author Gohar Saqib Fazal
	 */
	@Then("The position shall be valid")
	public void the_position_shall_be_valid() {
		assert Controller.initPosValidation(QuoridorApplication.getQuoridor().getCurrentGame().getMove(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()).getTargetTile(), QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallDirection()):"ERROR: The position is invalid";
	}

	/**
	 * @author Gohar Saqib Fazal
	 */
	@Then("The position shall be invalid")
	public void the_position_shall_be_invalid() {
		assert (! Controller.initPosValidation(QuoridorApplication.getQuoridor().getCurrentGame().getMove(QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()).getTargetTile(), QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallDirection()));
	}

	//HELPER METHODS
	/**
	 * @author Gohar Saqib Fazal
	 * @param okOrError: A boolean value
	 * @return String: The result of the validation method and it stores whether the position is valid or not. 
	 */
	public static String posValidationResult(Boolean okOrError){
		String result; 
		if(okOrError) {
			result = "ok";
		}
		else {
			result = "error";
		}
		return result;	
	}
}
