package edu.ntnu.idi.idatt.service;

import edu.ntnu.idi.idatt.model.Grocery;
import edu.ntnu.idi.idatt.model.Recipe;
import edu.ntnu.idi.idatt.storage.CookBook;
import edu.ntnu.idi.idatt.storage.Fridge;
import edu.ntnu.idi.idatt.util.Utility;
import java.util.ArrayList;

/**
 * Main functionality for CookBook.
 */
public class CookBookService {
  /**
   * Checks if a given recipe can be made with the current items in the fridge.
   *
   * @param recipe The recipe in question.
   * @return Boolean, true if possible to make.
   */
  public static boolean recipeCheck(Recipe recipe) {
    //ChatGPT
    return recipe.getFoods().stream()
        //All instances of food (all Grocery objects within recipe) must match the condition.
        .allMatch(food -> {
          // Use the search method to get all groceries with the same name
          ArrayList<Grocery> matchingGroceries = Utility.search(Fridge.overview(), food.getName());

          // Calculate the quantity of matching groceries
          float totalAmount = matchingGroceries.stream()
              .map(Grocery::getAmount)
              .reduce(0f, Float::sum);

          // Check if the total amount is sufficient
          return totalAmount >= food.getAmount(); //If true, this food object is approved.
        });
  }

  /**
   * Consumes all the items given in a recipe, to avoid running the command for each by hand.
   *
   * @param recipe the recipe to be cooked
   * @return boolean, true if recipe was possible to make and was cooked
   */
  public static boolean cookRecipe(Recipe recipe) {
    if (recipeCheck(recipe)) {
      recipe.getFoods()
          .forEach(grocery -> Fridge.use(grocery.getName(), grocery.getAmount()));
      return true;
    }
    return false;
  }

  /**
   * Shows which recipes are available with the current groceries stored in the fridge.
   *
   * @return ArrayList with recipe objects.
   */
  public static ArrayList<Recipe> recipeAvailability() {

    ArrayList<Recipe> availableRecipes = new ArrayList<>();

    for (Recipe recipe : CookBook.getRecipes()) {
      //If returned boolean is true
      if (recipeCheck(recipe)) {
        availableRecipes.add(recipe);
      }
    }

    return availableRecipes;

  }
}
