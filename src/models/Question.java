package models;

import interfaces.Answerable;
import interfaces.Displayable;

public abstract class Question implements Answerable, Displayable {
    private static int idCounter = 0;
    private final int questionId;
    private final String questionText;
    private final String correctAnswer;
    private final String hint;
    private final String difficulty;
    private final int points;

    public Question(String questionText, String correctAnswer,
                    String hint, String difficulty) {
        this.questionId = ++idCounter;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.hint = hint;
        this.difficulty = difficulty;
        this.points = calculatePoints();
    }

    private int calculatePoints() {
        return switch (difficulty.toUpperCase()) {
            case "EASY" -> 5;
            case "MEDIUM" -> 10;
            case "HARD" -> 15;
            default -> 5;
        };
    }

    public int getQuestionId() { return questionId; }
    public String getQuestionText() { return questionText; }
    public String getDifficulty() { return difficulty; }
    public int getPoints() { return points; }

    @Override
    public String getCorrectAnswer() { return correctAnswer; }

    @Override
    public String getHint() { return hint; }

    @Override
    public abstract void display();

    @Override
    public abstract boolean checkAnswer(String userAnswer);
}
