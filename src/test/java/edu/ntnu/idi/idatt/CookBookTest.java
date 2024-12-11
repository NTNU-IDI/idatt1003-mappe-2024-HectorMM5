package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.service.Fridge;
import edu.ntnu.idi.idatt.service.CookBook;
import edu.ntnu.idi.idatt.model.Grocery;
import edu.ntnu.idi.idatt.model.Recipe;
import edu.ntnu.idi.idatt.model.Unit;


import edu.ntnu.idi.idatt.util.Utility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CookBookTest {

  @BeforeEach
  void setUp() {
    // Resets the lists
    for (Recipe recipe : CookBook.getRecipes()) {
      CookBook.deleteRecipe(recipe.getName());
    }

    for (Grocery grocery : Fridge.overview()) {
      Fridge.use(grocery.getName(), grocery.getAmount());
    }

    ArrayList<String> instructions = new ArrayList<>();
    instructions.add("Step 1");
    instructions.add("Step 2");
    instructions.add("Step 3");

    ArrayList<Grocery> ingredients = new ArrayList<>();
    ingredients.add(new Grocery("Tomato", Unit.KILOGRAM, 1.0f));

    CookBook.createRecipe("Tomato Soup", "A simple tomato soup.", instructions, ingredients, 2);
  }

  @Test
  void createRecipe() {
    ArrayList<String> instructions = new ArrayList<>();
    instructions.add("Step 1");
    instructions.add("Step 2");
    instructions.add("Step 3");

    ArrayList<Grocery> ingredients = new ArrayList<>();
    ingredients.add(new Grocery("Carrot", Unit.KILOGRAM, 1.0f));

    CookBook.createRecipe("Shredded carrots", "Carrots but shredded.", instructions, ingredients, 2);

    // Check if the recipe has been correctly added to the cookbook
    // Remember alphabetical order
    assertEquals(2, CookBook.getRecipes().size());
    assertEquals("Shredded carrots", CookBook.getRecipes().get(0).getName());
  }

  @Test
  void recipeCheck() {
    ArrayList<String> instructions = new ArrayList<>();
    instructions.add("Step 1");
    instructions.add("Step 2");
    instructions.add("Step 3");

    ArrayList<Grocery> ingredients = new ArrayList<>();
    ingredients.add(new Grocery("Pasta", Unit.GRAM, 200.0f));
    ingredients.add(new Grocery("Salt", Unit.GRAM, 10.0f));

    CookBook.createRecipe("Pasta", "Simple pasta recipe.", instructions, ingredients, 2);

    // Adding enough tomatoes for the default "Tomato soup" recipe
    Fridge.newGrocery("Tomato", Unit.KILOGRAM, 3.0f, 10, LocalDate.of(2025, 1, 1));

    //Since only Tomato soup has the necessary ingredients, only it should pass
    ArrayList<Recipe> recipes = CookBook.getRecipes();
    // Pasta recipe
    assertFalse(CookBook.recipeCheck(recipes.get(0)));
    // Tomato soup
    assertTrue(CookBook.recipeCheck(recipes.get(1)));
  }

  @Test
  void deleteRecipe() {
    ArrayList<String> instructions = new ArrayList<>();
    instructions.add("Step 1");
    instructions.add("Step 2");
    instructions.add("Step 3");

    ArrayList<Grocery> ingredients = new ArrayList<>();
    ingredients.add(new Grocery("Pasta", Unit.GRAM, 200.0f));
    ingredients.add(new Grocery("Salt", Unit.GRAM, 10.0f));

    // Create new recipe to differentiate
    CookBook.createRecipe("Pasta", "Simple pasta recipe.", instructions, ingredients, 2);

    // Deletes default recipe Tomato soup
    CookBook.deleteRecipe("Tomato Soup");

    // If successful deletion, the following is true:
    assertEquals(1, CookBook.getRecipes().size());
    assertEquals("Pasta", CookBook.getRecipes().get(0).getName());
  }

  @Test
  void recipeAvailability() {
    ArrayList<String> instructions = new ArrayList<>();
    instructions.add("Step 1");
    instructions.add("Step 2");
    instructions.add("Step 3");

    ArrayList<Grocery> ingredients = new ArrayList<>();
    ingredients.add(new Grocery("Pasta", Unit.GRAM, 200.0f));
    ingredients.add(new Grocery("Salt", Unit.GRAM, 10.0f));

    // Two recipes to ensure both are returned
    // A third to ensure it gets filtered away
    CookBook.createRecipe("Pasta", "Simple pasta recipe.", instructions, ingredients, 2);
    CookBook.createRecipe("Simpler pasta", "Even simpler pasta recipe.", instructions, ingredients, 2);

    ArrayList<Grocery> fake = new ArrayList<>();
    fake.add(new Grocery("Fake", Unit.GRAM, 1));

    CookBook.createRecipe("Z", "Fake pasta recipe.", instructions, fake, 2);

    // Store ingredients needed to make the recipe
    Fridge.newGrocery("Pasta", Unit.GRAM, 250.0f, 10, LocalDate.of(2025, 1, 1));
    Fridge.newGrocery("Salt", Unit.GRAM, 20.0f, 10, LocalDate.of(2025, 1, 1));

    ArrayList<Recipe> recipes = CookBook.getRecipes();
    ArrayList<Recipe> availableRecipes = CookBook.recipeAvailability();

    // Pasta, ingredients were added so the recipe should appear
    assertTrue(availableRecipes.contains(recipes.get(0)));

    // Simpler pasta, should appear
    assertTrue(availableRecipes.contains(recipes.get(1)));

    //Tomato soup, should NOT appear
    assertFalse(availableRecipes.contains(recipes.get(2)));

    // Due to alphabetical order, this last element will be "Z".
    // It should not be in the given ArrayList
    assertFalse(availableRecipes.contains(recipes.get(3)));

    assertEquals(2, availableRecipes.size());
  }

  @Test
  void search() {
    ArrayList<String> instructions = new ArrayList<>();
    instructions.add("Step 1");
    instructions.add("Step 2");
    instructions.add("Step 3");

    ArrayList<Grocery> ingredients = new ArrayList<>();
    ingredients.add(new Grocery("Pasta", Unit.GRAM, 200.0f));
    ingredients.add(new Grocery("Salt", Unit.GRAM, 10.0f));

    // Make an object, then search and compare it
    CookBook.createRecipe("Pasta", "Simple pasta recipe.", instructions, ingredients, 2);

    // If search function is successful, a reference to pastaRecipe will be created
    assertSame(CookBook.search("Pasta"), CookBook.getRecipes().get(0));
  }

  @Test
  void cookRecipe() {
    //Create a pasta recipe
    ArrayList<String> instructions = new ArrayList<>();
    instructions.add("Step 1");
    instructions.add("Step 2");
    instructions.add("Step 3");

    ArrayList<Grocery> ingredients = new ArrayList<>();
    ingredients.add(new Grocery("Pasta", Unit.GRAM, 200.0f));
    ingredients.add(new Grocery("Salt", Unit.GRAM, 10.0f));
    ingredients.add(new Grocery("Tomato", Unit.KILOGRAM, 0.3f));

    // Make a recipe
    CookBook.createRecipe("Tomato pasta", "Simple pasta recipe.", instructions, ingredients, 2);

    Fridge.newGrocery("Pasta", Unit.GRAM, 250.0f, 10, LocalDate.of(2025, 1, 1));
    Fridge.newGrocery("Salt", Unit.GRAM, 20.0f, 10, LocalDate.of(2025, 1, 1));
    Fridge.newGrocery("Tomato", Unit.KILOGRAM, 0.3f, 10, LocalDate.of(2025, 1, 1));

    //Returns true if successful
    assertTrue(CookBook.cookRecipe(CookBook.search("Tomato pasta")));

    //We used all the tomatoes, so it should be deleted from the Fridge
    assertTrue(Utility.search(Fridge.overview(), "Tomato").isEmpty());
    //We used 200g of pasta, 50g should remain
    assertEquals(0.050, Utility.search(Fridge.overview(), "Pasta").get(0).getAmount(), 0.0001);
    //We used 10g of salt, 10g should remain
    assertEquals(0.010, Utility.search(Fridge.overview(), "Salt").get(0).getAmount(), 0.0001);

  }

  @Test
  void cookRecipeNegative() {
    //Since tomato soup (default recipe) has no necessary ingredients in the fridge, return should be false

    assertFalse(CookBook.cookRecipe(CookBook.search("Tomato soup")));

  }


}
