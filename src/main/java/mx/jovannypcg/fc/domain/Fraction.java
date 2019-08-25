package mx.jovannypcg.fc.domain;

import java.util.regex.Pattern;

public abstract class Fraction {
    private static final String INTEGER_PATTERN = "-?\\d+";
    private static final String SIMPLE_FRACTION_PATTERN = "-?\\d+\\/\\d+";
    private static final String MIXED_FRACTION_PATTERN = "-?\\d+_\\d+\\/\\d+";

    int numerator;
    int denominator;

    protected Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public boolean hasZeroAsDenominator() {
        return denominator == 0;
    }

    public boolean isImproper() {
        return Math.abs(numerator) > denominator;
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

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }
}
