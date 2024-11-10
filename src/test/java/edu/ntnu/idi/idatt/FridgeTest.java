package edu.ntnu.idi.idatt;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

public class FridgeTest {

  private Fridge fridge;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @BeforeEach
  void setUp() {
    fridge = new Fridge();
    fridge.ingredients.add(new Grocery("Chocolate", "KG", 1, 10, LocalDate.of(2025, 1, 1)));
    System.setOut(new PrintStream(outContent));
  }

  @Test
  void newGrocery() {
    fridge.newGrocery("White chocolate", "KG", 1, 10, LocalDate.of(2020, 1, 1));
    assertEquals("White chocolate", fridge.ingredients.get(1).getName());
    assertEquals("KG", fridge.ingredients.get(1).getUnit());
    assertEquals(1, fridge.ingredients.get(1).getAmount());
    assertEquals(10, fridge.ingredients.get(1).getCost());
    assertEquals(LocalDate.of(2020, 1, 1), fridge.ingredients.get(1).getExpiryDate());

    assertTrue(outContent.toString().contains("Your grocery has been put in the fridge."));
  }

  @Test
  void usePositive() {
    fridge.use("Chocolate", 0.5f);
    assertEquals(0.5f, fridge.ingredients.get(0).getAmount());
  }

  @Test
  void useNotFound() {
    fridge.use("Vanilla", 0.5f);
    assertTrue(outContent.toString().contains("The ingredient you're looking for does not exist."));
  }

  @Test
  void useInsufficientAmount() {
    assertThrows(IllegalStateException.class, () -> fridge.use("Chocolate", 2.0f));
  }

  @Test
  void useReachesZero() {
    fridge.ingredients.add(new Grocery("Milk", "L", 1.0f, 5, LocalDate.of(2024, 12, 1)));

    fridge.use("Milk", 1.0f);

    assertNull(fridge.search("Milk"));
    assertEquals(1, fridge.ingredients.size());
  }

  @Test
  void searchPositive() {
    assertEquals(fridge.ingredients.get(0), fridge.search("Chocolate"));
  }

  @Test
  void searchNegative() {
    assertNull(fridge.search("Dark chocolate"));
  }

  @Test
  void searchIllegalArgument() {
    assertNull(fridge.search("1"));
  }

  @Test
  void searchEmpty() {
    assertNull(fridge.search(""));
  }

  @Test
  void overview() {
    fridge.ingredients.add(new Grocery("Apple", "KG", 1, 10, LocalDate.of(2024, 12, 1)));
    fridge.overview();

    assertEquals("Apple", fridge.ingredients.get(0).getName());
    assertEquals("Chocolate", fridge.ingredients.get(1).getName());

    assertTrue(outContent.toString().contains("Apple: 1.0 KG."));
    assertTrue(outContent.toString().contains("Chocolate: 1.0 KG."));
  }

  @Test
  void dateOverviewWithExpiredItems() {
    fridge.ingredients.add(new Grocery("Milk", "L", 1, 5, LocalDate.of(2019, 12, 1))); // Expired item
    fridge.dateOverview();

    assertTrue(outContent.toString().contains("Milk: 1.0 L."));
  }

  @Test
  void dateOverviewNoExpiredItems() {
    fridge.ingredients.add(new Grocery("Milk", "L", 1, 5, LocalDate.of(2025, 12, 1))); // Not expired
    fridge.dateOverview();

    assertEquals("The following items have expired:", outContent.toString().trim());
  }

  @Test
  void valueTestWithItems() {
    fridge.ingredients.add(new Grocery("Milk", "L", 2, 3, LocalDate.of(2025, 5, 1)));
    fridge.value();

    assertTrue(outContent.toString().contains("The value of the content is 13.0 euros."));
  }

  @Test
  void valueTestEmptyFridge() {
    fridge.ingredients.clear();
    fridge.value();

    // Expected value output for an empty fridge
    assertTrue(outContent.toString().contains("The value of the content is 0.0 euros."));
  }

  @Test
  void help() {
    fridge.help();

    // Check that the output contains key command descriptions
    assertTrue(outContent.toString().contains("\n    - \"/newItem\" to add a new item."));
    assertTrue(outContent.toString().contains("    - \"/use\" to retrieve an item."));
    assertTrue(outContent.toString().contains(
        "    - \"/search\" to search for an item and retrieve associated information."));
    assertTrue(outContent.toString().contains(
        "    - \"/overview\" to check everything that is currently in the fridge."));
    assertTrue(outContent.toString().contains(
        "    - \"/expiredOverview\" to check everything in the fridge that has expired."));
    assertTrue(outContent.toString().contains(
        "    - \"/value\" to check the value of the food currently in the fridge."));
  }
}