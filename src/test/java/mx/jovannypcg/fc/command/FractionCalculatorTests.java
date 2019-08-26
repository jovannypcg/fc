package mx.jovannypcg.fc.command;

import mx.jovannypcg.fc.domain.Fraction;
import mx.jovannypcg.fc.domain.MixedFraction;
import mx.jovannypcg.fc.validator.ArgumentValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FractionCalculatorTests {
    private Fraction[] xs; // First operand
    private Fraction[] ys; // Second operand

    @Mock
    private ArgumentValidator argumentValidator;
    private FractionCalculator fractionCalculator;

    @Before
    public void init() {
        xs = new Fraction[]{
                Fraction.with(2, 3),
                Fraction.with(17, 11),
                Fraction.with(-11, 8),
                Fraction.with(8, 9),
                Fraction.with(-9, 3) };

        ys = new Fraction[]{
                Fraction.with(9, 11),
                Fraction.with(-3, 15),
                Fraction.with(-11, 8),
                Fraction.with(9, 8),
                Fraction.with(7, 3) };

        fractionCalculator = new FractionCalculator(argumentValidator);
        Mockito.when(argumentValidator.isValid()).thenReturn(true);
    }

    @Test
    public void add_shouldReturnExpectedResults() {
        Fraction[] addResults = new Fraction[] {
                Fraction.with(49, 33),
                Fraction.with(222, 165),
                Fraction.with(-176, 64),
                Fraction.with(145, 72),
                Fraction.with(-6, 9),
        };

        for (int i = 0; i < xs.length; i++) { // or ys.length
            Fraction addResult = fractionCalculator.add(xs[i], ys[i]);

            assertThat(addResult).isEqualTo(addResults[i]);
        }
    }

    @Test
    public void subtract_shouldReturnExpectedResults() {
        Fraction[] addResults = new Fraction[] {
                Fraction.with(-5, 33),
                Fraction.with(288, 165),
                Fraction.with(0, 64),
                Fraction.with(-17, 72),
                Fraction.with(-48, 9),
        };

        for (int i = 0; i < xs.length; i++) { // or ys.length
            Fraction addResult = fractionCalculator.subtract(xs[i], ys[i]);

            assertThat(addResult).isEqualTo(addResults[i]);
        }
    }

    @Test
    public void multiply_shouldReturnExpectedResults() {
        Fraction[] addResults = new Fraction[] {
                Fraction.with(18, 33),
                Fraction.with(-51, 165),
                Fraction.with(121, 64),
                Fraction.with(72, 72),
                Fraction.with(-63, 9),
        };

        for (int i = 0; i < xs.length; i++) { // or ys.length
            Fraction addResult = fractionCalculator.multiply(xs[i], ys[i]);

            assertThat(addResult).isEqualTo(addResults[i]);
        }
    }

    @Test
    public void divide_shouldReturnExpectedResults() {
        Fraction[] addResults = new Fraction[] {
                Fraction.with(22, 27),
                Fraction.with(-255, 33),
                Fraction.with(88, 88),
                Fraction.with(64, 81),
                Fraction.with(-27, 21),
        };

        for (int i = 0; i < xs.length; i++) { // or ys.length
            Fraction addResult = fractionCalculator.divide(xs[i], ys[i]);

            assertThat(addResult).isEqualTo(addResults[i]);
        }
    }

    @Test
    public void greatestCommonFactor_shouldReturnOneIfNoCommonFactor() {
        int a = 3;
        int b = 2;

        assertThat(fractionCalculator.greatestCommonFactor(a, b)).isEqualTo(1);
    }

    @Test
    public void greatestCommonFactor_shouldReturnCommonFactor() {
        int a = 121;
        int b = 11;

        assertThat(fractionCalculator.greatestCommonFactor(a, b)).isEqualTo(11);
    }

    @Test
    public void greatestCommonFactor_shouldReturnAbsoluteCommonFactor() {
        int a = -1;
        int b = 3;

        assertThat(fractionCalculator.greatestCommonFactor(a, b)).isEqualTo(1);
    }

    @Test
    public void simplify_shouldReturnSameFractionIfProperFraction() {
        List<Fraction> properFractions = List.of(Fraction.with(1, 2),
                Fraction.with(3, 4),
                Fraction.with(-1, 3));

        properFractions.forEach(fraction -> {
            assertThat(fractionCalculator.simplify(fraction)).isEqualTo(fraction);
        });
    }

    @Test
    public void simplify_shouldReturnSimpleFractionSimplified() {
        List<Fraction> properFractions = List.of(Fraction.with(9, 27),
                Fraction.with(11, 121),
                Fraction.with(7, 49),
                Fraction.with(2, 4));

        List<Fraction> expectedSimplifiedFractions = List.of(Fraction.with(1, 3),
                Fraction.with(1, 11),
                Fraction.with(1, 7),
                Fraction.with(1, 2));

        for (int i = 0; i < properFractions.size(); i++) {
            assertThat(fractionCalculator.simplify(properFractions.get(i)))
                    .isEqualTo(expectedSimplifiedFractions.get(i));
        }
    }

    @Test
    public void simplify_shouldReturnMixedFractionIfImproperFraction() {
        List<Fraction> improperFractions = List.of(Fraction.with(3, 2),
                Fraction.with(21, 4),
                Fraction.with(-13, 9));

        List<Fraction> expectedSimplifiedFractions = List.of(MixedFraction.with(1, 1, 2),
                MixedFraction.with(5, 1, 4),
                MixedFraction.with(-1, 4, 9));

        for (int i = 0; i < improperFractions.size(); i++) {
            assertThat(fractionCalculator.simplify(improperFractions.get(i)))
                    .isEqualTo(expectedSimplifiedFractions.get(i));
        }
    }
}
