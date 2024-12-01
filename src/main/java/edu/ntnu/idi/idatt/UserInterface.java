package edu.ntnu.idi.idatt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that handles the main user interface.
 */

public class UserInterface {

  /**
   * Handles the users actions in an infinite loop, until the program is terminated.
   *
   * @param scanner  * Scanner object to take in user input.
   */
  public void start(Scanner scanner) {

    System.out.println("Welcome to your food registry:"
        + "\nWrite \"1\" to access the fridge."
        + "\nWrite \"2\" to access the cookbook."
        + "\nWrite \"3\" to terminate the program.");

    int choice;
    boolean running = true;
    while (running) {
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
  private void enteredFridge(Scanner scanner) {
    boolean running = true;
    String command;
    System.out.println("You have now accessed the fridge."
        + "Enter command, or write \"/help\" to view the available commands.");

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
          System.out.println("Exited cookbook.");
          running = false;
          break;

        default:
          System.out.println("Invalid command. Write \"/help\" to see all available commands.");
      }
    }
  }

  private void handleNewItem(Scanner scanner) {
    System.out.println("Write the name of the grocery.");
    final String name = ValidateInput.forceValidString(scanner);

    System.out.println("Write the unit of the grocery.");
    final String unit = ValidateInput.forceValidString(scanner);

    System.out.println("Enter the amount (in numeric format):");
    final float amount = ValidateInput.forceValidFloat(scanner);

    System.out.println("Enter the cost (of the total amount):");
    float cost = ValidateInput.forceValidFloat(scanner);


    System.out.println("Enter the expiry date (DDMMYYYY):");
    LocalDate expiryDate;
    while (true) {
      try {
        expiryDate = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("ddMMyyyy"));
        break;
      } catch (DateTimeParseException e) {
        System.out.println("Invalid input, please enter the date in the format DDMMYYYY:");

      }
    }

    Fridge.newGrocery(name, unit, amount, cost, expiryDate);
  }

  private void handleUse(Scanner scanner) {
    System.out.println("Write the name of the grocery you want to use:");
    String groceryName = ValidateInput.forceValidString(scanner);

    System.out.println("Write the amount you want to use:");
    float consume = ValidateInput.forceValidFloat(scanner);

    try {
      Fridge.use(groceryName, consume);
    } catch (Exception e) {
      System.out.println("The item was either not found, or you do not have enough of it.");
    }
  }

  private void handleSearch(Scanner scanner) {
    System.out.println("Write the name of the grocery:");
    String groceryName = ValidateInput.forceValidString(scanner);

    ArrayList<Grocery> result = Fridge.search(groceryName);
    if (result != null) {
      System.out.println("The item was found. Search result:");
      for (Grocery grocery : result) {
        System.out.println(grocery.toString());
      }

    } else {
      System.out.println("The ingredient you're looking for does not exist.");
    }
  }

  private void handleOverview() {
    ArrayList<Grocery> items = Fridge.overview();
    for (Grocery grocery : items) {
      System.out.println(grocery.toString());
    }
    System.out.println("The combined value of items in the fridge is: "
        + Fridge.calculateValue(items) + " euros.");

  }

  private void handleExpiredOverview() {
    ArrayList<Grocery> expiredItems = Fridge.dateOverview();
    if (!expiredItems.isEmpty()) {
      System.out.println("These items have expired, but they may still be consumable.");
      for (Grocery grocery : expiredItems) {
        System.out.println(grocery.toString());
      }

      System.out.println("You may have lost up to " + Fridge.calculateValue(expiredItems)
          + " euros worth of food.");
    } else {
      System.out.println("You have no expired items.");
    }


  }

  private void handleExpiresBefore(Scanner scanner) {
    System.out.println("Enter the expiry date (in numeric format, e.g., DDMMYYYY):");
    LocalDate expiryDate;
    while (true) {
      try {
        expiryDate = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("ddMMyyyy"));
        break;
      } catch (DateTimeParseException e) {
        System.out.println("Invalid input, please enter the date in the format DDMMYYYY:");
      }
    }
    Fridge.expiresBefore(expiryDate);
  }

  private void handleValue() {
    Fridge.calculateValue(Fridge.overview());
  }

  //
  //COOKBOOK SECTION
  //
  private void enteredCookBook(Scanner scanner) {
    boolean running = true;
    String command;
    System.out.println("You have now accessed the cookbook."
        + " Enter a command, or write \"/help\" to view the available commands.");

    while (running) {
      command = ValidateInput.forceValidString(scanner);

      switch (command) {
        case "/createRecipe":
          handleCreateRecipe(scanner);
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


  private void handleCreateRecipe(Scanner scanner) {
    System.out.println("Now creating a new recipe.");

    System.out.println("Write the name of the dish:");
    final String dishName = ValidateInput.forceValidString(scanner);

    //If no dish with this name already exists:
    if (CookBook.search(dishName) == null) {
      System.out.println("Write a short description of the dish:");
      final String description = ValidateInput.forceValidString(scanner);

      ArrayList<Grocery> ingredients = new ArrayList<>();
      System.out.println("Specify the ingredients in the format: name, amount, unit.");
      System.out.println("Write \"Done\" when finished.");

      String line = "";
      while (!line.equalsIgnoreCase("Done")) {
        line = ValidateInput.forceValidString(scanner);

        String[] parts = line.split(",");
        if (parts.length == 3) {
          try {
            float amount = Float.parseFloat(parts[1].trim());
            if (amount > 0) {
              ingredients.add(new Grocery(parts[0].trim(), parts[2].trim(), amount));
            } else {
              System.out.println("Amount must be larger than zero. Try again.");
            }
          } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Use numeric values.");
          }
        } else {
          System.out.println("Invalid format. Use: name, amount, unit.");
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
    } else {
      System.out.println("A recipe with the given name already exists. Aborting operation.");
    }

  }


  void handleAvailableRecipes() {
    System.out.println("With the ingredients you have, you are able to make:");
    for (Recipe recipe : CookBook.recipeAvailability()) {
      System.out.println("  - " + recipe.getName());
    }
  }

  void handleCheckRecipe(Scanner scanner) {
    System.out.println("What recipe do you want to check?");
    String choice = ValidateInput.forceValidString(scanner);

    Recipe chosenRecipe = CookBook.search(choice);

    if (chosenRecipe != null) {
      boolean found = CookBook.recipeCheck(chosenRecipe);

      if (found) {
        System.out.println("Recipe " + chosenRecipe.getName() + " is possible to make.");
      } else {
        System.out.println("Recipe " + chosenRecipe.getName() + " is not possible to make.");
      }

    } else {
      System.out.println("Recipe " + choice + " was not found.");
    }
  }

  void handlePrintRecipe(Scanner scanner) {
    System.out.println("What recipe do you want to print?");
    String choice = ValidateInput.forceValidString(scanner);
    Recipe recipe = CookBook.search(choice);
    if (recipe == null) {
      System.out.println("Recipe " + choice + " was not found.");
    } else {

      //Prints out a small presentation
      System.out.println("Recipe " + recipe.getName() + " - " + recipe.getPortions() + " portions"
          + "\n" + recipe.getDescription() + "\n\nYou need:");

      //For each specified ingredient, print out the name, amount and unit in a string.
      for (Grocery food : recipe.getFoods()) {
        System.out.println(
            "    - " + food.getAmount() + " " + food.getUnit() + " "
                + food.getName());
      }
      System.out.println("\nInstructions:");
      //For each step in instructions, print out the instruction.
      //Keeping i in the loop for numbering of steps.
      for (int i = 0; i < recipe.getInstructions().size(); i++) {
        System.out.println(i + "." + recipe.getInstructions().get(i));
      }
    }
  }

  void handleAllRecipes() {
    for (Recipe recipe : CookBook.getRecipes()) {
      System.out.println(recipe.getName() + " - " + recipe.getPortions() + " portions");
    }
  }

  /**
   * Prints out an overview of all the fridge-related commands.
   */
  void fridgeHelp() {
    System.out.println("-----------------------------------------------------------");
    System.out.println("An overview of available FRIDGE commands can be seen below:");
    System.out.println("-----------------------------------------------------------");
    System.out.println("\n    - \"/newItem\" to add a new item.");
    System.out.println("    - \"/use\" to retrieve an item.");
    System.out.println(
        "    - \"/search\" to search for an item and retrieve associated information.");
    System.out.println("    - \"/overview\" to check everything that is currently in the fridge.");
    System.out.println(
        "    - \"/expiredOverview\" to check everything in the fridge that has expired.");
    System.out.println("    - \"/value\" to check the value of the food currently in the fridge.");
    System.out.println("-   - \"/back\" to return to the main menu.");
  }

  /**
   * Prints out an overview of all the CookBook-related commands.
   */

  void cookBookHelp() {
    System.out.println("--------------------------------------------------------");
    System.out.println("An overview of available cookbook commands can be seen below:");
    System.out.println("--------------------------------------------------------");
    System.out.println("\n    - \"/createRecipe\" to create a new recipe.");
    System.out.println("    - \"/availableRecipes\" to view recipes you can make with the "
        + "ingredients in the fridge.");
    System.out.println("    - \"/checkRecipe\" to check if you have enough ingredients to"
        + " make a specific recipe.");
    System.out.println("    - \"/printRecipe\" to print the details of a specific recipe.");
    System.out.println("    - \"/listRecipes\" to list all recipes saved in the cookbook.");
    System.out.println("    - \"/deleteRecipe\" to delete a recipe by its name.");
  }
}