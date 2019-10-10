package ca.mcgill.ecse223.quoridor.features.stepfiles;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.*;
import io.cucumber.java.After;
import io.cucumber.java.en.*;

public class RotateWall
{
	@When("I try to flip the wall")
	public void i_try_to_flip_the_wall() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The wall shall be rotated over the board to {string}")
	public void the_wall_shall_be_rotated_over_the_board_to(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
}
