Feature: Monitoring and Reporting

  Scenario: Monitor profits and generate financial reports
    Given the admin is logged in
    When the admin navigates to the financial reports page
    Then the admin should see a summary of profits for each store
    And the admin should be able to generate a detailed financial report

  Scenario: Identify best-selling products in each store
    Given the admin is logged in
    When the admin navigates to the sales reports page
    Then the admin should see a list of the best-selling products in each store
    And the list should be sorted by sales volume

  Scenario: Gather and display statistics on registered users by city
    Given the admin is logged in
    When the admin navigates to the user statistics page
    Then the admin should see a summary of registered users by city
    And the summary should include the number of users in each city (e.g., Nablus, Jenin)
