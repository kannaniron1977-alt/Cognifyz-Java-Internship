import java.util.Scanner;

/**
 * Level 2 - Task 2: Password Strength Checker
 * Scores a password on length and character variety, then gives feedback.
 */
public class PasswordStrengthChecker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a password to check: ");
        String password = sc.nextLine();

        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) hasUpper = true;
            else if (Character.isLowerCase(ch)) hasLower = true;
            else if (Character.isDigit(ch)) hasDigit = true;
            else if (!Character.isWhitespace(ch)) hasSpecial = true;
        }

        int score = 0;
        if (password.length() >= 8)  score++;
        if (password.length() >= 12) score++;
        if (hasUpper)   score++;
        if (hasLower)   score++;
        if (hasDigit)   score++;
        if (hasSpecial) score++;
        // score is now between 0 and 6

        String strength;
        if (score <= 2)      strength = "WEAK";
        else if (score <= 4) strength = "MEDIUM";
        else                 strength = "STRONG";

        System.out.println();
        System.out.println("Length            : " + password.length());
        System.out.println("Uppercase letters : " + (hasUpper ? "Yes" : "No"));
        System.out.println("Lowercase letters : " + (hasLower ? "Yes" : "No"));
        System.out.println("Numbers           : " + (hasDigit ? "Yes" : "No"));
        System.out.println("Special characters: " + (hasSpecial ? "Yes" : "No"));
        System.out.println("Password strength : " + strength + " (score " + score + "/6)");

        // Feedback on what to improve
        if (!strength.equals("STRONG")) {
            System.out.println("\nTips to improve:");
            if (password.length() < 8)  System.out.println(" - Use at least 8 characters (12+ is better).");
            else if (password.length() < 12) System.out.println(" - Use 12 or more characters.");
            if (!hasUpper)   System.out.println(" - Add an uppercase letter.");
            if (!hasLower)   System.out.println(" - Add a lowercase letter.");
            if (!hasDigit)   System.out.println(" - Add a number.");
            if (!hasSpecial) System.out.println(" - Add a special character like ! @ # $.");
        }
        sc.close();
    }
}
