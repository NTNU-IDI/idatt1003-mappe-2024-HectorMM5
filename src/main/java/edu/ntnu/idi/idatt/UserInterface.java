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
   * Function used to declare key variables.
   */
  public void init() {
    Fridge fridge = new Fridge();
    Scanner scanner = new Scanner(System.in);
    CookBook cookBook = new CookBook();

    System.out.println("Welcome to the fridge, write /help for all commands.");
  }

  /**
   * Function that handles the main user interface functionality.
   *
   * @param scanner * Scanner used to take in inputs.
   * @param fridge * Fridge object used as reference to the current existing groceries.
   * @param cookBook * Cookbook object used to keep recipes.
   */

  public void start(Scanner scanner, Fridge fridge, CookBook cookBook) {
    String input;

    while (true) {
      input = scanner.nextLine();
      String input2;

      switch (input) {
        case "/newItem":
          System.out.println("Write the name of the grocery.");
          final String name = scanner.nextLine();

          System.out.println("Write the unit of the grocery.");
          final String unit = scanner.nextLine();

          System.out.println("Enter the amount (in numeric format):");
          float amount;

          while (true) {
            try {
              amount = Float.parseFloat(scanner.nextLine());
              break;
            } catch (NumberFormatException e) {
              System.out.println("Invalid input, please enter a numeric value for amount:");
            }
          }

          System.out.println("Enter the cost (of the total amount):");
          float cost;
          while (true) {
            try {
              cost = Float.parseFloat(scanner.nextLine());
              break;
            } catch (NumberFormatException e) {
              System.out.println("Invalid input, please enter a numeric value for cost:");
            }
          }

          System.out.println("Enter the expiry date (in numeric format, e.g., DDMMYYYY):");
          LocalDate expiryDate;
          while (true) {
            String date = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            try {
              expiryDate = LocalDate.parse(date, formatter);
              break;

            } catch (DateTimeParseException e) {

              System.out.println(
                  "Invalid input, please enter the date in the correct format (DDMMYYYY):");
            }
          }

          fridge.newGrocery(name, unit, amount, cost, expiryDate);
          break;

        case "/use":
          System.out.println("Write the name of the grocery you want to use:");
          String groceryName = scanner.nextLine();

          System.out.println("Write the amount you want to use:");
          float consume = Float.parseFloat(scanner.nextLine());

          try {
            fridge.use(groceryName, consume);

          } catch (Exception e) {
            System.out.println(
                "The item was either not found, or you do not have enough of it to use this amount.");
          }
          break;

        case "/search":
          System.out.println("Write the name of the grocery:");

          Grocery result = fridge.search(scanner.nextLine());

          if (result != null) {
            System.out.println("The ingredient " + result.getName() + " exists, you have "
                + result.getAmount() + " " + result.getUnit() + ".");
          } else {
            System.out.println("The ingredient you're looking for does not exist.");
          }

          break;

        case "/overview":
          fridge.overview();
          break;

        case "/expiredOverview":
          fridge.dateOverview();
          break;

        case "/value":
          fridge.value();
          break;

        case "/createRecipe":
          System.out.println("Now creating a new recipe.");
          System.out.println("Write the name of the dish:");
          final String dishName = scanner.nextLine();

          System.out.println("Write a short description of the dish.");
          final String description = scanner.nextLine();

          ArrayList<Grocery> food = new ArrayList<>();
          System.out.println("Specify the ingredients in the following format: name, amount, unit. "
              + "\nWrite \"Done\" when done.");

          while (true) {
            String ingredient = scanner.nextLine();
            if (ingredient.equalsIgnoreCase("Done")) {
              break;
            }

            String[] splitIngredient = ingredient.split(",");
            if (splitIngredient.length == 3) {
              for (int i = 0; i < splitIngredient.length; i++) {
                splitIngredient[i] = splitIngredient[i].trim();
              }

              try {
                food.add(new Grocery(splitIngredient[0], splitIngredient[2], Float.parseFloat(splitIngredient[1])));
              } catch (IllegalArgumentException e) {
                System.out.println("There was an issue with your input. "
                    + "Please verify the format: name, amount, unit.");
              }
            } else {
              throw new IllegalArgumentException("Expected three arguments, but received "
                  + splitIngredient.length + " instead. Check your input.");
            }
          }

          ArrayList<String> instructions = new ArrayList<>();
          System.out.println("Write the instructions, use multiple lines if needed. "
              + "\nWrite \"Done\" when done.");

          while (true) {
            String instruction = scanner.nextLine();
            if (instruction.equalsIgnoreCase("Done")) {
              break;
            }
            instructions.add(instruction);
          }

          System.out.println("How many portions does this make?");

          int portions;

          while (true) {
            try {
              portions = Integer.parseInt(scanner.nextLine().trim());
              break;
            } catch (NumberFormatException e) {
              System.out.println("Invalid input. Please enter a whole number.");
            }
          }

          cookBook.createRecipe(dishName, description, instructions, food, portions);
          System.out.println("Your recipe has been saved.");
          break;

        case "/viewRecipes":
          cookBook.viewRecipes();
          break;

        case "/recommendedRecipes":
          cookBook.recipeAvailability(fridge);
          break;

        case "/help":
          fridge.help();
          break;

        default:
          System.out.println(
              "Invalid command. Write \"/help\" to see all available commands.");
      }
    }
  }
}


