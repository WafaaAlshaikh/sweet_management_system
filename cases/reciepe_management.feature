Feature: Recipe Management

Scenario: Admin adds a new recipe
Given the admin is logged in
When the admin adds a new recipe with name "Chocolate Cake" and content "A delicious chocolate cake recipe"
Then the recipe should be successfully added to the system
And the recipe with name "Chocolate Cake" should be visible in the recipe list

Scenario: Admin updates an existing recipe
Given the admin is logged in
And a recipe with name "Chocolate Cake" exists
When the admin updates the recipe content to "An updated delicious chocolate cake recipe"
Then the recipe should be updated in the system
And the recipe with name "Chocolate Cake" should display the updated content

Scenario: Admin deletes a recipe
Given the admin is logged in
And a recipe with name "Chocolate Cake" exists
When the admin deletes the recipe with name "Chocolate Cake"
Then the recipe should be removed from the system
And the recipe with name "Chocolate Cake" should no longer be visible in the recipe list


Scenario: User views a recipe
Given a recipe with name "Chocolate Cake" exists
When the user views the recipe with name "Chocolate Cake"
Then the system should display the recipe content "A delicious chocolate cake recipe"


Scenario: User adds a recipe to their account
    Given the user with ID "1" is logged in
    When the user adds a recipe with name "Vanilla Cake" and content "A simple vanilla cake recipe"
    Then the recipe should be added to the system and associated with the user
    And the recipe with name "Vanilla Cake" should be visible in the user's recipe list