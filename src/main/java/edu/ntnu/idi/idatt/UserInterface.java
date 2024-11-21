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
   *
   * @param scanner * Scanner object to take in user input.
   * @param fridge * Fridge object as food storage reference.
   * @param cookBook * Cookbook object with a set of saved recipes.
   */
  public void start(Scanner scanner, Fridge fridge, CookBook cookBook) {
    String input;

    System.out.println("Welcome to the fridge. Write in a command below, or write \"help\" to "
        + "check all the available commands.");

    while (true) {
      input = scanner.nextLine();

      switch (input) {
        case "/newItem":
          handleNewItem(scanner, fridge);
          break;

        case "/use":
          handleUse(scanner, fridge);
          break;

        case "/search":
          handleSearch(scanner, fridge);
          break;

        case "/overview":
          handleOverview(fridge);
          break;

        case "/expiredOverview":
          handleExpiredOverview(fridge);
          break;

        case "/expiresBefore":
          handleExpiresBefore(scanner, fridge);
          break;

        case "/value":
          handleValue(fridge);
          break;

        case "/createRecipe":
          handleCreateRecipe(scanner, cookBook);
          break;

        case "/availableRecipes":
          handleAvailableRecipes(fridge, cookBook);
          break;

        case "/checkRecipe":
          handleCheckRecipe(scanner, cookBook, fridge);
          break;

        default:
          System.out.println("Invalid command. Write \"/help\" to see all available commands.");
      }
    }
  }

  private void handleNewItem(Scanner scanner, Fridge fridge) {
    System.out.println("Write the name of the grocery.");
    final String name = ValidateInput.forceValidString(scanner);

    System.out.println("Write the unit of the grocery.");
    final String unit = ValidateInput.forceValidString(scanner);

    System.out.println("Enter the amount (in numeric format):");
    final float amount = ValidateInput.forceValidFloat(scanner);

    System.out.println("Enter the cost (of the total amount):");
    float cost = ValidateInput.forceValidFloat(scanner);


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

    fridge.newGrocery(name, unit, amount, cost, expiryDate);
  }

  private void handleUse(Scanner scanner, Fridge fridge) {
    System.out.println("Write the name of the grocery you want to use:");
    String groceryName = ValidateInput.forceValidString(scanner);

    System.out.println("Write the amount you want to use:");
    float consume = ValidateInput.forceValidFloat(scanner);

    try {
      fridge.use(groceryName, consume);
    } catch (Exception e) {
      System.out.println("The item was either not found, or you do not have enough of it.");
    }
  }

  private void handleSearch(Scanner scanner, Fridge fridge) {
    System.out.println("Write the name of the grocery:");
    String groceryName = ValidateInput.forceValidString(scanner);

    Grocery result = fridge.search(groceryName);
    if (result != null) {
      System.out.println("The ingredient " + result.getName() + " exists, you have "
          + result.getAmount() + " " + result.getUnit() + ".");
    } else {
      System.out.println("The ingredient you're looking for does not exist.");
    }
  }

  private void handleOverview(Fridge fridge) {
    for (Grocery ingredient : fridge.overview()) {
      System.out.println(
          ingredient.getName() + ": " + ingredient.getAmount() + " "
              + ingredient.getUnit() + ".");
    }
  }

  private void handleExpiredOverview(Fridge fridge) {
    fridge.dateOverview();
  }

  private void handleExpiresBefore(Scanner scanner, Fridge fridge) {
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
    fridge.expiresBefore(expiryDate);
  }

  private void handleValue(Fridge fridge) {
    fridge.value();
  }

  private void handleCreateRecipe(Scanner scanner, CookBook cookBook) {
    System.out.println("Now creating a new recipe.");

    System.out.println("Write the name of the dish:");
    final String dishName = scanner.nextLine().trim();

    System.out.println("Write a short description of the dish:");
    final String description = scanner.nextLine().trim();

    ArrayList<Grocery> ingredients = new ArrayList<>();
    System.out.println("Specify the ingredients in the format: name, amount, unit.");
    System.out.println("Write \"Done\" when finished.");

    while (true) {
      String line = scanner.nextLine();
      if (line.equalsIgnoreCase("Done")) {
        break;
      }

      String[] parts = line.split(",");
      if (parts.length == 3) {
        try {
          float amount = Float.parseFloat(parts[1].trim());
          if (amount > 0) {
            ingredients.add(new Grocery(parts[0].trim(), parts[2].trim(), amount));
          } else {
            System.out.println("Amount must be positive. Try again.");
          }
        } catch (NumberFormatException e) {
          System.out.println("Invalid amount. Use numeric values.");
        }
      } else {
        System.out.println("Invalid format. Use: name, amount, unit.");
      }
    }

    ArrayList<String> instructions = new ArrayList<>();
    System.out.println("Write the instructions, one line at a time.");
    System.out.println("Write \"Done\" when finished.");

    while (true) {
      String line = scanner.nextLine();
      if (line.equalsIgnoreCase("Done")) {
        break;
      }
      instructions.add(line);
    }

    System.out.println("How many portions does this make?");
    int portions = 0;
    while (true) {
      try {
        portions = Integer.parseInt(scanner.nextLine());
        if (portions > 0) {
          break;
        }
        System.out.println("Portions must be positive. Try again.");
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Use numeric values.");
      }
    }

    cookBook.createRecipe(dishName, description, instructions, ingredients, portions);
    System.out.println("Recipe saved successfully.");
  }


  void handleAvailableRecipes(Fridge fridge, CookBook cookBook) {
    System.out.println("With the ingredients you have, you are able to make:");
    for (Recipe recipe : cookBook.recipeAvailability(fridge)) {
      System.out.println("  - " + recipe.getName());
    }
  }

  void handleCheckRecipe(Scanner scanner, CookBook cookBook, Fridge fridge) {
    System.out.println("What recipe do you want to check?");
    String choice = scanner.nextLine().trim();

    Recipe chosenRecipe = null;
    for (Recipe recipe : cookBook.recipeList) {
      if (recipe.getName().equalsIgnoreCase(choice)) {
        chosenRecipe = recipe;
      }
    }

    if (chosenRecipe != null) {
      Boolean found = cookBook.recipeCheck(chosenRecipe, fridge);

      if (found) {
        System.out.println("Recipe " + chosenRecipe.getName() + " is possible to make.");
      } else {
        System.out.println("Recipe " + chosenRecipe.getName() + " is not possible to make.");
      }

    } else {
      System.out.println("Recipe " + choice + "was not found.");
    }
  }


}



