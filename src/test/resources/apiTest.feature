@severity=critical
Feature: api test cbr
  Scenario: check response body, then assert that response data is equal to tinkoff
    Given response has returned with status 200, json content type, and json schema has passed
    Then Date and TimeStamp should be correct dates, and response should have have eur and usd courses
    When tinkoff is open
    Then eur course from CBR should be almost equal to tinkoff's course
    When i change ruble to dollar
    Then usd to euro couses should be equal too