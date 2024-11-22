package edu.ntnu.idi.idatt;

import java.util.ArrayList;

/**
 * Class representing a recipe. Holds name, description, instructions, ingredients and portions.
 */

public class Recipe {
  private final String name;
  private final String description;
  private final ArrayList<Grocery> foods;
  private final ArrayList<String> instructions;
  private final int portions;

  /**
   * Recipe constructor.
   *
   * @param name * Dish name
   * @param description * Dish description
   * @param instructions * Instruction description (array)
   * @param foods * Ingredients
   * @param portions * Amount of portions
   */
  public Recipe(String name, String description, ArrayList<String> instructions,
                ArrayList<Grocery> foods, int portions) {
    this.name = name;
    this.description = description;
    this.instructions = instructions;
    this.foods = foods;
    this.portions = portions;

  }



  /**
   * Prints out a recipe presentation and step-by-step guide.
   */

  public void writeRecipe() {
    //Prints out a small presentation
    System.out.println("Recipe " + this.name + " - " + this.portions + " portions"
        + "\n" + this.description + "\n\nYou need:");

    //For each specified ingredient, print out the name, amount and unit in a string.
    for (Grocery food : this.foods) {
      System.out.println(
          "    - " + food.getAmount() + " " + food.getUnit() + " "
              + food.getName());
    }
    System.out.println("\nInstructions:");
    for (int i = 0; i < this.instructions.size(); i++) {
      System.out.println(i + "." + this.instructions.get(i));
    }
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public ArrayList<Grocery> getFoods() {
    return new ArrayList<>(foods); // Return a defensive copy
  }

  public ArrayList<String> getInstructions() {
    return new ArrayList<>(instructions); // Return a defensive copy
  }

  public int getPortions() {
    return portions;
  }


}