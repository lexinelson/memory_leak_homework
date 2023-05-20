import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {

    public static void main(String... args) {
        boolean shouldContinue = true;
        while (shouldContinue) {
            try {
                writeCatFactToFile(fetchCatFact());
                shouldContinue = checkForAnotherFact();
            } catch (IOException exc) {
                System.out.println("ERROR - " + exc.getMessage());
                shouldContinue = false;
            }
        }
        System.out.println("Goodbye.");
    }

    public static String fetchCatFact() throws IOException {
        URL catFactUrl = new URL("https://catfact.ninja/fact");
        HttpURLConnection connection = (HttpURLConnection) catFactUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder responseStr = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null)
            responseStr.append(line);
        return responseStr.toString().split(":")[1].split("\"")[1];
    }

    public static void writeCatFactToFile(String catFact) throws IOException {
        System.out.println("Writing Fact: " + catFact);
        (new FileWriter("target/cat_facts.txt", true)).write(catFact + "\n");
    }

    public static boolean checkForAnotherFact() {
        System.out.print("\nShould we write another fact? (Y/N) >> ");
        Scanner scanner = new Scanner(System.in);
        char response = scanner.nextLine().trim().toLowerCase().charAt(0);
        if (response != 'y' && response != 'n') {
            return checkForAnotherFact();
        } else {
            return response == 'y';
        }
    }
}
