package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Class dedicated to validating user input. Improves readability and avoids cluttered code.
 */

public class ValidateInput {
  Scanner scanner = new Scanner(System.in);

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
      if (!(input == null || input.trim().isEmpty())) {
        isValid = true;
      } else {
        System.out.println("Please enter a valid string.");
      }
    }

    return input.trim();
  }


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
   * Validates a numeric input from the user and converts it to an integer.
   *
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