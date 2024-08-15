package com.example.sweetSystem.AcceptanceTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.example.sweetsystem.TestFeatures;

import static org.junit.Assert.*;

public class recipes {
    private TestFeatures testFeatures = new TestFeatures();

    @When("the admin adds a new recipe with name {string} and content {string}")
    public void theAdminAddsANewRecipeWithNameAndContent(String name, String content) {
        testFeatures.addRecipe(1, name, content);
    }

    @Then("the recipe should be successfully added to the system")
    public void theRecipeShouldBeSuccessfullyAddedToTheSystem() {
        assertTrue(testFeatures.isRecipeAdded());
    }

    @Then("the recipe with name {string} should be visible in the recipe list")
    public void theRecipeWithNameShouldBeVisibleInTheRecipeList(String name) {
        assertTrue(testFeatures.isRecipeVisible(name));
    }

    @When("the admin updates the recipe content to {string}")
    public void theAdminUpdatesTheRecipeContentTo(String content) {
        testFeatures.updateRecipe("Chocolate Cake", content);
    }

    @Then("the recipe should be updated in the system")
    public void theRecipeShouldBeUpdatedInTheSystem() {
        assertTrue(testFeatures.isRecipeUpdated());
    }

    @Then("the recipe with name {string} should display the updated content")
    public void theRecipeWithNameShouldDisplayTheUpdatedContent(String name) {
        String content = testFeatures.getRecipeContent(name);
        assertEquals("An updated delicious chocolate cake recipe", content);
    }

    @When("the admin deletes the recipe with name {string}")
    public void theAdminDeletesTheRecipeWithName(String name) {
        testFeatures.deleteRecipe(name);
    }

    @Then("the recipe should be removed from the system")
    public void theRecipeShouldBeRemovedFromTheSystem() {
        assertTrue(testFeatures.isRecipeDeleted());
    }

    @Then("the recipe with name {string} should no longer be visible in the recipe list")
    public void theRecipeWithNameShouldNoLongerBeVisibleInTheRecipeList(String name) {
        assertFalse(testFeatures.isRecipeVisible(name));
    }

    @Given("a recipe with name {string} exists")
    public void aRecipeWithNameExists(String name) {
        testFeatures.createRecipe(1, name, "A delicious chocolate cake recipe");
    }

    @When("the user views the recipe with name {string}")
    public void theUserViewsTheRecipeWithName(String name) {
        String content = testFeatures.viewRecipe(name);
        assertEquals("A delicious chocolate cake recipe", content);
    }

    @Then("the system should display the recipe content {string}")
    public void theSystemShouldDisplayTheRecipeContent(String content) {
    }

    @When("the user adds a recipe with name {string} and content {string}")
    public void theUserAddsARecipeWithNameAndContent(String name, String content) {
        testFeatures.addUserRecipe(1, name, content);
    }

    @Then("the recipe should be added to the system and associated with the user")
    public void theRecipeShouldBeAddedToTheSystemAndAssociatedWithTheUser() {
        assertTrue(testFeatures.isUserRecipeAdded());
    }

    @Then("the recipe with name {string} should be visible in the user's recipe list")
    public void theRecipeWithNameShouldBeVisibleInTheUserSRecipeList(String name) {
        assertTrue(testFeatures.isUserRecipeVisible(name, 1));
    }
}
