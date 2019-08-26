package mx.jovannypcg.fc.commons;

public class Message {
    public static String usage(String failureReason) {
        return  "Failure: " + failureReason + "\n" +
                "Usage: java -jar fc.jar <operand1> <operator> <operand2>\n" +
                "Example: java -jar fc.jar 1/2 * 3_3/4\n" +
                "Operators:\n" +
                "        \\*: multiply\n" +
                "        /: divide\n" +
                "        +: add\n" +
                "        -: subtract\n" +
                "Operand format: (sign)<whole_number>(_<numerator>/<denominator>)(numerator/denominator)\n" +
                "        Examples: -2, 3, 1/2, -7/9, 4_1/2, -5_7/4";
    }

    public static String badOrderOrFormat() {
        return  "Verify either the order of the operator and operands or the format of the operands";
    }

    public static String badArgumentSize(int expectedArguments) {
        return "There must be exactly " + expectedArguments + " arguments";
    }

    public static String parsingError(String operand) {
        return "Error while parsing operand " + operand;
    }

    public static String resultOutput(String[] args, String result) {
        StringBuilder sb = new StringBuilder();

        for (String arg : args) {
            sb.append(arg).append(" ");
        }

        sb.append("= ").append(result);
        return sb.toString();
    }

    public static String zeroAsDenominatorFor(String operand) {
        return "Improper format for operand " + operand + ", 0 in the denominator";
    }
}
