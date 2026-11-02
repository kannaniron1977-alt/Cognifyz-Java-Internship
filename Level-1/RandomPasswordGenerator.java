import java.security.SecureRandom;
import java.util.Scanner;

/**
 * Level 1 - Task 4: Random Password Generator
 * Guarantees at least one character from every selected type.
 */
public class RandomPasswordGenerator {

    static final String NUMBERS = "0123456789";
    static final String LOWER   = "abcdefghijklmnopqrstuvwxyz";
    static final String UPPER   = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final String SPECIAL = "!@#$%^&*()-_=+[]{};:,.<>?";

    static boolean askYesNo(Scanner sc, String question) {
        System.out.print(question + " (y/n): ");
        return sc.next().equalsIgnoreCase("y");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter password length: ");
        int length = sc.nextInt();

        boolean useNumbers = askYesNo(sc, "Include numbers?");
        boolean useLower   = askYesNo(sc, "Include lowercase letters?");
        boolean useUpper   = askYesNo(sc, "Include uppercase letters?");
        boolean useSpecial = askYesNo(sc, "Include special characters?");

        StringBuilder pool = new StringBuilder();
        StringBuilder required = new StringBuilder(); // one char from each chosen type
        SecureRandom random = new SecureRandom();

        if (useNumbers) { pool.append(NUMBERS); required.append(NUMBERS.charAt(random.nextInt(NUMBERS.length()))); }
        if (useLower)   { pool.append(LOWER);   required.append(LOWER.charAt(random.nextInt(LOWER.length()))); }
        if (useUpper)   { pool.append(UPPER);   required.append(UPPER.charAt(random.nextInt(UPPER.length()))); }
        if (useSpecial) { pool.append(SPECIAL); required.append(SPECIAL.charAt(random.nextInt(SPECIAL.length()))); }

        if (pool.length() == 0 || length <= 0) {
            System.out.println("Choose at least one option and a length greater than 0.");
            return;
        }
        if (length < required.length()) {
            System.out.println("Length is too short for the selected character types (minimum " + required.length() + ").");
            return;
        }

        StringBuilder password = new StringBuilder(required);
        while (password.length() < length) {
            password.append(pool.charAt(random.nextInt(pool.length())));
        }

        // Shuffle so the guaranteed characters are not always at the start
        for (int i = password.length() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char tmp = password.charAt(i);
            password.setCharAt(i, password.charAt(j));
            password.setCharAt(j, tmp);
        }

        System.out.println("Generated password: " + password);
        sc.close();
    }
}
