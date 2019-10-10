package ca.mcgill.ecse223.quoridor.features.stepfiles;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.features.CucumberStepDefinitions;
import ca.mcgill.ecse223.quoridor.model.*;
import io.cucumber.java.After;
import io.cucumber.java.en.*;

public class ProvideSelectUserName
{
	@Given("A new game is initializing")
	public void a_new_game_is_initializing() {
	    //CucumberStepDefinitions.initQuoridorAndBoard();
	    //createUsersAndPlayers("user1", "user2");
	    throw new cucumber.api.PendingException();
	}

	@Given("Next player to set user name is {string}")
	public void next_player_to_set_user_name_is(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("There is existing user {string}")
	public void there_is_existing_user(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("The player selects existing {string}")
	public void the_player_selects_existing(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The name of player {string} in the new game shall be {string}")
	public void the_name_of_player_in_the_new_game_shall_be(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("There is no existing user {string}")
	public void there_is_no_existing_user(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("The player provides new user name: {string}")
	public void the_player_provides_new_user_name(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("The player shall be warned that {string} already exists")
	public void the_player_shall_be_warned_that_already_exists(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("Next player to set user name shall be {string}")
	public void next_player_to_set_user_name_shall_be(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

}
