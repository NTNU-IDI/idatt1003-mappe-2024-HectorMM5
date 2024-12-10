package edu.ntnu.idi.idatt;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Utility {

  /**
   * Calculates the total value of a given list of groceries.
   *
   * @param groceries The list of groceries to calculate the value for.
   * @return The total value of the groceries as an integer.
   */
  public static int calculateValue(ArrayList<Grocery> groceries) {
    float sum = groceries.stream()
        .map(grocery -> (grocery.getAmount() * grocery.getCost()))
        .reduce(0f, Float::sum);

    return Math.round(sum);
  }

  /**
   * Allows the user to search for an ingredient within the fridge.
   *
   * @param name * Ingredient's name.
   * @return List of matching Grocery objects.
   */
  public static ArrayList<Grocery> search(ArrayList<Grocery> list, String name) {
    return list.stream()
        .filter(ingredient -> ingredient.getName().equalsIgnoreCase(name))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public static void displayList(ArrayList<Grocery> list, String notification, String ifEmpty) {
    if (!list.isEmpty()) {
      if (!(notification == null)) {
        System.out.println(notification);
      }

      //ChatGPT
      list.stream()
          .map(Utility::presentGrocery) // Map each Grocery to its string representation
          .forEach(System.out::println); // Print each formatted Grocery string

    } else {
      System.out.println(ifEmpty);
    }
  }

  /**
   * Formats a Grocery object into a user-friendly string for display.
   *
   * @param grocery The Grocery object to format.
   * @return A formatted string representation of the grocery.
   */
  public static String presentGrocery(Grocery grocery) {
    StringBuilder builder = new StringBuilder();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");
    String dateToText = grocery.getExpiryDate().format(dateTimeFormatter);

    builder.append("    - ");
    builder.append(grocery.getName());
    builder.append(": ");
    builder.append(grocery.getAmount());
    builder.append(" ");
    builder.append(grocery.getUnit());
    builder.append(" (Expires: ");
    builder.append(dateToText);
    builder.append(")");

    return builder.toString();
  }

  /**
   * Formats an ingredient into a user-friendly string for display.
   *
   * @param grocery The Grocery object representing the ingredient.
   * @return A formatted string representation of the ingredient.
   */
  public static String presentIngredient(Grocery grocery) {
    StringBuilder builder = new StringBuilder();
    builder.append("    - ");
    builder.append(grocery.getAmount());
    builder.append(" ");
    builder.append(grocery.getUnit());
    builder.append(" ");
    builder.append(grocery.getName());

    return builder.toString();
  }

}
