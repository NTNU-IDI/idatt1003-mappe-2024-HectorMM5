package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.util.ValidateInput;
import java.util.Scanner;

/**
 * Represents a unit (metric type) for groceries. Goal is to avoid the user
 */

public enum Unit {

  GRAM("g", 0.001f, "weight"),
  KILOGRAM("kg", 1.0f, "weight"),
  LITRE("L", 1.0f, "volume"),
  MILLILITRE("mL", 0.001f, "volume"),
  PIECES("pcs", 1f, "count");

  private final String metric;
  private final float value;
  private final String metricType;

  Unit(String metric, float value, String metricType) {
    this.metric = metric;
    this.value = value;
    this.metricType = metricType;
  }

  public String getMetric() {
    return this.metric;
  }

  public float getValue() {
    return this.value;
  }

  public String getMetricType() {
    return this.metricType;
  }

  /**
   * Searches for and returns a unit enum. If not found, throws error.
   *
   * @param metric Unit to be searched for.
   * @return Unit enum object.
   */
  public static Unit searchMetric(String metric) {
    for (Unit unit : Unit.values()) {
      if (unit.metric.equalsIgnoreCase(metric.trim())) {
        return unit;
      }
    }
    throw new IllegalArgumentException("Invalid unit: " + metric);
  }

  /**
   * Forces the user to input a valid, previously specified unit.
   *
   * @param scanner Scanner object to access user input.
   * @return The approved unit enum object.
   */
  public static Unit forceValidUnit(Scanner scanner) {
    Unit metric = null;
    String input;
    boolean isValid = false;

    while (!isValid) {
      input = ValidateInput.forceValidString(scanner);

      for (Unit type : Unit.values()) {
        if (type.metric.equalsIgnoreCase(input.trim())) {
          metric = type; // Assign the corresponding enum
          isValid = true; // Mark input as valid
          break;
        }
      }

      if (!isValid) {
        System.out.println("Invalid unit. Please enter one of the following: g, kg, L, mL or pcs.");
      }
    }
    return metric; // Return the matching Unit enum
  }
}
