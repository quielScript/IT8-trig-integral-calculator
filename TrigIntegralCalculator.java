import javax.swing.*;
import java.awt.*;

// Application used for testing: https://www.integral-calculator.com/
public class TrigIntegralCalculator extends JFrame {

    private JComboBox<String> functionBox;
    private JTextField coefficientField;
    private JLabel resultLabel;
    private JTextArea explanationArea;

    // Color palette
    private static final Color BG_MAIN = new Color(248, 249, 250);
    private static final Color BG_CARD = Color.WHITE;
    private static final Color ACCENT = new Color(13, 110, 253);
    private static final Color TEXT_DARK = new Color(33, 37, 41);
    private static final Color BORDER_LIGHT = new Color(222, 226, 230);

    public TrigIntegralCalculator() {
        setTitle("Indefinite Integral Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(540, 560);
        setLocationRelativeTo(null);

        // Main content
        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBackground(BG_MAIN);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 24, 24, 24));

        // Title
        JLabel titleLabel = new JLabel("Indefinite Integral Calculator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(TEXT_DARK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Input Card
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BG_CARD);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_LIGHT),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Function
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel funcLabel = new JLabel("Function:");
        funcLabel.setForeground(TEXT_DARK);
        formPanel.add(funcLabel, gbc);

        gbc.gridx = 1;
        functionBox = new JComboBox<>(new String[]{"sin(x)", "cos(x)", "sec²(x)"});
        functionBox.setPreferredSize(new Dimension(180, 36));
        formPanel.add(functionBox, gbc);

        // Coefficient
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel coeffLabel = new JLabel("Coefficient:");
        coeffLabel.setForeground(TEXT_DARK);
        formPanel.add(coeffLabel, gbc);

        gbc.gridx = 1;
        coefficientField = new JTextField();
        coefficientField.setPreferredSize(new Dimension(180, 36));
        coefficientField.setFont(coefficientField.getFont().deriveFont(14f));
        formPanel.add(coefficientField, gbc);

        // Button
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 8, 8, 8);

        JButton calculateButton = new JButton("Calculate Integral");
        calculateButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        calculateButton.setPreferredSize(new Dimension(220, 44));
        calculateButton.setBackground(ACCENT);
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFocusPainted(false);
        calculateButton.setBorderPainted(false);
        formPanel.add(calculateButton, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Explanation + Result Card
        JPanel bottomPanel = new JPanel(new BorderLayout(0, 12));
        bottomPanel.setBackground(BG_MAIN);

        explanationArea = new JTextArea(6, 35);
        explanationArea.setEditable(false);
        explanationArea.setLineWrap(true);
        explanationArea.setWrapStyleWord(true);
        explanationArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        explanationArea.setBackground(new Color(245, 247, 250));
        explanationArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_LIGHT),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        JScrollPane scroll = new JScrollPane(explanationArea);
        scroll.setBorder(BorderFactory.createTitledBorder("Integration Rule"));

        resultLabel = new JLabel("Result will appear here", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 17));
        resultLabel.setForeground(TEXT_DARK);
        resultLabel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

        bottomPanel.add(scroll, BorderLayout.CENTER);
        bottomPanel.add(resultLabel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Action
        calculateButton.addActionListener(e -> calculateIntegral());
    }

    private void calculateIntegral() {
        try {
            int coefficient = Integer.parseInt(coefficientField.getText().trim());
            String function = (String) functionBox.getSelectedItem();

            explanationArea.setText(getIntegralRule(function));
            resultLabel.setText("Result:  " + getIntegralResult(function, coefficient));
            resultLabel.setForeground(new Color(25, 135, 84)); // success green

        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid coefficient");
            resultLabel.setForeground(Color.RED);
            explanationArea.setText("");
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid integer.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getIntegralRule(String function) {
        return switch (function) {
            case "sin(x)" -> "∫ sin(x) dx = −cos(x) + C\n\nBecause d/dx [cos(x)] = −sin(x)";
            case "cos(x)" -> "∫ cos(x) dx = sin(x) + C\n\nBecause d/dx [sin(x)] = cos(x)";
            case "sec²(x)" -> "∫ sec²(x) dx = tan(x) + C\n\nBecause d/dx [tan(x)] = sec²(x)";
            default -> "";
        };
    }

    private String getIntegralResult(String function, int coeffcient) {
        return switch (function) {
            case "sin(x)" -> formatResult(-coeffcient, "cos(x)");
            case "cos(x)" -> formatResult(coeffcient, "sin(x)");
            case "sec²(x)" -> formatResult(coeffcient, "tan(x)");
            default -> "";
        };
    }
    
    private String formatResult(int coeffcient, String trig) {
        if (coeffcient == 0) return "0 + C";
        if (coeffcient == 1) return trig + " + C";
        if (coeffcient == -1) return "-" + trig + " + C";
        return coeffcient + trig + " + C";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrigIntegralCalculator().setVisible(true));
    }
}
