package edu.ntnu.idi.idatt;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




class GroceryTest {

  Grocery testGrocery;
  @BeforeEach
  void setUp() {
    testGrocery = new Grocery("Banana", "KG", 5f, 20, LocalDate.of(2024, 12, 24));
  }

  @Test
  void getName() {
    assertEquals("Banana", testGrocery.getName());
  }

  @Test
  void getUnit() {
    assertEquals("KG", testGrocery.getUnit());
  }

  @Test
  void getAmount() {
    assertEquals(5f, testGrocery.getAmount());
  }

  @Test
  void getCost() {
    assertEquals(4, testGrocery.getCost());
  }

  @Test
  void getExpiryDate() {
    LocalDate expectedDate = LocalDate.of(2024, 12, 24);
    assertEquals(expectedDate, testGrocery.getExpiryDate());
  }

  @Test
  void setAmountPositive() {
    testGrocery.setAmount(10);  // Set a valid amount
    assertEquals(10, testGrocery.getAmount());
  }

  @Test
  void setAmountNegative() {
    assertThrows(IllegalArgumentException.class, () -> testGrocery.setAmount(-5));

    assertThrows(IllegalArgumentException.class, () -> testGrocery.setAmount(0));
  }
}