import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Application used for testing: https://www.integral-calculator.com/

public class TrigIntegralCalculator extends JFrame {

    private JTextField expressionField;
    private JLabel resultLabel;
    private JTextArea explanationArea;
    private JLabel errorLabel;
    private JLabel exampleLabel;

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
    private static final Font SMALL_FONT = new Font("Segoe UI", Font.ITALIC, 12);

    public TrigIntegralCalculator() {
        setTitle("Indefinite Trigonometric Integral Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(680, 740);
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

        // Expression
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel expressionLabel = new JLabel("Expression");
        expressionLabel.setFont(LABEL_FONT);
        formPanel.add(expressionLabel, gbc);

        gbc.gridx = 1;
        expressionField = new JTextField(20);
        expressionField.setFont(INPUT_FONT);
        expressionLabel.setLabelFor(expressionField);
        formPanel.add(expressionField, gbc);

        // Example label
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2;
        exampleLabel = new JLabel("<html>Examples: 9sin(x) - cos(x), 3tan(x) + 2sec^2(x), sin^2(x) - cos^2(x)<br>Supported: sin, cos, tan, cot, sec, csc (and their squares using ^2)</html>");
        exampleLabel.setFont(SMALL_FONT);
        exampleLabel.setForeground(TEXT_MUTED);
        formPanel.add(exampleLabel, gbc);

        // Error label
        gbc.gridy = 2;
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

        explanationArea = new JTextArea(10, 40);
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
        
        // Allow Enter key to calculate
        expressionField.addActionListener(e -> calculateIntegral());
    }

    private void calculateIntegral() {
        errorLabel.setText(" ");
        resultLabel.setForeground(TEXT_MUTED);

        String input = expressionField.getText().trim();
        if (input.isEmpty()) {
            showError("Expression is required.");
            return;
        }

        try {
            List<Term> terms = parseExpression(input);
            if (terms.isEmpty()) {
                showError("Invalid expression format.");
                return;
            }

            explanationArea.setText(getStepByStepSolution(terms, input));
            resultLabel.setText("Final Answer:  " + getIntegralResult(terms));
            resultLabel.setForeground(SUCCESS);
        } catch (Exception ex) {
            showError("Invalid expression: " + ex.getMessage());
        }
    }

    private List<Term> parseExpression(String expression) throws Exception {
        List<Term> terms = new ArrayList<>();
        
        // Remove all spaces
        expression = expression.replaceAll("\\s+", "");
        
        // Convert ^ notation to appropriate format
        // sec^2(x) -> sec²(x), sin^2(x) -> sin²(x), cos^2(x) -> cos²(x)
        expression = expression.replaceAll("sec\\^2", "sec²");
        expression = expression.replaceAll("sin\\^2", "sin²");
        expression = expression.replaceAll("cos\\^2", "cos²");
        expression = expression.replaceAll("tan\\^2", "tan²");
        expression = expression.replaceAll("cot\\^2", "cot²");
        expression = expression.replaceAll("csc\\^2", "csc²");
        
        // Normalize consecutive signs: -- becomes +, +- becomes -, -+ becomes -, ++ becomes +
        while (expression.contains("--") || expression.contains("+-") || 
               expression.contains("-+") || expression.contains("++")) {
            expression = expression.replaceAll("\\-\\-", "+");
            expression = expression.replaceAll("\\+\\-", "-");
            expression = expression.replaceAll("\\-\\+", "-");
            expression = expression.replaceAll("\\+\\+", "+");
        }
        
        // Pattern to match terms with various trig functions
        // Supports: sin, cos, tan, cot, sec, csc and their squared versions
        Pattern pattern = Pattern.compile("([+\\-]?)(\\d*)(?:(sin²|cos²|tan²|cot²|sec²|csc²|sin|cos|tan|cot|sec|csc)\\(x\\))");
        Matcher matcher = pattern.matcher(expression);
        
        int lastEnd = 0;
        while (matcher.find()) {
            if (matcher.start() != lastEnd) {
                throw new Exception("Unexpected characters in expression");
            }
            lastEnd = matcher.end();
            
            String sign = matcher.group(1);
            String coeffStr = matcher.group(2);
            String function = matcher.group(3);
            
            // Determine coefficient
            int coefficient = 1;
            if (!coeffStr.isEmpty()) {
                coefficient = Integer.parseInt(coeffStr);
            }
            
            // Apply sign
            if (sign.equals("-")) {
                coefficient = -coefficient;
            }
            
            terms.add(new Term(coefficient, function));
        }
        
        if (lastEnd != expression.length()) {
            throw new Exception("Invalid characters at end of expression");
        }
        
        return terms;
    }

    private void clearAll() {
        expressionField.setText("");
        explanationArea.setText("");
        resultLabel.setText("Result will appear here");
        resultLabel.setForeground(TEXT_MUTED);
        errorLabel.setText(" ");
        expressionField.requestFocus();
    }

    private void showError(String message) {
        errorLabel.setText(message);
        explanationArea.setText("");
        resultLabel.setText("Unable to calculate integral");
        resultLabel.setForeground(ERROR);
    }

    private String getStepByStepSolution(List<Term> terms, String originalExpression) {
        StringBuilder steps = new StringBuilder();

        steps.append("Step 1: Write the integral\n");
        steps.append("∫ (").append(formatExpression(terms)).append(") dx\n\n");

        steps.append("Step 2: Apply the sum/difference rule\n");
        steps.append("= ");
        for (int i = 0; i < terms.size(); i++) {
            Term term = terms.get(i);
            if (i > 0) {
                steps.append(" + ");
            }
            steps.append("∫ ").append(term.coefficient).append(term.function).append("(x) dx");
        }
        steps.append("\n\n");

        steps.append("Step 3: Apply the constant multiple rule to each term\n");
        for (int i = 0; i < terms.size(); i++) {
            Term term = terms.get(i);
            if (i > 0) steps.append(" + ");
            steps.append(term.coefficient).append(" ∫ ").append(term.function).append("(x) dx");
        }
        steps.append("\n\n");

        steps.append("Step 4: Integrate each trigonometric function\n");
        for (Term term : terms) {
            String integral = getBasicIntegral(term.function);
            String reason = getDerivativeReason(term.function);
            steps.append("∫ ").append(term.function).append("(x) dx = ").append(integral).append("\n");
            steps.append("  ").append(reason).append("\n");
        }
        steps.append("\n");

        steps.append("Step 5: Multiply each integral by its coefficient\n");
        for (int i = 0; i < terms.size(); i++) {
            Term term = terms.get(i);
            if (i > 0) steps.append(" + ");
            steps.append(term.coefficient).append("(").append(getBasicIntegral(term.function)).append(")");
        }
        steps.append("\n\n");

        steps.append("Step 6: Simplify and add the constant of integration\n");
        steps.append("= ").append(getIntegralResult(terms));

        return steps.toString();
    }

    private String formatExpression(List<Term> terms) {
        StringBuilder expr = new StringBuilder();
        for (int i = 0; i < terms.size(); i++) {
            Term term = terms.get(i);
            if (i == 0) {
                if (term.coefficient == 1) {
                    expr.append(term.function).append("(x)");
                } else if (term.coefficient == -1) {
                    expr.append("-").append(term.function).append("(x)");
                } else {
                    expr.append(term.coefficient).append(term.function).append("(x)");
                }
            } else {
                if (term.coefficient >= 0) {
                    expr.append(" + ");
                    if (term.coefficient == 1) {
                        expr.append(term.function).append("(x)");
                    } else {
                        expr.append(term.coefficient).append(term.function).append("(x)");
                    }
                } else {
                    expr.append(" - ");
                    if (term.coefficient == -1) {
                        expr.append(term.function).append("(x)");
                    } else {
                        expr.append(Math.abs(term.coefficient)).append(term.function).append("(x)");
                    }
                }
            }
        }
        return expr.toString();
    }

    private String getBasicIntegral(String function) {
        return switch (function) {
            case "sin" -> "−cos(x)";
            case "cos" -> "sin(x)";
            case "tan" -> "−ln|cos(x)|";
            case "cot" -> "ln|sin(x)|";
            case "sec" -> "ln|sec(x) + tan(x)|";
            case "csc" -> "−ln|csc(x) + cot(x)|";
            case "sec²" -> "tan(x)";
            case "csc²" -> "−cot(x)";
            case "sin²" -> "x/2 − sin(2x)/4";
            case "cos²" -> "x/2 + sin(2x)/4";
            case "tan²" -> "tan(x) − x";
            case "cot²" -> "−cot(x) − x";
            default -> "";
        };
    }

    private String getDerivativeReason(String function) {
        return switch (function) {
            case "sin" -> "Because d/dx[cos(x)] = −sin(x)";
            case "cos" -> "Because d/dx[sin(x)] = cos(x)";
            case "tan" -> "Because d/dx[−ln|cos(x)|] = tan(x)";
            case "cot" -> "Because d/dx[ln|sin(x)|] = cot(x)";
            case "sec" -> "Because d/dx[ln|sec(x) + tan(x)|] = sec(x)";
            case "csc" -> "Because d/dx[−ln|csc(x) + cot(x)|] = csc(x)";
            case "sec²" -> "Because d/dx[tan(x)] = sec²(x)";
            case "csc²" -> "Because d/dx[−cot(x)] = csc²(x)";
            case "sin²" -> "Using the identity sin²(x) = (1 − cos(2x))/2";
            case "cos²" -> "Using the identity cos²(x) = (1 + cos(2x))/2";
            case "tan²" -> "Using the identity tan²(x) = sec²(x) − 1";
            case "cot²" -> "Using the identity cot²(x) = csc²(x) − 1";
            default -> "";
        };
    }

    private String getIntegralResult(List<Term> terms) {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < terms.size(); i++) {
            Term term = terms.get(i);
            int finalCoeff = term.coefficient;
            String resultFunc = "";
            boolean needsParentheses = false;
            
            switch (term.function) {
                case "sin" -> {
                    finalCoeff = -term.coefficient;
                    resultFunc = "cos(x)";
                }
                case "cos" -> resultFunc = "sin(x)";
                case "tan" -> {
                    resultFunc = "ln|cos(x)|";
                    finalCoeff = -term.coefficient;
                    needsParentheses = Math.abs(finalCoeff) > 1;
                }
                case "cot" -> {
                    resultFunc = "ln|sin(x)|";
                    needsParentheses = Math.abs(finalCoeff) > 1;
                }
                case "sec" -> {
                    resultFunc = "ln|sec(x) + tan(x)|";
                    needsParentheses = Math.abs(finalCoeff) > 1;
                }
                case "csc" -> {
                    resultFunc = "ln|csc(x) + cot(x)|";
                    finalCoeff = -term.coefficient;
                    needsParentheses = Math.abs(finalCoeff) > 1;
                }
                case "sec²" -> resultFunc = "tan(x)";
                case "csc²" -> {
                    finalCoeff = -term.coefficient;
                    resultFunc = "cot(x)";
                }
                case "sin²" -> {
                    resultFunc = "(x/2 − sin(2x)/4)";
                    needsParentheses = Math.abs(finalCoeff) > 1;
                }
                case "cos²" -> {
                    resultFunc = "(x/2 + sin(2x)/4)";
                    needsParentheses = Math.abs(finalCoeff) > 1;
                }
                case "tan²" -> {
                    resultFunc = "(tan(x) − x)";
                    needsParentheses = Math.abs(finalCoeff) > 1;
                }
                case "cot²" -> {
                    resultFunc = "(−cot(x) − x)";
                    finalCoeff = -term.coefficient;
                    needsParentheses = Math.abs(finalCoeff) > 1;
                }
            }
            
            if (i == 0) {
                result.append(formatTermWithParens(finalCoeff, resultFunc, true, needsParentheses));
            } else {
                if (finalCoeff >= 0) {
                    result.append(" + ").append(formatTermWithParens(finalCoeff, resultFunc, false, needsParentheses));
                } else {
                    result.append(" - ").append(formatTermWithParens(Math.abs(finalCoeff), resultFunc, false, needsParentheses));
                }
            }
        }
        
        result.append(" + C");
        return result.toString();
    }

    private String formatTerm(int coefficient, String trig, boolean isFirst) {
        if (coefficient == 0) return "0";
        if (coefficient == 1) return trig;
        if (coefficient == -1) return isFirst ? "-" + trig : trig;
        return coefficient + trig;
    }

    private String formatTermWithParens(int coefficient, String trig, boolean isFirst, boolean needsParens) {
        if (coefficient == 0) return "0";
        
        // If the trig already has parentheses (complex expressions) or needs them
        if (needsParens || trig.startsWith("(")) {
            if (coefficient == 1) return trig;
            if (coefficient == -1) return isFirst ? "-" + trig : trig;
            return coefficient + trig;
        } else {
            // Simple expressions without parentheses
            if (coefficient == 1) return trig;
            if (coefficient == -1) return isFirst ? "-" + trig : trig;
            return coefficient + trig;
        }
    }

    // Inner class to represent a term
    private static class Term {
        int coefficient;
        String function;

        Term(int coefficient, String function) {
            this.coefficient = coefficient;
            this.function = function;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrigIntegralCalculator().setVisible(true));
    }
}