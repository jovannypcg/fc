package mx.jovannypcg.fc.domain;

import mx.jovannypcg.fc.exception.CalculatorException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FractionTests {
    @Test
    public void isImproper_shouldReturnTrue() {
        int numerator = 7;
        int denominator = 5;

        assertThat(Fraction.with(numerator, denominator).isImproper()).isTrue();
    }

    @Test
    public void isImproper_shouldReturnFalse() {
        int numerator = 5;
        int denominator = 7;

        assertThat(Fraction.with(numerator, denominator).isImproper()).isFalse();
    }

    @Test
    public void hasZeroAsDenominator_shouldReturnTrue() {
        int numerator = 2;
        int denominator = 0;

        assertThat(Fraction.with(numerator, denominator).hasZeroAsDenominator()).isTrue();
    }

    @Test
    public void hasZeroAsDenominator_shouldReturnFalse() {
        int numerator = 2;
        int denominator = 3;

        assertThat(Fraction.with(numerator, denominator).hasZeroAsDenominator()).isFalse();
    }

    @Test
    public void isInteger_shouldReturnTrueDueToValidIntegerOperand() {
        String[] operands = { "3", "-242", "8", "-1234" };

        for (String operand : operands) {
            assertThat(Fraction.isInteger(operand)).isTrue();
        }
    }

    @Test
    public void isInteger_shouldReturnTrueDueToNoIntegerOperand() {
        String[] operands = { "3/3", "abc", "7_2/4", "-13/8" };

        for (String operand : operands) {
            assertThat(Fraction.isInteger(operand)).isFalse();
        }
    }

    @Test
    public void isSimpleFraction_shouldReturnTrueDueToValidSimpleFractionOperand() {
        String[] operands = { "2/4", "-7/2", "12/15", "-1/8" };

        for (String operand : operands) {
            assertThat(Fraction.isSimpleFraction(operand)).isTrue();
        }
    }

    @Test
    public void isSimpleFraction_shouldReturnFalseDueToNotSimpleFractionOperand() {
        String[] operands = { "2_3/4", "-a/2", "34" };

        for (String operand : operands) {
            assertThat(Fraction.isSimpleFraction(operand)).isFalse();
        }
    }

    @Test
    public void isMixedFraction_shouldReturnTrueDueToValidMixedFractionOperand() {
        String[] operands = { "2_7/3", "-5_2/5", "14_8/9" };

        for (String operand : operands) {
            assertThat(Fraction.isMixedFraction(operand)).isTrue();
        }
    }

    @Test
    public void isMixedFraction_shouldReturnFalseDueToNotMixedFractionOperand() {
        String[] operands = { "3", "-a_4/5", "7/8" };

        for (String operand : operands) {
            assertThat(Fraction.isMixedFraction(operand)).isFalse();
        }
    }

    @Test
    public void parseInteger_shouldReturnSimpleFractionWithPositiveOperand() {
        Fraction fraction = Fraction.parseInteger("23");

        assertThat(fraction)
                .hasFieldOrPropertyWithValue("numerator", 23)
                .hasFieldOrPropertyWithValue("denominator", 1);
    }

    @Test
    public void parseInteger_shouldReturnSimpleFractionWithNegativeOperand() {
        Fraction fraction = Fraction.parseInteger("-7");

        assertThat(fraction)
                .hasFieldOrPropertyWithValue("numerator", -7)
                .hasFieldOrPropertyWithValue("denominator", 1);
    }

    @Test
    public void parseSimpleFraction_shouldReturnSimpleFractionWithPositiveOperand() {
        Fraction fraction = Fraction.parseSimpleFraction("8/9");

        assertThat(fraction)
                .hasFieldOrPropertyWithValue("numerator", 8)
                .hasFieldOrPropertyWithValue("denominator", 9);
    }

    @Test
    public void parseSimpleFraction_shouldReturnSimpleFractionWithNegativeOperand() {
        Fraction fraction = Fraction.parseSimpleFraction("-3/4");

        assertThat(fraction)
                .hasFieldOrPropertyWithValue("numerator", -3)
                .hasFieldOrPropertyWithValue("denominator", 4);
    }

    @Test
    public void parseMixedFraction_shouldReturnSimpleFractionWithPositiveOperand() {
        Fraction fraction = Fraction.parseMixedFraction("3_2/5");

        int expectedNumerator = 3 * 5 + 2;

        assertThat(fraction)
                .hasFieldOrPropertyWithValue("numerator", expectedNumerator)
                .hasFieldOrPropertyWithValue("denominator", 5);
    }

    @Test
    public void parseMixedFraction_shouldReturnSimpleFractionWithNegativeOperand() {
        Fraction fraction = Fraction.parseMixedFraction("-7_5/8");

        int expectedNumerator = -7 * 8 + 5;

        assertThat(fraction)
                .hasFieldOrPropertyWithValue("numerator", expectedNumerator)
                .hasFieldOrPropertyWithValue("denominator", 8);
    }

    @Test
    public void parse_shouldThrowIllegalArgumentExceptionWhenInvalidOperand() {
        assertThatExceptionOfType(CalculatorException.class).isThrownBy(() -> {
            Fraction.parse("-x_y/z");
        });
    }

    @Test
    public void parse_shouldReturnSimpleFractionWithPositiveInteger() throws Exception {
        Fraction fraction = Fraction.parse("8");

        assertThat(fraction)
                .hasFieldOrPropertyWithValue("numerator", 8)
                .hasFieldOrPropertyWithValue("denominator", 1);
    }

    @Test
    public void parse_shouldReturnSimpleFractionWithNegativeInteger() throws Exception {
        Fraction fraction = Fraction.parse("-41");

        assertThat(fraction)
                .hasFieldOrPropertyWithValue("numerator", -41)
                .hasFieldOrPropertyWithValue("denominator", 1);
    }

    @Test
    public void parse_shouldReturnSimpleFractionWithPositiveOperandSimpleFraction() throws Exception {
        Fraction fraction = Fraction.parse("8/9");

        assertThat(fraction)
                .hasFieldOrPropertyWithValue("numerator", 8)
                .hasFieldOrPropertyWithValue("denominator", 9);
    }

    @Test
    public void parse_shouldReturnSimpleFractionWithNegativeSimpleFraction() throws Exception {
        Fraction fraction = Fraction.parse("-3/4");

        assertThat(fraction)
                .hasFieldOrPropertyWithValue("numerator", -3)
                .hasFieldOrPropertyWithValue("denominator", 4);
    }

    @Test
    public void parse_shouldReturnSimpleFractionWithPositiveMixedFraction() throws Exception {
        Fraction fraction = Fraction.parse("3_2/5");

        int expectedNumerator = 3 * 5 + 2;

        assertThat(fraction)
                .hasFieldOrPropertyWithValue("numerator", expectedNumerator)
                .hasFieldOrPropertyWithValue("denominator", 5);
    }

    @Test
    public void parse_shouldReturnSimpleFractionWithNegativeMixedFraction() throws Exception {
        Fraction fraction = Fraction.parse("-7_5/8");

        int expectedNumerator = -7 * 8 + 5;

        assertThat(fraction)
                .hasFieldOrPropertyWithValue("numerator", expectedNumerator)
                .hasFieldOrPropertyWithValue("denominator", 8);
    }
}
