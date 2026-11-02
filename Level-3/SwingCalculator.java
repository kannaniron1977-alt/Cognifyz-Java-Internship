import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Level 3 - Task 3: Desktop Application (Swing Calculator)
 * Demonstrates layout managers and event handling with ActionListeners.
 */
public class SwingCalculator extends JFrame {

    private final JTextField display = new JTextField("0");
    private double firstNumber = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    public SwingCalculator() {
        setTitle("Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        display.setFont(new Font("SansSerif", Font.BOLD, 28));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        String[] labels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C", "<-", "%", ""
        };

        JPanel buttons = new JPanel(new GridLayout(5, 4, 5, 5));
        for (String label : labels) {
            if (label.isEmpty()) {
                buttons.add(new JPanel()); // empty filler cell
                continue;
            }
            JButton button = new JButton(label);
            button.setFont(new Font("SansSerif", Font.BOLD, 20));
            button.addActionListener(e -> handleInput(label)); // event handling
            buttons.add(button);
        }
        add(buttons, BorderLayout.CENTER);

        setSize(320, 420);
        setLocationRelativeTo(null);
    }

    private void handleInput(String input) {
        switch (input) {
            case "C":
                display.setText("0");
                firstNumber = 0;
                operator = "";
                startNewNumber = true;
                break;
            case "<-": {
                String text = display.getText();
                if (!startNewNumber && text.length() > 1) {
                    display.setText(text.substring(0, text.length() - 1));
                } else {
                    display.setText("0");
                    startNewNumber = true;
                }
                break;
            }
            case "+": case "-": case "*": case "/":
                firstNumber = parseDisplay();
                operator = input;
                startNewNumber = true;
                break;
            case "%":
                display.setText(format(parseDisplay() / 100));
                startNewNumber = true;
                break;
            case "=":
                calculate();
                break;
            case ".":
                if (startNewNumber) {
                    display.setText("0.");
                    startNewNumber = false;
                } else if (!display.getText().contains(".")) {
                    display.setText(display.getText() + ".");
                }
                break;
            default: // digits
                if (startNewNumber || display.getText().equals("0") || display.getText().equals("Error")) {
                    display.setText(input);
                    startNewNumber = false;
                } else {
                    display.setText(display.getText() + input);
                }
        }
    }

    private void calculate() {
        if (operator.isEmpty()) return;
        double second = parseDisplay();
        double result = 0;

        switch (operator) {
            case "+": result = firstNumber + second; break;
            case "-": result = firstNumber - second; break;
            case "*": result = firstNumber * second; break;
            case "/":
                if (second == 0) {
                    display.setText("Error");
                    operator = "";
                    startNewNumber = true;
                    return;
                }
                result = firstNumber / second;
                break;
        }
        display.setText(format(result));
        operator = "";
        startNewNumber = true;
    }

    private double parseDisplay() {
        try {
            return Double.parseDouble(display.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Show 5.0 as "5" but keep 2.5 as "2.5"
    private String format(double value) {
        if (value == Math.rint(value) && Math.abs(value) < 1e15) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SwingCalculator().setVisible(true));
    }
}
