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
  public static String ForceValidString(Scanner scanner) {

    String input = scanner.nextLine();

    if (input == null || input.trim().isEmpty()) {
      throw new IllegalArgumentException("Input cannot be null or empty.");
    }
    return input.trim();
  }

  /**
   * Validates a numeric input in string format and converts it to a float.
   *
   * @param input The string to validate.
   * @return The validated float value.
   * @throws IllegalArgumentException if the input is not a valid number or is negative.
   */
  public static float validateFloat(String input) {
    try {
      float value = Float.parseFloat(input.trim());
      if (value <= 0) {
        throw new IllegalArgumentException("Value cannot be negative or zero.");
      }
      return value;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid numeric value. Please enter a valid number.", e);
    }
  }

  /**
   * Validates a numeric input in string format and converts it to an integer.
   *
   * @param input The string to validate.
   * @return The validated integer value.
   * @throws IllegalArgumentException if the input is not a valid integer or is negative.
   */
  public static int validateInteger(String input) {
    try {
      int value = Integer.parseInt(input.trim());
      if (value < 0) {
        throw new IllegalArgumentException("Value cannot be negative.");
      }
      return value;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid integer value. Please enter a valid number.", e);
    }
  }

  /**
   * Validates a date input in a specified format and converts it to a LocalDate object.
   *
   * @param input The string to validate.
   * @param format The expected date format (e.g., "ddMMyyyy").
   * @return The validated LocalDate object.
   * @throws IllegalArgumentException if the input does not match the format or is invalid.
   */
  public static LocalDate validateDate(String input, String format) {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
      return LocalDate.parse(input.trim(), formatter);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid date format. Please enter the date in the format " + format + ".", e);
    }
  }

  /**
   * Validates that a string matches a specific pattern.
   *
   * @param input The string to validate.
   * @param regex The regular expression pattern to match.
   * @return The validated string.
   * @throws IllegalArgumentException if the input does not match the pattern.
   */
  public static String validatePattern(String input, String regex) {
    if (!input.matches(regex)) {
      throw new IllegalArgumentException("Input does not match the required pattern.");
    }
    return input.trim();
  }
}
