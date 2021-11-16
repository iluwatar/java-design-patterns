Feature: List and Create New Simple Objects

  @DomainAppDemo
  Scenario: Existing simple objects can be listed and new ones created
    Given there are initially 10 simple objects
    When  I create a new simple object
    Then  there are 11 simple objects

  Scenario: When there are no objects at all
    Given there are initially 0 simple objects
    When  I create a new simple object
    And   I create another new simple object
    Then  there are 2 simple objects

