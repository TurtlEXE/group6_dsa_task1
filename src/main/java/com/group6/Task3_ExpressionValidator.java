package com.group6;

import java.util.Scanner;
import java.util.Stack;

/**
 * Task 3 — Stack: Expression Validator
 *
 * A bracket and operator validator for simple arithmetic expressions using a Stack.
 *
 * Features:
 * - Balanced bracket checking: (), [], {}
 * - Operator validation: no leading/trailing operators, no consecutive operators
 * - Operands are single-digit integers (0–9)
 * - Processes multiple expressions until user enters "quit"
 *
 * Bonus: Supports multi-digit integers via a tokenizer pass.
 *
 * @author Group 6
 */
public class Task3_ExpressionValidator {

    /**
     * Checks whether the expression has balanced brackets using a Stack.
     * Brackets checked: (), [], {}
     *
     * @param expr the arithmetic expression string
     * @return true if all brackets are properly balanced, false otherwise
     */
    public static boolean isBalancedBrackets(String expr) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);

            // Push opening brackets onto the stack
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            }
            // For closing brackets, check if they match the top of the stack
            else if (ch == ')' || ch == ']' || ch == '}') {
                if (stack.isEmpty()) {
                    return false; // No matching opening bracket
                }
                char top = stack.pop();
                if (!isMatchingPair(top, ch)) {
                    return false; // Mismatched bracket types
                }
            }
        }

        // If stack is empty, all brackets were matched
        return stack.isEmpty();
    }

    /**
     * Validates the full expression: balanced brackets AND operator placement rules.
     *
     * Operator rules:
     * - Expression (ignoring spaces) cannot start or end with a binary operator (+, -, *, /)
     * - No two operators can appear consecutively (e.g., "3++4" is invalid)
     * - Operators must appear between operands or bracket groups
     *
     * @param expr the arithmetic expression string
     * @return true if the expression is valid, false otherwise
     */
    public static boolean isValidExpression(String expr) {
        // Step 1: Check balanced brackets first
        if (!isBalancedBrackets(expr)) {
            return false;
        }

        // Step 2: Remove all spaces for operator analysis
        String cleaned = expr.replaceAll("\\s+", "");

        // Empty expression after cleaning is invalid
        if (cleaned.isEmpty()) {
            return false;
        }

        // Step 3: Check that expression does not start or end with a binary operator
        char first = cleaned.charAt(0);
        char last = cleaned.charAt(cleaned.length() - 1);

        if (isOperator(first) || isOperator(last)) {
            return false;
        }

        // Step 4: Check for consecutive operators and invalid operator positions
        for (int i = 0; i < cleaned.length() - 1; i++) {
            char current = cleaned.charAt(i);
            char next = cleaned.charAt(i + 1);

            // Two operators in a row → invalid (e.g., "3++4")
            if (isOperator(current) && isOperator(next)) {
                return false;
            }

            // Operator immediately after opening bracket → invalid (e.g., "(+3)")
            if (isOpenBracket(current) && isOperator(next)) {
                return false;
            }

            // Operator immediately before closing bracket → invalid (e.g., "(3+)")
            if (isOperator(current) && isCloseBracket(next)) {
                return false;
            }
        }

        return true;
    }

    // ==================== BONUS: Multi-digit integer support ====================

    /**
     * BONUS: Validates an expression that may contain multi-digit integers.
     * Uses a tokenizer pass to separate numbers, operators, and brackets into tokens,
     * then validates token sequence using a second stack.
     *
     * @param expr the arithmetic expression string (may contain multi-digit numbers)
     * @return "VALID" if valid, or an appropriate "INVALID (...)" message
     */
    public static String validateMultiDigit(String expr) {
        // Step 1: Check balanced brackets
        if (!isBalancedBrackets(expr)) {
            return "INVALID (Unbalanced brackets)";
        }

        // Step 2: Tokenize the expression
        String cleaned = expr.replaceAll("\\s+", "");
        if (cleaned.isEmpty()) {
            return "INVALID (Operator error)";
        }

        // Use a stack-based approach to collect tokens
        Stack<String> tokenStack = new Stack<>();
        int i = 0;

        while (i < cleaned.length()) {
            char ch = cleaned.charAt(i);

            if (Character.isDigit(ch)) {
                // Collect multi-digit number
                StringBuilder number = new StringBuilder();
                while (i < cleaned.length() && Character.isDigit(cleaned.charAt(i))) {
                    number.append(cleaned.charAt(i));
                    i++;
                }
                tokenStack.push(number.toString());
            } else if (isOperator(ch) || isOpenBracket(ch) || isCloseBracket(ch)) {
                tokenStack.push(String.valueOf(ch));
                i++;
            } else {
                // Invalid character
                return "INVALID (Operator error)";
            }
        }

        // Step 3: Validate token sequence (convert stack to array for sequential check)
        String[] tokens = new String[tokenStack.size()];
        for (int j = tokens.length - 1; j >= 0; j--) {
            tokens[j] = tokenStack.pop();
        }

        if (tokens.length == 0) {
            return "INVALID (Operator error)";
        }

        // First token cannot be an operator
        if (isOperatorToken(tokens[0])) {
            return "INVALID (Operator error)";
        }

        // Last token cannot be an operator
        if (isOperatorToken(tokens[tokens.length - 1])) {
            return "INVALID (Operator error)";
        }

        // Check consecutive tokens
        for (int j = 0; j < tokens.length - 1; j++) {
            if (isOperatorToken(tokens[j]) && isOperatorToken(tokens[j + 1])) {
                return "INVALID (Operator error)";
            }
            if (isOpenBracketToken(tokens[j]) && isOperatorToken(tokens[j + 1])) {
                return "INVALID (Operator error)";
            }
            if (isOperatorToken(tokens[j]) && isCloseBracketToken(tokens[j + 1])) {
                return "INVALID (Operator error)";
            }
        }

        return "VALID";
    }

    // ==================== Helper Methods ====================

    /**
     * Checks if the opening and closing bracket characters form a matching pair.
     */
    private static boolean isMatchingPair(char open, char close) {
        return (open == '(' && close == ')')
                || (open == '[' && close == ']')
                || (open == '{' && close == '}');
    }

    /**
     * Checks if the character is a binary arithmetic operator.
     */
    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    /**
     * Checks if the character is an opening bracket.
     */
    private static boolean isOpenBracket(char ch) {
        return ch == '(' || ch == '[' || ch == '{';
    }

    /**
     * Checks if the character is a closing bracket.
     */
    private static boolean isCloseBracket(char ch) {
        return ch == ')' || ch == ']' || ch == '}';
    }

    /**
     * Checks if a token string is an operator.
     */
    private static boolean isOperatorToken(String token) {
        return token.length() == 1 && isOperator(token.charAt(0));
    }

    /**
     * Checks if a token string is an opening bracket.
     */
    private static boolean isOpenBracketToken(String token) {
        return token.length() == 1 && isOpenBracket(token.charAt(0));
    }

    /**
     * Checks if a token string is a closing bracket.
     */
    private static boolean isCloseBracketToken(String token) {
        return token.length() == 1 && isCloseBracket(token.charAt(0));
    }

    /**
     * Validates the expression and prints the formatted result.
     *
     * @param input the arithmetic expression string
     */
    private static void processAndPrintResult(String input) {
        if (!isBalancedBrackets(input)) {
            System.out.println("Result: INVALID (Unbalanced brackets)");
        } else if (!isValidExpression(input)) {
            System.out.println("Result: INVALID (Operator error)");
        } else {
            System.out.println("Result: VALID");
        }
        System.out.println();
    }

    // ==================== Main Method ====================

    /**
     * Main entry point. Processes multiple expressions until user enters "quit".
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Expression Validator ===");
        System.out.println("Enter an arithmetic expression to validate.");
        System.out.println("Supported brackets: (), [], {}");
        System.out.println("Supported operators: +, -, *, /");
        System.out.println("Operands: single-digit integers (0-9)");
        System.out.println("Type 'quit' to exit.\n");

        while (true) {
            System.out.print("Expression: ");
            String input = scanner.nextLine();

            // Check for quit command (case-insensitive)
            if (input.trim().equalsIgnoreCase("quit")) {
                System.out.println("Goodbye!");
                break;
            }

            // Validate expression length constraint (max 200 characters)
            if (input.length() > 200) {
                System.out.println("Result: INVALID (Expression exceeds 200 characters)");
                System.out.println();
                continue;
            }

            // Validate and print result
            processAndPrintResult(input);
        }

        scanner.close();
    }
}
