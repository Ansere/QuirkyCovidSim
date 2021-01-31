import java.util.Scanner;

import static java.lang.System.out;

public class PrecautionSimulator {
    public static void precautionSim(){
        String choice = "-1";
        while (!choice.isEmpty()) {
            Scanner input = new Scanner(System.in);
            out.println("Coronavirus Precaution Simulator");
            out.println();
            out.println("How many days should the simulation last? As the simulation runs longer, more people can be infected by Covid-19.");
            int days = input.nextInt();
            int rows = days * 2 + 1;
            String[][] people = new String[rows][rows];
            out.println("Covid-19 has infected patient zero of the community.");
            out.println("A patient has a lower chance of spreading the coronavirus if he/she takes the necessary precautions.");
            out.println();
            out.println("What are the chances a person is wearing a mask in your community? Answer with a percent number. (10% -> 0.1)");
            double maskper = input.nextDouble() * 100;
            out.println("What are the chances a person is social distancing in your community? Answer with a percent number. (10% -> 0.1)");
            double disper = input.nextDouble() * 100;
            out.println("What are the chances a person will quarantine for 14 days if tested positive for Covid-19 in your community? Answer with a percent number. (10% -> 0.1)");
            double quar = input.nextDouble() * 100;
            out.println();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < rows; j++) {
                    if (precaution(maskper)) {
                        if (precaution(disper)) {
                            if (precaution(quar))
                                people[i][j] = "0-M-D-Q   ";
                            else
                                people[i][j] = "0-M-D     ";
                        } else {
                            if (precaution(quar))
                                people[i][j] = "0-M-Q     ";
                            else
                                people[i][j] = "0-M       ";
                        }

                    } else {
                        if (precaution(disper)) {
                            if (precaution(quar))
                                people[i][j] = "0-D-Q     ";
                            else
                                people[i][j] = "0-D       ";
                        } else {
                            if (precaution(quar))
                                people[i][j] = "0-Q       ";
                            else
                                people[i][j] = "0         ";
                        }
                    }
                }
            }
            people[days][days] = people[days][days].replace('0', 'I');
            for (int i = 0; i < days; i++) {
                for (int j = 0; j < rows; j++) {
                    for (int k = 0; k < rows; k++) {
                        if (people[j][k].charAt(0) == 'I') {
                            people = infection(people, j, k, "I         ", 82.5);
                            people = infection(people, j, k, "I-M       ", 75);
                            people = infection(people, j, k, "I-D       ", 62.5);
                            people = infection(people, j, k, "I-Q       ", 50);
                            people = infection(people, j, k, "I-M-D     ", 37.5);
                            people = infection(people, j, k, "I-M-Q     ", 25);
                            people = infection(people, j, k, "I-D-Q     ", 12.5);
                            people = infection(people, j, k, "I-M-D-Q   ", 0);
                            people[j][k] = people[j][k].replace('I', 'i');
                        }
                    }
                }
                for (int j = 0; j < rows; j++) {
                    for (int k = 0; k < rows; k++) {
                        if (people[j][k].charAt(0) == 'G') {
                            people[j][k] = people[j][k].replace('G', 'I');
                        }
                    }
                }
            }
            people[days][days] = "PZ" + people[days][days].replace('i', 'I').substring(0, people[days][days].length() - 2);
            int Dsum = 0;
            int sum = 1;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < rows; j++) {
                    if (people[i][j].charAt(0) == 'i')
                        people[i][j] = people[i][j].replace('i', 'I');
                    if (people[i][j].charAt(0) == 'I') {
                        sum++;
                        if (precaution(1.5)) {
                            people[i][j] = people[i][j].replace('I', 'X');
                            Dsum++;
                        }
                    }
                    out.print(people[i][j]);
                }
                out.println();
                out.println();
            }
            out.println("Number of Cases: " + sum + "/" + rows * rows + " people\tNumber of Deaths: " + Dsum);
            out.println();
            out.println("PZ = Patient Zero\t0 = Uninfected     I = Infected     X = Covid-19 Death     M = Wears a Mask     D = Does Social Distancing     Q = Quarantines When Infected");
            System.out.println();
            System.out.println("Type anything to redo the simulation or press enter to return to dashboard.");
            choice = input.nextLine();
        }
    }

    public static boolean precaution(double percent)
    {
        if(Math.random()*100 < percent)
            return true;
        else
            return false;
    }
    public static String[][] infection(String[][] people, int j, int k, String str, double percent)
    {
        if(str.equals(people[j][k]))
        {
            if(precaution(percent))
                people[j-1][k] = people[j-1][k].replace('0','G');
            if(precaution(percent))
                people[j-1][k-1] = people[j-1][k-1].replace('0','G');
            if(precaution(percent))
                people[j-1][k+1] = people[j-1][k+1].replace('0','G');
            if(precaution(percent))
                people[j+1][k] = people[j+1][k].replace('0','G');
            if(precaution(percent))
                people[j+1][k-1] = people[j+1][k-1].replace('0','G');
            if(precaution(percent))
                people[j+1][k+1] = people[j+1][k+1].replace('0','G');
            if(precaution(percent))
                people[j][k-1] = people[j][k-1].replace('0','G');
            if(precaution(percent))
                people[j][k+1] = people[j][k+1].replace('0','G');
        }
        return people;
    }
}
