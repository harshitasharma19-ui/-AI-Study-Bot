package models;

import interfaces.Scorable;
import interfaces.Displayable;
import java.util.*;

public class PerformanceReport implements Scorable, Displayable {
    private String studentName;
    private Map<String, Double> subjectAverages;
    private int totalQuizzesTaken;
    private int totalCorrectAnswers;
    private int totalQuestions;
    private int totalStudyMinutes;

    public PerformanceReport(String studentName) {
        this.studentName = studentName;
        this.subjectAverages = new HashMap<>();
        this.totalQuizzesTaken = 0;
        this.totalCorrectAnswers = 0;
        this.totalQuestions = 0;
        this.totalStudyMinutes = 0;
    }

    public void addQuizResult(String subject, int correct, int total) {
        totalQuizzesTaken++;
        totalCorrectAnswers += correct;
        totalQuestions += total;
        double avg = (double) correct / total * 100;
        subjectAverages.merge(subject, avg, (old, newVal) -> (old + newVal) / 2);
    }

    public void setTotalStudyMinutes(int minutes) {
        this.totalStudyMinutes = minutes;
    }

    @Override
    public int calculateScore() {
        return totalCorrectAnswers;
    }

    @Override
    public double getPercentage() {
        if (totalQuestions == 0) return 0;
        return (double) totalCorrectAnswers / totalQuestions * 100;
    }

    @Override
    public String getGrade() {
        double pct = getPercentage();
        if (pct >= 90) return "A+ Excellent!";
        if (pct >= 80) return "A";
        if (pct >= 70) return "B";
        if (pct >= 60) return "C";
        if (pct >= 50) return "D";
        return "F - Need more practice!";
    }

    @Override
    public void display() {
        System.out.println("\n========================================");
        System.out.println("        PERFORMANCE REPORT              ");
        System.out.println("========================================");
        System.out.println(" Student    : " + studentName);
        System.out.println(" Quizzes    : " + totalQuizzesTaken);
        System.out.println(" Score      : " + totalCorrectAnswers + "/" + totalQuestions);
        System.out.println(" Percentage : " + String.format("%.1f", getPercentage()) + "%");
        System.out.println(" Grade      : " + getGrade());
        System.out.println(" Study Time : " + totalStudyMinutes + " minutes");
        System.out.println();
        System.out.println(" Subject-wise Performance:");
        for (Map.Entry<String, Double> entry : subjectAverages.entrySet()) {
            String bar = getProgressBar(entry.getValue());
            System.out.println("   " + entry.getKey() + ": " + bar + " " +
                    String.format("%.0f", entry.getValue()) + "%");
        }
        System.out.println("========================================");
    }

    private String getProgressBar(double percentage) {
        int filled = (int) (percentage / 10);
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < 10; i++) {
            bar.append(i < filled ? "#" : "-");
        }
        bar.append("]");
        return bar.toString();
    }

    @Override
    public String getSummary() {
        return studentName + " | " + getGrade() + " | " +
                String.format("%.1f", getPercentage()) + "%";
    }
}
