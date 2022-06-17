package com.revature;

import com.revature.exception.Number13Exception;
import com.revature.exception.DivideBy0Exception;

/**
 * @author KevinTranHuu
 * @author Trey Ratcliff
 *
 */
public class Calculator {
    /*
     * - add(int a, int b)
     * 		- if the result is 13
     * 			- throw an exception
     * 				- Number13Exception
     * - subtract
     * - multiply
     * - sum of an array
     * - divide
     * 		- throw a custom exception if divided by 0
     */

    public int add(int a, int b) {
        if(a + b == 13) {
            throw new Number13Exception();
        }
        return a + b;
    }

    public int subtract(int a, int b) {
        if(a - b == 13){
            throw new Number13Exception();
        }
        return a - b;
    }

    public int multiply(int a, int b){
        return a * b;
    }

    public int sumOfAnArray(int[] intArr) {
        int sum = 0;
        for (int i = 0; i < intArr.length; i++) {
            sum = sum + intArr[i];
        }
        if(sum == 13){
            throw new Number13Exception();
        }
        return sum;
    }

    public int divide (int a, int b) throws DivideBy0Exception{
        if(b != 0){
            return a / b;
        } else{
            throw new DivideBy0Exception();
        }
    }
}