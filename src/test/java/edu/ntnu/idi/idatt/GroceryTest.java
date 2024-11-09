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
  void setAmount() {
    testGrocery.setAmount(6);
    assertEquals(6, testGrocery.getAmount());

  }

  @Test
  void use() {
    testGrocery.use(4);
    assertEquals(1, testGrocery.getAmount());

  }
}