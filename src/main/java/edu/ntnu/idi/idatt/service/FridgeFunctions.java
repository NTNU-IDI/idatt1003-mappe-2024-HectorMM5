package edu.ntnu.idi.idatt.service;

import edu.ntnu.idi.idatt.model.Grocery;
import edu.ntnu.idi.idatt.model.Unit;
import edu.ntnu.idi.idatt.storage.Fridge;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Main functionality tied to Fridge.
 */
public class FridgeFunctions {
  private static final ArrayList<Grocery> groceryProfiles = new ArrayList<>();

  public static ArrayList<Grocery> getGroceryProfiles() {
    return groceryProfiles.stream()
        .sorted(Comparator.comparing(Grocery::getName))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * Adds a grocery profile.
   *
   * @param name Grocery's name
   * @param unit Grocery's unit (Unit enum)
   */
  public static void createGroceryProfile(String name, Unit unit) {
    groceryProfiles.add(new Grocery(name, unit));
  }

  /**
   * Returns all the groceries that have expired.
   *
   * @return A new list of all expired groceries.
   */
  public static ArrayList<Grocery> dateOverview() {
    return Fridge.overview().stream()
        .filter(ingredient -> ingredient.getExpiryDate().isBefore(LocalDate.now()))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * Returns ArrayList of items that will expire before a given date.
   *
   * @param date The date to compare against.
   * @return * ArrayList with Grocery objects
   */
  public static ArrayList<Grocery> expiresBefore(LocalDate date) {
    return Fridge.overview().stream()
        .filter(ingredient -> ingredient.getExpiryDate().isBefore(date))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * Clears all grocery profiles. Testing purposes only.
   */
  public static void clearGroceryProfiles() {
    groceryProfiles.clear();
  }

}
