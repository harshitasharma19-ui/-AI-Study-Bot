package exceptions;

public class QuizNotAvailableException extends Exception {
    public QuizNotAvailableException(String subject) {
        super("No quiz available for subject: " + subject);
    }
}

