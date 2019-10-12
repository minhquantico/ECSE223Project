package ca.mcgill.ecse223.quoridor.features.stepfiles;

import static org.junit.Assert.assertTrue;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.*;
import cucumber.api.PendingException;
import io.cucumber.java.After;
import io.cucumber.java.en.*;


public static class Controller
{
	
	
	/**
	   * This method is used to set the thinking time for each player. This will dictate how much time the player has remaining. 
	   * It is called before the start of the game in order to set the initial remaining time for both players
	   * @param int minutes: This will be used to set the specific number of minutes of remaining time (thinking time) (must be less than the minute equivalent of 24 hours)
	   * @param int seconds: This will be used to set the specific number of seconds of remaining time (thinking time) for each player (must be less than the second equivalent of 24 hours)
	   */
private void setThinkingTime(int minutes, int seconds) 
{
	
	throw new java.lang.UnsupportedOperationException();
}
	


}

public class SetTotalThinkingTime
{
	@When("{int}:{int} is set as the thinking time")
	public void is_set_as_the_thinking_time(Integer int1, Integer int2) {
		throw new PendingException();
		//Controller.InitializeThinkingTime(int1, int2);

	}

	@Then("Both players shall have {int}:{int} remaining time left")
	public void both_players_shall_have_remaining_time_left(Integer int1, Integer int2) {
		Time time = new Time(0, int1, int2);
		assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().getRemainingTime().toString().equals(time.toString()) && QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().getNextPlayer().getRemainingTime().toString().equals(time.toString()));

	}
	
}
