Feature: User Registration

  Background:
    Given I am on the registration page

  Scenario: Attempting to sign up with an existing username
    Given a user with the username "existingUser" already exists
    When I enter a username "Wafaa"
    And I enter a password "1234"
    And I confirm the password "1234"
    And I enter an email "wafaa@gmail.com"
    And I select the role "Admin"
    Then message is displayed "Username already exists"

  Scenario: Attempting to sign up with mismatched passwords
    When I enter a username "Razan"
    And I enter a password "1234"
    And I confirm the password "12345"
    And I enter an email "razan@gmail.com"
    And I select the role "Customer"
    Then I should see an invalid message "Invalid username or password"

  Scenario: Attempting to sign up with empty fields
    When I enter a username ""
    And I enter a password ""
    And I confirm the password ""
    And I enter an email ""
    And I select the role ""
    Then this message will displayed "Invalid username or password"

  Scenario: Successful registration with valid details and specific role
    When I fill in 'ID' with '55'
    And I fill in 'Name' with 'Ahmad'
    And I fill in 'Password' with '05*-Aa'
    And I fill in 'Email' with 'shahd22@gmail.com'
    And I fill in 'Role' with 'Customer'
    And I click on "Sign Up"
    Then information message 'Information has been entered successfully'

  Scenario Outline: Errors with input
    When I fill in 'ID' with '<ID>'
    And I fill in 'Name' with '<Name>'
    And I fill in 'Email' with '<Email>'
    And I fill in 'Password' with '<Password>'
    And I fill in 'Role' with '<Role>'
    Then displayeddd '<message>'

    Examples:
      | ID   | Name   | Password     | Email               | Role       | message                           |
      | 123  | Ahmad  | password123  | ahmad@gmail.com     | Admin      | 'Information has been entered successfully' |
      | 456  | Razan  | mypass321    | mary@gmail.com      | Customer   | 'Invalid email format'             |
      |     | Rawan  | pass456      | khaled@gmail.com    | Customer   | 'ID cannot be empty'               |
      | 789  | Sami   | short         | sami@gmail.com      | Manager    | 'Role not valid'                   |

  @duplicate-email
  Scenario: User attempts to register with a duplicate email
    Given a user with the email "wafaa@gmail.com" already exists
    When I enter a username "Lama"
    And I enter a password "1234"
    And I confirm the password "1234"
    And I enter the email "wafaa@gmail.com"
    And I select the role "Admin"
    Then I will look a popup message indicating the email is already in use
