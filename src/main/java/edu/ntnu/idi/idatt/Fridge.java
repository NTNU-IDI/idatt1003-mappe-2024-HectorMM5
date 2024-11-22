package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Fridge represents the food storage, keeping accessible grocery objects.
 */
public class Fridge {

  static ArrayList<Grocery> ingredients = new ArrayList<>();
  Scanner scanner = new Scanner(System.in);

  /**
   * Guides the user through creating a new grocery item.
   */
  public void newGrocery(String name, String unit, float amount, float cost, LocalDate expiryDate) {
    ingredients.add(new Grocery(name, unit, amount, cost, expiryDate));
  }

  /**
   * Finds and takes out a given amount of an item within the fridge.
   *
   * @param name    * Ingredient's name.
   * @param consume * Amount to be consumed.
   */
  public void use(String name, float consume) {
    Iterator<Grocery> iterator = ingredients.iterator();

    while (iterator.hasNext()) {
      Grocery ingredient = iterator.next();
      if (ingredient.getName().equalsIgnoreCase(name)) {
        ingredient.use(consume);
        if (ingredient.getAmount() == 0) {
          iterator.remove();
        }
      }
    }
  }

  /**
   * Allows the user to search for an ingredient within the fridge.
   *
   * @param name * Ingredient's name.
   * @return List of matching Grocery objects.
   */
  public ArrayList<Grocery> search(String name) {
    return ingredients.stream()
        .filter(ingredient -> ingredient.getName().equalsIgnoreCase(name))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * Returns a sorted overview of the ingredients.
   *
   * @return A new sorted list of all ingredients.
   */
  public ArrayList<Grocery> overview() {
    return ingredients.stream()
        .sorted(Comparator.comparing(Grocery::getName))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * Returns all the ingredients that have expired.
   *
   * @return A new list of all expired ingredients.
   */
  public ArrayList<Grocery> dateOverview() {
    return ingredients.stream()
        .sorted(Comparator.comparing(Grocery::getName))
        .filter(ingredient -> ingredient.getExpiryDate().isBefore(LocalDate.now()))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * Calculates the total value of a given list of groceries.
   *
   * @param groceries The list of groceries to calculate the value for.
   * @return The total value of the groceries as an integer.
   */
  public int calculateValue(ArrayList<Grocery> groceries) {
    float sum = 0;
    for (Grocery grocery : groceries) {
      sum += grocery.getAmount() * grocery.getCost();
    }
    return Math.round(sum);
  }

  /**
   * Prints a list of items that will expire before a given date.
   *
   * @param date The date to compare against.
   */
  public ArrayList<Grocery> expiresBefore(LocalDate date) {
    return ingredients.stream()
        .filter(ingredient -> ingredient.getExpiryDate().isBefore(date))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * Prints out the total value of all ingredients in the fridge.
   */
  public void value() {
    float value = 0f;
    for (Grocery ingredient : ingredients) {
      value += ingredient.getCost() * ingredient.getAmount();
    }
    System.out.println("The value of the content is " + value + " euros.");
  }

  /**
   * Prints out an overview of all the commands.
   */
  public void help() {
    System.out.println("--------------------------------------------------------");
    System.out.println("An overview of available commands can be seen below:");
    System.out.println("--------------------------------------------------------");
    System.out.println("\n    - \"/newItem\" to add a new item.");
    System.out.println("    - \"/use\" to retrieve an item.");
    System.out.println(
        "    - \"/search\" to search for an item and retrieve associated information.");
    System.out.println("    - \"/overview\" to check everything that is currently in the fridge.");
    System.out.println(
        "    - \"/expiredOverview\" to check everything in the fridge that has expired.");
    System.out.println("    - \"/value\" to check the value of the food currently in the fridge.");
  }
}
