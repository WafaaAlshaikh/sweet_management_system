package com.example.sweetSystem.AcceptanceTest;

import com.example.sweetsystem.TestFeatures;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

public class UserManagement {
    private TestFeatures testFeatures = new TestFeatures();
    private boolean result;

    @Given("the admin is logged in")
    public void theAdminIsLoggedIn() {
        result = testFeatures.loginClicked("wafaa@gmail.com", "1482003");
        Assertions.assertTrue(result, "Admin should be logged in successfully.");
    }

    @When("the admin navigates to the user management page")
    public void theAdminNavigatesToTheUserManagementPage() {
        System.out.println("Admin navigates to the user management page.");
    }

    @Then("the admin should see a list of all users including store owners and suppliers")
    public void theAdminShouldSeeAListOfAllUsersIncludingStoreOwnersAndSuppliers() {
        System.out.println("Admin sees the list of all users.");
    }

    @Given("the following users already exist:")
    public void theFollowingUsersAlreadyExist(io.cucumber.datatable.DataTable dataTable) {
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            boolean userExists = testFeatures.isEmailAlreadyRegistered(row.get("email"));
            Assertions.assertTrue(userExists, "User with email " + row.get("email") + " should already exist.");
        }
    }

    @When("the admin adds a new user with the following details:")
    public void theAdminAddsANewUserWithTheFollowingDetails(io.cucumber.datatable.DataTable dataTable) {
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            result = testFeatures.registerUser(row.get("username"), row.get("password"), row.get("email"), row.get("role"));
        }
    }

    @Then("the user account should be created successfully")
    public void theUserAccountShouldBeCreatedSuccessfully() {
        Assertions.assertTrue(result, "The user account should be created successfully.");
    }

    @Then("the new user should receive an email notification")
    public void theNewUserShouldReceiveAnEmailNotification() {
        System.out.println("Email notification sent to the new user.");
    }

    @When("the admin tries to add a new user with the following details:")
    public void theAdminTriesToAddANewUserWithTheFollowingDetails(io.cucumber.datatable.DataTable dataTable) {
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            result = testFeatures.registerUser(row.get("username"), row.get("password"), row.get("email"), row.get("role"));
        }
    }

    @Then("the user account should not be created")
    public void theUserAccountShouldNotBeCreated() {
        Assertions.assertFalse(result, "The user account should not be created.");
    }

    @Then("an error message {string} should be displayed")
    public void anErrorMessageShouldBeDisplayed(String errorMessage) {
        System.out.println("Error: " + errorMessage);
    }

    @When("the admin selects the user with username {string} and updates the role to {string}")
    public void theAdminSelectsTheUserWithUsernameAndUpdatesTheRoleTo(String username, String newRole) {
        result = testFeatures.updateUserRole(username, newRole);
    }

    @Then("the user's role should be updated successfully")
    public void theUserSRoleShouldBeUpdatedSuccessfully() {
        Assertions.assertTrue(result, "User role should be updated successfully.");
    }

    @Then("the updated information should be reflected in the user table")
    public void theUpdatedInformationShouldBeReflectedInTheUserTable() {
        System.out.println("The user table reflects the updated role.");
    }

    @When("the admin selects the user with username {string} and deletes the account")
    public void theAdminSelectsTheUserWithUsernameAndDeletesTheAccount(String username) {
        result = testFeatures.deleteUser(username);
    }

    @Then("the user account should be removed from the system")
    public void theUserAccountShouldBeRemovedFromTheSystem() {
        Assertions.assertTrue(result, "User account should be removed from the system.");
    }

    @Then("the user should no longer appear in the user table")
    public void theUserShouldNoLongerAppearInTheUserTable() {
        System.out.println("User should no longer appear in the user table.");
    }
}
