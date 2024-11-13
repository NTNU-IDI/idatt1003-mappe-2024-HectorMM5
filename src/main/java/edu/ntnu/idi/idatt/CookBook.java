package edu.ntnu.idi.idatt;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that represents a cookbook.
 * Saves individual dishes with their respective names, ingredients, descriptions and instructions.
 */
public class CookBook {
  Scanner scanner = new Scanner(System.in);
  ArrayList<Recipe> cookBook = new ArrayList<>();

  /**
   * Guides the user through the creation of a recipe.
   */

  public void createRecipe(String name, String description, ArrayList<String> instructions,
                           ArrayList<Grocery> food, int portions) {
    cookBook.add(new Recipe(name, description, instructions, food, portions));
  }

  /**
   * Allows the user to view the created recipes.
   */
  public void viewRecipes() {
    System.out.println("Your saved recipes:");
    for (int i = 0; i < cookBook.size(); i++) {
      System.out.println(i + 1 + ". " + cookBook.get(i).name);

    }

  }

  /**
   * Shows which recipes are available with the current groceries stored in the fridge.
   *
   * @param fridge * Fridge object, necessary to access its contents.
   */
  public void recipeAvailability(Fridge fridge) {

    ArrayList<Recipe> availableRecipes = new ArrayList<>();

    for (int i = 0; i < cookBook.size(); i++) {
      int maxIngredients = cookBook.get(i).foods.size();
      int ingredientsOk = 0;
      for (int j = 0; j < cookBook.get(i).foods.size(); j++) {
        for (int k = 0; k < fridge.ingredients.size(); k++) {
          if (cookBook.get(i).foods.get(j).getName()
              .equalsIgnoreCase(fridge.ingredients.get(k).getName())) {
            if (fridge.ingredients.get(k).getAmount() >= cookBook.get(i).foods.get(j).getAmount()) {
              ingredientsOk += 1;
            }
          }
        }
      }

      if (maxIngredients == ingredientsOk) {
        availableRecipes.add(cookBook.get(i));
      }

    }

    System.out.println("With the ingredients you have, you are able to make:");

    for (int i = 0; i < availableRecipes.size(); i++) {
      System.out.println(i + ". " + availableRecipes.get(i).name);

    }

  }
}