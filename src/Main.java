import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter numbers and mathematical operator separated by a space : ");
            String input = scanner.nextLine();
            String result;
            try {
                result = calc(input);
                System.out.println("Result: " + result);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: invalid expression ");
            }
        }
    }

    public static String calc(String input) {
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Enter numbers separated by spaces, for example 1 + 2 ");
        }

        String firstOperand = parts[0].trim();
        String operator = parts[1].trim();
        String secondOperand = parts[2].trim();

        boolean isFirstRoman = isFirstOperandRoman(firstOperand);
        boolean isSecondRoman = isSecondOperandRoman(secondOperand);
        if (isFirstRoman != isSecondRoman) {
            throw new IllegalArgumentException("Use either Roman or Arabic numerals");
        }

        int a, b;
        if (isFirstRoman) {
            a = fromRomanNumeral(firstOperand);
            b = fromRomanNumeral(secondOperand);
        } else {
            a = Integer.parseInt(firstOperand);
            b = Integer.parseInt(secondOperand);
            if (a < 0 || a > 10 || b < 0 || b > 10) {
                throw new IllegalArgumentException("Arabic number must be between 1 and 10");
            }
        }

        int result;
        switch (operator) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                if (b == 0) {
                    throw new IllegalArgumentException("Can't divide by zero");
                }
                result = a / b;
                break;
            default:
                throw new IllegalArgumentException("Invalid statement ");
        }

        if (isFirstRoman) {
            return toRomanNumeral(result);
        } else {
            return String.valueOf(result);
        }
    }

    private static boolean isFirstOperandRoman(String operand) {
        return operand.matches("[IVXLCDM]+");
    }

    private static boolean isSecondOperandRoman(String operand) {
        return isFirstOperandRoman(operand);
    }

    private static int fromRomanNumeral(String romanNumeral) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        int number = 0;
        int prevValue = 0;

        for (int i = romanNumeral.length() - 1; i >= 0; i--) {
            int value = romanMap.get(romanNumeral.charAt(i));

            if (value < prevValue) {
                number -= value;
            } else {
                number += value;
            }
            prevValue = value;
        }
        return number;
    }

    private static String toRomanNumeral(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("Roman numerals are valid from 1 to 3999");
        }

        StringBuilder romanNumeral = new StringBuilder();
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] numerals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        for (int i = 0; i < values.length; i++) {
            while (number >= values[i]) {
                number -= values[i];
                romanNumeral.append(numerals[i]);
            }
        }

        return romanNumeral.toString();
    }
}
