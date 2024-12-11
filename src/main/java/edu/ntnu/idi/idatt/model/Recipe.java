package edu.ntnu.idi.idatt.model;

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

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public ArrayList<Grocery> getFoods() {
    return foods;
  }

  public ArrayList<String> getInstructions() {
    return instructions;
  }

  public int getPortions() {
    return portions;
  }


}
