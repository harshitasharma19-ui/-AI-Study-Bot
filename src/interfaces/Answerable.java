package interfaces;

public interface Answerable {
    boolean checkAnswer(String userAnswer);
    String getCorrectAnswer();
    String getHint();
}