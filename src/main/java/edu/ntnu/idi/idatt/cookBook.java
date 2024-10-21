import java.util.ArrayList;
import java.util.Scanner;

public class cookBook {
    Scanner scanner = new Scanner(System.in);
    ArrayList<recipe> cookBook = new ArrayList<>(); 

    public void createRecipe() {

        ArrayList<grocery> food = new ArrayList<>();
        ArrayList<String> instructions = new ArrayList<>();
        
        System.out.println("Write the name of the dish.");
        String input = scanner.nextLine();

        String name = input;



        System.out.println("Write info about the ingredients in the following format: name, amount, unit.\n Write \"Done\" when done.");
        
        while (true) {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("Done")) {break;}

            String[] splitInput = input.split(",");

            if (splitInput.length == 3) {

                for (int i = 0; i < splitInput.length; i++) {
                    splitInput[i] = splitInput[i].trim();
                }

                try {
                    food.add(new grocery(splitInput[0], splitInput[2], Float.parseFloat(splitInput[1])));
                    
                } 
                
                catch (IllegalArgumentException e) {
                    System.out.println("There was an issue with your input, please try again, and verify that it is in the format: name, amount, unit.");
                }
            }

            else {
                throw new IllegalArgumentException("3 arguments expected, but recieved " + splitInput.length + " instead.");

            }


        }

        System.out.println("Write the instructions. You can use multiple lines if needed, and have them assigned in and numbered list.\n Write \"Done\" when done.");

        while (true) {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("Done")) {break;}
            
            instructions.add(input);

        }

        cookBook.add(new recipe(name, instructions, food));
        System.out.println("Your recipe has been saved.");

    }


    public void viewRecipes() {
        System.out.println("Your saved recipes:");
        for (int i = 0; i < cookBook.size(); i++) {
            System.out.println(i + " " + cookBook.get(i).name);
            
        }

    }

    public void recipe() {

    }
}