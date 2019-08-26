package mx.jovannypcg.fc.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MixedFractionTests {
    private SimpleFraction[] improperFractions;

    @Before
    public void init() {
        improperFractions = new SimpleFraction[] {
                SimpleFraction.with(8, 3),
                SimpleFraction.with(-7, 4),
                SimpleFraction.with(65, 44),
                SimpleFraction.with(27, 9),
                SimpleFraction.with(-3, 2),
        };
    }

    @Test
    public void parse_shouldReturnMixedFractionWithZeroAsWholeNumberForProperFraction() {
        SimpleFraction properFraction = SimpleFraction.with(1, 3);
        MixedFraction mixedFraction = MixedFraction.parse(properFraction);

        attributesAssertion(mixedFraction, 0, 1, 3);
    }

    @Test
    public void parse_shouldReturnMixedFractionForImproperFraction() {
        MixedFraction[] expectedMixedFractions = new MixedFraction[] {
                MixedFraction.with(2, 2, 3),
                MixedFraction.with(-1, 3, 4),
                MixedFraction.with(1, 21, 44),
                MixedFraction.with(3, 0, 9),
                MixedFraction.with(-1, 1, 2),
        };

        for (int i = 0; i < improperFractions.length; i++) {
            MixedFraction expectedMixedFraction = expectedMixedFractions[i];
            MixedFraction mixedFraction = MixedFraction.parse(improperFractions[i]);

            attributesAssertion(mixedFraction,
                    expectedMixedFraction.getWholeNumber(),
                    expectedMixedFraction.getNumerator(),
                    expectedMixedFraction.getDenominator());
        }
    }

    @Test
    public void toString_shouldPrintInteger() {
        MixedFraction mixedFraction = MixedFraction.with(3, 0, 9);
        assertThat(mixedFraction.toString()).isEqualTo("3");
    }

    @Test
    public void toString_shouldReturnSimpleFractionIfNoWholeNumber() {
        MixedFraction mixedFraction = MixedFraction.with(0, 2, 7);
        assertThat(mixedFraction.toString()).isEqualTo("2/7");
    }

    @Test
    public void toString_shouldReturnMixedFraction() {
        MixedFraction mixedFraction = MixedFraction.with(-2, 3, 9);
        assertThat(mixedFraction.toString()).isEqualTo("-2_3/9");
    }

    private void attributesAssertion(MixedFraction mixedFraction, int wholeNumber, int numerator, int denominator) {
        assertThat(mixedFraction)
                .hasFieldOrPropertyWithValue("wholeNumber", wholeNumber)
                .hasFieldOrPropertyWithValue("numerator", numerator)
                .hasFieldOrPropertyWithValue("denominator", denominator);
    }
}
