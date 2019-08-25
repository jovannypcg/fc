package mx.jovannypcg.fc.domain;

import mx.jovannypcg.fc.commons.Message;
import mx.jovannypcg.fc.exception.CalculatorException;

import java.util.Objects;

public class SimpleFraction extends Fraction {
    private SimpleFraction(int numerator, int denominator) {
        super(numerator, denominator);
    }

    public static SimpleFraction with(int numerator, int denominator) {
        return new SimpleFraction(numerator, denominator);
    }

    public static SimpleFraction parse(String operand) throws CalculatorException {
        SimpleFraction simpleFraction;

        if (isInteger(operand)) {
            simpleFraction = parseInteger(operand);
        } else if (isSimpleFraction(operand)) {
            simpleFraction = parseSimpleFraction(operand);
        } else if (isMixedFraction(operand)) {
            simpleFraction = parseMixedFraction(operand);
        } else {
            throw new CalculatorException(Message.parsingError(operand));
        }

        if (simpleFraction.hasZeroAsDenominator()) {
            throw new CalculatorException(Message.zeroAsDenominatorFor(operand));
        }

        return simpleFraction;
    }

    /**
     * Transforms a pure integer into a simple fraction with shape: 3 => 3/1
     *
     * @param operand The string operand to parse.
     * @return Fraction representation of the integer.
     */
    protected static SimpleFraction parseInteger(String operand) {
        return new SimpleFraction(Integer.parseInt(operand), 1);
    }

    protected static SimpleFraction parseSimpleFraction(String operand) {
        String strNumerator = operand.substring(0, operand.indexOf('/'));
        String strDenominator = operand.substring(operand.indexOf('/') + 1);

        return new SimpleFraction(Integer.parseInt(strNumerator), Integer.parseInt(strDenominator));
    }

    protected static SimpleFraction parseMixedFraction(String operand) {
        String strWholeNumber = operand.substring(0, operand.indexOf('_'));
        String strNumerator = operand.substring(operand.indexOf('_') + 1, operand.indexOf('/'));
        String strDenominator = operand.substring(operand.indexOf('/') + 1);

        int nWholeNumber = Integer.parseInt(strWholeNumber);
        int nNumerator = Integer.parseInt(strNumerator);
        int nDenominator = Integer.parseInt(strDenominator);
        int compositeNumerator = nWholeNumber * nDenominator + nNumerator;

        return new SimpleFraction(compositeNumerator, nDenominator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (!(that instanceof SimpleFraction)) return false;

        SimpleFraction thatFraction = (SimpleFraction) that;

        return Objects.equals(this.numerator, thatFraction.numerator) &&
                Objects.equals(this.denominator, thatFraction.denominator);
    }
}
