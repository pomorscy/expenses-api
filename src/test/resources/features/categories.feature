Feature: Obtain Categories information
  As an Expenses service
  I want to provide Categories information
  so that external services can utilise it.

  Scenario: Successfully return a Category when the service receives a valid request
    Given There are two categories
    When the client calls /categories
    Then the client receives status code of 200
    And the client receives a list containing these categories
