# Cognifyz Technologies - Java Development Internship

Projects for all 3 levels of the Cognifyz Java Development internship.
(The internship requires any 2 of 3 levels; all 3 are included here.)

Requires **JDK 11 or newer** (JDK 17/21 recommended).

## Folder structure

```
Cognifyz-Java-Internship/
├── Level-1/
│   ├── TemperatureConverter.java
│   ├── PalindromeChecker.java
│   ├── StudentGradeCalculator.java
│   └── RandomPasswordGenerator.java
├── Level-2/
│   ├── TicTacToe.java
│   ├── PasswordStrengthChecker.java
│   └── FileEncryption.java
├── Level-3/
│   ├── ChatServer.java
│   ├── ChatClient.java
│   ├── MultiThreadedBank.java
│   ├── SwingCalculator.java
│   └── CurrencyConverter.java
└── README.md
```

## How to run

Open a terminal in the level folder, then compile and run:

```
javac TemperatureConverter.java
java TemperatureConverter
```

Replace the file/class name for the other programs.

### Level 3 notes

- **Chat application**: start the server first, then open 2+ extra terminals for clients.
  ```
  javac ChatServer.java ChatClient.java
  java ChatServer
  java ChatClient          (in each new terminal)
  ```
  Type `/quit` in a client to leave.
- **Swing Calculator**: needs a normal desktop (opens a window). `java SwingCalculator`
- **Currency Converter**: needs internet. Uses the free API `open.er-api.com` (no key).
- **File Encryption (Level 2)**: create a `.txt` file first, e.g. `sample.txt`, then give its name to the program.
  Output is saved as `encrypted_sample.txt` / `decrypted_sample.txt` next to the original.

## Skills covered

| Level | Task | Concepts |
|-------|------|----------|
| 1 | Temperature Converter | Input/output, arithmetic |
| 1 | Palindrome Checker | Strings, loops, conditions |
| 1 | Student Grade Calculator | Arrays, loops, arithmetic |
| 1 | Random Password Generator | Random, string manipulation, user input |
| 2 | Tic-Tac-Toe | 2D arrays, loops, conditions |
| 2 | Password Strength Checker | String manipulation, conditions |
| 2 | File Encryption/Decryption | File handling, strings, I/O |
| 3 | Chat Application | Sockets, client-server, threads |
| 3 | Multi-threaded Application | Threads, synchronized, locks |
| 3 | Desktop Application | Swing UI, event handling |
| 3 | Currency Converter | API integration, JSON parsing |

#cognifyztechnologies #cognifyz #cognifyztech
