import java.util.Scanner;
public class main {

    public static void main(String[] args) {
        fridge fridge = new fridge();
        Scanner scanner = new Scanner(System.in);
        cookBook cookBook = new cookBook();

        System.out.println("Welcome to the fridge, write /help for all commands.");

        String input;

        while (true) {
            input = scanner.nextLine();
            String input2;

            switch (input) {
                case "/newItem":
                    fridge.newGrocery();
                    break;

                case "/use":
                    System.out.println("Write the name of the grocery you want to use:");
                    input2 = scanner.nextLine();

                    System.out.println("Write the amount of the grocery you want to use:");
                    float amount = Float.parseFloat(scanner.nextLine());

                    try {
                        fridge.use(input2, amount);
                        
                    } 
                    
                    catch (Exception e) {
                        System.out.println("Something went wrong. The item was either not found, or you do not have enough of it to use this amount.");
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

                case "/exit":
                    System.out.println("Exiting the application...");
                    scanner.close();
                    System.exit(0);
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
                    System.out.println("Invalid command. Please try again. Write \"/help\" to see all available commands.");
            }
        }



    }



}
