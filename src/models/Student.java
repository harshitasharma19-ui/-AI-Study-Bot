package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends User {
    private final List<Subject> enrolledSubjects;
    private final Map<String, List<Integer>> quizScores;
    private int totalStudyMinutes;
    private int streakDays;

    public Student() {
        super();
        this.enrolledSubjects = new ArrayList<>();
        this.quizScores = new HashMap<>();
        this.totalStudyMinutes = 0;
        this.streakDays = 0;
    }

    public Student(String userId, String username, String password, String email) {
        super(userId, username, password, email);
        this.enrolledSubjects = new ArrayList<>();
        this.quizScores = new HashMap<>();
        this.totalStudyMinutes = 0;
        this.streakDays = 0;
    }

    @Override
    public String getRole() {
        return "STUDENT";
    }

    public void addSubject(Subject subject) {
        enrolledSubjects.add(subject);
        System.out.println("Subject '" + subject.getName() + "' added successfully!");
    }

    public void removeSubject(String subjectName) {
        enrolledSubjects.removeIf(s -> s.getName().equalsIgnoreCase(subjectName));
        System.out.println("Subject '" + subjectName + "' removed.");
    }

    public List<Subject> getEnrolledSubjects() {
        return enrolledSubjects;
    }

    public void addQuizScore(String subject, int score) {
        quizScores.computeIfAbsent(subject, k -> new ArrayList<>()).add(score);
    }

    public double getAverageScore(String subject) {
        List<Integer> scores = quizScores.get(subject);
        if (scores == null || scores.isEmpty()) return 0;
        return scores.stream().mapToInt(Integer::intValue).average().orElse(0);
    }

    public Map<String, List<Integer>> getAllScores() {
        return quizScores;
    }

    public void addStudyTime(int minutes) {
        this.totalStudyMinutes += minutes;
    }

    public int getTotalStudyMinutes() { return totalStudyMinutes; }
    public int getStreakDays() { return streakDays; }
    public void incrementStreak() { streakDays++; }
    public void resetStreak() { streakDays = 0; }

    @Override
    public void display() {
        System.out.println("================================");
        System.out.println("       STUDENT PROFILE          ");
        System.out.println("================================");
        System.out.println(" Name    : " + getUsername());
        System.out.println(" Email   : " + getEmail());
        System.out.println(" Subjects: " + enrolledSubjects.size());
        System.out.println(" Study   : " + totalStudyMinutes + " minutes");
        System.out.println(" Streak  : " + streakDays + " days");
        System.out.println("================================");
    }

    @Override
    public String getSummary() {
        return getUsername() + " | Subjects: " + enrolledSubjects.size() +
                " | Study: " + totalStudyMinutes + "min";
    }

    @Override
    public String toFileString() {
        return getUserId() + "|" + getUsername() + "|" + getPassword() + "|" +
                getEmail() + "|" + totalStudyMinutes + "|" + streakDays;
    }

    @Override
    public void fromFileString(String data) {
        String[] parts = data.split("\\|");
        setUserId(parts[0]);
        setUsername(parts[1]);
        setPassword(parts[2]);
        setEmail(parts[3]);
        totalStudyMinutes = Integer.parseInt(parts[4]);
        streakDays = Integer.parseInt(parts[5]);
    }
}
