package edu.ntnu.idi.idatt;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

public class FridgeTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @BeforeEach
  void setUp() {
    Fridge.ingredients.clear(); // Clear static ingredients for a clean state
    Fridge.ingredients.add(new Grocery("Chocolate", "KG", 1, 10, LocalDate.of(2025, 1, 1)));
    System.setOut(new PrintStream(outContent));
  }

  @Test
  void newGrocery() {
    Fridge.newGrocery("White chocolate", "KG", 1, 10, LocalDate.of(2020, 1, 1));
    assertEquals("White chocolate", Fridge.ingredients.get(1).getName());
    assertEquals("KG", Fridge.ingredients.get(1).getUnit());
    assertEquals(1, Fridge.ingredients.get(1).getAmount());
    assertEquals(10, Fridge.ingredients.get(1).getCost());
    assertEquals(LocalDate.of(2020, 1, 1), Fridge.ingredients.get(1).getExpiryDate());

    assertEquals(Fridge.ingredients.get(1).toString(),
        new Grocery("White chocolate", "KG", 1, 10, LocalDate.of(2020, 1, 1)).toString());
  }

  @Test
  void usePositive() {
    System.out.println(Fridge.ingredients);
    Fridge.use("Chocolate", 0.5f);
    assertEquals(0.5f, Fridge.ingredients.get(0).getAmount());
  }

  @Test
  void useNotFound() {
    assertSame(Fridge.use("Vanilla", 0.5f), Fridge.UseStatus.ITEM_NOT_FOUND);
  }

  @Test
  void useInsufficientAmount() {
    assertSame(Fridge.use("Chocolate", 20f), Fridge.UseStatus.INSUFFICIENT_AMOUNT);
  }

  @Test
  void useMultiple() {
    Fridge.ingredients.add(new Grocery("Chocolate", "KG", 2, 10, LocalDate.of(2025, 1, 1)));

    assertSame(Fridge.use("Chocolate", 3), Fridge.UseStatus.SUCCESS);
    assertSame(Fridge.ingredients.size(), 0);
  }

  @Test
  void useMultipleInsufficientAmount() {
    Fridge.ingredients.add(new Grocery("Chocolate", "KG", 2, 10, LocalDate.of(2025, 1, 1)));

    assertSame(Fridge.use("Chocolate", 10), Fridge.UseStatus.INSUFFICIENT_AMOUNT);
    assertSame(Fridge.ingredients.size(), 2);
    assertEquals(3, Fridge.ingredients.get(0).getAmount() + Fridge.ingredients.get(1).getAmount());
  }

  @Test
  void useExactMatch() {
    Fridge.ingredients.add(new Grocery("Milk", "L", 1.0f, 5, LocalDate.of(2025, 12, 1)));
    assertSame(Fridge.use("Milk", 1.0f), Fridge.UseStatus.SUCCESS);
    assertEquals(1, Fridge.ingredients.size());
  }

  @Test
  void useFloatingPointPrecision() {
    Fridge.ingredients.add(new Grocery("Oil", "L", 1.0001f, 5, LocalDate.of(2025, 12, 1)));
    assertSame(Fridge.use("Oil", 1.0f), Fridge.UseStatus.SUCCESS);
    assertEquals(2, Fridge.ingredients.size());
  }

  @Test
  void usePartialMultipleGroceries() {
    Fridge.ingredients.add(new Grocery("Milk", "L", 1.0f, 5, LocalDate.of(2025, 12, 1)));
    Fridge.ingredients.add(new Grocery("Milk", "L", 0.5f, 5, LocalDate.of(2024, 12, 1))); // Earlier expiry
    assertSame(Fridge.use("Milk", 1.2f), Fridge.UseStatus.SUCCESS);
    assertEquals(0.3f, Fridge.ingredients.get(1).getAmount(), 0.0001f);
  }

  @Test
  void searchPositive() {
    // Should return an ArrayList with a single object, being the "Chocolate" object
    assertEquals(Fridge.search("Chocolate").get(0).toString(),
        new Grocery("Chocolate", "KG", 1, 10, LocalDate.of(2025, 1, 1)).toString());
  }

  @Test
  void searchNegative() {
    // Should return an empty ArrayList
    assertEquals(0, Fridge.search("Dark chocolate").size());
  }

  @Test
  void searchCaseInsensitive() {
    assertEquals(1, Fridge.search("CHOCOLATE").size());
    assertEquals("Chocolate", Fridge.search("CHOCOLATE").get(0).getName());
  }

  @Test
  void searchMultipleMatches() {
    Fridge.ingredients.add(new Grocery("Chocolate", "KG", 0.5f, 8, LocalDate.of(2026, 1, 1)));
    ArrayList<Grocery> results = Fridge.search("Chocolate");
    assertEquals(2, results.size());
  }

  @Test
  void overview() {
    // Added at the end of the list, yet overview sorts the list in alphabetical order
    Fridge.ingredients.add(new Grocery("Apple", "KG", 1, 10, LocalDate.of(2024, 12, 1)));
    ArrayList<Grocery> overview = Fridge.overview();

    // If this is true, the sorting was successful
    assertEquals(overview.get(0).getName(), "Apple");
    assertEquals(overview.get(1).getName(), "Chocolate");

    assertEquals(2, overview.size());
  }

  @Test
  void dateOverviewWithExpiredItems() {
    // Adds an object at the end of the list
    Fridge.ingredients.add(new Grocery("Milk", "L", 1, 5, LocalDate.of(2019, 12, 1))); // Expired item
    ArrayList<Grocery> dateOverview = Fridge.dateOverview();

    // If successful, ArrayList should have a length of 1, with the object being Milk
    assertTrue(dateOverview.get(0).getName().equalsIgnoreCase("Milk"));
    assertEquals(1, dateOverview.size());
  }

  @Test
  void dateOverviewNoExpiredItems() {
    Fridge.ingredients.add(new Grocery("Milk", "L", 1, 5, LocalDate.of(2025, 12, 1))); // Not expired
    // Returned ArrayList should be empty
    ArrayList<Grocery> dateOverview = Fridge.dateOverview();

    assertEquals(0, dateOverview.size());
  }

  @Test
  void calculateValue() {
    Fridge.ingredients.add(new Grocery("Milk", "L", 2, 3, LocalDate.of(2025, 5, 1)));
    int total = Fridge.calculateValue(Fridge.ingredients);

    assertEquals(13, total);
  }

  @Test
  void valueOfBlank() {
    Fridge.ingredients.clear();

    // Expected value output for an empty fridge
    assertEquals(0, Fridge.calculateValue(Fridge.ingredients));
  }

}
