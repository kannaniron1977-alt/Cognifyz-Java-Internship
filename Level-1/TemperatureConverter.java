import java.util.Scanner;

/**
 * Level 1 - Task 1: Temperature Converter
 * Converts between Celsius and Fahrenheit.
 */
public class TemperatureConverter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter temperature value: ");
        double temp = sc.nextDouble();

        System.out.print("Enter unit (C or F): ");
        char unit = Character.toUpperCase(sc.next().charAt(0));

        if (unit == 'C') {
            double fahrenheit = (temp * 9 / 5) + 32;
            System.out.printf("%.2f C = %.2f F%n", temp, fahrenheit);
        } else if (unit == 'F') {
            double celsius = (temp - 32) * 5 / 9;
            System.out.printf("%.2f F = %.2f C%n", temp, celsius);
        } else {
            System.out.println("Invalid unit! Please enter C or F.");
        }
        sc.close();
    }
}
