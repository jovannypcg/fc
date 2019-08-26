package mx.jovannypcg.fc.domain;

import mx.jovannypcg.fc.commons.Message;
import mx.jovannypcg.fc.exception.CalculatorException;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Represents a simple fraction with numerator and denominator.
 * Provides methos to parse strings into {@code Fraction}s.
 */
public class Fraction {
    private static final String INTEGER_PATTERN = "-?\\d+";
    private static final String SIMPLE_FRACTION_PATTERN = "-?\\d+/\\d+";
    private static final String MIXED_FRACTION_PATTERN = "-?\\d+_\\d+/\\d+";

    int numerator;
    int denominator;

    protected Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    /**
     * Factory method to create a {@code Fraction}.
     *
     * @param numerator The numerator.
     * @param denominator The denominator.
     * @return Fraction object.
     */
    public static Fraction with(int numerator, int denominator) {
        return new Fraction(numerator, denominator);
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    /**
     * Verifies if this {@code Fraction} has 0 as denominator. Might be helpful to
     * validate incoming fractions.
     *
     * @return {@code true} if the fraction has 0 as denominator. {@code false} otherwise.
     */
    public boolean hasZeroAsDenominator() {
        return denominator == 0;
    }

    /**
     * Verifies if this fraction is improper, which means that its numerator is greater than the denominator.
     *
     * @return {@code true} if this {@code Fraction} is improper. {@code false} otherwise.
     */
    public boolean isImproper() {
        return Math.abs(numerator) > denominator;
    }

    /**
     * Given a {@code String} representing a fraction, this method parses it to a simple fraction.
     * In other words, {@code String} representing integer, fraction or mixed fractions are converted into a
     * simple fraction with numerator and denominator only.
     *
     * The shape of the given string must be like: 1/2, -1/3, 2_1/4, 3, -1.
     *
     * @param operand String representing
     * @return Fraction object parsed from the given string.
     * @throws CalculatorException If anything goes wrong while parsing the String.
     */
    public static Fraction parse(String operand) throws CalculatorException {
        Fraction fraction;

        if (isInteger(operand)) {
            fraction = parseInteger(operand);
        } else if (isSimpleFraction(operand)) {
            fraction = parseSimpleFraction(operand);
        } else if (isMixedFraction(operand)) {
            fraction = parseMixedFraction(operand);
        } else {
            throw new CalculatorException(Message.parsingError(operand));
        }

        if (fraction.hasZeroAsDenominator()) {
            throw new CalculatorException(Message.zeroAsDenominatorFor(operand));
        }

        return fraction;
    }

    /**
     * Validates {@code operand} is representing an integer.
     *
     * @param operand String to validate.
     * @return {@code true} if the incoming string is representing an integer. {@code false} otherwise.
     */
    protected static boolean isInteger(String operand) {
        return Pattern.compile(INTEGER_PATTERN)
                .matcher(operand)
                .matches();
    }

    /**
     * Validates {@code operand} is representing a simple fraction with shape {@code <numerator>/<denominator>}.
     *
     * @param operand String to validate.
     * @return {@code true} if the incoming string is representing a simple fraction. {@code false} otherwise.
     */
    protected static boolean isSimpleFraction(String operand) {
        return Pattern.compile(SIMPLE_FRACTION_PATTERN)
                .matcher(operand)
                .matches();
    }

    /**
     * Validates {@code operand} is representing a mixed with shape
     * {@code <whole_number>(_<numerator>/<denominator>)(<numerator>/<denominator>)}.
     *
     * @param operand String to validate.
     * @return {@code true} if the incoming string is representing a mixed. {@code false} otherwise.
     */
    protected static boolean isMixedFraction(String operand) {
        return Pattern.compile(MIXED_FRACTION_PATTERN)
                .matcher(operand)
                .matches();
    }

    /**
     * Transforms a pure integer into a simple fraction with shape: 3 => 3/1
     *
     * @param operand The string operand to parse.
     * @return Fraction representation of the integer.
     */
    protected static Fraction parseInteger(String operand) {
        return new Fraction(Integer.parseInt(operand), 1);
    }

    /**
     * Transforms a string representing a simple fraction into a {@code Fraction}.
     *
     * @param operand The string operand to parse.
     * @return Fraction representation of the simple fraction.
     */
    protected static Fraction parseSimpleFraction(String operand) {
        String strNumerator = operand.substring(0, operand.indexOf('/'));
        String strDenominator = operand.substring(operand.indexOf('/') + 1);

        return new Fraction(Integer.parseInt(strNumerator), Integer.parseInt(strDenominator));
    }
    /**
     * Transforms a string representing a simple fraction into a {@code Fraction}.
     * Expected behaviour: 1_1/2 => 3/2
     *
     * @param operand The string operand to parse.
     * @return Fraction representation of the mixed fraction.
     */
    protected static Fraction parseMixedFraction(String operand) {
        String strWholeNumber = operand.substring(0, operand.indexOf('_'));
        String strNumerator = operand.substring(operand.indexOf('_') + 1, operand.indexOf('/'));
        String strDenominator = operand.substring(operand.indexOf('/') + 1);

        int nWholeNumber = Integer.parseInt(strWholeNumber);
        int nNumerator = Integer.parseInt(strNumerator);
        int nDenominator = Integer.parseInt(strDenominator);
        int compositeNumerator = nWholeNumber * nDenominator + nNumerator;

        return new Fraction(compositeNumerator, nDenominator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (!(that instanceof Fraction)) return false;

        Fraction thatFraction = (Fraction) that;

        return Objects.equals(this.numerator, thatFraction.numerator) &&
                Objects.equals(this.denominator, thatFraction.denominator);
    }

    @Override
    public String toString() {
        return denominator == 1 ? String.valueOf(numerator) : numerator + "/" + denominator;
    }
}
