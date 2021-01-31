import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String choice = "-1";
        while (!choice.isEmpty()) {
            System.out.println("Welcome to our COVID-19 dashboard!\nHere, you can:\n\t1. Learn COVID Info or get tested\n\t2. Understand how precautions affect infectivity\n\t3. View a COVID infection over a simulated population");
            choice = input.nextLine();
            if (choice.equals("1")) {
                CovidInfo.covidInfo();
            } else if (choice.equals("2")) {
                PrecautionSimulator.precautionSim();
            } else if (choice.equals("3")) {
                CovidSim.covidSim();
            } else if (!choice.isEmpty()) {
                System.out.println("That's not a valid choice!");
            } else {
                System.out.println("See you soon, stay safe and six feet away!");
            }
        }

    }
}
