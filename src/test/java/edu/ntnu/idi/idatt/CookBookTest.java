package edu.ntnu.idi.idatt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CookBookTest {



  @BeforeEach
  void setUp() {

  }

  @Test
  void testAddAndViewRecipes() {
    ArrayList<String> instructions = new ArrayList<>();
    instructions.add("Step 1");
    instructions.add("Step 2");
    instructions.add("Step 3");

    ArrayList<Grocery> ingredients = new ArrayList<>();
    ingredients.add(new Grocery("Tomato", "kg", 1.0f));

    CookBook.createRecipe("Tomato Soup", "A simple tomato soup.", instructions, ingredients, 2);

    // Capture output for the viewRecipes method
    CookBook.getRecipes();

    // Check if the recipe has been correctly added to the cookbook
    assertEquals(1, CookBook.recipeList.size());
    assertEquals("Tomato Soup", CookBook.recipeList.get(0).getName());
  }

  @Test
  void testRecipeAvailabilityWithAvailableIngredients() {
    // Create mock recipe with ingredients
    ArrayList<String> instructions = new ArrayList<>(Arrays.asList("Boil water", "Add ingredients"));
    ArrayList<Grocery> ingredients = new ArrayList<>();
    ingredients.add(new Grocery("Pasta", "g", 200.0f));
    ingredients.add(new Grocery("Salt", "g", 10.0f));

    Recipe pastaRecipe = new Recipe("Pasta", "Simple pasta recipe", instructions, ingredients, 2);
    CookBook.recipeList.add(pastaRecipe);

    // Add ingredients to the fridge to satisfy the recipe
    Fridge.ingredients.add(new Grocery("Pasta", "g", 250.0f));
    Fridge.ingredients.add(new Grocery("Salt", "g", 20.0f));

    // Check if the recipe is available
    CookBook.recipeAvailability();

    // We should have one recipe available that can be made with the current ingredients
    assertTrue(CookBook.recipeList.contains(pastaRecipe));
  }

  @Test
  void testRecipeAvailabilityWithInsufficientIngredients() {
    // Create a mock recipe
    ArrayList<String> instructions = new ArrayList<>(Arrays.asList("Mix ingredients", "Bake at 350F"));
    ArrayList<Grocery> ingredients = new ArrayList<>();
    ingredients.add(new Grocery("Flour", "g", 500.0f));
    ingredients.add(new Grocery("Sugar", "g", 100.0f));

    Recipe cakeRecipe = new Recipe("Cake", "Simple cake recipe", instructions, ingredients, 4);
    CookBook.recipeList.add(cakeRecipe);

    // Add insufficient ingredients to the fridge
    Fridge.ingredients.add(new Grocery("Flour", "g", 200.0f));
    Fridge.ingredients.add(new Grocery("Sugar", "g", 50.0f));


    // The recipe should not be available since we don't have enough ingredients

    assertFalse(CookBook.recipeAvailability().contains(cakeRecipe));
  }
}