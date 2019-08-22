package mx.jovannypcg.fc.validator;

import mx.jovannypcg.fc.exception.CalculatorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ArgumentValidatorTests {
    private ArgumentValidator argumentValidator;

    @Before
    public void init() {
        argumentValidator = new ArgumentValidator();
    }

    @Test
    public void validateSize_shouldNotThrowExceptionDueToValidNumberOfArguments() throws Exception {
        String[] args = { "3", "*", "1" };
        argumentValidator.validateSize(args);
    }

    /**
     * @see ArgumentValidator#ARGUMENTS_ALLOWED
     */
    @Test
    public void validateSize_shouldThrowExceptionDueToInvalidNumberOfArguments() {
        String[] args = { "2" };
        assertThatExceptionOfType(CalculatorException.class).isThrownBy(() -> {
            argumentValidator.validateSize(args);
        }).withMessageMatching("There must be exactly \\d+ arguments");
    }

    @Test
    public void validateArguments_shouldNotThrowExceptionDueToValidOrderAndFormat() throws Exception {
        String[] args = { "2/3", "*", "2_7/3" };

        argumentValidator.validateArguments(args);
    }

    @Test
    public void validateArguments_shouldThrowExceptionDueToBadOrder() throws Exception {
        String[] args = { "*", "2/3", "2_7/3" };

        assertThatExceptionOfType(CalculatorException.class).isThrownBy(() -> {
            argumentValidator.validateArguments(args);
        }).withMessageContaining("the order");
    }

    @Test
    public void validateArguments_shouldThrowExceptionDueToBadOperandFormat() throws Exception {
        String[] args = { "a/3", "*", "2/7_3" };

        assertThatExceptionOfType(CalculatorException.class).isThrownBy(() -> {
            argumentValidator.validateArguments(args);
        }).withMessageContaining("the format");
    }

    @Test
    public void isValidOperand_shouldReturnTrueDueToValidOperand() {
        String[] validOperands = { "1_7/8", "2", "45_3/2", "7/8", "3_1/2" };

        for (String operand : validOperands) {
            assertThat(argumentValidator.isValidOperand(operand)).isTrue();
        }
    }

    @Test
    public void isValidOperand_shouldReturnFalseDueToInvalidOperand() {
        String[] invalidOperands = { "1_/8", "2/", "_45_3/2", "abc", "1-2/7", "/23_2" };

        for (String operand : invalidOperands) {
            assertThat(argumentValidator.isValidOperand(operand)).isFalse();
        }
    }

    @Test
    public void isValidOperator_shouldReturnTrueDueToValidOperator() {
        String[] validOperators = { "+", "-", "/", "*" };

        for (String operator : validOperators) {
            assertThat(argumentValidator.isValidOperator(operator)).isTrue();
        }
    }

    @Test
    public void isValidOperator_shouldReturnFalseDueToInvalidOperator() {
        String[] invalidOperators = { "+-", "-/*+", "*/", "abc", "z", "1_4/3" };

        for (String operator : invalidOperators) {
            assertThat(argumentValidator.isValidOperator(operator)).isFalse();
        }
    }
}
