import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {
static Scanner sc;

    static {
        try {
            sc = new Scanner(new FileReader("config.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static final Double EXRATE = sc.nextDouble();
    public Calculator() throws FileNotFoundException {
    }

    public static String reverse(String input) { // обратная польская запись
        if(input.isEmpty()){
            input="0";
        }
        String[] tokens = input.split(" ");
        Stack<String> stack = new Stack<>();
        StringBuilder output = new StringBuilder();

        for (String token : tokens) {
            if (isOperator(token)) {
                while (!stack.isEmpty() && isOperator(stack.peek())) {
                    output.append(stack.pop()).append(" ");
                }
                stack.push(token);
            } else if (isNumber(token)) {
                output.append(token).append(" ");
            }
        }
        while (!stack.isEmpty()) {
            output.append(stack.pop()).append(" ");
        }
        return (output.toString());
    }
    public static Double calculate(String input) { // подсчет полськой записи
        String[] tokens = reverse(input).split(" ");
        Stack<Double> stack = new Stack<>();
        for (String token : tokens) {
            if (token.matches("\\d+(\\.\\d+)?")) {
                stack.push(Double.parseDouble(token));
            } else {
                Double operand2 = stack.pop();
                Double operand1 = stack.pop();
                Double result = 0.0;

                switch (token) {
                    case "+":
                        result = operand1 + operand2;
                        break;
                    case "-":
                        result = operand1 - operand2;
                        break;
                    case "*":
                        result = operand1 * operand2;
                        break;
                    case "/":
                        result = operand1 / operand2;
                        break;
                }

                stack.push(result);
            }
        }

        return stack.pop();
    }

    private static Boolean isOperator(String token) {
        return token.equals("+") || token.equals("-");
    }

    private static Boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Double toRubles(Double dollars) {
        return dollars * EXRATE;
    }

    public static Double toDollars(Double rubles) {
        return rubles / EXRATE;
    }

}
