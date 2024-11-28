package edu.ntnu.idi.idatt;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * Class that represents a cookbook.
 * Saves individual dishes with their respective names, ingredients, descriptions and instructions.
 */
public class CookBook {
  private static final ArrayList<Recipe> recipeList = new ArrayList<>();

  /**
   * Searches for and returns a recipe-object with the given name.
   *
   * @param name * recipe/dish's name
   * @return * Dish object, if found. Otherwise, return null.
   */
  public static Recipe search(String name) {
    return recipeList.stream()
        .filter(recipe -> recipe.getName().equalsIgnoreCase(name))
        .findFirst()
        .orElse(null);

  }

  /**
   * Guides the user through the creation of a recipe.
   */
  public static void createRecipe(String name, String description, ArrayList<String> instructions,
                           ArrayList<Grocery> food, int portions) {
    recipeList.add(new Recipe(name, description, instructions, food, portions));
  }

  /**
   * Returns a sorted ArrayList of all the created recipes.
   *
   * @return ArrayList with recipe objects.
   */
  public static ArrayList<Recipe> getRecipes() {
    return recipeList.stream()
        .sorted(Comparator.comparing(Recipe::getName))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * Shows which recipes are available with the current groceries stored in the fridge.
   *
   * @return ArrayList with recipe objects.
   */
  public static ArrayList<Recipe> recipeAvailability() {

    ArrayList<Recipe> availableRecipes = new ArrayList<>();

    for (Recipe recipe : getRecipes()) {
      //If returned boolean is true
      if (recipeCheck(recipe)) {
        availableRecipes.add(recipe);
      }
    }

    return availableRecipes;

  }

  /**
   * Searches for and deletes the recipe with the given name.
   *
   * @param name Recipe's name
   * @return BOOLEAN: true if found and deleted, false if not found.
   */

  public static boolean deleteRecipe(String name) {
    Iterator<Recipe> iterator = recipeList.iterator();
    boolean found = false;

    while (!found && iterator.hasNext()) {
      Recipe recipe = iterator.next();
      if (recipe.getName().equalsIgnoreCase(name)) {
        iterator.remove();
        found = true;
      }
    }

    return found;
  }

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
          ArrayList<Grocery> matchingGroceries = Fridge.search(food.getName());

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
      for (Grocery grocery : recipe.getFoods()) {
        Fridge.use(grocery.getName(), grocery.getAmount());

      }

      return true;

    } else {
      return false;
    }
  }
}