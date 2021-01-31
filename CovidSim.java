import java.util.Scanner;

import static java.lang.System.out;

public class CovidSim{
    static double symptomaticRate = 0.83; //Estimate
    static double deathRate = 0.015;
    static double recoveryRate = 0.5; //Difficult to set an estimate, too high and the virus dies too fast, too low and everyone will die in the simulation
    static double incubationPeriod = 14;
    static double symptomaticPeriod = 5;
    static double quarantineRate;
    static double infectivityRate = 0.36; //based on a study
    public static void covidSim(){
        String choice = "-1";
        while (!choice.isEmpty()) {
            Scanner sc = new Scanner(System.in);
            out.println("Coronavirus Simulator");
            out.println("How big do you want your simulation? The simulation will be square, and the number you input will be the length");
            int length = sc.nextInt();
            Person[][] people = new Person[length][length];
            for (int i = 0; i < people.length; i++) {
                for (int j = 0; j < people[i].length; j++) {
                    people[i][j] = new Person();
                }
            }
            out.println("Covid-19 has infected patient zero of the community.");
            out.println("What are the chances a person is wearing a mask in your community? Answer with a percent number. (10% -> 0.1)");
            double maskPer = sc.nextDouble();
            out.println("What are the chances a person will social distance in your community? Answer with a percent number. (10% -> 0.1)");
            double socialDistancePer = sc.nextDouble();
            out.println("What are the chances that a person who is infected will quarantine in your community? Answer with a percent number. (10% -> 0.1)");
            quarantineRate = sc.nextDouble();
            for (int i = 0; i < people.length * people.length * maskPer; i++) {
                int randomX = (int) (Math.random() * people.length);
                int randomY = (int) (Math.random() * people.length);
                if (people[randomX][randomY].isMasked()) {
                    i--;
                } else {
                    people[randomX][randomY].setMasked(true);
                }
            }
            for (int i = 0; i < people.length * people.length * socialDistancePer; i++) {
                int randomX = (int) (Math.random() * people.length);
                int randomY = (int) (Math.random() * people.length);
                if (people[randomX][randomY].isSocialDistancing()) {
                    i--;
                } else {
                    people[randomX][randomY].setSocialDistancing(true);
                }
            }
            int randomX = (int) (Math.random() * people.length);
            int randomY = (int) (Math.random() * people.length);
            people[randomX][randomY].setStatus("I");
            people[randomX][randomY].setDayExposed(0);
            System.out.println("Person infected in " + randomX + ", " + randomY + "!");
            printPeople(people);
            sc.nextLine();
            String input = sc.nextLine();
            int dayCount = 0;
            while (input.isEmpty() && getQuarantined(people) + getSymptomatic(people) + getInfected(people) > 0) {
                printDay(people, ++dayCount);
                people = nextGeneration(people, dayCount);
                printPeople(people);
                System.out.println("Alive: " + getAlive(people) + ", Masked (m): " + getMasked(people) + ", Social Distanced (d): " + getSocialDistanced(people) + ", Masked and Distanced (n): " + getMaskedAndSocialDistanced(people));
                System.out.println("Infected (I): " + getInfected(people) + ", Symptomatic (S): " + getSymptomatic(people) + ", Quarantined (Q): " + getQuarantined(people) + ", Dead (X): " + getDead(people) + ", Recovered (R): " + getRecovered(people));
                input = sc.nextLine();
                System.out.println();
            }
            System.out.println("It took " + dayCount + " days for the virus to kill " + getDead(people) + " people before it got wiped out");
            System.out.println();
            System.out.println("Type anything to redo the simulation or press enter to return to dashboard.");
            choice = sc.nextLine();
        }
    }

    public static void printDay(Person[][] people, int day){
        String dayStr = "Day " + day;
        int simLength = (people.length - 1) * 3 + 2 - dayStr.length();
        for (int i = 0; i < simLength; i++){
            if (i == simLength/2){
                System.out.print(dayStr);
            } else {
                System.out.print("=");
            }
        }
        System.out.println();
    }

    public static void printPeople(Person[][] people){
        for (int r = 0; r < people.length; r++){
            for (int c = 0; c < people[r].length; c++){
                System.out.print(people[r][c]);
                if (c < people[r].length - 1){
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
        System.out.println("Press enter to continue");
    }
    public static Person[][] nextGeneration(Person[][] people, int day){
        Person[][] nextGeneration = new Person[people.length][people.length];
        for (int r = 0; r < people.length; r++){
            for (int c = 0; c < people[r].length; c++){
                Person person = people[r][c];
                double chance = infectivityRate * getInfectedNeighbors(r, c, people);
                if (person.isMasked()){
                    chance /= 1.5; //estimates, not based on real fact
                }
                if (person.isSocialDistancing()){
                    chance /= 5; //estimates, not based on real fact
                }
                if ("ISQ".contains(person.getStatus()) && person.getDayExposed() >= 0 && day - person.getDayExposed() > incubationPeriod){
                    if (Math.random() < deathRate){
                        nextGeneration[r][c] = new Person(people[r][c].isMasked(), "X", person.getDayExposed(), person.isSocialDistancing());
                    } else if (Math.random() < recoveryRate){
                        nextGeneration[r][c] = new Person(people[r][c].isMasked(), "R", person.getDayExposed(), person.isSocialDistancing());
                    } else {
                        nextGeneration[r][c] = new Person(person.isMasked(), person.getStatus(), person.getDayExposed(), person.isSocialDistancing());
                    }
                }else if (person.getStatus().equals("I") && person.getDayExposed() >= 0 && day - person.getDayExposed() > symptomaticPeriod && Math.random() < symptomaticRate){
                    nextGeneration[r][c] = new Person(person.isMasked(), "S", person.getDayExposed(), person.isSocialDistancing());
                } else if (person.getStatus().equals("S") && Math.random() < quarantineRate){
                    nextGeneration[r][c] = new Person(people[r][c].isMasked(), "Q", person.getDayExposed(), person.isSocialDistancing());
                } else if (Math.random() < chance && !"ISQRX".contains(person.getStatus())){
                    nextGeneration[r][c] = new Person(people[r][c].isMasked(), "I", day, person.isSocialDistancing());
                } else {
                    nextGeneration[r][c] = new Person(person.isMasked(), person.getStatus(), person.getDayExposed(), person.isSocialDistancing());
                }
            }
        }
        return nextGeneration;
    }
    public static int getInfectedNeighbors(int x, int y, Person[][] people){
        int sumInfected = 0;
        if (x - 1 >= 0 && "IS".contains(people[x-1][y].getStatus())){
            sumInfected++;
        }
        if (y + 1 < people.length && "IS".contains(people[x][y+1].getStatus())){
            sumInfected++;
        }
        if (y - 1 >= 0 && "IS".contains(people[x][y-1].getStatus())){
            sumInfected++;
        }
        if (x + 1 < people.length && "IS".contains(people[x + 1][y].getStatus())){
            sumInfected++;
        }
        return sumInfected;
    }

    public static int getInfected(Person[][] people){
        int sum = 0;
        for (int r = 0; r < people.length; r++){
            for (int c = 0; c < people[r].length; c++){
                if (people[r][c].getStatus().equals("I")){
                    sum++;
                }
            }
        }
        return sum;
    }
    public static int getSymptomatic(Person[][] people){
        int sum = 0;
        for (int r = 0; r < people.length; r++){
            for (int c = 0; c < people[r].length; c++){
                if (people[r][c].getStatus().equals("S")){
                    sum++;
                }
            }
        }
        return sum;
    }
    public static int getQuarantined(Person[][] people){
        int sum = 0;
        for (int r = 0; r < people.length; r++){
            for (int c = 0; c < people[r].length; c++){
                if (people[r][c].getStatus().equals("Q")){
                    sum++;
                }
            }
        }
        return sum;
    }
    public static int getRecovered(Person[][] people){
        int sum = 0;
        for (int r = 0; r < people.length; r++){
            for (int c = 0; c < people[r].length; c++){
                if (people[r][c].getStatus().equals("R")){
                    sum++;
                }
            }
        }
        return sum;
    }
    public static int getDead(Person[][] people){
        int sum = 0;
        for (int r = 0; r < people.length; r++){
            for (int c = 0; c < people[r].length; c++){
                if (people[r][c].getStatus().equals("X")){
                    sum++;
                }
            }
        }
        return sum;
    }
    public static int getAlive(Person[][] people){
        int sum = 0;
        for (int r = 0; r < people.length; r++){
            for (int c = 0; c < people[r].length; c++){
                if (!people[r][c].getStatus().equals("X")){
                    sum++;
                }
            }
        }
        return sum;
    }
    public static int getMasked(Person[][] people){
        int sum = 0;
        for (int r = 0; r < people.length; r++){
            for (int c = 0; c < people[r].length; c++){
                if (people[r][c].toString().equals("m")){
                    sum++;
                }
            }
        }
        return sum;
    }
    public static int getSocialDistanced(Person[][] people){
        int sum = 0;
        for (int r = 0; r < people.length; r++){
            for (int c = 0; c < people[r].length; c++){
                if (people[r][c].toString().equals("d")){
                    sum++;
                }
            }
        }
        return sum;
    }
    public static int getMaskedAndSocialDistanced(Person[][] people){
        int sum = 0;
        for (int r = 0; r < people.length; r++){
            for (int c = 0; c < people[r].length; c++){
                if (people[r][c].toString().equals("n")){
                    sum++;
                }
            }
        }
        return sum;
    }

}

class Person {
    private boolean isMasked;
    private boolean isSocialDistancing;
    private String status; // a - Alive, X - Dead, I - Infected, S - Symptomatic, Q - Quarantined, R - Recovered, m - Masked, d - Distancing, n - Masked and Social Distancing
    private int dayExposed;

    public Person(){
        isMasked = false;
        isSocialDistancing = false;
        status = "a";
        dayExposed = -1;
    }
    public Person(boolean isMasked, String status, int dayExposed, boolean isSocialDistancing){
        this.isMasked = isMasked;
        this.status = status;
        this.dayExposed = dayExposed;
        this.isSocialDistancing = isSocialDistancing;
    }

    public void setMasked(boolean masked) {
        isMasked = masked;
    }
    public boolean isMasked() {
        return isMasked;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus(){
        return status;
    }

    public void setDayExposed(int dayExposed) {
        this.dayExposed = dayExposed;
    }

    public int getDayExposed() {
        return dayExposed;
    }

    public void setSocialDistancing(boolean socialDistancing) {
        isSocialDistancing = socialDistancing;
    }

    public boolean isSocialDistancing() {
        return isSocialDistancing;
    }

    public String toString(){
        if (status.equals("a")){
            if (isSocialDistancing) {
                if (isMasked){
                    return "n";
                }
                return "d";
            } else if (isMasked){
                return "m";
            }
            return " ";
        } else {
            return status;
        }
    }
}
