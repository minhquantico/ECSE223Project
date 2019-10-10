package ca.mcgill.ecse223.quoridor.features.stepfiles;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.*;
import io.cucumber.java.After;
import io.cucumber.java.en.*;

public class SetTotalThinkingTime
{
	@When("{int}:{int} is set as the thinking time")
	public void is_set_as_the_thinking_time(Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("Both players shall have {int}:{int} remaining time left")
	public void both_players_shall_have_remaining_time_left(Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
}
