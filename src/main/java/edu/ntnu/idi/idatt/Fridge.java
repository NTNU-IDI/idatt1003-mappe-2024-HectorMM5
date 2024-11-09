package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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

  public void search(String argument) {
    for (int i = 0; i < ingredients.size(); i++) {
      if (ingredients.get(i).getName().equals(argument)) {
        System.out.println("The ingredient " + ingredients.get(i).getName() + " exists, you have "
            + ingredients.get(i).getAmount() + " " + ingredients.get(i).getUnit() + ".");
      } else if (i == ingredients.size() - 1) {
        System.out.println("The ingredient you're looking for does not exist.");
      }
    }
  }

  /**
   * Writes out an overview of the ingredients in the fridge.
   */
  public void overview() {
    for (int i = 0; i < ingredients.size(); i++) {
      System.out.println(
          ingredients.get(i).getName() + ": " + ingredients.get(i).getAmount() + " "
              + ingredients.get(i).getUnit() + ".");
    }
  }

  /**
   * Writes out an overview of the ingredients that have expired.
   */
  public void dateOverview() {
    System.out.println("The following items have expired:");
    for (int i = 0; i < ingredients.size(); i++) {
      if (ingredients.get(i).getExpiryDate().isBefore(LocalDate.now())) {
        System.out.println(
            ingredients.get(i).getName() + ": " + ingredients.get(i).getAmount() + " "
                + ingredients.get(i).getUnit() + ".");
      }
    }
  }

  /**
   * Prints out the value of all the ingredients in the fridge.
   */
  public void value() {

    float value = 0f;
    for (int i = 0; i < ingredients.size(); i++) {
      value += ingredients.get(i).getCost() * ingredients.get(i).getAmount();
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