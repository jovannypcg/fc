package mx.jovannypcg.fc.command;

import mx.jovannypcg.fc.commons.Message;
import mx.jovannypcg.fc.domain.SimpleFraction;
import mx.jovannypcg.fc.exception.CalculatorException;
import mx.jovannypcg.fc.validator.ArgumentValidator;
import org.springframework.stereotype.Component;

@Component
public class CalculatorCommand {
    private ArgumentValidator argumentValidator;

    public CalculatorCommand(ArgumentValidator argumentValidator) {
        this.argumentValidator = argumentValidator;
    }

    public void perform(String... args) throws CalculatorException {
        argumentValidator.validate(args);

        if (!argumentValidator.isValid()) {
            System.out.println(Message.usage(argumentValidator.getOutcome()));
            return;
        }

        String xOperand = args[0];
        String yOperand = args[2];
        char operator = args[1].charAt(0);

        SimpleFraction x = SimpleFraction.parse(xOperand);
        SimpleFraction y = SimpleFraction.parse(yOperand);
        SimpleFraction result;

        switch (operator) {
            case '+':
                result = add(x, y);
                break;
            case '-':
                result = subtract(x, y);
                break;
            case '*':
                result = multiply(x, y);
                break;
            case '/':
                result = divide(x, y);
                break;
            default:
                throw new CalculatorException("Unknown operator \"" + operator + "\"");

        }

        System.out.println("===> " + xOperand + " " + operator + " " + yOperand + " = " + result);
    }

    protected SimpleFraction add(SimpleFraction x, SimpleFraction y) {
        int resultingNumerator = (x.getNumerator() * y.getDenominator()) + (y.getNumerator() * x.getDenominator());
        int resultingDenominator = x.getDenominator() * y.getDenominator();

        return SimpleFraction.with(resultingNumerator, resultingDenominator);
    }

    protected SimpleFraction subtract(SimpleFraction x, SimpleFraction y) {
        int resultingNumerator = (x.getNumerator() * y.getDenominator()) - (y.getNumerator() * x.getDenominator());
        int resultingDenominator = x.getDenominator() * y.getDenominator();

        return SimpleFraction.with(resultingNumerator, resultingDenominator);
    }

    protected SimpleFraction multiply(SimpleFraction x, SimpleFraction y) {
        int resultingNumerator = x.getNumerator() * y.getNumerator();
        int resultingDenominator = x.getDenominator() * y.getDenominator();

        return SimpleFraction.with(resultingNumerator, resultingDenominator);
    }

    protected SimpleFraction divide(SimpleFraction x, SimpleFraction y) {
        int resultingNumerator = x.getNumerator() * y.getDenominator();
        int resultingDenominator = x.getDenominator() * y.getNumerator();

        if (resultingNumerator < 0 && resultingDenominator < 0) {
            resultingNumerator = Math.abs(resultingNumerator);
            resultingDenominator = Math.abs(resultingDenominator);
        } else if (resultingDenominator < 0) {
            resultingDenominator = Math.abs(resultingDenominator);
            resultingNumerator = -resultingNumerator;
        }

        return SimpleFraction.with(resultingNumerator, resultingDenominator);
    }

    /**
     * Returns the greatest common factor for {@code a} and {@code b} using the
     * { @link https://en.wikipedia.org/wiki/Greatest_common_divisor Euclid's Algorithm}.
     * Necessary to simplify fractions.
     *
     * @return Greatest common factor for {@code a} and {@code b}.
     */
    protected int GCD(int a, int b) {
        return b == 0 ? a : GCD(b, a % b);
    }
}
