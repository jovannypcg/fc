package mx.jovannypcg.fc.domain;

import mx.jovannypcg.fc.commons.Message;

import java.util.regex.Pattern;

public class SimpleFraction {
    private static final String INTEGER_PATTERN = "-?\\d+";
    private static final String SIMPLE_FRACTION_PATTERN = "-?\\d+\\/\\d+";
    private static final String MIXED_FRACTION_PATTERN = "-?\\d+_\\d+\\/\\d+";

    private int numerator;
    private int denominator;

    private SimpleFraction() {}

    private SimpleFraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public int getNumerator() {
        return numerator;
    }
    public int getDenominator() {
        return denominator;
    }

    public static SimpleFraction parse(String operand) {
        SimpleFraction simpleFraction;

        if (isInteger(operand)) {
            simpleFraction = parseInteger(operand);
        } else if (isSimpleFraction(operand)) {
            simpleFraction = parseSimpleFraction(operand);
        } else if (isMixedFraction(operand)) {
            simpleFraction = parseMixedFraction(operand);
        } else {
            throw new IllegalArgumentException(Message.parsingError(operand));
        }

        return simpleFraction;
    }

    protected static boolean isInteger(String operand) {
        return Pattern.compile(INTEGER_PATTERN)
                .matcher(operand)
                .matches();
    }

    protected static boolean isSimpleFraction(String operand) {
        return Pattern.compile(SIMPLE_FRACTION_PATTERN)
                .matcher(operand)
                .matches();
    }

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
}