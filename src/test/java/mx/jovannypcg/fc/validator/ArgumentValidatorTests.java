package mx.jovannypcg.fc.validator;

import mx.jovannypcg.fc.exception.ArgumentSizeException;
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
        assertThatExceptionOfType(ArgumentSizeException.class).isThrownBy(() -> {
            argumentValidator.validateSize(args);
        });
    }
}
