package ca.mcgill.ecse223.quoridor.features.stepfiles;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.*;
import io.cucumber.java.After;
import io.cucumber.java.en.*;

public class MoveWall
{
	@Given("A wall move candidate exists with {string} at position \\({int}, {int})")
	public void a_wall_move_candidate_exists_with_at_position(String string, Integer int1, Integer int2) {
		// Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("The wall candidate is not at the {string} edge of the board")
	public void the_wall_candidate_is_not_at_the_edge_of_the_board(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("I try to move the wall {string}")
	public void i_try_to_move_the_wall(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The wall shall be moved over the board to position \\({int}, {int})")
	public void the_wall_shall_be_moved_over_the_board_to_position(Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("A wall move candidate shall exist with {string} at position \\({int}, {int})")
	public void a_wall_move_candidate_shall_exist_with_at_position(String string, Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	
	
	@Given("The wall candidate is at the {string} edge of the board")
	public void the_wall_candidate_is_at_the_edge_of_the_board(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("I shall be notified that my move is illegal")
	public void i_shall_be_notified_that_my_move_is_illegal() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
}
