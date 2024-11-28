package edu.ntnu.idi.idatt;

import java.lang.reflect.Array;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CookBookTest {



  @BeforeEach
  void setUp() {
    CookBook.recipeList.clear();
    Fridge.ingredients.clear();

    ArrayList<String> instructions = new ArrayList<>();
    instructions.add("Step 1");
    instructions.add("Step 2");
    instructions.add("Step 3");

    ArrayList<Grocery> ingredients = new ArrayList<>();
    ingredients.add(new Grocery("Tomato", "kg", 1.0f));

    CookBook.recipeList.add(new Recipe("Tomato Soup", "A simple tomato soup.", instructions, ingredients, 2));

  }

  @Test
  void createRecipe() {

    ArrayList<String> instructions = new ArrayList<>();
    instructions.add("Step 1");
    instructions.add("Step 2");
    instructions.add("Step 3");

    ArrayList<Grocery> ingredients = new ArrayList<>();
    ingredients.add(new Grocery("Carrot", "kg", 1.0f));

    CookBook.createRecipe("Shredded carrots", "Carrots but shredded.", instructions, ingredients, 2);

    // Check if the recipe has been correctly added to the cookbook
    assertEquals(2, CookBook.recipeList.size());
    assertEquals("Shredded carrots", CookBook.recipeList.get(1).getName());
  }

  @Test
  void recipeCheck() {
    ArrayList<String> instructions = new ArrayList<>();
    instructions.add("Step 1");
    instructions.add("Step 2");
    instructions.add("Step 3");

    ArrayList<Grocery> ingredients = new ArrayList<>();
    ingredients.add(new Grocery("Pasta", "g", 200.0f));
    ingredients.add(new Grocery("Salt", "g", 10.0f));

    CookBook.createRecipe("Pasta", "Simple pasta recipe.", instructions, ingredients, 2);

    //Adding enough tomatoes for the default "Tomato soup" recipe
    Fridge.ingredients.add(new Grocery("Tomato", "kg", 3.0f));

    ArrayList<Recipe> recipes = CookBook.getRecipes();
    //Pasta recipe
    assertFalse(CookBook.recipeCheck(recipes.get(0)));
    //Tomato soup
    assertTrue(CookBook.recipeCheck(recipes.get(1)));

  }

  @Test
  void deleteRecipe() {
    ArrayList<String> instructions = new ArrayList<>();
    instructions.add("Step 1");
    instructions.add("Step 2");
    instructions.add("Step 3");

    ArrayList<Grocery> ingredients = new ArrayList<>();
    ingredients.add(new Grocery("Pasta", "g", 200.0f));
    ingredients.add(new Grocery("Salt", "g", 10.0f));

    //Created new recipe, to differentiate
    CookBook.createRecipe("Pasta", "Simple pasta recipe.", instructions, ingredients, 2);

    //Deletes default recipe Tomato soup
    CookBook.deleteRecipe("Tomato soup");

    //If successful deletion, the following is true:
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
    ingredients.add(new Grocery("Pasta", "g", 200.0f));
    ingredients.add(new Grocery("Salt", "g", 10.0f));

    //Two recipes to ensure both are returned
    //A third to ensure it gets filtered away
    CookBook.createRecipe("Pasta", "Simple pasta recipe.", instructions, ingredients, 2);
    CookBook.createRecipe("Simpler pasta", "Even simpler pasta recipe", instructions, ingredients, 2);

    ArrayList<Grocery> fake = new ArrayList<>();
    fake.add(new Grocery("Fake", "g", 1));

    CookBook.createRecipe("Z", "Fake pasta recipe", instructions, fake, 2);

    // Store ingredients needed to make the recipe.
    Fridge.ingredients.add(new Grocery("Pasta", "g", 250.0f));
    Fridge.ingredients.add(new Grocery("Salt", "g", 20.0f));

    ArrayList<Recipe> recipes = CookBook.getRecipes();
    ArrayList<Recipe> availableRecipes = CookBook.recipeAvailability();


    assertTrue(availableRecipes.contains(recipes.get(0)));
    assertTrue(availableRecipes.contains(recipes.get(1)));
    //Remember alphabetical order
    assertFalse(availableRecipes.contains(recipes.get(2)));

    assertEquals(2, availableRecipes.size());

  }

  @Test
  void search() {
    ArrayList<String> instructions = new ArrayList<>();
    instructions.add("Step 1");
    instructions.add("Step 2");
    instructions.add("Step 3");

    ArrayList<Grocery> ingredients = new ArrayList<>();
    ingredients.add(new Grocery("Pasta", "g", 200.0f));
    ingredients.add(new Grocery("Salt", "g", 10.0f));

    //Making an object, to then search and compare it
    Recipe pastaRecipe = new Recipe("Pasta", "Simple pasta recipe.", instructions, ingredients, 2);

    CookBook.recipeList.add(pastaRecipe);

    //If search function is successful, a reference to pastaRecipe will be created:
    assertSame(CookBook.search("Pasta"), pastaRecipe);

  }
}