package edu.ntnu.idi.idatt;

import java.util.ArrayList;

/**
 * Class representing a recipe. Holds name, description, instructions, ingredients and portions.
 */

public class Recipe {
  public String name;
  public String description;
  public ArrayList<Grocery> foods;
  private ArrayList<String> instructions;
  private int portions;

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
    System.out.println("Recipe " + this.name + "\n" + this.description + "\n\nYou need:");
    for (int i = 0; i < this.foods.size(); i++) {
      System.out.println(
          "    - " + this.foods.get(i).getAmount() + " " + this.foods.get(i).getUnit() + " "
              + this.foods.get(i).getName());
    }
    System.out.println("\nInstructions:");
    for (int i = 0; i < this.instructions.size(); i++) {
      System.out.println(i + "." + this.instructions.get(i));
    }
  }


}