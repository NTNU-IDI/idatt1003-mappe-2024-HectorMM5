import java.util.ArrayList;

public class fridge {

    public ArrayList<grocery> ingredients = new ArrayList<>();

    public void newGrocery(String name, String unit, float amount, int cost, int expiryDate) {
        ingredients.add(new grocery(name, unit, amount, cost, expiryDate));
    }

    public void use(String argument, float consume) {
        for (int i = 0; i <= ingredients.size()-1; i++) {
            if (ingredients.get(i).getName().equals(argument)) {
                if (ingredients.get(i).getAmount() > consume) {
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
        for (int i = 0; i <= ingredients.size()-1; i++) {
            if (ingredients.get(i).getName().equals(argument)) {
                System.out.println("Ingrediensen " + ingredients.get(i).getName() + " finnes, du har " + ingredients.get(i).getAmount() + " " + ingredients.get(i).getUnit() + ".");
            }

            else if (i == ingredients.size()-1) {
                System.out.println("Ingrediensen du leter etter finnes ikke.");
            }
        }
    } 

    public void overview() {
        for (int i = 0; i <= ingredients.size()-1; i++) {
            System.out.println(ingredients.get(i).getName() + ": " + ingredients.get(i).getAmount() + " " + ingredients.get(i).getUnit() + ".");
        }
    }

    public void dateOverview() {
        for (int i = 0; i <= ingredients.size()-1; i++) {
            if (ingredients.get(i).getExpiryDate() > 111111) {
                System.out.println(ingredients.get(i).getName() + ": " + ingredients.get(i).getAmount() + " " + ingredients.get(i).getUnit() + ".");
            }
        }
    }

    public void value() {

        float value = 0f;
        for (int i = 0; i <= ingredients.size()-1; i++) {
            value += ingredients.get(i).getCost() * ingredients.get(i).getAmount();
        }

        System.out.println("Verdien av innholdet er " + value + " kroner.");
    }


    public void help() {
        System.out.println("--------------------------------------------------------");
        System.out.println("En oversikt over tilgjengelige kommandoer kan ses under:");
        System.out.println("--------------------------------------------------------");
        System.out.println("\n    - \"/nyVare\" for å legge inn en ny vare.");
        System.out.println("    - \"/bruk\" for å hente ut en vare.");
        System.out.println("    - \"/sok\" for å søke etter en vare og hente ut tilhørende informasjon.");
        System.out.println("    - \"/oversikt\" for å sjekke alt som finnes i kjøleskapet akkurat nå.");
        System.out.println("    - \"/datoOversikt\" for å sjekke alt som finnes i kjøleskapet, som har gått ut på dato.");
        System.out.println("    - \"/verdi\" for å sjekke verdien av maten som finnes i kjøleskapet.");
    }
}