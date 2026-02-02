import javax.swing.*;
import java.awt.*;

//Application used for testing: https://www.integral-calculator.com/

public class TrigIntegralCalculator extends JFrame {

    private JComboBox<String> functionBox;
    private JTextField coefficientField;
    private JLabel resultLabel;
    private JTextArea explanationArea;
    private JLabel errorLabel;

    // Colors
    private static final Color BG_MAIN = new Color(248, 249, 250);
    private static final Color BG_CARD = Color.WHITE;
    private static final Color ACCENT = new Color(13, 110, 253);
    private static final Color TEXT_PRIMARY = new Color(33, 37, 41);
    private static final Color TEXT_MUTED = new Color(108, 117, 125);
    private static final Color BORDER_LIGHT = new Color(206, 212, 218);
    private static final Color SUCCESS = new Color(25, 135, 84);
    private static final Color ERROR = new Color(220, 53, 69);
    private static final Color BTN_SECONDARY = new Color(108, 117, 125);

    // Fonts
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    private static final Font INPUT_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    private static final Font RESULT_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public TrigIntegralCalculator() {
        setTitle("Indefinite Trigonometric Integral Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(580, 650);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(16, 16));
        mainPanel.setBackground(BG_MAIN);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        // Header
        JLabel titleLabel = new JLabel("Indefinite Integral Calculator", SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_PRIMARY);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Input Card
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BG_CARD);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_LIGHT),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Function
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel functionLabel = new JLabel("Trigonometric Function");
        functionLabel.setFont(LABEL_FONT);
        formPanel.add(functionLabel, gbc);

        gbc.gridx = 1;
        functionBox = new JComboBox<>(new String[]{"sin(x)", "cos(x)", "sec²(x)"});
        functionBox.setFont(INPUT_FONT);
        functionLabel.setLabelFor(functionBox);
        formPanel.add(functionBox, gbc);

        // Coefficient
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel coefficientLabel = new JLabel("Coefficient");
        coefficientLabel.setFont(LABEL_FONT);
        formPanel.add(coefficientLabel, gbc);

        gbc.gridx = 1;
        coefficientField = new JTextField();
        coefficientField.setFont(INPUT_FONT);
        coefficientLabel.setLabelFor(coefficientField);
        formPanel.add(coefficientField, gbc);

        // Error label
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        errorLabel = new JLabel(" ");
        errorLabel.setFont(BODY_FONT);
        errorLabel.setForeground(ERROR);
        formPanel.add(errorLabel, gbc);

        // Buttons panel
        gbc.gridy = 3;
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        buttonPanel.setBackground(BG_CARD);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        calculateButton.setBackground(ACCENT);
        calculateButton.setForeground(Color.WHITE);

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        clearButton.setBackground(BTN_SECONDARY);
        clearButton.setForeground(Color.WHITE);

        buttonPanel.add(calculateButton);
        buttonPanel.add(clearButton);
        formPanel.add(buttonPanel, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Output
        JPanel outputPanel = new JPanel(new BorderLayout(12, 12));
        outputPanel.setBackground(BG_MAIN);

        explanationArea = new JTextArea(7, 40);
        explanationArea.setEditable(false);
        explanationArea.setFont(BODY_FONT);
        explanationArea.setLineWrap(true);
        explanationArea.setWrapStyleWord(true);
        explanationArea.setBackground(new Color(243, 245, 247));
        explanationArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_LIGHT),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JScrollPane scrollPane = new JScrollPane(explanationArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Step-by-Step Solution"));

        resultLabel = new JLabel("Result will appear here", SwingConstants.CENTER);
        resultLabel.setFont(RESULT_FONT);
        resultLabel.setForeground(TEXT_MUTED);

        outputPanel.add(scrollPane, BorderLayout.CENTER);
        outputPanel.add(resultLabel, BorderLayout.SOUTH);

        mainPanel.add(outputPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Actions
        calculateButton.addActionListener(e -> calculateIntegral());
        clearButton.addActionListener(e -> clearAll());
    }

    private void calculateIntegral() {
        errorLabel.setText(" ");
        resultLabel.setForeground(TEXT_MUTED);

        String input = coefficientField.getText().trim();
        if (input.isEmpty()) {
            showError("Coefficient is required.");
            return;
        }

        int coefficient;
        try {
            coefficient = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            showError("Coefficient must be an integer.");
            return;
        }

        String function = (String) functionBox.getSelectedItem();
        explanationArea.setText(getStepByStepSolution(function, coefficient));
        resultLabel.setText("Final Answer:  " + getIntegralResult(function, coefficient));
        resultLabel.setForeground(SUCCESS);
    }

    private void clearAll() {
        functionBox.setSelectedIndex(0);
        coefficientField.setText("");
        explanationArea.setText("");
        resultLabel.setText("Result will appear here");
        resultLabel.setForeground(TEXT_MUTED);
        errorLabel.setText(" ");
        coefficientField.requestFocus();
    }

    private void showError(String message) {
        errorLabel.setText(message);
        explanationArea.setText("");
        resultLabel.setText("Unable to calculate integral");
        resultLabel.setForeground(ERROR);
    }

    private String getStepByStepSolution(String function, int coefficient) {
        StringBuilder steps = new StringBuilder();

        String trigIntegral = "";
        String derivativeReason = "";
        String resultTrig = "";
        int finalCoefficient = coefficient;

        switch (function) {
            case "sin(x)" -> {
                trigIntegral = "−cos(x)";
                derivativeReason = "Because d/dx[cos(x)] = −sin(x)";
                resultTrig = "cos(x)";
                finalCoefficient = -coefficient;
            }
            case "cos(x)" -> {
                trigIntegral = "sin(x)";
                derivativeReason = "Because d/dx[sin(x)] = cos(x)";
                resultTrig = "sin(x)";
            }
            case "sec²(x)" -> {
                trigIntegral = "tan(x)";
                derivativeReason = "Because d/dx[tan(x)] = sec²(x)";
                resultTrig = "tan(x)";
            }
        }

        steps.append("Step 1: Write the integral\n");
        steps.append("∫ ").append(coefficient).append(" ").append(function).append(" dx\n\n");

        steps.append("Step 2: Apply the constant multiple rule\n");
        steps.append("= ").append(coefficient).append(" ∫ ").append(function).append(" dx\n\n");

        steps.append("Step 3: Use the known integral\n");
        steps.append("∫ ").append(function).append(" dx = ").append(trigIntegral).append("\n");
        steps.append(derivativeReason).append("\n\n");

        steps.append("Step 4: Multiply by the coefficient\n");
        steps.append("= ").append(coefficient).append("(").append(trigIntegral).append(")\n\n");

        steps.append("Step 5: Simplify and add the constant of integration\n");
        steps.append("= ").append(formatResult(finalCoefficient, resultTrig));

        return steps.toString();
    }

    private String getIntegralResult(String function, int coefficient) {
        return switch (function) {
            case "sin(x)" -> formatResult(-coefficient, "cos(x)");
            case "cos(x)" -> formatResult(coefficient, "sin(x)");
            case "sec²(x)" -> formatResult(coefficient, "tan(x)");
            default -> "";
        };
    }

    private String formatResult(int coefficient, String trig) {
        if (coefficient == 0) return "0 + C";
        if (coefficient == 1) return trig + " + C";
        if (coefficient == -1) return "-" + trig + " + C";
        return coefficient + trig + " + C";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrigIntegralCalculator().setVisible(true));
    }
}
