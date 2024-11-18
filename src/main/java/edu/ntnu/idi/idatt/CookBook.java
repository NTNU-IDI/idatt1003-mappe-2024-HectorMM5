package edu.ntnu.idi.idatt;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that represents a cookbook.
 * Saves individual dishes with their respective names, ingredients, descriptions and instructions.
 */
public class CookBook {
  ArrayList<Recipe> recipeList = new ArrayList<>();

  /**
   * Guides the user through the creation of a recipe.
   */

  public void createRecipe(String name, String description, ArrayList<String> instructions,
                           ArrayList<Grocery> food, int portions) {
    recipeList.add(new Recipe(name, description, instructions, food, portions));
  }

  /**
   * Allows the user to view the created recipes.
   */
  public void viewRecipes() {
    System.out.println("Your saved recipes:");
    for (int i = 0; i < recipeList.size(); i++) {
      System.out.println(i + 1 + ". " + recipeList.get(i).getName());

    }

  }

  /**
   * Shows which recipes are available with the current groceries stored in the fridge.
   *
   * @param fridge * Fridge object, necessary to access its contents.
   */
  public ArrayList<Recipe> recipeAvailability(Fridge fridge) {

    ArrayList<Recipe> availableRecipes = new ArrayList<>();

    for (Recipe recipe : recipeList) {
      int maxIngredients = recipe.getFoods().size();
      int ingredientsOk = 0;
      for (Grocery food : recipe.getFoods()) {
        for (Grocery ingredient : fridge.ingredients) {
          if (food.getName()
              .equalsIgnoreCase(ingredient.getName())) {
            if (ingredient.getAmount() >= food.getAmount()) {
              ingredientsOk += 1;
            }
          }
        }
      }

      if (maxIngredients == ingredientsOk) {
        availableRecipes.add(recipe);
      }

    }

    return availableRecipes;

  }

  /**
   * Checks if a given recipe can be made with the current items in the fridge.
   *
   * @param recipe * The recipe in question.
   * @param fridge * Fridge object to access groceries.
   */

  public Boolean recipeCheck(Recipe recipe, Fridge fridge) {
    int maxIngredients = recipe.getFoods().size();
    int ingredientsOk = 0;
    for (Grocery ingredient : recipe.getFoods()) {
      for (Grocery food : fridge.ingredients) {
        if (ingredient.getName()
            .equalsIgnoreCase(food.getName())) {
          if (food.getAmount() >= ingredient.getAmount()) {
            ingredientsOk += 1;

          }
        }
      }
    }

    if (maxIngredients == ingredientsOk) {
      return true;
    }

    return false;
  }
}