import java.util.ArrayList;
import java.util.Scanner;

public class recipe {
    private String name;
    private ArrayList<grocery> foods = new ArrayList<>();


    Scanner scanner = new Scanner(System.in);

    public recipe(String name, ArrayList<String> instructions, grocery... groceries) {
        this.name = name;

        for (grocery g : groceries) {
            this.foods.add(g);
        }
    }


    
    public void createRecipe() {
        ArrayList<grocery> food = new ArrayList<>();
        ArrayList<String> instructions = new ArrayList<>();

        System.out.println("Write info about the ingredients in the following format: name, amount, unit.\n Write \"Done\" when done.");

        String input = "Blank";
        
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
                    System.out.println("There was an issue with your input, check that it is in the format: name, amount, unit.");
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

    }


    public void viewRecipes() {

    }

    void recipeCheck() {

    }
}