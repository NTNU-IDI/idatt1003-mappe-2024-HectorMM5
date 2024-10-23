package edu.ntnu.idi.idatt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GroceryTest {

  Grocery testGrocery;
  @BeforeEach
  void setUp() {
    testGrocery = new Grocery("Banan", "KG", 5.3f);
  }

  @Test
  void getName() {
    assertEquals("Banan", testGrocery.getName());
  }

  @Test
  void getUnit() {
    assertEquals("KG", testGrocery.getUnit());
  }

  @Test
  void getAmount() {
  }

  @Test
  void getCost() {
  }

  @Test
  void getExpiryDate() {
  }

  @Test
  void setAmount() {

  }

  @Test
  void use() {

  }
}