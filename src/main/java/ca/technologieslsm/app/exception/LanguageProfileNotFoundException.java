package ca.technologieslsm.app.exception;

public class LanguageProfileNotFoundException extends RuntimeException {

    public LanguageProfileNotFoundException() {
        super("Language profiles not found");
    }

    public LanguageProfileNotFoundException(String message) {
        super(message);
    }

    public LanguageProfileNotFoundException(Exception exception) {
        super(exception.getMessage());
    }
}
