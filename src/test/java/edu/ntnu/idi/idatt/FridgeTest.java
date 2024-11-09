package edu.ntnu.idi.idatt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;


public class FridgeTest {
  Fridge fridge;
  @BeforeEach
  void setUp() {
    fridge = new Fridge();
    fridge.ingredients.add(new Grocery("Chocolate", "KG", 1, 10, LocalDate.of(2020, 1, 1)));

  }

  @Test
  void testNewGrocery() {
    fridge.newGrocery("White chocolate", "KG", 1, 10, LocalDate.of(2020, 1, 1));
    assertEquals("White chocolate", fridge.ingredients.get(1).getName());
    assertEquals("KG", fridge.ingredients.get(1).getUnit());
    assertEquals(1, fridge.ingredients.get(1).getAmount());
    assertEquals(10, fridge.ingredients.get(1).getCost());
    assertEquals(LocalDate.of(2020, 1, 1), fridge.ingredients.get(1).getExpiryDate());
  }


}
