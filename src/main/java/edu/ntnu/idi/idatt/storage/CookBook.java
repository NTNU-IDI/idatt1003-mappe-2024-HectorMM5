package edu.ntnu.idi.idatt.storage;

import edu.ntnu.idi.idatt.model.Grocery;
import edu.ntnu.idi.idatt.model.Recipe;
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

}
