package com.iluwatar.masterworker;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
* Testing utility methods in {@link ArrayUtilityMethods} class.
*/

class ArrayUtilityMethodsTest {

  @Test
  void arraysSameTest() {
    int[] arr1 = new int[] {1,4,2,6};
    int[] arr2 = new int[] {1,4,2,6};
    assertTrue(ArrayUtilityMethods.arraysSame(arr1, arr2));
  }

  @Test
  void matricesSameTest() {
    int[][] matrix1 = new int[][] {{1,4,2,6},{5,8,6,7}};
    int[][] matrix2 = new int[][] {{1,4,2,6},{5,8,6,7}};
    assertTrue(ArrayUtilityMethods.matricesSame(matrix1, matrix2));
  }

}
