package ca.mcgill.ecse223.quoridor.features.stepfiles;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.*;
import io.cucumber.java.After;
import io.cucumber.java.en.*;

public class LoadPosition
{
	@When("I initiate to load a saved game {string}")
	public void i_initiate_to_load_a_saved_game(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("The position to load is valid")
	public void the_position_to_load_is_valid() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("It shall be {string}'s turn")
	public void it_shall_be_s_turn(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("{string} shall be at {int}:{int}")
	public void shall_be_at(String string, Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("{string} shall have a vertical wall at {int}:{int}")
	public void shall_have_a_vertical_wall_at(String string, Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("{string} shall have a horizontal wall at {int}:{int}")
	public void shall_have_a_horizontal_wall_at(String string, Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("Both players shall have {int} in their stacks")
	public void both_players_shall_have_in_their_stacks(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("The position to load is invalid")
	public void the_position_to_load_is_invalid() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The load shall return an error")
	public void the_load_shall_return_an_error() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
}
