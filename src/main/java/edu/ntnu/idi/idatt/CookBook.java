package edu.ntnu.idi.idatt;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Class that represents a cookbook.
 * Saves individual dishes with their respective names, ingredients, descriptions and instructions.
 */
public class CookBook {
  static final ArrayList<Recipe> recipeList = new ArrayList<>();

  /**
   * Searches for and returns a recipe-object with the given name.
   *
   * @param name * recipe/dish's name
   * @return * Dish object, if found. Otherwise, return null.
   */
  public Recipe search(String name) {
    return recipeList.stream()
        .filter(recipe -> recipe.getName().equalsIgnoreCase(name))
        .findFirst()
        .orElse(null);

  }

  /**
   * Guides the user through the creation of a recipe.
   */
  public void createRecipe(String name, String description, ArrayList<String> instructions,
                           ArrayList<Grocery> food, int portions) {
    recipeList.add(new Recipe(name, description, instructions, food, portions));
  }

  /**
   * Returns an ArrayList of all the created recipes.
   */
  public ArrayList<Recipe> getRecipes() {
    return recipeList.stream()
        .sorted(Comparator.comparing(Recipe::getName))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * Shows which recipes are available with the current groceries stored in the fridge.
   */
  public ArrayList<Recipe> recipeAvailability(Fridge fridge) {

    ArrayList<Recipe> availableRecipes = new ArrayList<>();

    for (Recipe recipe : recipeList) {
      //If returned boolean is true
      if (recipeCheck(recipe, fridge)) {
        availableRecipes.add(recipe);
      }
    }

    return availableRecipes;

  }

  /**
   * Checks if a given recipe can be made with the current items in the fridge.
   *
   * @param recipe * The recipe in question.
   */
  public Boolean recipeCheck(Recipe recipe, Fridge fridge) {
    //ChatGPT
    return recipe.getFoods().stream()
        //All instances of food (all Grocery objects within recipe) must match the condition.
        .allMatch(food -> {
          // Use the search method to get all groceries with the same name
          ArrayList<Grocery> matchingGroceries = fridge.search(food.getName());

          // Calculate the quantity of matching groceries
          float totalAmount = matchingGroceries.stream()
              .map(Grocery::getAmount)
              .reduce(0f, Float::sum);

          // Check if the total amount is sufficient
          return totalAmount >= food.getAmount(); //If true, this food object is approved.
        });
  }
}