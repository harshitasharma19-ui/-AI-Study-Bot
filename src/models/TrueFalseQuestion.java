package models;

public class TrueFalseQuestion extends Question {

    public TrueFalseQuestion(String questionText, String correctAnswer,
                             String hint, String difficulty) {
        super(questionText, correctAnswer, hint, difficulty);
    }

    @Override
    public void display() {
        System.out.println("\n[True/False - " + getDifficulty() + " - " + getPoints() + "pts]");
        System.out.println("   " + getQuestionText());
        System.out.println("   A) True");
        System.out.println("   B) False");
    }

    @Override
    public boolean checkAnswer(String userAnswer) {
        String normalized = userAnswer.trim().toUpperCase();
        String correct = getCorrectAnswer().trim().toUpperCase();

        if (normalized.equals("A") || normalized.equals("TRUE") || normalized.equals("T")) {
            return correct.equals("TRUE") || correct.equals("A");
        }
        if (normalized.equals("B") || normalized.equals("FALSE") || normalized.equals("F")) {
            return correct.equals("FALSE") || correct.equals("B");
        }
        return false;
    }

    @Override
    public String getSummary() {
        return "T/F: " + getQuestionText().substring(0,
                Math.min(50, getQuestionText().length())) + "...";
    }
}