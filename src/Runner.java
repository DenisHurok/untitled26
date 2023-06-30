import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Runner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //String input = sc.nextLine();
        String input ="toRubles(toDollars(60р) + toDollars(60р))";
        System.out.println(input);

        String regex = "^toDollars\\((.+)\\)";
        Pattern patternDol = Pattern.compile(regex);
        Matcher matcherDol = patternDol.matcher(input);

        String regexRub = "^toRubles\\((.+)\\)";
        Pattern patternRub = Pattern.compile(regexRub);
        Matcher matcherRub = patternRub.matcher(input);

        StringBuffer sb = new StringBuffer();
        try {
            if (matcherDol.find()) {
                String expression = matcherDol.group(1);
                String[] values = expression.split(" ");
                for (String str : values) {
                    if (str.matches("^toRubles\\((\\${1}\\d+(\\.\\d+)?)\\)")) {
                        str = str.replaceAll("[()a-zA-Z\\$]+", "");
                        sb.append(Calculator.toRubles(Double.parseDouble(str))).append(" ");
                    } else if (str.matches("\\d+(\\.\\d+)?р{1}")) {
                        sb.append(str.replace("р", "")).append(" ");
                    } else if (str.matches("^\\+{1}") || str.matches("^\\-{1}")) {
                        sb.append(str).append(" ");
                    } else {
                        throw new Exception();
                    }
                }
                System.out.printf("%.2f", Calculator.toDollars(Calculator.calculate(sb.toString())));
            } else if (matcherRub.find()) {
                String expression = matcherRub.group(1);
                String[] values = expression.split(" ");
                for (String str : values) {
                    if (str.matches("^toDollars\\((\\d+(\\.\\d+)?р{1})\\)")) {
                        str = str.replaceAll("[()a-zA-Zр]+", "");
                        sb.append(Calculator.toDollars(Double.parseDouble(str))).append(" ");
                    } else if (str.matches("\\${1}\\d+(\\.\\d+)?")) {
                        sb.append(str.replace("$", "")).append(" ");
                    } else if (str.matches("^\\+{1}") || str.matches("^\\-{1}")) {
                        sb.append(str).append(" ");
                    } else {
                        throw new Exception();
                    }
                }
                System.out.printf("%.2f", Calculator.toRubles(Calculator.calculate(sb.toString())));
            }

        } catch (NumberFormatException e) {
            System.out.println("ошибка ввода");
            throw new RuntimeException(e);

        } catch (Exception e) {
            System.out.println("ошибка ввода");
        }
    }
}
