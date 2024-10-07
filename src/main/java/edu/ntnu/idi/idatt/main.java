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
