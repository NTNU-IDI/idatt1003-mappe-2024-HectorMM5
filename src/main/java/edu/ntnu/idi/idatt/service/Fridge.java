package edu.ntnu.idi.idatt.service;

import edu.ntnu.idi.idatt.FridgeFunctions;
import edu.ntnu.idi.idatt.model.Grocery;
import edu.ntnu.idi.idatt.model.Unit;
import edu.ntnu.idi.idatt.util.Utility;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Fridge represents the food storage, keeping accessible grocery objects.
 */
public class Fridge {

  private static final ArrayList<Grocery> groceries = new ArrayList<>();

  /**
   * Returns a sorted overview of the groceries.
   *
   * @return A new sorted list of all groceries.
   */
  public static ArrayList<Grocery> overview() {
    return groceries.stream()
        .sorted(Comparator.comparing(Grocery::getName))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * Creates a new grocery item. Ensures unit mismatch does not occur.
   *
   * @param name       Grocery's name
   * @param unit       Grocery's unit (Unit enum)
   * @param amount     Grocery's amount
   * @param cost       Grocery's total cost
   * @param expiryDate Grocery's expiry date (LocalDate)
   * @return Boolean. If successful creation, true.
   */
  public static Boolean newGrocery(String name, Unit unit, float amount, float cost,
                                   LocalDate expiryDate) {
    //ChatGPT
    Optional<Grocery> matchingProfile = FridgeFunctions.getGroceryProfiles().stream()
        .filter(profile -> profile.getName().equalsIgnoreCase(name))
        .findFirst();

    if (matchingProfile.isPresent()) {
      Grocery foundProfile = matchingProfile.get();
      //If new grocery unit type is equal to profile metric type, proceed.
      if (foundProfile.getUnit().getMetricType().equalsIgnoreCase(unit.getMetricType())) {
        groceries.add(new Grocery(name, unit, amount, cost, expiryDate));
        return true;
      } else {
        return false;
      }
    }
    //If no item under this name has been created, create item and item profile.
    groceries.add(new Grocery(name, unit, amount, cost, expiryDate));
    FridgeFunctions.createGroceryProfile(name, unit);
    return true;

  }

  /**
   * Represents different outcomes for the "use" function.
   * Used to give the logic, and thus the user, appropriate feedback.
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

    ArrayList<Grocery> matchingGroceries = Utility.search(groceries, name);

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

        while (consume > 0.00001) {
          Grocery grocery = iterator.next();
          if (grocery.getAmount() > consume) {
            grocery.setAmount(grocery.getAmount() - consume);
            consume = 0;
          } else {
            consume -= grocery.getAmount();
            grocery.setAmount(0);
          }
        }
        groceries.removeIf(grocery -> grocery.getAmount() < 0.00001);

        return UseStatus.SUCCESS;

      } else {
        return UseStatus.INSUFFICIENT_AMOUNT;
      }
    }

    return UseStatus.ITEM_NOT_FOUND;
  }

}
