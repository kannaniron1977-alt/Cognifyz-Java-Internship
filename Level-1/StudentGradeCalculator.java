import java.util.Scanner;

/**
 * Level 1 - Task 3: Student Grade Calculator
 * Reads N grades into an array and prints the average.
 */
public class StudentGradeCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("How many grades do you want to enter? ");
        int n = sc.nextInt();

        if (n <= 0) {
            System.out.println("Number of grades must be greater than 0.");
            return;
        }

        double[] grades = new double[n];
        double sum = 0;

        for (int i = 0; i < n; i++) {
            System.out.print("Enter grade " + (i + 1) + ": ");
            grades[i] = sc.nextDouble();
            sum += grades[i];
        }

        double average = sum / n;
        System.out.printf("Average grade = %.2f%n", average);
        sc.close();
    }
}
