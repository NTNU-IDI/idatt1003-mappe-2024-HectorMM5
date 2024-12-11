package edu.ntnu.idi.idatt.controller;


import edu.ntnu.idi.idatt.FridgeFunctions;
import edu.ntnu.idi.idatt.model.Grocery;
import edu.ntnu.idi.idatt.model.Recipe;
import edu.ntnu.idi.idatt.model.Unit;
import edu.ntnu.idi.idatt.service.CookBook;
import edu.ntnu.idi.idatt.service.Fridge;
import edu.ntnu.idi.idatt.util.Utility;
import edu.ntnu.idi.idatt.util.ValidateInput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Class that handles the main user interface.
 */

public class UserInterface {

  /**
   * Initializes a set of default groceries.
   */
  public void init() {
    LocalDate defaultDate = LocalDate.of(2024, 12, 24);
    //Creates default groceries
    Fridge.newGrocery("Banana", Unit.PIECES, 3, 30, defaultDate);
    Fridge.newGrocery("Milk", Unit.LITRE, 1.75f, 35, defaultDate);
    Fridge.newGrocery("Chocolate", Unit.GRAM, 200, 45, defaultDate);

    System.out.println("\033[38;2;205;127;50m"
        + "=================================================\033[0m");

    System.out.println("\033[38;2;234;221;202m     WELCOME TO YOUR PERSONAL FOOD REGISTRY!     ");
    System.out.println("\033[38;2;234;221;202m   Manage your fridge, plan recipes, and more.   ");
    System.out.println("\033[38;2;205;127;50m"
        + "=================================================\033[0m");

  }

  /**
   * Entry point.
   * Handles the users actions in an infinite loop, until the program is terminated.
   *
   * @param scanner Scanner object to take in user input.
   */

  public void start(Scanner scanner) {
    int choice;
    boolean running = true;
    while (running) {
      System.out.println("\033[38;2;234;221;202m\nWrite \"1\" to access the fridge."
          + "\nWrite \"2\" to access the cookbook."
          + "\nWrite \"3\" to terminate the program.");

      choice = ValidateInput.forceValidInteger(scanner);

      switch (choice) {
        case 1:
          System.out.println("You have now entered the fridge.");
          enteredFridge(scanner);
          break;

        case 2:
          System.out.println("You have now entered the cookbook.");
          enteredCookBook(scanner);
          break;

        case 3:
          System.out.println("You have now terminated the program.");
          running = false;
          break;

        default:
          System.out.println("Invalid choice.");
      }
    }
  }

  //
  //FRIDGE SECTION
  //
  /**
   * Brings the user through the fridge menu and handles their commands.
   *
   * @param scanner Scanner object to take user input.
   */
  private void enteredFridge(Scanner scanner) {
    boolean running = true;
    String command;
    System.out.println("Enter command, or write \"/help\" to view the available commands.");

    while (running) {
      command = ValidateInput.forceValidString(scanner);

      switch (command) {
        case "/newItem":
          handleNewItem(scanner);
          break;

        case "/use":
          handleUse(scanner);
          break;

        case "/search":
          handleSearch(scanner);
          break;

        case "/overview":
          handleOverview();
          break;

        case "/expiredOverview":
          handleExpiredOverview();
          break;

        case "/expiresBefore":
          handleExpiresBefore(scanner);
          break;

        case "/value":
          handleValue();
          break;

        case "/help":
          fridgeHelp();
          break;

        case "/back":
          System.out.println("Exited FRIDGE.");
          running = false;
          break;

        default:
          System.out.println("Invalid command. Write \"/help\" to see all available commands.");
      }
    }
  }

  /**
   * Guides the user through the process of creating a new grocery item.
   *
   * @param scanner Scanner object to read user input.
   */
  private void handleNewItem(Scanner scanner) {
    System.out.println("Write the name of the grocery.");
    final String name = ValidateInput.forceValidString(scanner);

    System.out.println("Write the unit of the grocery.");
    final Unit unit = Unit.forceValidUnit(scanner);

    System.out.println("Enter the amount (in numeric format):");
    final float amount = ValidateInput.forceValidFloat(scanner);

    System.out.println("Enter the cost (of the total amount):");
    float cost = ValidateInput.forceValidFloat(scanner);


    System.out.println("Enter the expiry date (DDMMYYYY):");
    LocalDate expiryDate;
    while (true) {
      //Verifies valid user input for the grocery
      try {
        expiryDate = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("ddMMyyyy"));
        break;
      } catch (DateTimeParseException e) {
        System.out.println("Invalid input, please enter the date in the format DDMMYYYY:");

      }
    }

    if (Fridge.newGrocery(name, unit, amount, cost, expiryDate)) {
      //If grocery was successfully created:
      System.out.println("The registration was successful.");
    } else {
      //If an equivalent grocery was previously created using a different unit, abort operation.
      System.out.println("It appears you have previously registered this item under"
          + " a different unit type. The operation has been aborted.");
    }
  }

  /**
   * Guides the user through using a specified amount of a grocery item from the fridge.
   *
   * @param scanner Scanner object to read user input.
   */
  private void handleUse(Scanner scanner) {
    System.out.println("Write the name of the grocery you want to use:");
    String groceryName = ValidateInput.forceValidString(scanner);

    System.out.println("Write the amount you want to use:");
    float consume = ValidateInput.forceValidFloat(scanner);

    Fridge.UseStatus outcome = Fridge.use(groceryName, consume);

    switch (outcome) {
      case SUCCESS:
        System.out.println("You have successfully used the grocery.");
        break;

      case INSUFFICIENT_AMOUNT:
        System.out.println("You do not have enough to use this much of the grocery.");
        break;

      case ITEM_NOT_FOUND:
        System.out.println("No item under that name was found.");
    }
  }

  /**
   * Allows the user to search for a specific grocery item in the fridge by name.
   * Displays all matching results or notifies the user if no matches are found.
   *
   * @param scanner Scanner object to read user input.
   */
  private void handleSearch(Scanner scanner) {
    System.out.println("Write the name of the grocery:");
    String groceryName = ValidateInput.forceValidString(scanner);

    ArrayList<Grocery> result = Utility.search(Fridge.overview(), groceryName);

    Utility.displayList(result,
        "Search results:",
        "The ingredient you're looking for does not exist.");
  }

  /**
   * Displays an overview of all groceries in the fridge, including their total value.
   */
  private void handleOverview() {
    ArrayList<Grocery> items = Fridge.overview();

    Utility.displayList(items,
        "All groceries are displayed below:",
        "The fridge is empty.");

    System.out.println("The combined value of items in the fridge is: "
        + Utility.calculateValue(items) + " euros.");

  }

  /**
   * Displays all the expired items currently in the fridge.
   * If no items have expired, notifies the user.
   */
  private void handleExpiredOverview() {
    ArrayList<Grocery> expiredItems = FridgeFunctions.dateOverview();
    Utility.displayList(expiredItems,
        "The following items are expired:",
        "None of your items have expired.");

  }

  /**
   * Displays all the groceries in the fridge that will expire before a given date.
   *
   * @param scanner Scanner object to take in the expiry date from the user.
   */
  private void handleExpiresBefore(Scanner scanner) {
    System.out.println("Enter the expiry date (in numeric format, e.g., DDMMYYYY):");

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");

    LocalDate expiryDate = null;
    while (expiryDate == null) {
      try {
        expiryDate = LocalDate.parse(scanner.nextLine(), dateTimeFormatter);

      } catch (DateTimeParseException e) {
        System.out.println("Invalid input, please enter the date in the format DDMMYYYY:");
      }
    }

    String dateToText = expiryDate.format(dateTimeFormatter);

    ArrayList<Grocery> willExpire = FridgeFunctions.expiresBefore(expiryDate);

    Utility.displayList(willExpire,
        null,
        "The following groceries will expire before " + dateToText + ":");
  }

  /**
   * Calculates and displays the total value of all existing groceries in the fridge.
   */
  private void handleValue() {
    System.out.println("The current value of items in the fridge is: "
        + Utility.calculateValue(Fridge.overview()) + " euros.");
  }

  //
  //COOKBOOK SECTION
  //

  /**
   * Handles user interactions within the cookbook section.
   * Allows the user to create, delete, view, and check recipes or get help.
   *
   * @param scanner Scanner object to read user input.
   */
  private void enteredCookBook(Scanner scanner) {
    boolean running = true;
    String command;
    System.out.println("Enter a command, or write \"/help\" to view the available commands.");

    while (running) {
      command = ValidateInput.forceValidString(scanner);

      switch (command) {
        case "/createRecipe":
          handleCreateRecipe(scanner);
          break;

        case "/cookRecipe":
          handleCookRecipe(scanner);
          break;

        case "/availableRecipes":
          handleAvailableRecipes();
          break;

        case "/checkRecipe":
          handleCheckRecipe(scanner);
          break;

        case "/printRecipe":
          handlePrintRecipe(scanner);
          break;

        case "/listRecipes":
          handleAllRecipes();
          break;

        case "/deleteRecipe":
          handleDeleteRecipe(scanner);
          break;

        case "/help":
          cookBookHelp();
          break;

        case "/back":
          System.out.println("Exited cookbook.");
          running = false;
          break;

        default:
          System.out.println("Invalid command. Write \"/help\" to see all available commands.");
      }
    }
  }

  /**
   * Handles the process of deleting a recipe from the cookbook.
   * Prompts the user for the name of the recipe to delete and confirms deletion.
   *
   * @param scanner Scanner object to read user input.
   */
  private void handleDeleteRecipe(Scanner scanner) {
    System.out.println("Write the name of the recipe you want to delete");
    String choiceName = ValidateInput.forceValidString(scanner);

    //If returns true (meaning successful search and deletion):
    if (CookBook.deleteRecipe(choiceName)) {
      System.out.println("Recipe deleted.");
    } else {
      System.out.println("A recipe with the given name was not found.");
    }
  }

  private void handleCookRecipe(Scanner scanner) {
    System.out.println("Write the name of the recipe you want to use:");
    String choiceName = ValidateInput.forceValidString(scanner);

    Recipe recipe = CookBook.search(choiceName);

    if (!(recipe == null)) {
      boolean available = CookBook.recipeCheck(recipe);

      if (available) {
        recipe.getFoods()
            .forEach(grocery -> Fridge.use(grocery.getName(), grocery.getAmount()));
      } else {
        System.out.println("You cannot make this recipe right now.");
      }
    } else {
      System.out.println("A recipe with the given name was not found.");
    }
  }

  /**
   * Guides the user through creating a new recipe.
   * Prompts for recipe details such as name, description, ingredients, instructions, and portions.
   * Ensures the recipe is not a duplicate and validates ingredient input.
   *
   * @param scanner Scanner object to read user input.
   */
  private void handleCreateRecipe(Scanner scanner) {
    System.out.println("Now creating a new recipe.");

    System.out.println("Write the name of the dish:");
    final String dishName = ValidateInput.forceValidString(scanner);

    //If a dish with this name already exists, abort operation with early return
    if (CookBook.search(dishName) != null) {
      System.out.println("A recipe with the given name already exists. Aborting operation.");
      return;
    }

    System.out.println("Write a short description of the dish:");
    final String description = ValidateInput.forceValidString(scanner);

    ArrayList<Grocery> ingredients = new ArrayList<>();
    System.out.println("Specify the ingredients in the format: name, amount, unit.");
    System.out.println("Write \"Done\" when finished.");

    String line = "";
    while (!line.equalsIgnoreCase("Done")) {
      line = ValidateInput.forceValidString(scanner);
      String[] parts = line.split(",");

      if (line.equalsIgnoreCase("Done")) {
        continue;
      }

      //Input verification:
      if (parts.length != 3) {
        //User input was invalid, new loop instance created
        System.out.println("Invalid format. Use: name, amount, unit. Write \"Done\" when finished");
        continue;
      }

      String name = parts[0].trim();
      if (name.isEmpty()) {
        //Name was empty, new loop instance created
        System.out.println("Name cannot be empty.");
        continue;
      }

      try {
        //Tries parsing amount and searching for unit
        float amount = Float.parseFloat(parts[1].trim());
        Unit unit = Unit.searchMetric(parts[2].trim());

        //If amount is above 0, check for existing food profiles, to ensure correct functionality
        //Limits the user's ability to break the program by typing in a very small amount

        if (!(amount * unit.getValue() > 0.00001)) {
          System.out.println("Amount must be larger than zero. Try again.");
          //Early continue
          continue;
        }

        //Gets the profiles for all groceries so far
        ArrayList<Grocery> existingUnits =
            Utility.search(FridgeFunctions.getGroceryProfiles(), parts[0].trim());

        if (!existingUnits.isEmpty()) {
          //Only one object should be present in this list
          String existingUnit = existingUnits.get(0).getUnit().getMetricType();
          if (!existingUnit.equalsIgnoreCase(unit.getMetricType())) {
            //Ensures user isn't registering under a different unit type
            System.out.println("Unit mismatch. "
                + "The existing unit for \"" + name + "\" is " + existingUnit + ".");
            System.out.println("Try again.");
            continue;
          } else {
            ingredients.add(new Grocery(name, unit, amount));
          }
        } else {
          //If no grocery profile was found, this is the first time the object is created
          //Therefore, add the ingredient and create the grocery profile
          ingredients.add(new Grocery(name, unit, amount));
          FridgeFunctions.createGroceryProfile(name, unit);
        }

      } catch (NumberFormatException e) {
        //If parsing fails
        System.out.println("Invalid amount. Please use appropriate values.");

      } catch (IllegalArgumentException e) {
        //If a unit is not found
        System.out.println("Unit must be one of the following: g, kg, L, mL or pcs. Try again.");
      }
    }

    final ArrayList<String> instructions = new ArrayList<>();
    System.out.println("Write the instructions, one line at a time.");
    System.out.println("Write \"Done\" when finished.");

    line = "";
    while (!line.equalsIgnoreCase("Done")) {
      line = ValidateInput.forceValidString(scanner);
      instructions.add(line);
    }

    System.out.println("How many portions does this make?");
    int portions = ValidateInput.forceValidInteger(scanner);

    CookBook.createRecipe(dishName, description, instructions, ingredients, portions);
    System.out.println("Recipe saved successfully.");
  }

  /**
   * Displays all recipes that can currently be made with the ingredients available in the fridge.
   */
  void handleAvailableRecipes() {
    ArrayList<Recipe> availableRecipes = CookBook.recipeAvailability();

    if (!availableRecipes.isEmpty()) {
      System.out.println("With the ingredients you have, you are able to make:");
      for (Recipe recipe : availableRecipes) {
        System.out.println("  - " + recipe.getName());
      }
    } else {
      System.out.println("No available recipes.");
    }
  }

  /**
   * Check if a specific recipe can be made using the existing groceries in the fridge.
   * Prompts the user for the recipe name and displays the result.
   *
   * @param scanner Scanner object to read user input.
   */
  void handleCheckRecipe(Scanner scanner) {
    System.out.println("What recipe do you want to check?");
    String choice = ValidateInput.forceValidString(scanner);
    Recipe chosenRecipe = CookBook.search(choice);

    if (chosenRecipe != null) {
      boolean possible = CookBook.recipeCheck(chosenRecipe);
      if (possible) {
        System.out.println("Recipe " + chosenRecipe.getName() + " is possible to make.");
      } else {
        System.out.println("Recipe " + chosenRecipe.getName() + " is not possible to make.");
      }

    } else {
      System.out.println("Recipe " + choice + " was not found.");
    }
  }

  /**
   * Displays the full details of a specific recipe.
   * Including name, portions, description, ingredients, and instructions.
   * Prompts the user for the name of the recipe to print.
   *
   * @param scanner Scanner object to read user input.
   */
  void handlePrintRecipe(Scanner scanner) {
    System.out.println("What recipe do you want to print?");
    String choice = ValidateInput.forceValidString(scanner);
    Recipe recipe = CookBook.search(choice);
    if (recipe == null) {
      System.out.println("Recipe " + choice + " was not found.");
    } else {
      //Prints out a small presentation
      System.out.println(recipe.getName() + " - " + recipe.getPortions() + " portions"
          + "\n" + recipe.getDescription() + "\n\nYou need:");

      //For each specified ingredient, print out the name, amount and unit in a string.
      for (Grocery food : recipe.getFoods()) {
        System.out.println(Utility.presentIngredient(food));
      }

      System.out.println("\nInstructions:");
      //For each step in instructions, print out the instruction.
      //ChatGPT
      AtomicInteger counter = new AtomicInteger(1); // Start numbering from 1
      recipe.getInstructions()
          .forEach(instruction ->
              System.out.println(counter.getAndIncrement() + ". " + instruction));
    }
  }

  /**
   * Displays all the recipes in the cookbook with their names and portions.
   * Recipes are numerated for better readability.
   */
  void handleAllRecipes() {
    System.out.println("Here are all the registered recipes:");
    ArrayList<Recipe> recipes = CookBook.getRecipes();

    AtomicInteger counter = new AtomicInteger(1); // Start numbering from 1
    recipes.forEach(recipe ->
            System.out.println(counter.getAndIncrement() + ". " + recipe.getName()));
  }

  /**
   * Prints out an overview of all the fridge-related commands.
   */
  void fridgeHelp() {
    System.out.println("------------------------------------------------------------------------");
    System.out.println("      An overview of available FRIDGE commands can be seen below:");
    System.out.println("------------------------------------------------------------------------");
    System.out.println("\n    - \"/newItem\" to add a new item.");
    System.out.println("    - \"/use\" to retrieve an item.");
    System.out.println(
        "    - \"/search\" to search for an item and retrieve associated information.");
    System.out.println("    - \"/overview\" to check everything that is currently in the fridge.");
    System.out.println(
        "    - \"/expiredOverview\" to check everything in the fridge that has expired.");
    System.out.println("    - \"/value\" to check the value of the food currently in the fridge.");
    System.out.println("    - \"/back\" to return to the main menu.");
  }

  /**
   * Prints out an overview of all the CookBook-related commands.
   */
  void cookBookHelp() {
    System.out.println("------------------------------------------------------------------------");
    System.out.println("     An overview of available COOKBOOK commands can be seen below:");
    System.out.println("------------------------------------------------------------------------");
    System.out.println("\n    - \"/createRecipe\" to create a new recipe.");
    System.out.println("    - \"/cookRecipe\" to cook a recipe (consuming their items).");
    System.out.println("    - \"/availableRecipes\" to view recipes you can make with the "
        + "ingredients in the fridge.");
    System.out.println("    - \"/checkRecipe\" to check if you have enough ingredients to"
        + " make a specific recipe.");
    System.out.println("    - \"/printRecipe\" to print the details of a specific recipe.");
    System.out.println("    - \"/listRecipes\" to list all recipes saved in the cookbook.");
    System.out.println("    - \"/deleteRecipe\" to delete a recipe by its name.");
  }
}
