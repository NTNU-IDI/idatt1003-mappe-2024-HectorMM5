package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * Fridge represents the food storage, keeping accessible grocery objects.
 */
public class Fridge {

  private static final ArrayList<Grocery> ingredients = new ArrayList<>();



  /**
   * Guides the user through creating a new grocery item.
   */
  public static void newGrocery(String name, String unit, float amount, float cost,
                                LocalDate expiryDate) {
    ingredients.add(new Grocery(name, unit, amount, cost, expiryDate));
  }

  /**
   * Represents different outcomes for the "use" function.
   * Used to give the user appropriate feedback.
   */
  public enum UseStatus {
    SUCCESS,
    INSUFFICIENT_AMOUNT,
    ITEM_NOT_FOUND
  }

  /**
   * Finds and takes out a given amount of an item within the fridge.
   *
   * @param name    * Ingredient's name.
   * @param consume * Amount to be consumed.
   */
  public static UseStatus use(String name, float consume) {

    ArrayList<Grocery> matchingGroceries = search(name);

    if (!matchingGroceries.isEmpty()) {
      matchingGroceries = matchingGroceries.stream()
          //Sorts based on the earliest expiry date
          .sorted(Comparator.comparing(Grocery::getExpiryDate))
          .collect(Collectors.toCollection(ArrayList::new));

      float totalAmount = matchingGroceries.stream()
          .map(Grocery::getAmount)
          .reduce(0f, Float::sum);

      if (totalAmount >= consume) {
        Iterator<Grocery> iterator = matchingGroceries.iterator();

        while (consume > 0) {
          Grocery grocery = iterator.next();
          if (grocery.getAmount() > consume) {
            grocery.setAmount(grocery.getAmount() - consume);
            consume = 0;
          } else {
            consume -= grocery.getAmount();
            grocery.setAmount(0);
          }
        }
        ingredients.removeIf(grocery -> grocery.getAmount() == 0);

        return UseStatus.SUCCESS;

      } else {
        return UseStatus.INSUFFICIENT_AMOUNT;
      }
    }

    return UseStatus.ITEM_NOT_FOUND;
  }

  /**
   * Allows the user to search for an ingredient within the fridge.
   *
   * @param name * Ingredient's name.
   * @return List of matching Grocery objects.
   */
  public static ArrayList<Grocery> search(String name) {
    return ingredients.stream()
        .filter(ingredient -> ingredient.getName().equalsIgnoreCase(name))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * Returns a sorted overview of the ingredients.
   *
   * @return A new sorted list of all ingredients.
   */
  public static ArrayList<Grocery> overview() {
    return ingredients.stream()
        .sorted(Comparator.comparing(Grocery::getName))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * Returns all the ingredients that have expired.
   *
   * @return A new list of all expired ingredients.
   */
  public static ArrayList<Grocery> dateOverview() {
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
  public static int calculateValue(ArrayList<Grocery> groceries) {
    float sum = 0;
    for (Grocery grocery : groceries) {
      sum += grocery.getAmount() * grocery.getCost();
    }
    return Math.round(sum);
  }

  /**
   * Returns ArrayList of items that will expire before a given date.
   *
   * @param date The date to compare against.
   * @return * ArrayList with Grocery objects
   */
  public static ArrayList<Grocery> expiresBefore(LocalDate date) {
    return ingredients.stream()
        .filter(ingredient -> ingredient.getExpiryDate().isBefore(date))
        .collect(Collectors.toCollection(ArrayList::new));
  }
}
