package edu.ntnu.idi.idatt;


import java.util.ArrayList;
import java.util.Scanner;

public class CookBook {
    Scanner scanner = new Scanner(System.in);
    ArrayList<Recipe> cookBook = new ArrayList<>();

    public void createRecipe() {
        ArrayList<Grocery> food = new ArrayList<>();
        ArrayList<String> instructions = new ArrayList<>();
        
        System.out.println("Write the name of the dish.");
        String input = scanner.nextLine();
        String name = input;

        System.out.println("Write a short description of the dish.");
        input = scanner.nextLine();
        String description = input;

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
                    food.add(new Grocery(splitInput[0], splitInput[2], Float.parseFloat(splitInput[1])));
                    
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

        cookBook.add(new Recipe(name, description, instructions, food));
        System.out.println("Your recipe has been saved.");

    }


    public void viewRecipes() {
        System.out.println("Your saved recipes:");
        for (int i = 0; i < cookBook.size(); i++) {
            System.out.println(i + " " + cookBook.get(i).name);
            
        }

    }

    public void recipeAvailability(Fridge fridge) {

        ArrayList<Recipe> availableRecipes = new ArrayList<>();

        for (int i = 0; i < cookBook.size(); i++) {
            int maxIngredients = cookBook.get(i).foods.size();
            int ingredientsOK = 0;
            for (int j = 0; j < cookBook.get(i).foods.size(); j++) {
                for (int k = 0; k < fridge.ingredients.size(); k++) {
                    if (cookBook.get(i).foods.get(j).getName().equalsIgnoreCase(fridge.ingredients.get(k).getName())) {
                        if (fridge.ingredients.get(k).getAmount() >= cookBook.get(i).foods.get(j).getAmount()) {
                            ingredientsOK += 1;
                        }
                    }
                }
            }

            if (maxIngredients == ingredientsOK) {
                availableRecipes.add(cookBook.get(i));
            }

        }

        System.out.println("With the ingredients you have, you are able to make:");

        for (int i = 0; i < availableRecipes.size(); i++) {
            System.out.println(i + ". " + availableRecipes.get(i).name);
            
        }

    }
}