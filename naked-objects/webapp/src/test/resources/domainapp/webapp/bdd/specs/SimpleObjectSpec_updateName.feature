Feature: Can modify name (with restrictions)

  @DomainAppDemo
  Scenario: Can modify name with most characters
    Given a simple object
    When  I modify the name to 'abc'
    Then  the name is now 'abc'

  @DomainAppDemo
  Scenario: Cannot modify name if has invalid character
    Given a simple object
    When  I attempt to change the name to 'abc&'
    Then  the name is unchanged
     And  a warning is raised


