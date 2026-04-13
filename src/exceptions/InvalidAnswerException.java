package exceptions;

public class InvalidAnswerException extends Exception {
    private String invalidInput;

    public InvalidAnswerException(String message) {
        super(message);
    }

    public InvalidAnswerException(String message, String invalidInput) {
        super(message);
        this.invalidInput = invalidInput;
    }

    public String getInvalidInput() {
        return invalidInput;
    }
}
