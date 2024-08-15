Feature:  User Management

  Scenario: View all users
    Given the admin is logged in
    When the admin navigates to the user management page
    Then the admin should see a list of all users including store owners and suppliers


  Scenario: Add a new user account with a unique email
    Given the admin is logged in
    And the following users already exist:
      | username  | email               | role        |
      | Wafaa  | wafaa@gmail.com   | Admin       |
      | Aleen  | Aleen@gmail.com | Store Owner |
    When the admin adds a new user with the following details:
      | username |  password   |email   | role        |
      | Amal    | Pass1234 | Amal@gmail.com | Supplier    |
    Then the user account should be created successfully
    And the new user should receive an email notification


  Scenario: Add a new user account with an existing email
    Given the admin is logged in
    And the following users already exist:
      | username  | email               | password| role        |
      | Wafaa  | wafaa@gmail.com    | 1482003     | Admin       |
    When the admin tries to add a new user with the following details:
      | username | email              | password | role        |
      | Shahd | wafaa@gmail.com| 5678 | Store Owner |
    Then the user account should not be created
    And an error message "This email is already registered" should be displayed


  Scenario: Edit an existing user account
    Given the admin is logged in
    And the following users already exist:
      | username  | email               | role        |
      | Aleen  | Aleen@gmail.com | Store Owner |
    When the admin selects the user with username "Aleen" and updates the role to "Supplier"
    Then the user's role should be updated successfully
    And the updated information should be reflected in the user table


  Scenario: Delete a user account
    Given the admin is logged in
    And the following users already exist:
      | username  | email               | role        |
      | Amal  | Amal@gmail.com | Supplier |
    When the admin selects the user with username "Amal" and deletes the account
    Then the user account should be removed from the system
    And the user should no longer appear in the user table
