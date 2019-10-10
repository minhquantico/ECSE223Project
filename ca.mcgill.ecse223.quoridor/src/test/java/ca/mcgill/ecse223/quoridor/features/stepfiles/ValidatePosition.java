package ca.mcgill.ecse223.quoridor.features.stepfiles;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.*;
import io.cucumber.java.After;
import io.cucumber.java.en.*;

public class ValidatePosition
{
	@Given("A game position is supplied with pawn coordinate {int}:{int}")
	public void a_game_position_is_supplied_with_pawn_coordinate(Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("Validation of the position is initiated")
	public void validation_of_the_position_is_initiated() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The position shall be {string}")
	public void the_position_shall_be(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("A game position is supplied with wall coordinate {int}:{int}-{string}")
	public void a_game_position_is_supplied_with_wall_coordinate(Integer int1, Integer int2, String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The position shall be valid")
	public void the_position_shall_be_valid() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The position shall be invalid")
	public void the_position_shall_be_invalid() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
}
