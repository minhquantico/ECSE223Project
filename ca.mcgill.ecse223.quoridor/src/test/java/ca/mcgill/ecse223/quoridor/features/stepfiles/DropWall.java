package ca.mcgill.ecse223.quoridor.features.stepfiles;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.*;
import io.cucumber.java.After;
import io.cucumber.java.en.*;

public class DropWall
{
	@Given("The wall move candidate with {string} at position \\({int}, {int}) is valid")
	public void the_wall_move_candidate_with_at_position_is_valid(String string, Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("I release the wall in my hand")
	public void i_release_the_wall_in_my_hand() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("A wall move shall be registered with {string} at position \\({int}, {int})")
	public void a_wall_move_shall_be_registered_with_at_position(String string, Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("I shall not have a wall in my hand")
	public void i_shall_not_have_a_wall_in_my_hand() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("My move shall be completed")
	public void my_move_shall_be_completed() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("It shall not be my turn to move")
	public void it_shall_not_be_my_turn_to_move() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("The wall move candidate with {string} at position \\({int}, {int}) is invalid")
	public void the_wall_move_candidate_with_at_position_is_invalid(String string, Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("I shall be notified that my wall move is invalid")
	public void i_shall_be_notified_that_my_wall_move_is_invalid() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}


	@Then("It shall be my turn to move")
	public void it_shall_be_my_turn_to_move() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("No wall move shall be registered with {string} at position \\({int}, {int})")
	public void no_wall_move_shall_be_registered_with_at_position(String string, Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
}
