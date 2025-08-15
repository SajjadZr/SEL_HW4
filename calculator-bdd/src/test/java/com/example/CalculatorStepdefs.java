package com.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;

public class CalculatorStepdefs {

    private Calculator calculator;
    private double result;
    private Exception thrownException;

    @Given("I have a calculator")
    public void i_have_a_calculator() {
        calculator = new Calculator();
    }

    @When("I enter {double} and {double} with the operator {string}")
    public void i_enter_and_with_the_operator(Double operand1, Double operand2, String operator) {
        result = calculator.calculate(operand1, operand2, operator);
    }

    @Then("the result should be {double}")
    public void the_result_should_be(Double expectedResult) {
        assertEquals(expectedResult, result, 0.001);
    }

    @When("I try to divide {double} by {double}")
    public void i_try_to_divide_by(Double operand1, Double operand2) {
        try {
            calculator.calculate(operand1, operand2, "/");
        } catch (IllegalArgumentException e) {
            thrownException = e;
        }
    }

    @Then("the operation should throw an exception")
    public void the_operation_should_throw_an_exception() {
        assertNotNull(thrownException);
        assertTrue(thrownException instanceof IllegalArgumentException);
    }
}