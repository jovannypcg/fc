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
public class SimpleFractionTests {
    @Test
    public void parseInteger_shouldReturnSimpleFractionWithPositiveOperand() {
        SimpleFraction simpleFraction = SimpleFraction.parseInteger("23");

        assertThat(simpleFraction)
                .hasFieldOrPropertyWithValue("numerator", 23)
                .hasFieldOrPropertyWithValue("denominator", 1);
    }

    @Test
    public void parseInteger_shouldReturnSimpleFractionWithNegativeOperand() {
        SimpleFraction simpleFraction = SimpleFraction.parseInteger("-7");

        assertThat(simpleFraction)
                .hasFieldOrPropertyWithValue("numerator", -7)
                .hasFieldOrPropertyWithValue("denominator", 1);
    }

    @Test
    public void parseSimpleFraction_shouldReturnSimpleFractionWithPositiveOperand() {
        SimpleFraction simpleFraction = SimpleFraction.parseSimpleFraction("8/9");

        assertThat(simpleFraction)
                .hasFieldOrPropertyWithValue("numerator", 8)
                .hasFieldOrPropertyWithValue("denominator", 9);
    }

    @Test
    public void parseSimpleFraction_shouldReturnSimpleFractionWithNegativeOperand() {
        SimpleFraction simpleFraction = SimpleFraction.parseSimpleFraction("-3/4");

        assertThat(simpleFraction)
                .hasFieldOrPropertyWithValue("numerator", -3)
                .hasFieldOrPropertyWithValue("denominator", 4);
    }

    @Test
    public void parseMixedFraction_shouldReturnSimpleFractionWithPositiveOperand() {
        SimpleFraction simpleFraction = SimpleFraction.parseMixedFraction("3_2/5");

        int expectedNumerator = 3 * 5 + 2;

        assertThat(simpleFraction)
                .hasFieldOrPropertyWithValue("numerator", expectedNumerator)
                .hasFieldOrPropertyWithValue("denominator", 5);
    }

    @Test
    public void parseMixedFraction_shouldReturnSimpleFractionWithNegativeOperand() {
        SimpleFraction simpleFraction = SimpleFraction.parseMixedFraction("-7_5/8");

        int expectedNumerator = -7 * 8 + 5;

        assertThat(simpleFraction)
                .hasFieldOrPropertyWithValue("numerator", expectedNumerator)
                .hasFieldOrPropertyWithValue("denominator", 8);
    }

    @Test
    public void parse_shouldThrowIllegalArgumentExceptionWhenInvalidOperand() {
        assertThatExceptionOfType(CalculatorException.class).isThrownBy(() -> {
            SimpleFraction.parse("-x_y/z");
        });
    }

    @Test
    public void parse_shouldReturnSimpleFractionWithPositiveInteger() throws Exception {
        SimpleFraction simpleFraction = SimpleFraction.parse("8");

        assertThat(simpleFraction)
                .hasFieldOrPropertyWithValue("numerator", 8)
                .hasFieldOrPropertyWithValue("denominator", 1);
    }

    @Test
    public void parse_shouldReturnSimpleFractionWithNegativeInteger() throws Exception {
        SimpleFraction simpleFraction = SimpleFraction.parse("-41");

        assertThat(simpleFraction)
                .hasFieldOrPropertyWithValue("numerator", -41)
                .hasFieldOrPropertyWithValue("denominator", 1);
    }

    @Test
    public void parse_shouldReturnSimpleFractionWithPositiveOperandSimpleFraction() throws Exception {
        SimpleFraction simpleFraction = SimpleFraction.parse("8/9");

        assertThat(simpleFraction)
                .hasFieldOrPropertyWithValue("numerator", 8)
                .hasFieldOrPropertyWithValue("denominator", 9);
    }

    @Test
    public void parse_shouldReturnSimpleFractionWithNegativeSimpleFraction() throws Exception {
        SimpleFraction simpleFraction = SimpleFraction.parse("-3/4");

        assertThat(simpleFraction)
                .hasFieldOrPropertyWithValue("numerator", -3)
                .hasFieldOrPropertyWithValue("denominator", 4);
    }

    @Test
    public void parse_shouldReturnSimpleFractionWithPositiveMixedFraction() throws Exception {
        SimpleFraction simpleFraction = SimpleFraction.parse("3_2/5");

        int expectedNumerator = 3 * 5 + 2;

        assertThat(simpleFraction)
                .hasFieldOrPropertyWithValue("numerator", expectedNumerator)
                .hasFieldOrPropertyWithValue("denominator", 5);
    }

    @Test
    public void parse_shouldReturnSimpleFractionWithNegativeMixedFraction() throws Exception {
        SimpleFraction simpleFraction = SimpleFraction.parse("-7_5/8");

        int expectedNumerator = -7 * 8 + 5;

        assertThat(simpleFraction)
                .hasFieldOrPropertyWithValue("numerator", expectedNumerator)
                .hasFieldOrPropertyWithValue("denominator", 8);
    }
}
