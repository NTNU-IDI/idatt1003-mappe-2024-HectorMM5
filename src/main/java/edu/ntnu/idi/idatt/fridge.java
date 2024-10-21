import java.util.ArrayList;
import java.util.Scanner;

public class fridge {

    public ArrayList<grocery> ingredients = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public void newGrocery() {

        System.out.println("Write the name of the grocery.");
        String name = scanner.nextLine();

        System.out.println("Write the unit of the grocery.");
        String unit = scanner.nextLine();

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

        System.out.println("Enter the expiry date (in numeric format, e.g., YYYYMMDD):");
        int expiryDate;
        while (true) {
            try {
                expiryDate = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a numeric value for expiry date:");
            }
        }

        ingredients.add(new grocery(name, unit, amount, cost, expiryDate));
        System.out.println("Your grocery has been put in the fridge.");
    }

    public void use(String argument, float consume) {
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).getName().equals(argument)) {
                if (ingredients.get(i).getAmount() >= consume) {
                    ingredients.get(i).setAmount(ingredients.get(i).getAmount() - consume);
                    System.out.println("Du tar ut " + consume + " " + ingredients.get(i).getUnit() + " " + ingredients.get(i).getName() + ", og har " + ingredients.get(i).getAmount() + " " + ingredients.get(i).getUnit() + " igjen.");
                }

                else {
                    System.out.println("Du har ikke nok " + ingredients.get(i).getName() + " til dette.");
                }
                
            }

            else if (i == ingredients.size()-1) {
                System.out.println("Ingrediensen du leter etter finnes ikke.");
            }
        }
    }

    public void search(String argument) {
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).getName().equals(argument)) {
                System.out.println("Ingrediensen " + ingredients.get(i).getName() + " finnes, du har " + ingredients.get(i).getAmount() + " " + ingredients.get(i).getUnit() + ".");
            }

            else if (i == ingredients.size()-1) {
                System.out.println("Ingrediensen du leter etter finnes ikke.");
            }
        }
    } 

    public void overview() {
        for (int i = 0; i < ingredients.size(); i++) {
            System.out.println(ingredients.get(i).getName() + ": " + ingredients.get(i).getAmount() + " " + ingredients.get(i).getUnit() + ".");
        }
    }

    public void dateOverview() {
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).getExpiryDate() > 111111) {
                System.out.println(ingredients.get(i).getName() + ": " + ingredients.get(i).getAmount() + " " + ingredients.get(i).getUnit() + ".");
            }
        }
    }

    public void value() {

        float value = 0f;
        for (int i = 0; i < ingredients.size(); i++) {
            value += ingredients.get(i).getCost() * ingredients.get(i).getAmount();
        }

        System.out.println("Verdien av innholdet er " + value + " kroner.");
    }


    public void help() {
        System.out.println("--------------------------------------------------------");
        System.out.println("An overview of available commands can be seen below:");
        System.out.println("--------------------------------------------------------");
        System.out.println("\n    - \"/newItem\" to add a new item.");
        System.out.println("    - \"/use\" to retrieve an item.");
        System.out.println("    - \"/search\" to search for an item and retrieve associated information.");
        System.out.println("    - \"/overview\" to check everything that is currently in the fridge.");
        System.out.println("    - \"/expiredOverview\" to check everything in the fridge that has expired.");
        System.out.println("    - \"/value\" to check the value of the food currently in the fridge.");
    }
    
}