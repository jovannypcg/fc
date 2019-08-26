package mx.jovannypcg.fc.validator;

import mx.jovannypcg.fc.commons.Message;
import mx.jovannypcg.fc.exception.CalculatorException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Provides methos to validate the incoming arguments for the application, like the size of the arguments,
 * the operator and operands.
 */
@Component
public class ArgumentValidator {
    public static final int ARGUMENTS_ALLOWED = 3;
    private static final String OPERAND_PATTERN = "-?\\d+(_\\d+/\\d+)?(/\\d+)?";
    private static final String OPERATOR_PATTERN = "[+\\-*/]"; // Any: +, -, /, *

    /**
     * Validates the incoming arguments according to {@link ArgumentValidator#validateArguments(String...)}.
     *
     * @param args Arguments to validate.
     * @throws CalculatorException If anything goes wrong while validating the arguments.
     */
    public void validate(String... args) throws CalculatorException {
        validateArguments(args);
    }

    /**
     * Verifies the order of the arguments is {@code <operand> <operator> <operand>} and
     * that the "operands" are valid according to #isValidOperand.
     */
    protected void validateArguments(String... args) throws CalculatorException {
        validateSize(args);

        // 3 arguments at this point
        String x = args[0];
        String operator = args[1];
        String y = args[2];

        if (!isValidOperand(x) || !isValidOperand(y) || !isValidOperator(operator)) {
            throw new CalculatorException(Message.badOrderOrFormat());
        }
    }

    protected void validateSize(String... args) throws CalculatorException {
        if (args.length != ARGUMENTS_ALLOWED) {
            throw new CalculatorException(Message.badArgumentSize(ARGUMENTS_ALLOWED));
        }
    }

    /**
     * Verifies the given String is a valid operator by validating its structure.
     *
     * @param operator String to validate.
     * @return {@code true} if the incoming String is a valid operator. {@code false} otherwise.
     */
    protected boolean isValidOperator(String operator) {
        return Pattern.compile(OPERATOR_PATTERN)
                .matcher(operator)
                .matches();
    }

    /**
     * Verifies the given String is a valid operand by validating its structure.
     *
     * @param strOperand String to validate.
     * @return {@code true} if the incoming String is a valid operand. {@code false} otherwise.
     */
    protected boolean isValidOperand(String strOperand) {
        return Pattern.compile(OPERAND_PATTERN)
                .matcher(strOperand)
                .matches();
    }
}
