Feature: Calculator
  As a user
  I want to perform basic arithmetic operations
  So that I can get the correct result

  Scenario Outline: Perform a calculation
    Given I have a calculator
    When I enter <operand1> and <operand2> with the operator "<operator>"
    Then the result should be <result>

    Examples:
      | operand1 | operator | operand2 | result |
      | 10.0     | +        | 5.0      | 15.0   |
      | 20.0     | -        | 8.0      | 12.0   |
      | 7.0      | * | 6.0      | 42.0   |
      | 100.0    | /        | 10.0     | 10.0   |

  Scenario: Division by zero
    Given I have a calculator
    When I try to divide 10.0 by 0.0
    Then the operation should throw an exception