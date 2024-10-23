package edu.ntnu.idi.idatt;

import java.time.LocalDate;


public class Grocery {

  private String name;
  private String unit;
  private float amount;
  private float cost;
  private LocalDate expiryDate;

  public Grocery(String name, String unit, float amount, float cost, LocalDate expiryDate) {
    this.name = name;
    this.unit = unit;
    this.amount = amount;
    this.cost = cost / this.amount;
    this.expiryDate = expiryDate;

  }

  /**
   * Grocery Constructor.
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

  public void setAmount(float amount) {
    this.amount = amount;
  }

  public void use(float amount) {
    if (amount <= 0) {
      System.out.println("Amount must be greater than zero.");
    } else if (this.amount >= amount) {
      this.amount -= amount;
    } else {
      System.out.println("You do not have enough " + this.name + " to take out that amount.");
    }
  }

}