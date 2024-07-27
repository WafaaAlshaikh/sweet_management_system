Feature: User Login
  As a user
  I want to log in to the Sweet Management System
  So that I can access my account and use the system's features

  Scenario: User logs in successfully
    Given I am on the login page
    When I enter my username "wafaa@gmail.com"
    And I enter my password "1482003"
    And I click on the "Login" button and flag is 'true'
    Then I should be redirected to the dashboard page
    And I should see a welcome message "Welcome, wafaa@gmail.com!"

  Scenario: User enters incorrect password
    Given I am on the login page
    When I enter my username1 "wafaa@gmail.com"
    And I enter my password1 "148203"
    And I click on the "Login" buttonn
    Then I should see an error message "Invalid username or password"

  Scenario: User tries to log in with an unregistered username
    Given I am on the login page
    When I enter my username2 "rrrr"
    And I enter my password2 "1234"
    And I click on the "Login" button
    Then I should see an error message "Invalid username or password"


  Scenario:empty fields
    Given I am on the login page
    When I leave the username and password fields empty
    And I click the login button
    Then I should see an error message indicating that fields cannot be empty