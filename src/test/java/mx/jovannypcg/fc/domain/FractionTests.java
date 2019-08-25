package mx.jovannypcg.fc.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FractionTests {
    @Test
    public void isImproper_shouldReturnTrue() {
        int numerator = 7;
        int denominator = 5;

        assertThat(SimpleFraction.with(numerator, denominator).isImproper()).isTrue();
    }

    @Test
    public void isImproper_shouldReturnFalse() {
        int numerator = 5;
        int denominator = 7;

        assertThat(SimpleFraction.with(numerator, denominator).isImproper()).isFalse();
    }

    @Test
    public void hasZeroAsDenominator_shouldReturnTrue() {
        int numerator = 2;
        int denominator = 0;

        assertThat(SimpleFraction.with(numerator, denominator).hasZeroAsDenominator()).isTrue();
    }

    @Test
    public void hasZeroAsDenominator_shouldReturnFalse() {
        int numerator = 2;
        int denominator = 3;

        assertThat(SimpleFraction.with(numerator, denominator).hasZeroAsDenominator()).isFalse();
    }

    @Test
    public void isInteger_shouldReturnTrueDueToValidIntegerOperand() {
        String[] operands = { "3", "-242", "8", "-1234" };

        for (String operand : operands) {
            assertThat(SimpleFraction.isInteger(operand)).isTrue();
        }
    }

    @Test
    public void isInteger_shouldReturnTrueDueToNoIntegerOperand() {
        String[] operands = { "3/3", "abc", "7_2/4", "-13/8" };

        for (String operand : operands) {
            assertThat(SimpleFraction.isInteger(operand)).isFalse();
        }
    }

    @Test
    public void isSimpleFraction_shouldReturnTrueDueToValidSimpleFractionOperand() {
        String[] operands = { "2/4", "-7/2", "12/15", "-1/8" };

        for (String operand : operands) {
            assertThat(SimpleFraction.isSimpleFraction(operand)).isTrue();
        }
    }

    @Test
    public void isSimpleFraction_shouldReturnFalseDueToNotSimpleFractionOperand() {
        String[] operands = { "2_3/4", "-a/2", "34" };

        for (String operand : operands) {
            assertThat(SimpleFraction.isSimpleFraction(operand)).isFalse();
        }
    }

    @Test
    public void isMixedFraction_shouldReturnTrueDueToValidMixedFractionOperand() {
        String[] operands = { "2_7/3", "-5_2/5", "14_8/9" };

        for (String operand : operands) {
            assertThat(SimpleFraction.isMixedFraction(operand)).isTrue();
        }
    }

    @Test
    public void isMixedFraction_shouldReturnFalseDueToNotMixedFractionOperand() {
        String[] operands = { "3", "-a_4/5", "7/8" };

        for (String operand : operands) {
            assertThat(SimpleFraction.isMixedFraction(operand)).isFalse();
        }
    }
}
