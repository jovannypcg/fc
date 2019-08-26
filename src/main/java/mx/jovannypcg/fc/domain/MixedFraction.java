package mx.jovannypcg.fc.domain;

import java.util.Objects;

public class MixedFraction extends Fraction {
    private int wholeNumber;

    public int getWholeNumber() {
        return wholeNumber;
    }

    public static MixedFraction with(int wholeNumber, int numerator, int denominator) {
        return new MixedFraction(wholeNumber, numerator, denominator);
    }

    private MixedFraction(int wholeNumber, int numerator, int denominator) {
        super(numerator, denominator);
        this.wholeNumber = wholeNumber;
    }

    public static MixedFraction parse(Fraction fraction) {
        if (!fraction.isImproper()) {
            return new MixedFraction(0, fraction.numerator, fraction.denominator);
        }

        int wholeNumber = fraction.numerator / fraction.denominator;

        // whole number keeps the negative sign
        int numerator = Math.abs(fraction.numerator % fraction.denominator);
        int denominator = Math.abs(fraction.denominator);

        return new MixedFraction(wholeNumber, numerator, denominator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator, wholeNumber);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (!(that instanceof MixedFraction)) return false;

        MixedFraction thatFraction = (MixedFraction) that;

        return Objects.equals(this.numerator, thatFraction.numerator) &&
                Objects.equals(this.denominator, thatFraction.denominator) &&
                Objects.equals(this.wholeNumber, thatFraction.wholeNumber);
    }

    @Override
    public String toString() {
        String wholeNumberPrefix = wholeNumber != 0 ? wholeNumber + "_" : "";

        if (wholeNumber == 0) {
            return super.toString(); // Only numerator and denominator
        } else if (numerator == 0) {
            return String.valueOf(wholeNumber); // Integer without fraction
        } else {
            return wholeNumberPrefix + super.toString();
        }
    }
}
