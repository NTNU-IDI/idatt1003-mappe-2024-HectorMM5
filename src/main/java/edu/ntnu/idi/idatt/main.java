

public class main {

    public class grocery {
        private String name;
        private String unit;
        private int amount;
        private int cost;
        private int expiryDate;

        public grocery(String name, String unit, int amount, int cost, int expiryDate) {
            this.name = name;
            this.unit = unit;
            this.amount = amount;
            this.cost = cost;
            this.expiryDate = expiryDate;

        }

        public String getName() {
            return this.name;
        }

        public String getUnit() {
            return this.unit;
        }

        public int getAmount() {
            return this.amount;
        }

        public int getCost() {
            return this.cost;
        }

        public int getExpiryDate() {
            return this.expiryDate;
        }


        public void use(float amount) {
            if (this.amount > amount) {
                this.amount -= amount;
            }

            else {
                System.out.println("Du har ikke nok " + this.name + " til dette.");
            }
        }


    }

    public grocery[] ingredients = {};


    public void search(String argument) {
        for (int i = 0; i <= ingredients.length-1; i++) {
            if (ingredients[i].name == argument) {
                System.out.println("Ingrediensen " + ingredients[i].name + " finnes, du har " + ingredients[i].amount + " " + ingredients[i].unit + ".");
            }

            else if (i == ingredients.length-1) {
                System.out.println("Ingrediensen du leter etter finnes ikke.");
            }
        }
    }

    public void use(String argument, double consume) {
        for (int i = 0; i <= ingredients.length-1; i++) {
            if (ingredients[i].name == argument) {
                if (ingredients[i].amount > consume) {
                    ingredients[i].amount -= consume;
                    System.out.println("Du tar ut " + consume + " " + ingredients[i].unit + " " + ingredients[i].name + ", og har " + ingredients[i].amount + " " + ingredients[i].unit + " igjen.");
                }

                else {
                    System.out.println("Du har ikke nok " + ingredients[i].name + " til dette.");
                }
                
            }

            else if (i == ingredients.length-1) {
                System.out.println("Ingrediensen du leter etter finnes ikke.");
            }
        }
    }

    public void help() {
        System.out.println("---------------------------------------------------------");
        System.out.println("En oversikt over tilgjengelige kommandoer kan ses under:");
        System.out.println("---------------------------------------------------------");
        System.out.println("\n    - \"/nyVare\" for å legge inn en ny vare.");
        System.out.println("    - \"/brukVare\" for å hente ut en vare.");
        System.out.println("    - \"/sjekk\" for å sjekke om og hvor mye du har av en vare.");
        System.out.println("    - \"/søk\" for å søke etter en vare og hente ut tilhørende informasjon.");
        System.out.println("    - \"/oversikt\" for å sjekke alt som finnes i kjøleskapet akkurat nå.");
        System.out.println("    - \"/oversikt\" for å sjekke alt som finnes i kjøleskapet akkurat nå.");
        System.out.println("    - \"/datoOversikt\" for å sjekke alt som finnes i kjøleskapet, som har gått ut på dato.");

    }

    public void start() {
        System.out.println("Velkommen til kjøleskapet. Sjekk ut alle kommandoer ved å skrive \"/hjelp\".");


    }



    public static void main(String[] args) {
        

    }



}
