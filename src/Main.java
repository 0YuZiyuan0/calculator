import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine().toUpperCase();
        Calculator calculator = validateDigitsAndGetRightCalculator(line);
        Operations operations = validateOperationsAndGetOperation(line);
        String[] split = line.split(" ");
        String calculate = calculate(calculator, split[0], split[2], operations);
        System.out.println("Результат: " + calculate);
    }

    public static Operations validateOperationsAndGetOperation(String input) {
        String[] splitInput = input.split(" ");
        String operation = splitInput[1];
        if (!Operations.contains(operation)) {
            throw new RuntimeException("Некоректный математический оператор");
        }
        return Operations.valueOfLabel(operation);
    }

    public static Calculator validateDigitsAndGetRightCalculator(String input) {
        String[] splitInput = input.split(" ");
        if (splitInput.length != 3) {
            throw new RuntimeException("Некорректный ввод, должно быть 2 числа и один оператор, разделенные пробелами");
        }
        String firstDigit = splitInput[0];
        String secondDigit = splitInput[2];
        if (checkArabicDigits(firstDigit, secondDigit)) {
            return Calculator.ARABIC;
        }
        if (checkRomeDigit(firstDigit, secondDigit)) {
            return Calculator.ROME;
        }
        throw new RuntimeException("Введены неверные числа");
    }

    public static boolean checkArabicDigits(String first, String second) {
        boolean isFirstValid = first.matches(("^10|[1-9]$"));
        boolean isSecondValid = second.matches(("^10|[1-9]$"));
        return isFirstValid&&isSecondValid;
    }

    public static boolean checkRomeDigit(String first, String second) {
        boolean isFirstValid = first.matches("^(X|IX|IV|V?I{0,3})$");
        boolean isSecondValid = second.matches("^(X|IX|IV|V?I{0,3})$");
        return isFirstValid&&isSecondValid;
    }

    public static String calculate(Calculator calculator, String firstDigit, String secondDigit, Operations operation) {
        if (calculator.equals(Calculator.ARABIC)) {
            return String.valueOf(calculateArabic(firstDigit, secondDigit, operation));
        } else {
            return calculateRome(firstDigit, secondDigit, operation);
        }
    }

    private static String calculateRome(String firstDigit, String secondDigit, Operations operation) {
        int one = convertRomeToArabic(firstDigit);
        int two = convertRomeToArabic(secondDigit);
        int i = calculateArabic(String.valueOf(one), String.valueOf(two), operation);
        if (i < 1) {
            throw new RuntimeException("В римской системе нет чисел меньше единицы");
        }
        return convertArabicToRome(i);
    }

    private static int convertRomeToArabic(String input) {
        char[] charArray = input.toCharArray();
        int arabian;
        int last = charArray.length - 1;
        int result = RomeDigits.getValueByCharacter(charArray[last]);

        for (int i = last - 1; i >= 0; i--) {
            arabian = RomeDigits.getValueByCharacter(charArray[i]);

            if (arabian < RomeDigits.getValueByCharacter(charArray[i + 1])) {
                result -= arabian;
            } else {
                result += arabian;
            }
        }
        return result;

    }

    private static String convertArabicToRome(int arabicDigit) {
        List<RomeDigits> romanNumerals = RomeDigits.getReverseSortedValues();
        int i = 0;
        String result = "";
        while ((arabicDigit > 0) && (i < romanNumerals.size())) {
            RomeDigits currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= arabicDigit) {
                result = result + currentSymbol.name();
                arabicDigit -= currentSymbol.getValue();
            } else {
                i++;
            }
        }
        return result;
    }



    private static int calculateArabic(String firstDigit, String secondDigit, Operations operation) {
        int one = Integer.parseInt(firstDigit);
        int two = Integer.parseInt(secondDigit);
        int result = 0;
        switch (operation) {
            case PLUS:
                result = one + two;
                break;
            case MINUS:
                result = one - two;
                break;
            case DIVIDE:
                result = one / two;
                break;
            case MULTIPLY:
                result = one * two;
                break;
        }
        return result;
    }
}