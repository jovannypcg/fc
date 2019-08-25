package mx.jovannypcg.fc.commons;

public class Message {
    public static String usage(String failureReason) {
        return  "Usage: java -jar fc.jar <operand1> <operator> <operand2>\n" +
                "Failure: " + failureReason + "\n" +
                "Example: java -jar fc.jar 1/2 * 3_3/4\n" +
                "Operators:\n" +
                "        *: multiply\n" +
                "        /: divide\n" +
                "        +: add\n" +
                "        -: subtract";
    }

    public static String badOrderOrFormat() {
        return  "Verify either the order of the operator and operands is correct or " +
                "the format of the operands";
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
