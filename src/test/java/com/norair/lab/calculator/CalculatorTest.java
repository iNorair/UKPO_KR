package com.norair.lab.calculator;

import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {

    private Calculator calc = new Calculator();

    @Test
    public void testAddition() {
        //given
        String input = "2+3";
        String expectedResult = "5";

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testSubtraction() {
        //given
        String input = "4-6";
        String expectedResult = "-2";

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testMultiplication() {
        //given
        String input = "2*3";
        String expectedResult = "6";

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testDivision() {
        //given
        String input = "12/3";
        String expectedResult = "4";

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testAllOperations() {
        //given
        String input = "10/2-7+3*4";
        String expectedResult = "10";

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testAllOperationsOrderWithBrackets() {
        //given
        String input = "10/(2-7+3)*4";
        String expectedResult = "-20";

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }


    @Test
    public void testIncorrectBrackets() {
        //given
        String input = "- 12)1//(";
        String expectedResult = null;

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testDivisionByZero() {
        //given
        String input = "10/(5-5)";
        String expectedResult = null;

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testNullInput() {
        //given
        String input = null;
        String expectedResult = null;

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testNotMatchingBrackets() {
        //given
        String input = "(12*(5-1)";
        String expectedResult = null;

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testEmptyString() {
        //given
        String input = "";
        String expectedResult = null;

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testDoubleOperation() {
        //given
        String input = "5++41-6";
        String expectedResult = null;

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testDoubleMinus() {
        //given
        String input = "5--41-6";
        String expectedResult = "40";

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testTripleMinus() {
        //given
        String input = "5---6";
        String expectedResult = null;

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testFloat() {
        //given
        String input = "-8*(99-(-6*2+2.2895*2))";
        String expectedResult = "-851.368";

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testWithInnerBrackets() {
        //given
        String input = "-8+(1+2+(3+(1+2)+4)+6)";
        String expectedResult = "11";

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testWithMultipleBrackets() {
        //given
        String input = "(2-1)*(2+3)";
        String expectedResult = "5";

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testIncorrectOperations() {
        //given
        String input = "2+*6";
        String expectedResult = null;

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testIncorrectOperationAtTheEnd() {
        //given
        String input = "2+3*4+";
        String expectedResult = null;

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testIncorrectSymbols() {
        //given
        String input = "2*b";
        String expectedResult = null;

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testOriginal() {
        //given
        String input = "2+(-5)*(7-8)";
        String expectedResult = "7";

        //run
        String result = calc.evaluate(input);

        //assert
        Assert.assertEquals(expectedResult, result);
    }

}