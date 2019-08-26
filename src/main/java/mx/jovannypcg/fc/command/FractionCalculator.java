package mx.jovannypcg.fc.command;

import mx.jovannypcg.fc.domain.MixedFraction;
import mx.jovannypcg.fc.domain.Fraction;
import mx.jovannypcg.fc.exception.CalculatorException;
import mx.jovannypcg.fc.validator.ArgumentValidator;
import org.springframework.stereotype.Component;

@Component
public class FractionCalculator {
    private ArgumentValidator argumentValidator;

    public FractionCalculator(ArgumentValidator argumentValidator) {
        this.argumentValidator = argumentValidator;
    }

    /**
     * Execute the fraction operation based on the operator sent as argument.
     * Operations are executed using simple fractions only, which means that integers or mixed numbers
     * are first converted into simple fractions.
     *
     * The order is: operator1, operator, operator2.
     *
     * The arguments are validated by {@link ArgumentValidator}.
     *
     * @param args Operands and operator to execute the fraction operation.
     * @return Fraction result based on the given operator: +, -, *, /.
     * @throws CalculatorException If anything goes wrong while validationg the arguments.
     */
    public Fraction perform(String... args) throws CalculatorException {
        argumentValidator.validate(args);

        String xOperand = args[0];
        String yOperand = args[2];
        char operator = args[1].charAt(0);

        Fraction x = Fraction.parse(xOperand);
        Fraction y = Fraction.parse(yOperand);
        Fraction simpleResult;

        switch (operator) {
            case '+':
                simpleResult = add(x, y);
                break;
            case '-':
                simpleResult = subtract(x, y);
                break;
            case '*':
                simpleResult = multiply(x, y);
                break;
            case '/':
                simpleResult = divide(x, y);
                break;
            default:
                throw new CalculatorException("Unknown operator \"" + operator + "\"");

        }

        return simpleResult.hasZeroAsDenominator() ? simpleResult : simplify(simpleResult);
    }

    /**
     * Add {@code x} to {@code y}.
     *
     * @param x First operand for the operation.
     * @param y Second operand for the operation.
     * @return Result of the Add operation.
     */
    protected Fraction add(Fraction x, Fraction y) {
        int resultingNumerator = (x.getNumerator() * y.getDenominator()) + (y.getNumerator() * x.getDenominator());
        int resultingDenominator = x.getDenominator() * y.getDenominator();

        return Fraction.with(resultingNumerator, resultingDenominator);
    }

    /**
     * Subtract {@code y} from {@code x}.
     *
     * @param x First operand for the operation.
     * @param y Second operand for the operation.
     * @return Result of the Subtract operation.
     */
    protected Fraction subtract(Fraction x, Fraction y) {
        int resultingNumerator = (x.getNumerator() * y.getDenominator()) - (y.getNumerator() * x.getDenominator());
        int resultingDenominator = x.getDenominator() * y.getDenominator();

        return Fraction.with(resultingNumerator, resultingDenominator);
    }

    /**
     * Multiply {@code x} by {@code y}.
     *
     * @param x First operand for the operation.
     * @param y Second operand for the operation.
     * @return Result of the Multiply operation.
     */
    protected Fraction multiply(Fraction x, Fraction y) {
        int resultingNumerator = x.getNumerator() * y.getNumerator();
        int resultingDenominator = x.getDenominator() * y.getDenominator();

        return Fraction.with(resultingNumerator, resultingDenominator);
    }

    /**
     * Divide {@code x} by {@code y}.
     *
     * @param x First operand for the operation.
     * @param y Second operand for the operation.
     * @return Result of the Divide operation.
     */
    protected Fraction divide(Fraction x, Fraction y) {
        int resultingNumerator = x.getNumerator() * y.getDenominator();
        int resultingDenominator = x.getDenominator() * y.getNumerator();

        if (resultingNumerator < 0 && resultingDenominator < 0) {
            resultingNumerator = Math.abs(resultingNumerator);
            resultingDenominator = Math.abs(resultingDenominator);
        } else if (resultingDenominator < 0) {
            resultingDenominator = Math.abs(resultingDenominator);
            resultingNumerator = -resultingNumerator;
        }

        return Fraction.with(resultingNumerator, resultingDenominator);
    }

    /**
     * Returns the greatest common factor for {@code a} and {@code b} using the
     * { @link https://en.wikipedia.org/wiki/Greatest_common_divisor Euclid's Algorithm}.
     * Necessary to simplify fractions.
     *
     * @return Greatest common factor for {@code a} and {@code b}.
     */
    protected int greatestCommonFactor(int a, int b) {
        return b == 0 ? Math.abs(a) : Math.abs(greatestCommonFactor(b, a % b));
    }

    /**
     * Gets a simplified version of the given fraction. The result can be a {@link Fraction} or a {@link MixedFraction},
     * according to the rules to simplify fractions. For instance, if {@code fraction} is improper, then a
     * mixed fraction is return, otherwise the numerator and denomitor of the proper fraction are divided by their
     * {@link FractionCalculator#greatestCommonFactor(int, int)}.
     *
     * @param fraction Fraction to simplify.
     * @return Simplified version of the incoming fraction.
     */
    protected Fraction simplify(Fraction fraction) {
        int gcf = greatestCommonFactor(fraction.getNumerator(), fraction.getDenominator());
        int simplifiedNumerator = fraction.getNumerator() / gcf;
        int simplifiedDenominator = fraction.getDenominator() / gcf;

        Fraction simplified = Fraction.with(simplifiedNumerator, simplifiedDenominator);

        if (simplified.isImproper()) {
            simplified = MixedFraction.parse(simplified);
        }

        return simplified;
    }
}
