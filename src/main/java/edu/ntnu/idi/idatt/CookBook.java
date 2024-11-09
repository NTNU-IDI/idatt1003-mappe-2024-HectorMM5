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
  public void createRecipe() {
    System.out.println("Write the name of the dish.");
    final String name = scanner.nextLine();

    System.out.println("Write a short description of the dish.");
    final String description = scanner.nextLine();

    ArrayList<Grocery> food = new ArrayList<>();
    System.out.println("Specify the ingredients in the following format: name, amount, unit. "
        + "\nWrite \"Done\" when done.");

    // Collect ingredients
    while (true) {
      String ingredient = scanner.nextLine();
      if (ingredient.equalsIgnoreCase("Done")) {
        break;
      }

      String[] splitIngredient = ingredient.split(",");
      if (splitIngredient.length == 3) {
        for (int i = 0; i < splitIngredient.length; i++) {
          splitIngredient[i] = splitIngredient[i].trim();
        }

        try {
          food.add(new Grocery(splitIngredient[0], splitIngredient[2], Float.parseFloat(splitIngredient[1])));
        } catch (IllegalArgumentException e) {
          System.out.println("There was an issue with your input. "
              + "Please verify the format: name, amount, unit.");
        }
      } else {
        throw new IllegalArgumentException("Expected three arguments, but received "
            + splitIngredient.length + " instead. Check your input.");
      }
    }

    ArrayList<String> instructions = new ArrayList<>();
    System.out.println("Write the instructions, use multiple lines if needed. "
        + "\nWrite \"Done\" when done.");

    // Collect instructions
    while (true) {
      String instruction = scanner.nextLine();
      if (instruction.equalsIgnoreCase("Done")) {
        break;
      }
      instructions.add(instruction);
    }

    System.out.println("How many portions does this make?");

    int portions;

    while (true) {
      try {
        portions = Integer.parseInt(scanner.nextLine().trim());
        break;
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a whole number.");
      }
    }

    cookBook.add(new Recipe(name, description, instructions, food, portions));
    System.out.println("Your recipe has been saved.");
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