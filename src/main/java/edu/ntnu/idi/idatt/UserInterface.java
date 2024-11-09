package edu.ntnu.idi.idatt;

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
          fridge.newGrocery();
          break;

        case "/exit":
          System.out.println("Exiting the application...");
          scanner.close();
          System.exit(0);

          break;

        case "/use":
          System.out.println("Write the name of the grocery you want to use:");
          input2 = scanner.nextLine();

          System.out.println("Write the amount of the grocery you want to use:");
          float amount = Float.parseFloat(scanner.nextLine());

          try {
            fridge.use(input2, amount);

          } catch (Exception e) {
            System.out.println(
                "The item was either not found, or you do not have enough of it to use this amount.");
          }

          break;

        case "/search":
          System.out.println("Write the name of the grocery:");
          input2 = scanner.nextLine();

          fridge.search(input2);

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
          cookBook.createRecipe();
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


