import java.util.Scanner;

class TestRace {
    public static void main(String[] args) {
        Horse horseA = new Horse('A', "HorseA", 0.5);
        Horse horseB = new Horse('B', "HorseB", 0.5);
        Horse horseC = new Horse('C', "HorseC", 0.5);

        Race race1 = new Race(10);
        race1.addHorse(horseA, 1);
        race1.addHorse(horseB, 2);  
        race1.addHorse(horseC, 3);

        boolean continueRace = true;

        while (continueRace) {
            race1.startRace();

            if(!getYesNo("Do you want to run the race again? Y/N ")) {
                continueRace = false;
            }
        }
    }

    //Get input Y or N from user
    public static boolean getYesNo(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        String input = scanner.nextLine();

        while (input.isEmpty() || (!input.equalsIgnoreCase("Y") 
            && !input.equalsIgnoreCase("N"))) {
            System.out.println("Invalid input. Please enter Y or N");
            input = scanner.nextLine();
        }

        if (input.equalsIgnoreCase("Y")) {
            return true;
        } else {
            return false;
        }   
    }
}