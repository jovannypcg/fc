package mx.jovannypcg.fc.command;

import mx.jovannypcg.fc.domain.SimpleFraction;
import mx.jovannypcg.fc.validator.ArgumentValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FractionCalculatorTests {
    private SimpleFraction[] xs; // First operand
    private SimpleFraction[] ys; // Second operand

    @Mock
    private ArgumentValidator argumentValidator;
    private FractionCalculator fractionCalculator;

    @Before
    public void init() {
        xs = new SimpleFraction[]{
                SimpleFraction.with(2, 3),
                SimpleFraction.with(17, 11),
                SimpleFraction.with(-11, 8),
                SimpleFraction.with(8, 9),
                SimpleFraction.with(-9, 3) };

        ys = new SimpleFraction[]{
                SimpleFraction.with(9, 11),
                SimpleFraction.with(-3, 15),
                SimpleFraction.with(-11, 8),
                SimpleFraction.with(9, 8),
                SimpleFraction.with(7, 3) };

        fractionCalculator = new FractionCalculator(argumentValidator);
        Mockito.when(argumentValidator.isValid()).thenReturn(true);
    }

    @Test
    public void add_shouldReturnExpectedResults() {
        SimpleFraction[] addResults = new SimpleFraction[] {
                SimpleFraction.with(49, 33),
                SimpleFraction.with(222, 165),
                SimpleFraction.with(-176, 64),
                SimpleFraction.with(145, 72),
                SimpleFraction.with(-6, 9),
        };

        for (int i = 0; i < xs.length; i++) { // or ys.length
            SimpleFraction addResult = fractionCalculator.add(xs[i], ys[i]);

            assertThat(addResult).isEqualTo(addResults[i]);
        }
    }

    @Test
    public void subtract_shouldReturnExpectedResults() {
        SimpleFraction[] addResults = new SimpleFraction[] {
                SimpleFraction.with(-5, 33),
                SimpleFraction.with(288, 165),
                SimpleFraction.with(0, 64),
                SimpleFraction.with(-17, 72),
                SimpleFraction.with(-48, 9),
        };

        for (int i = 0; i < xs.length; i++) { // or ys.length
            SimpleFraction addResult = fractionCalculator.subtract(xs[i], ys[i]);

            assertThat(addResult).isEqualTo(addResults[i]);
        }
    }

    @Test
    public void multiply_shouldReturnExpectedResults() {
        SimpleFraction[] addResults = new SimpleFraction[] {
                SimpleFraction.with(18, 33),
                SimpleFraction.with(-51, 165),
                SimpleFraction.with(121, 64),
                SimpleFraction.with(72, 72),
                SimpleFraction.with(-63, 9),
        };

        for (int i = 0; i < xs.length; i++) { // or ys.length
            SimpleFraction addResult = fractionCalculator.multiply(xs[i], ys[i]);

            assertThat(addResult).isEqualTo(addResults[i]);
        }
    }

    @Test
    public void divide_shouldReturnExpectedResults() {
        SimpleFraction[] addResults = new SimpleFraction[] {
                SimpleFraction.with(22, 27),
                SimpleFraction.with(-255, 33),
                SimpleFraction.with(88, 88),
                SimpleFraction.with(64, 81),
                SimpleFraction.with(-27, 21),
        };

        for (int i = 0; i < xs.length; i++) { // or ys.length
            SimpleFraction addResult = fractionCalculator.divide(xs[i], ys[i]);

            assertThat(addResult).isEqualTo(addResults[i]);
        }
    }
}
