package models;

import java.util.List;

public class MCQQuestion extends Question {
    private List<String> options;

    public MCQQuestion(String questionText, String correctAnswer,
                       String hint, String difficulty, List<String> options) {
        super(questionText, correctAnswer, hint, difficulty);
        this.options = options;
    }

    public List<String> getOptions() { return options; }

    @Override
    public void display() {
        System.out.println("\n[MCQ - " + getDifficulty() + " - " + getPoints() + "pts]");
        System.out.println("   " + getQuestionText());
        for (int i = 0; i < options.size(); i++) {
            char letter = (char) ('A' + i);
            System.out.println("   " + letter + ") " + options.get(i));
        }
    }

    @Override
    public boolean checkAnswer(String userAnswer) {
        return userAnswer.trim().equalsIgnoreCase(getCorrectAnswer().trim());
    }

    @Override
    public String getSummary() {
        return "MCQ: " + getQuestionText().substring(0,
                Math.min(50, getQuestionText().length())) + "...";
    }
}