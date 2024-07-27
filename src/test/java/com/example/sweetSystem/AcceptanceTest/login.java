package com.example.sweetSystem.AcceptanceTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.example.TestFeatures;

import static org.junit.Assert.assertEquals;

public class login {

    public String em;
    public String pass;
    private boolean isLoginButtonClicked;

    TestFeatures testFeatures = new TestFeatures();


    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {

    }
    @When("I enter my username {string}")
    public void iEnterMyUsername(String string) {
        this.em=string;
    }
    @When("I enter my password {string}")
    public void iEnterMyPassword(String string) {
        this.pass=string;
    }
    @When("I click on the {string} button and flag is {string}")
    public void iClickOnTheButtonAndFlagIs(String button, String flag) {
        if ("Login".equals(button) && "true".equals(flag)) {
            isLoginButtonClicked = testFeatures.loginClicked(em, pass);
        }
    }
    @Then("I should be redirected to the dashboard page")
    public void iShouldBeRedirectedToTheDashboardPage() {
        assertEquals(true, isLoginButtonClicked);

    }
    @Then("I should see a welcome message {string}")
    public void iShouldSeeAWelcomeMessage(String message) {
        if (isLoginButtonClicked) {
            assertEquals("Welcome, " + em + "!", message);
        }
    }

    @When("I enter my username1 {string}")
    public void iEnterMyUsername1(String string) {
        this.em=string;
    }
    @When("I enter my password1 {string}")
    public void iEnterMyPassword1(String string) {
        this.pass=string;
    }
    @When("I click on the {string} buttonn")
    public void iClickOnTheButtonn(String button) {
        if ("Login".equals(button)) {
            isLoginButtonClicked = testFeatures.loginClicked(em, pass);
        }
    }
    @Then("I should see an error message {string}")
    public void iShouldSeeAnErrorMessage(String message) {
        assertEquals(false, isLoginButtonClicked);
        assertEquals("Invalid username or password", message);
    }

    @When("I enter my username2 {string}")
    public void iEnterMyUsername2(String string) {
        this.em=string;
    }
    @When("I enter my password2 {string}")
    public void iEnterMyPassword2(String string) {
       this.pass=string;
    }
    @When("I click on the {string} button")
    public void iClickOnTheButton(String button) {
        if ("Login".equals(button)) {
            isLoginButtonClicked = testFeatures.loginClicked(em, pass);
        }
    }



    @When("I leave the username and password fields empty")
    public void iLeaveTheUsernameAndPasswordFieldsEmpty() {
        this.em = "";
        this.pass = "";
    }
    @When("I click the login button")
    public void iClickTheLoginButton() {
        System.out.println("button s clicked");

    }
    @Then("I should see an error message indicating that fields cannot be empty")
    public void iShouldSeeAnErrorMessageIndicatingThatFieldsCannotBeEmpty() {
        isLoginButtonClicked = testFeatures.loginClicked(em, pass);
        assertEquals(false, isLoginButtonClicked);

    }



}
