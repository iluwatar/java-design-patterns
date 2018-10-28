package com.iluwatar.masterworker;

/**
 *Class ArrayEquality has method matricesSame to compare values of 2
 *matrices (int[][]).
 */

public class ArrayEquality {
 
  /**
   * Method matricesSame compares 2 matrices @param m1 and @param m2
   * and @return whether their values are equal (boolean).
   */
	
  public static boolean matricesSame(int[][] m1, int[][] m2) {
    if (m1.length != m2.length) {
      return false;
    } else {
      boolean answer = false;
      for (int i = 0; i < m1.length; i++) {
        if (arraysSame(m1[i], m2[i])) {
          answer = true;
        } else {
          answer = false;
          break;
        }
      }
      return answer;
    }
  }

  static boolean arraysSame(int[] a1, int[] a2) {
    //compares if 2 arrays have the same value
    if (a1.length != a2.length) {
      return false;
    } else {
      boolean answer = false;
      for (int i = 0; i < a1.length; i++) {
        if (a1[i] == a2[i]) {
          answer = true;
        } else {
          answer = false;
          break;
        }
      }
      return answer;
    }
  }
}