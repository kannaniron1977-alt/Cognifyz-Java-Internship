import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Level 2 - Task 3: File Encryption / Decryption
 * Uses a Caesar cipher with a user-chosen shift key on letters and digits.
 * The result is saved to a NEW file; the original is never changed.
 * NOTE: A Caesar cipher is for learning only - it is not secure encryption.
 */
public class FileEncryption {

    // Shift a single character by 'key' positions (wraps around inside its own group)
    static char shift(char ch, int key) {
        if (ch >= 'a' && ch <= 'z') return (char) ('a' + (ch - 'a' + key + 26) % 26);
        if (ch >= 'A' && ch <= 'Z') return (char) ('A' + (ch - 'A' + key + 26) % 26);
        if (ch >= '0' && ch <= '9') return (char) ('0' + (ch - '0' + key + 10) % 10);
        return ch; // spaces, punctuation, newlines stay as they are
    }

    static String process(String text, int key) {
        StringBuilder sb = new StringBuilder();
        for (char ch : text.toCharArray()) {
            sb.append(shift(ch, key));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("1. Encrypt a file");
        System.out.println("2. Decrypt a file");
        System.out.print("Choose (1 or 2): ");
        String choice = sc.nextLine().trim();

        if (!choice.equals("1") && !choice.equals("2")) {
            System.out.println("Invalid choice.");
            return;
        }

        System.out.print("Enter file name or path: ");
        String fileName = sc.nextLine().trim();

        System.out.print("Enter secret key (a number, e.g. 3): ");
        int key;
        try {
            key = Integer.parseInt(sc.nextLine().trim()) % 26;
        } catch (NumberFormatException e) {
            System.out.println("Key must be a whole number.");
            return;
        }

        Path input = Paths.get(fileName);
        if (!Files.exists(input)) {
            System.out.println("File not found: " + fileName);
            return;
        }

        try {
            String content = new String(Files.readAllBytes(input), StandardCharsets.UTF_8);

            boolean encrypt = choice.equals("1");
            String result = process(content, encrypt ? key : -key);

            String outName = (encrypt ? "encrypted_" : "decrypted_") + input.getFileName();
            Path parent = input.toAbsolutePath().getParent();
            Path output = parent.resolve(outName);

            Files.write(output, result.getBytes(StandardCharsets.UTF_8));
            System.out.println((encrypt ? "Encrypted" : "Decrypted") + " successfully!");
            System.out.println("Saved to: " + output);
        } catch (IOException e) {
            System.out.println("Error while reading/writing file: " + e.getMessage());
        }
        sc.close();
    }
}
