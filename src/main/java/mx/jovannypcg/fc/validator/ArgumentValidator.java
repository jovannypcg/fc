package mx.jovannypcg.fc.validator;

import mx.jovannypcg.fc.commons.Message;
import mx.jovannypcg.fc.domain.Fraction;
import mx.jovannypcg.fc.domain.SimpleFraction;
import mx.jovannypcg.fc.exception.CalculatorException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ArgumentValidator {
    public static final int ARGUMENTS_ALLOWED = 3;
    private static final String OPERAND_PATTERN = "-?\\d+(_\\d+/\\d+)?(/\\d+)?";
    private static final String OPERATOR_PATTERN = "[+\\-*/]"; // Any: +, -, /, *

    private String validationOutcome;
    private boolean valid;

    public boolean isValid() {
        return valid;
    }

    public String getOutcome() {
        return validationOutcome;
    }

    public void validate(String... args) {
        try {
            validateArguments(args);
            // Everything went well
            valid = true;
        } catch(CalculatorException ce) {
            validationOutcome = ce.getMessage();
        } catch (Exception ex) {
            validationOutcome = "Unexpected exception occurred while performing operation";
        }
    }

    protected void validateSize(String... args) throws CalculatorException {
        if (args.length != ARGUMENTS_ALLOWED) {
            throw new CalculatorException(Message.badArgumentSize(ARGUMENTS_ALLOWED));
        }
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

    protected boolean isValidOperator(String operator) {
        return Pattern.compile(OPERATOR_PATTERN)
                .matcher(operator)
                .matches();
    }

    protected boolean isValidOperand(String strOperand) {
        return Pattern.compile(OPERAND_PATTERN)
                .matcher(strOperand)
                .matches();
    }
}
