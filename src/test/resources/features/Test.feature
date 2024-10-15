@all
Feature: Testing apis

  @qa
  Scenario: Verify API is up and running
    When User hits the API
    Then Verify that API gives 200 response code