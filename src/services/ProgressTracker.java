package services;

import models.*;
import java.util.*;

public class ProgressTracker {
    private final List<StudySession> sessions;
    private final Map<String, List<Integer>> quizHistory;
    private final PerformanceReport currentReport;

    public ProgressTracker(String studentName) {
        sessions = new ArrayList<>();
        quizHistory = new HashMap<>();
        currentReport = new PerformanceReport(studentName);
    }

    public void recordStudySession(StudySession session) {
        sessions.add(session);
        System.out.println("Study session recorded!");
        session.displaySummary();
    }

    public void recordQuizScore(String subject, int correct, int total) {
        quizHistory.computeIfAbsent(subject, k -> new ArrayList<>())
                .add((int) ((double) correct / total * 100));
        currentReport.addQuizResult(subject, correct, total);
    }

    public void showProgress() {
        int totalMinutes = sessions.stream()
                .mapToInt(StudySession::getDurationMinutes)
                .sum();
        currentReport.setTotalStudyMinutes(totalMinutes);
        currentReport.display();
    }

    public void showStudyHistory() {
        System.out.println("\nSTUDY HISTORY");
        System.out.println("--------------------------------");
        if (sessions.isEmpty()) {
            System.out.println("No study sessions recorded yet.");
            return;
        }
        for (StudySession session : sessions) {
            session.displaySummary();
            System.out.println();
        }
    }

    public void showQuizHistory() {
        System.out.println("\nQUIZ HISTORY");
        System.out.println("--------------------------------");
        if (quizHistory.isEmpty()) {
            System.out.println("No quizzes taken yet.");
            return;
        }
        for (Map.Entry<String, List<Integer>> entry : quizHistory.entrySet()) {
            System.out.println(entry.getKey() + ":");
            List<Integer> scores = entry.getValue();
            for (int i = 0; i < scores.size(); i++) {
                System.out.println("   Attempt " + (i + 1) + ": " + scores.get(i) + "%");
            }
            double avg = scores.stream().mapToInt(Integer::intValue).average().orElse(0);
            System.out.println("   Average: " + String.format("%.1f", avg) + "%");
        }
    }

    public Map<String, Double> getSubjectAverages() {
        Map<String, Double> averages = new HashMap<>();
        for (Map.Entry<String, List<Integer>> entry : quizHistory.entrySet()) {
            double avg = entry.getValue().stream()
                    .mapToInt(Integer::intValue).average().orElse(0);
            averages.put(entry.getKey(), avg);
        }
        return averages;
    }
}
