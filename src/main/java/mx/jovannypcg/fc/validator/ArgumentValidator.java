package mx.jovannypcg.fc.validator;

import mx.jovannypcg.fc.exception.ArgumentSizeException;
import org.springframework.stereotype.Component;

@Component
public class ArgumentValidator {
    public static final int ARGUMENTS_ALLOWED = 3;

    private String validationOutcome;
    private boolean valid;

    public void validate(String... args) {
        try {
            validateSize(args);

            // Everything went well
            valid = true;
        } catch(ArgumentSizeException ase) {
            validationOutcome = ase.getMessage();
        } catch (Exception ex) {
            validationOutcome = "Unexpected exception occurred while performing operation";
        }
    }

    public boolean isValid() {
        return valid;
    }

    public String getOutcome() {
        return validationOutcome;
    }

    protected void validateSize(String... args) throws ArgumentSizeException {
        if (args.length != ARGUMENTS_ALLOWED) {
            throw new ArgumentSizeException();
        }
    }
}
