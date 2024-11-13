package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;



/**
 * Fridge represents the food storage, keeping accessible grocery objects.
 */

public class Fridge {

  public ArrayList<Grocery> ingredients = new ArrayList<>();
  Scanner scanner = new Scanner(System.in);

  /**
   * Guides the user through creating a new grocery item.
   */

  public void newGrocery(String name, String unit, float amount, float cost, LocalDate expiryDate) {
    ingredients.add(new Grocery(name, unit, amount, cost, expiryDate));
    System.out.println("Your grocery has been put in the fridge.");
  }

  /**
   * Finds and takes out a given amount of an item within the fridge.
   *
   * @param name * Ingredient's name.
   * @param consume * Amount to be consumed.
   */

  public void use(String name, float consume) {
    for (int i = 0; i < ingredients.size(); i++) {
      if (ingredients.get(i).getName().equalsIgnoreCase(name)) {
        ingredients.get(i).use(consume);
        if (ingredients.get(i).getAmount() == 0) {
          ingredients.remove(i);
          i -= 1;
        }

      } else if (i == ingredients.size() - 1) {
        System.out.println("The ingredient you're looking for does not exist. Check for typos.");
      }
    }
  }

  /**
   * Allows the user to search for an ingredient within the fridge.
   *
   * @param argument * Ingredient's name.
   */

  public Grocery search(String argument) {
    for (Grocery ingredient : ingredients) {
      if (ingredient.getName().equalsIgnoreCase(argument)) {
        return ingredient;
      }
    }
    return null;
  }

  /**
   * Prints a sorted overview of the ingredients.
   */
  public void overview() {
    ingredients.sort(Comparator.comparing(Grocery::getName));
    for (Grocery ingredient : ingredients) {
      System.out.println(
          ingredient.getName() + ": " + ingredient.getAmount() + " "
              + ingredient.getUnit() + ".");
    }
  }

  /**
   * Writes out an overview of the ingredients that have expired.
   */
  public void dateOverview() {
    float sum = 0;
    System.out.println("The following items have expired:");
    for (Grocery ingredient : ingredients) {
      if (ingredient.getExpiryDate().isBefore(LocalDate.now())) {
        System.out.println(
            ingredient.getName() + ": " + ingredient.getAmount() + " "
                + ingredient.getUnit() + ".");

        sum += ingredient.getCost() * ingredient.getAmount();
      }
    }

    System.out.println("You have a total of " + Math.round(sum) + " euros worth of expired food.");
  }

  public void expiresBefore(LocalDate date) {
    System.out.println("The following items will expire before the given date:");
    for (Grocery ingredient : ingredients) {
      if (ingredient.getExpiryDate().isBefore(date)) {
        System.out.println(
            ingredient.getName() + ": " + ingredient.getAmount() + " "
                + ingredient.getUnit() + ".");
      }
    }
  }

  /**
   * Prints out the value of all the ingredients in the fridge.
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