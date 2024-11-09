package edu.ntnu.idi.idatt;

import java.time.LocalDate;

/**
 * Represents a food/grocery. Takes in name, unit and amount, optionally cost and expiryDate.
 * Second constructor is generally to be used in cookbook, where cost and expiryDate are irrelevant.
 */

public class Grocery {

  private final String name;
  private final String unit;
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

  public Grocery(String name, String unit, float amount, float cost, LocalDate expiryDate) {
    this.name = name;
    this.unit = unit;
    this.amount = amount;
    this.cost = cost / this.amount;
    this.expiryDate = expiryDate;

  }

  /**
   * Creates a grocery item with these parameters, generally kept for cookbook purposes.
   *
   * @param name   name of grocery
   * @param unit   unit of grocery
   * @param amount amount of grocery
   */
  public Grocery(String name, String unit, float amount) {
    this.name = name;
    this.unit = unit;
    this.amount = amount;

  }

  public String getName() {
    return this.name;
  }

  public String getUnit() {
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
   * Sets a new amount for an existing grocery object.
   *
   * @param amount * Amount to be changed to.
   */

  public void setAmount(float amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be greater than zero.");
    } else {
      this.amount = amount;
    }
  }

  /**
   * Uses a given amount of the total amount.
   *
   * @param amount * Amount to be consumed.
   */

  public void use(float amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be greater than zero.");
    } else if (this.amount < amount) {
      throw new IllegalStateException("Not enough " + this.name + " to use the specified amount.");
    } else {
      this.amount -= amount;
      System.out.println("You take out " + amount + " " + this.getUnit() + " "
          + this.getName() + ", and have " + this.getAmount() + " "
          + this.getUnit() + " left.");
    }
  }

}