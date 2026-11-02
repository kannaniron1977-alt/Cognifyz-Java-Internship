import java.util.Scanner;

/**
 * Level 1 - Task 2: Palindrome Checker
 * Ignores spaces, punctuation and letter case.
 */
public class PalindromeChecker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a word or phrase: ");
        String input = sc.nextLine();

        // Remove everything except letters/digits, then lowercase
        String clean = input.replaceAll("[^A-Za-z0-9]", "").toLowerCase();

        boolean isPalindrome = true;
        int left = 0;
        int right = clean.length() - 1;

        while (left < right) {
            if (clean.charAt(left) != clean.charAt(right)) {
                isPalindrome = false;
                break;
            }
            left++;
            right--;
        }

        if (isPalindrome) {
            System.out.println("\"" + input + "\" is a palindrome.");
        } else {
            System.out.println("\"" + input + "\" is NOT a palindrome.");
        }
        sc.close();
    }
}
