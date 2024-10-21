import java.util.Scanner;
public class main {

    public static void main(String[] args) {
        fridge fridge = new fridge();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the fridge, write /help for all commands.");

        String input;

        while (true) {
            input = scanner.nextLine();

            switch (input) {
                case "/newItem":
                    fridge.newGrocery();
                    break;

                case "/use":
                    System.out.println("Retrieving an item...");
                    break;

                case "/search":
                    System.out.println("Searching for an item...");
                    break;

                case "/overview":
                    System.out.println("Displaying fridge overview...");
                    break;

                case "/expiredOverview":
                    System.out.println("Displaying expired items...");
                    break;

                case "/value":
                    System.out.println("Checking fridge value...");
                    break;

                case "/exit":
                    System.out.println("Exiting the application...");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }



    }



}
