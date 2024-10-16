import java.util.ArrayList;
import java.util.Scanner;

public class recipes {
    
    Scanner scanner = new Scanner(System.in);
    public void createRecipe() {
        ArrayList<grocery> food = new ArrayList<>();
        ArrayList<String> instructions = new ArrayList<>();

        System.out.println("Write info about the ingredients in the following format: name, amount, unit.\n Write \"Done\" when done.");

        String input = "Blank";
        
        while (true) {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("Done")) {break;}

            try {
                String[] splitInput = input.split(",");

                for (int i = 0; i < splitInput.length; i++) {
                    splitInput[i] = splitInput[i].trim();
                }

                if (splitInput.length != 3) {
                    throw new IllegalArgumentException("Invalid format (input is too long). Please enter data in the format: name, amount, unit");
                }

                // Validate and parse the amount
                float amount = Float.parseFloat(splitInput[1].trim());
            }


            food.add(new grocery(splitInput[0], splitInput[2], Float.parseFloat(splitInput[1])));
        }

        System.out.println("Write the instructions. You can use multiple lines and have them assigned in and numbered list.\n Write \"Done\" when done.");




    }


    public void viewRecipes() {

    }

    void recipeCheck() {

    }
}