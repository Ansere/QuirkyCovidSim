import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CovidInfo {
    public static void covidInfo() {
        Scanner input = new Scanner(System.in);
        String choice = "-1";
        while (!choice.isEmpty()) {
            System.out.println("Would you like to:\n" +
                    "1: Get tested\n" +
                    "2. Take a quick self-assessment\n" +
                    "\tor\n" +
                    "3. See symptoms for COVID-19\n" +
                    "Press enter to return to dashboard");
            choice = input.nextLine();
            if (choice.equals("1")) {
                setDelay(1);
                System.out.println("Go to 'https://gps-coordinates.org/my-location.php' and get your latitude and longitude and enter them in that order");
                double latitude = input.nextDouble(), longitude = input.nextDouble();
                ArrayList<Location> testingLocations = new ArrayList<>();
                testingLocations.add(new Location(29.765075362718875, -95.75141551311735, "1510 Mason Rd, Katy, TX 77450"));
                testingLocations.add(new Location(29.76054633810719, -95.75327578612259, "1616 Mason Rd, Katy, TX 77450"));
                testingLocations.add(new Location(29.76146182218281, -95.73267059867023, "20903 Highland Knolls Dr, Katy, TX 77450"));
                testingLocations.add(new Location(29.748309283392473, -95.79299991690475, "24919 Westheimer Pkwy, Katy, TX 77494"));
                testingLocations.add(new Location(29.741497468958613, -95.77784995358128, "23730 Westheimer Pkwy Suite N, Katy, TX 77494"));
                testingLocations.add(new Location(29.73820884119694, -95.77487406792845, "3050 Grand Pkwy, Katy, TX 77494"));
                testingLocations.add(new Location(29.726287180529898, -95.81911767626117, "10705 Spring Green Blvd #600, Katy, TX 77494"));
                testingLocations.add(new Location(29.718688838400485, -95.81360876596197, "10522 Spring Green Blvd, Katy, TX 77494"));
                testingLocations.add(new Location(29.716437367294187, -95.7721299119445, "6501 S Fry Rd, Katy, TX 77494"));
                testingLocations.add(new Location(29.69870526733636, -95.8262468543025, "26000 Farm to Market 1093, Richmond, TX 77406"));
                testingLocations.add(new Location(29.706023656098598, -95.80907201631088, "24948 Farm to Market 1093 #205, Richmond, TX 77406"));
                testingLocations.add(new Location(29.703208954295608, -95.76597289455837, "22123 Farm to Market 1093, Richmond, TX 77407"));
                testingLocations.add(new Location(29.705742189468154, -95.72125350507079, "6900 Grand Mission Blvd, Richmond, TX 77407"));
                testingLocations.add(new Location(29.68885274775894, -95.81458092664718, "25151 Fulshear-Gaston Rd, Richmond, TX 77406"));
                testingLocations.add(new Location(29.676465353116175, -95.75203859207396, "7101 W Grand Pkwy S #180, Richmond, TX 77407"));
                testingLocations.add(new Location(29.66661065376901, -95.73518780762936, "8111 W Grand Pkwy S, Richmond, TX 77407"));
                testingLocations.add(new Location(29.664921179780045, -95.71477243416764, "4828 Waterview Town Center Dr #100, Richmond, TX 77407"));
                testingLocations.add(new Location(29.657036592662138, -95.70375461356925, "18310 W Airport Blvd #100, Richmond, TX 77407"));
                testingLocations.add(new Location(29.662928539221788, -95.68668693148621, "10420 FM 1464, Richmond, TX 77407"));
                double shortestDistance = Double.MAX_VALUE;
                int row = -1;
                for (int r = 0; r < testingLocations.size(); r++) {
                    double distance = distance(latitude, testingLocations.get(r).getLatitude(), longitude, testingLocations.get(r).getLongitude());
                    if (distance < shortestDistance) {
                        shortestDistance = distance;
                        row = r;
                    }
                }
                System.out.println("The closest testing site near you is located at " + testingLocations.get(row).getAddress());


            } else if (choice.equals("2")) {
                String answer;
                System.out.println("Coronavirus Self-Checker\n" +
                        "Answer the following with: Yes or No\n");
                setDelay(1);
                System.out.println("Are you experiencing any of the following:\n" +
                        "--severe difficulty breathing (e.g., struggling for each breath, speaking in single words)\n" +
                        "--severe chest pain\n" +
                        "--having a very hard time waking up\n" +
                        "--feeling confused\n" +
                        "--lost consciousness\n");
                answer = input.next();

                if (answer.equalsIgnoreCase("no"))
                    System.out.println("Are you experiencing any of the following:\n" +
                            "--shortness of breath at rest\n" +
                            "--inability to lie down because of difficulty breathing\n" +
                            "--chronic health conditions that you are having difficulty managing because of your current respiratory illness\n");
                else {
                    System.out.println("Please call 911 or go directly to your nearest emergency department.");
                    return;
                }
                answer = input.next();
                boolean contact = false;
                if (answer.equalsIgnoreCase("no")) {
                    System.out.println("In the past 14 days, were you notified that you were connected to an outbreak by:\n" +
                            "--AHS\n" +
                            "--Your employer\n" +
                            "--The organizer of a social or sporting event you attended\n" +
                            "*They may have provided you with an Outbreak or EI Number, but this is not required to book a COVID-19 test appointment.\n");

                } else {
                    System.out.println("Please call 811 to speak with a nurse.\n" +
                            "You may need to speak with a nurse about your symptoms, please call 811.\n");
                    return;
                }

                answer = input.next();
                if (answer.equalsIgnoreCase("yes")) {
                    System.out.println("In the past 10 days, have you experienced any of the following:\n" +
                            "--fever\n" +
                            "--new onset of cough or worsening of chronic cough\n" +
                            "--new or worsening shortness of breath\n" +
                            "--new or worsening difficulty breathing\n" +
                            "--sore throat\n" +
                            "--runny nose\n");
                    contact = true;
                } else {
                    System.out.println("In the past 10 days, have you experienced any of the following:\n" +
                            "fever\n" +
                            "new onset of cough or worsening of chronic cough\n" +
                            "new or worsening shortness of breath\n" +
                            "new or worsening difficulty breathing\n" +
                            "sore throat\n" +
                            "runny nose\n");
                }
                answer = input.next();
                if (answer.equalsIgnoreCase("no"))
                    System.out.println("Do you have any of the following:\n" +
                            "--chills\n" +
                            "--painful swallowing\n" +
                            "--stuffy nose\n" +
                            "--headache\n" +
                            "--muscle or joint ache\n" +
                            "--feeling unwell, fatigue or severe exhaustion\n" +
                            "--nausea, vomiting, diarrhea or unexplained loss of appetite\n" +
                            "--loss of sense of smell or taste\n" +
                            "--conjunctivitis (pink eye)\n");
                else {
                    System.out.println("You are legally required to immediately isolate. You are recommended to be tested for COVID-19.\n" +
                            "Please re-run to find your nearest testing site\n");
                    return;
                }

                answer = input.next();
                if (answer.equalsIgnoreCase("no")) {
                    if (contact) {
                        System.out.println("You are legally required to immediately quarantine. You are recommended to be tested for COVID-19.\n" +
                                "Please re-run to find your nearest testing site\n");
                        return;
                    } else
                        System.out.println("In the past 14 days, were you notified that you were connected to an outbreak by:\n" +
                                "--AHS\n" +
                                "--Your employer\n" +
                                "--The organizer of a social or sporting event you attended\n" +
                                "*They may have provided you with an Outbreak or EI Number, but this is not required to book a COVID-19 test appointment.\n");
                }
                if (input.nextLine().equalsIgnoreCase("yes")) {
                    System.out.println("You are legally required to immediately quarantine. You are recommended to be tested for COVID-19.\n" +
                            "Please re-run to find your nearest testing site\n");
                    return;
                } else
                    System.out.println("In the past 14 days, did you return from travel outside of Canada?\n");
                answer = input.nextLine();
                if (answer.equalsIgnoreCase("yes")) {
                    System.out.println("You are legally required to immediately self-isolate.\n");
                    return;
                } else {
                    System.out.println("Do you require testing for the purpose of outgoing travel?\n");
                }
                answer = input.nextLine();
                if (answer.equalsIgnoreCase("yes")) {
                    System.out.println("Practice physical distancing and good hand hygiene. Monitor for COVID-19 symptoms: fever, cough, shortness of breath, sore throat or runny nose. If you develop any COVID-19 symptoms, stay home and take this self-assessment again.\n");
                } else {
                    System.out.println("You do not have coronavirus but continue to take steps to protect yourself and others.\n");
                }

            } else if (choice.equals("3")) {
                System.out.println("\n" +
                        "Symptoms may appear 2-14 days after exposure to the virus. People with these symptoms may have COVID-19:\n" +
                        "--Fever or chills\n" +
                        "--Cough\n" +
                        "--Shortness of breath or difficulty breathing\n" +
                        "--Fatigue\n" +
                        "--Muscle or body aches\n" +
                        "--Headache\n" +
                        "--New loss of taste or smell\n" +
                        "--Sore throat\n" +
                        "--Congestion or runny nose\n" +
                        "--Nausea or vomiting\n" +
                        "--Diarrhea\n\n" +
                        "Look for emergency warning signs for COVID-19. If someone is showing any of these signs, seek emergency medical care immediately:\n" +
                        "--Trouble breathing\n" +
                        "--Persistent pain or pressure in the chest\n" +
                        "--New confusion\n" +
                        "--Inability to wake or stay awake\n" +
                        "--Bluish lips or face\n");
            } else if (!choice.isEmpty()) {
                System.out.println("That's not a valid choice!");
            }
        }
    }
    public static double distance(double lat1, double lat2, double lon1, double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }

    public static void setDelay(int secondsDelayed) {
        int p;
        for (p = 0; p < secondsDelayed; p++)
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
class Location {
    double latitude;
    double longitude;
    String address;

    Location(double latitude, double longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }
}