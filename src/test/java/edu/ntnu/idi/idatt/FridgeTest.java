package edu.ntnu.idi.idatt;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class FridgeTest {

  @BeforeEach
  void setUp() {
    // Clearing the content
    for (Grocery grocery : Fridge.overview()) {
      Fridge.use(grocery.getName(), grocery.getAmount());
    }

    Fridge.newGrocery("Chocolate", "KG", 1, 10, LocalDate.of(2025, 1, 1));

  }

  @Test
  void newGrocery() {

    //Creates a new grocery
    Fridge.newGrocery("White chocolate", "KG", 1, 10, LocalDate.of(2020, 1, 1));
    ArrayList<Grocery> ingredients = Fridge.overview();

    //Checks each field individually, to ensure proper implementation
    assertEquals("White chocolate", ingredients.get(1).getName());
    assertEquals("KG", ingredients.get(1).getUnit());
    assertEquals(1, ingredients.get(1).getAmount());
    assertEquals(10, ingredients.get(1).getCost());
    assertEquals(LocalDate.of(2020, 1, 1), ingredients.get(1).getExpiryDate());

    //Checks to-string method
    assertEquals(ingredients.get(1).toString(),
        new Grocery("White chocolate", "KG", 1, 10, LocalDate.of(2020, 1, 1)).toString());
  }

  @Test
  void usePositive() {

    //Uses half the amount (0.5), then compares the returned output state
    assertEquals(Fridge.use("Chocolate", 0.5f), Fridge.UseStatus.SUCCESS);
    //Checks the remaining amount
    assertEquals(0.5f, Fridge.overview().get(0).getAmount());
  }

  @Test
  void useNotFound() {

    //Use outcome should be "ITEM_NOT_FOUND", as no food named "Vanilla" exists
    assertSame(Fridge.use("Vanilla", 0.5f), Fridge.UseStatus.ITEM_NOT_FOUND);
  }

  @Test
  void useInsufficientAmount() {

    //Use outcome should be "INSUFFICIENT_AMOUNT", as not enough "Chocolate" exists
    assertSame(Fridge.use("Chocolate", 20f), Fridge.UseStatus.INSUFFICIENT_AMOUNT);
  }

  @Test
  void useMultiple() {

    //Creates a new object with an amount of two, then attempts to use both to reach "consume" total
    Fridge.newGrocery("Chocolate", "KG", 2, 10, LocalDate.of(2025, 1, 1));

    //As there is 1 kg in the default "Chocolate", and 2 kg are added, the total should suffice
    assertSame(Fridge.use("Chocolate", 3), Fridge.UseStatus.SUCCESS);
    assertSame(Fridge.overview().size(), 0);
  }

  @Test
  void useMultipleInsufficientAmount() {

    //Creates a new object with an amount of two, then attempts to use both to reach "consume" total
    Fridge.newGrocery("Chocolate", "KG", 2, 10, LocalDate.of(2025, 1, 1));

    //Regardless of the addition, there should not be enough
    assertSame(Fridge.use("Chocolate", 10), Fridge.UseStatus.INSUFFICIENT_AMOUNT);

    //The objects were not used
    assertSame(Fridge.overview().size(), 2);
    assertEquals(3, Fridge.overview().get(0).getAmount() + Fridge.overview().get(1).getAmount());
  }

  @Test
  void useExactMatch() {

    //Exact amount check
    Fridge.newGrocery("Milk", "L", 1.0f, 5, LocalDate.of(2025, 12, 1));
    assertSame(Fridge.use("Milk", 1.0f), Fridge.UseStatus.SUCCESS);

    //Item is deleted once amount reaches 0, so only the default value should remain
    assertEquals(1, Fridge.overview().size());
  }

  @Test
  void searchPositive() {

    //Objects exists, so an identical objects is created with the same fields, and thus, to-string strings
    assertEquals(Fridge.search("Chocolate").get(0).toString(),
        new Grocery("Chocolate", "KG", 1, 10, LocalDate.of(2025, 1, 1)).toString());
  }

  @Test
  void searchNegative() {

    //Searches for a name that doesn't exist, returned ArrayList should be empty
    assertEquals(0, Fridge.search("Dark chocolate").size());
  }

  @Test
  void overview() {

    //New item is created, but apple is added at the end of the list
    Fridge.newGrocery("Apple", "KG", 1, 10, LocalDate.of(2024, 12, 1));
    ArrayList<Grocery> overview = Fridge.overview();

    //As overview() returns an alphabetically sorted list, Apple should be found in the first index
    assertEquals("Apple", overview.get(0).getName());
    assertEquals("Chocolate", overview.get(1).getName());
    assertEquals(2, overview.size());
  }

  @Test
  void dateOverviewWithExpiredItems() {

    //An expired item is added, as the default item is not expired
    Fridge.newGrocery("Milk", "L", 1, 5, LocalDate.of(2019, 12, 1)); // Expired

    ArrayList<Grocery> dateOverview = Fridge.dateOverview();

    //Only the expired, newly added item should be in the returned ArrayList
    assertTrue(dateOverview.get(0).getName().equalsIgnoreCase("Milk"));
    assertEquals(1, dateOverview.size());
  }

  @Test
  void calculateValue() {

    //Adds another object
    Fridge.newGrocery("Milk", "L", 2, 3, LocalDate.of(2025, 5, 1));

    //Value is calculated as a total for the given amount (as shown on the receipt)
    int total = Fridge.calculateValue(Fridge.overview());

    //Result should be 13, as 1 * 10 + 2 * 1.5 = 13
    assertEquals(13, total);
  }

}