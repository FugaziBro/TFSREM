@severity=critical
Feature: ui test tinkoff exchange page
  Scenario: get to tinkoff exchange page and check that convertation works correctrly
    Given tinkoff exchange page is open, page is loaded, and links are available
    Then check that exchange article is selected
    Then selectors should contain ruble and euro currencies and table row their convertation text
    When change ruble to euro in first selector
    Then selectors value should change
    When i change ruble to dollar in second selector
    Then selectors should contain euro and dollar and table row their convertation text