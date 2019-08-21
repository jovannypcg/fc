package mx.jovannypcg.fc.command;

import mx.jovannypcg.fc.validator.ArgumentValidator;
import org.springframework.stereotype.Component;

@Component
public class CalculatorCommand {
    private ArgumentValidator argumentValidator;

    public CalculatorCommand(ArgumentValidator argumentValidator) {
        this.argumentValidator = argumentValidator;
    }

    public void perform(String... args) {
        argumentValidator.validate(args);

        if (!argumentValidator.isValid()) {
            System.out.println(argumentValidator.getOutcome());
            return;
        }
    }
}
