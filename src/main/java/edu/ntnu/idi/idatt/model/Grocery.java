package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a food/grocery. Takes in name, unit and amount, optionally cost and expiryDate.
 * Second constructor is generally to be used in cookbook, where cost and expiryDate are irrelevant.
 */

public class Grocery {

  private final String name;
  private final Unit unit;
  private float amount;
  private float cost;
  private LocalDate expiryDate;

  /**
   * Creates a grocery item with these parameters. Generally kept for food storage.
   *
   * @param name * Name of grocery item.
   * @param unit * Unit of grocery item.
   * @param amount * Amount of grocery item, measured in units.
   * @param cost * Cost per unit.
   * @param expiryDate * LocalDate object, represents the expiry date of the grocery.
   */

  public Grocery(String name, Unit unit, float amount, float cost, LocalDate expiryDate) {
    this.name = name;
    this.unit = unit;
    this.amount = amount * unit.getValue();
    this.cost = cost / this.amount;
    this.expiryDate = expiryDate;

  }

  /**
   * Creates a grocery item with these parameters, generally kept for cookbook purposes.
   * (Recipes are not dependent on item cost or item expiry dates).
   *
   * @param name   name of grocery
   * @param unit   unit of grocery
   * @param amount amount of grocery
   */
  public Grocery(String name, Unit unit, float amount) {
    this.name = name;
    this.unit = unit;
    this.amount = amount * unit.getValue();

  }

  /**
   * Creates a grocery item with these parameters. Used exclusively for profiles.
   * Purpose: (example) User may not register an item as grams and attempt to use pieces.
   *
   * @param name name of grocery
   * @param unit unit of grocery
   */
  public Grocery(String name, Unit unit) {
    this.name = name;
    this.unit = unit;
  }

  public String getName() {
    return this.name;
  }

  public Unit getUnit() {
    return this.unit;
  }

  public float getAmount() {
    return this.amount;
  }

  public float getCost() {
    return this.cost;
  }

  public LocalDate getExpiryDate() {
    return this.expiryDate;
  }

  /**
   * Sets a new amount for an existing grocery object. Input quality ensured elsewhere.
   *
   * @param amount * Amount to be changed to.
   */

  public void setAmount(float amount) {
    this.amount = amount;

  }

  /**
   * String representing the object's state. Used for user information.
   */

  @Override
  public String toString() {
    LocalDate date = LocalDate.of(2024, 12, 25); // Example date

    //ChatGPT
    // Create a formatter with a user-friendly format
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    // Format the date and print it
    String formattedDate = date.format(formatter);

    return (name + " " + amount + " " + unit + " " + "(costs " + cost + " euros per "
        + unit.getMetric() + ") " + "(" + formattedDate + ")");

  }

}
