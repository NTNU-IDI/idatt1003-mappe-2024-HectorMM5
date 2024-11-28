package edu.ntnu.idi.idatt;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecipeTest {

  private Recipe testRecipe;

  @BeforeEach
  void setUp() {
    ArrayList<String> instructions = new ArrayList<>();
    instructions.add("Step 1");
    instructions.add("Step 2");
    instructions.add("Step 3");

    ArrayList<Grocery> foods = new ArrayList<>();
    foods.add(new Grocery("Tomato", "kg", 2f));
    foods.add(new Grocery("Salt", "g", 5f));

    testRecipe = new Recipe("Tomato Soup", "A simple tomato soup recipe.", instructions, foods, 4);
  }

  @Test
  void getName() {
    assertEquals("Tomato Soup", testRecipe.getName());
  }

  @Test
  void getDescription() {
    assertEquals("A simple tomato soup recipe.", testRecipe.getDescription());
  }

  @Test
  void getFoods() {
    ArrayList<Grocery> foods = testRecipe.getFoods();
    assertEquals(2, foods.size()); // Ensure two ingredients are present
    assertEquals("Tomato", foods.get(0).getName());
    assertEquals("Salt", foods.get(1).getName());
  }

  @Test
  void getInstructions() {
    ArrayList<String> instructions = testRecipe.getInstructions();
    assertEquals(3, instructions.size());
    assertEquals("Step 1", instructions.get(0));
    assertEquals("Step 2", instructions.get(1));
    assertEquals("Step 3", instructions.get(2));
  }

  @Test
  void getPortions() {
    assertEquals(4, testRecipe.getPortions());
  }

  @Test
  void testIntegrity() {
    // Ensures the integrity of all fields after creation
    assertEquals("Tomato Soup", testRecipe.getName());
    assertEquals("A simple tomato soup recipe.", testRecipe.getDescription());
    assertEquals(2, testRecipe.getFoods().size());
    assertEquals(3, testRecipe.getInstructions().size());
    assertEquals(4, testRecipe.getPortions());
  }
}
