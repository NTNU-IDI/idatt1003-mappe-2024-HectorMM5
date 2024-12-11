package edu.ntnu.idi.idatt.util;

import java.util.Scanner;

/**
 * Class dedicated to validating user input. Improves readability and avoids cluttered code.
 */

public class ValidateInput {
  /**
   * Validates a string input ensuring it is not null or empty.
   *
   * @return The validated string.
   * @throws IllegalArgumentException if the input is null or empty.
   */

  public static String forceValidString(Scanner scanner) {

    String input = null;
    boolean isValid = false;

    while (!isValid) {
      input = scanner.nextLine();
      //Regex from ChatGPT
      if (input != null && input.matches("[a-zA-Z0-9 .,/]+") && !input.trim().isEmpty()) {
        isValid = true;
      } else {
        System.out.println("Please enter a valid string.");
      }
    }

    return input.trim();
  }

  /**
   * Forces the user to input a valid float, through scanner.
   *
   * @param scanner Scanner object to access user input.
   * @return The validated float.
   */

  public static float forceValidFloat(Scanner scanner) {
    float value = 0;
    boolean isValid = false;

    while (!isValid) {
      try {
        value = Float.parseFloat(scanner.nextLine().trim());
        if (value > 0) {
          isValid = true;
        } else {
          System.out.println("Value must be greater than zero.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid numeric value. Try again.");
      }
    }

    return value;
  }

  /**
   * Forces the user to input a valid float, through scanner.
   *
   * @param scanner Scanner object to access user input.
   * @return The validated integer value.
   */

  public static int forceValidInteger(Scanner scanner) {
    int value = 0;
    boolean isValid = false;

    while (!isValid) {
      try {
        value = Integer.parseInt(scanner.nextLine().trim());
        if (value > 0) {
          isValid = true;
        } else {
          System.out.println("Value must be greater than zero.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid numeric value. Try again.");
      }
    }

    return value;
  }

}
