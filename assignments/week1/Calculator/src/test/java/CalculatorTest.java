package com.revature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.revature.exception.DivideBy0Exception;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.exception.Number13Exception;

public class CalculatorTest {

    private static Calculator sut;

    /*
     * JUnit 5
     * 	- @BeforeAll
     *  - @BeforeEach
     *  - @AfterAll
     *  - @AfterEach
     *
     *  - @Test
     *  - @Ignore
     *  - @Order
     */

    // Used to set up the test
    @BeforeAll
    public static void setUp() {
        sut = new Calculator();
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("Tear down behavior.");
    }

    @BeforeEach
    public void before() {
        System.out.println("@Before each behavior");
    }

    @Test
    public void addOneAndTwo() {
        int expected = 3;
        int actual = sut.add(1, 2);

        assertEquals(expected, actual);
    }

    @Test
    public void addEightAndFive() {
        assertThrows(Number13Exception.class, () -> sut.add(8, 5));
    }

    @Test
    public void addZeroAndThirteen() {
        assertThrows(Number13Exception.class, () -> sut.add(0, 13));
    }

    @Test
    public void subtractTenAndFive(){
        int expected = 5;
        int actual = sut.subtract(10,5);

        assertEquals(expected, actual);
    }

    @Test
    public void subtractThirteenAndZero() {
        assertThrows(Number13Exception.class, () -> sut.subtract(13, 0));
    }

    @Test
    public void subtractEighteenAndFive(){
        assertThrows(Number13Exception.class, () -> sut.subtract(18,5));
    }

    @Test
    public void arrToTen(){
        int[] arr = {3,2,4,1};
        int expected = 10;
        int actual = sut.sumOfAnArray(arr);

        assertEquals(expected, actual);
    }

    @Test
    public void arrToThirteen(){
        int[] arr = {3,2,5,3};

        assertThrows(Number13Exception.class, () -> sut.sumOfAnArray(arr));
    }

    @Test
    public void divideTenByFive(){
        int expected = 2;
        int actual = sut.divide(10,5);

        assertEquals(expected,actual);
    }

    @Test
    public void divideByZero(){
        assertThrows(DivideBy0Exception.class, () -> sut.divide(10,0));
    }

    @Test
    public void multiplyTenByTen(){
        int expected = 100;
        int actual = sut.multiply(10,10);

        assertEquals(expected,actual);
    }

    @Test
    public void multiplyThousandByThousand(){
        int expected = 1000000;
        int actual = sut.multiply(1000,1000);

        assertEquals(expected,actual);
    }
}
