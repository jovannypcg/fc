package mx.jovannypcg.fc.exception;

import mx.jovannypcg.fc.validator.ArgumentValidator;

public class ArgumentSizeException extends Exception {
    public ArgumentSizeException() {
        super("There must be exactly " + ArgumentValidator.ARGUMENTS_ALLOWED + " arguments");
    }
}
