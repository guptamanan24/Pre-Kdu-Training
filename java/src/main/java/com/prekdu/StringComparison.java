package com.prekdu;

import java.util.Scanner;

/** Compares two input strings for their length and equality. */
public final class StringComparison {

  private StringComparison() {
    // Prevent instantiation
  }

  /**
   * Main method to execute string comparison.
   *
   * @param args Command-line arguments
   */
  public static void main(final String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      System.out.println("Enter the first string:");
      String firstString = scanner.nextLine();

      System.out.println("Enter the second string:");
      String second = scanner.nextLine();

      System.out.println("Length of the first string: " + firstString.length());
      System.out.println("Length of the second string: " + second.length());

      if (firstString.length() == second.length()) {
        System.out.println("Both strings have the same length.");
      } else {
        System.out.println("The strings have different lengths.");
      }

      if (firstString.equals(second)) {
        System.out.println("The two strings are identical.");
      } else {
        System.out.println("The two strings are different.");
      }
    }
  }
}
