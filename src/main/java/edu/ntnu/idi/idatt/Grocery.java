package edu.ntnu.idi.idatt;

public class Grocery {

  private String name;
  private String unit;
  private float amount;
  private float cost;
  private int expiryDate;

  public Grocery(String name, String unit, float amount, float cost, int expiryDate) {
    this.name = name;
    this.unit = unit;
    this.amount = amount;
    this.cost = cost / this.amount;
    this.expiryDate = expiryDate;

  }

  /**
   * Grocery Constructor.
   *
   * @param name name of grocery
   * @param unit unit of grocery
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

  public int getExpiryDate() {
    return this.expiryDate;
  }

  public void setAmount(float amount) {
    this.amount = amount;
  }

  public void use(float amount) {
    if (this.amount > amount) {
      this.amount -= amount;
    } else {
      System.out.println("Du har ikke nok " + this.name + " til dette.");
    }
  }

}