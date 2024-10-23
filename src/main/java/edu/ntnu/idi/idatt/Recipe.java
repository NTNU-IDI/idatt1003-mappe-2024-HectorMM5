package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.Grocery;

import java.util.ArrayList;

public class Recipe {
    public String name;
    public String description;
    public ArrayList<Grocery> foods;
    private ArrayList<String> instructions;

    public Recipe(String name, String description, ArrayList<String> instructions, ArrayList<Grocery> foods) {
        this.name = name;
        this.description = description;
        this.instructions = instructions;
        this.foods = foods;
    
    }

    public void writeRecipe() {
        System.out.println("For the recipe " + this.name + ", you need:");
        for (int i = 0; i < this.foods.size(); i++) {
            System.out.println("    - " + this.foods.get(i).getAmount() + " " + this.foods.get(i).getUnit() + " " + this.foods.get(i).getName());
        }
        System.out.println("\nInstructions:");
        for (int i = 0; i < this.instructions.size(); i++) {
            System.out.println(i + "." + this.instructions.get(i));
        }
    }


    
    
}