Feature: Content Management

  Scenario: Admin creates a new post
    Given the admin is logged in
    When the admin creates a post with title "Summer Sweet Treats" and content "New summer recipes!"
    Then the post should be visible in the post list

  Scenario: Admin updates an existing post
    Given the admin is logged in
    And a post with title "Summer Sweet Treats" exists
    When the admin updates the post content to "Refreshing summer recipes!"
    Then the post should display the updated content

  Scenario: Admin deletes a post
    Given the admin is logged in
    And a post with title "Summer Sweet Treats" exists
    When the admin deletes the post
    Then the post should no longer be visible in the post list

  Scenario: Admin views user feedback
    Given the admin is logged in
    When the admin views user feedback
    Then the system should display a list of all user feedback

  Scenario: Admin responds to user feedback
    Given the admin is logged in
    And feedback with ID "4" exists
    When the admin sends an email to user with ID "25" with subject "Thank you" and content "We appreciate your feedback."
    Then the email should be sent

  Scenario: Admin deletes user feedback
    Given the admin is logged in
    And feedback with ID "6" exists
    When the admin deletes the feedback
    Then the feedback should no longer be visible in the list

  Scenario: User sends a message
    Given the user with ID "1" is logged in
    When the user sends a message to user with ID "25" with content "Details on the order?"
    Then the message should be sent


  Scenario: User views received messages
    Given the user with ID "25" is logged in
    When the user views their inbox
    Then all received messages should be displayed


  Scenario: User marks a message as read
    Given the user with ID "1" is logged in
    And a message with content "Details on the order?" is unread
    When the user marks the message as read
    Then the message status should be updated to "true"

  Scenario: User deletes a message
    Given the user with ID "25" is logged in
    And a message with content "Details on the order?" exists
    When the user deletes the message
    Then the message should no longer be visible in the inbox
