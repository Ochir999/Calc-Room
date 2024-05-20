import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {
    private static final Map<Character, Integer> romanToArabicMap = new HashMap<>();
    private static final Map<Integer, String> arabicToRomanMap = new HashMap<>();

    static {
        romanToArabicMap.put('I', 1);
        romanToArabicMap.put('V', 5);
        romanToArabicMap.put('X', 10);

        arabicToRomanMap.put(1, "I");
        arabicToRomanMap.put(2, "II");
        arabicToRomanMap.put(3, "III");
        arabicToRomanMap.put(4, "IV");
        arabicToRomanMap.put(5, "V");
        arabicToRomanMap.put(6, "VI");
        arabicToRomanMap.put(7, "VII");
        arabicToRomanMap.put(8, "VIII");
        arabicToRomanMap.put(9, "IX");
        arabicToRomanMap.put(10, "X");
        // Additional values for results greater than 10 can be added if needed.
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение:");
        String input = scanner.nextLine().trim();

        try {
            System.out.println("Ответ: " + calculate(input));
        } catch (Exception e) {
            System.out.println("throws Exception");
        }
    }

    private static String calculate(String input) throws Exception {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new Exception("Invalid format");
        }

        String first = parts[0];
        String operator = parts[1];
        String second = parts[2];

        boolean isFirstRoman = isRoman(first);
        boolean isSecondRoman = isRoman(second);

        if (isFirstRoman != isSecondRoman) {
            throw new Exception("Different numeral systems");
        }

        int a, b;

        if (isFirstRoman) {
            a = romanToArabic(first);
            b = romanToArabic(second);
        } else {
            a = Integer.parseInt(first);
            b = Integer.parseInt(second);
        }

        if (a < 1 || a > 10 || b < 1 || b > 10) {
            throw new Exception("Numbers out of range");
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
                    throw new ArithmeticException("Division by zero");
                }
                result = a / b;
                break;
            default:
                throw new Exception("Invalid operator");
        }

        if (isFirstRoman) {
            if (result < 1) {
                throw new Exception("Result less than 1 in Roman numerals");
            }
            return arabicToRoman(result);
        } else {
            return Integer.toString(result);
        }
    }

    private static boolean isRoman(String value) {
        return value.matches("[IVX]+");
    }

    private static int romanToArabic(String roman) {
        int result = 0;
        int previousValue = 0;
        for (char c : roman.toCharArray()) {
            int currentValue = romanToArabicMap.get(c);
            result += currentValue;
            if (currentValue > previousValue) {
                result -= 2 * previousValue;
            }
            previousValue = currentValue;
        }
        return result;
    }

    private static String arabicToRoman(int number) {
        StringBuilder result = new StringBuilder();
        while (number >= 10) {
            result.append("X");
            number -= 10;
        }
        if (arabicToRomanMap.containsKey(number)) {
            result.append(arabicToRomanMap.get(number));
        }
        return result.toString();
    }
}