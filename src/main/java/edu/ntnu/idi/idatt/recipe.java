import java.util.ArrayList;

public class recipe {
    public String name;
    public String description;
    public ArrayList<grocery> foods;
    private ArrayList<String> instructions;

    public recipe(String name, String description, ArrayList<String> instructions, ArrayList<grocery> foods) {
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