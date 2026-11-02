import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Level 3 - Task 4: Currency Converter
 * Calls a free exchange-rate API (no API key needed) using java.net.http.HttpClient,
 * then parses the JSON response by hand (no external libraries required).
 *
 * API used: https://open.er-api.com/v6/latest/{BASE}
 * Sample response: {"result":"success","base_code":"USD","rates":{"USD":1,"INR":83.2,...}}
 */
public class CurrencyConverter {

    static final String API_URL = "https://open.er-api.com/v6/latest/";

    // Fetch the raw JSON text for the given base currency
    static String fetchRates(String base) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + base))
                .timeout(Duration.ofSeconds(15))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("API returned HTTP " + response.statusCode());
        }
        return response.body();
    }

    // Pull one rate out of the JSON: finds  "INR":83.2  inside the "rates" object
    static double extractRate(String json, String target) {
        int ratesStart = json.indexOf("\"rates\"");
        if (ratesStart < 0) return -1;
        String ratesPart = json.substring(ratesStart);

        Pattern pattern = Pattern.compile("\"" + Pattern.quote(target) + "\"\\s*:\\s*([0-9]+(?:\\.[0-9]+)?(?:[eE][-+]?[0-9]+)?)");
        Matcher matcher = pattern.matcher(ratesPart);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        return -1; // currency code not found
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter amount: ");
        double amount;
        try {
            amount = Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
            return;
        }

        System.out.print("Base currency code (e.g. USD): ");
        String base = sc.nextLine().trim().toUpperCase();

        System.out.print("Target currency code (e.g. INR): ");
        String target = sc.nextLine().trim().toUpperCase();

        if (!base.matches("[A-Z]{3}") || !target.matches("[A-Z]{3}")) {
            System.out.println("Currency codes must be 3 letters, like USD, INR, EUR.");
            return;
        }

        try {
            String json = fetchRates(base);
            double rate = extractRate(json, target);

            if (rate < 0) {
                System.out.println("Could not find rate for " + base + " -> " + target + ". Check the currency codes.");
                return;
            }

            double converted = amount * rate;
            System.out.printf("Exchange rate : 1 %s = %.4f %s%n", base, rate, target);
            System.out.printf("%.2f %s = %.2f %s%n", amount, base, converted, target);
        } catch (IOException e) {
            System.out.println("Network/API error: " + e);
            System.out.println("Check your internet connection and try again.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Request interrupted.");
        }
        sc.close();
    }
}
