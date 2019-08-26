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

    protected Fraction add(Fraction x, Fraction y) {
        int resultingNumerator = (x.getNumerator() * y.getDenominator()) + (y.getNumerator() * x.getDenominator());
        int resultingDenominator = x.getDenominator() * y.getDenominator();

        return Fraction.with(resultingNumerator, resultingDenominator);
    }

    protected Fraction subtract(Fraction x, Fraction y) {
        int resultingNumerator = (x.getNumerator() * y.getDenominator()) - (y.getNumerator() * x.getDenominator());
        int resultingDenominator = x.getDenominator() * y.getDenominator();

        return Fraction.with(resultingNumerator, resultingDenominator);
    }

    protected Fraction multiply(Fraction x, Fraction y) {
        int resultingNumerator = x.getNumerator() * y.getNumerator();
        int resultingDenominator = x.getDenominator() * y.getDenominator();

        return Fraction.with(resultingNumerator, resultingDenominator);
    }

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
