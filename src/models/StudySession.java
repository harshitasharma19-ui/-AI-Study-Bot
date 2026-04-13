package models;

import java.time.LocalDateTime;
import java.time.Duration;

public class StudySession {
    private String subject;
    private String topic;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int durationMinutes;
    private String notes;

    public StudySession(String subject, String topic) {
        this.subject = subject;
        this.topic = topic;
        this.startTime = LocalDateTime.now();
    }

    public void endSession() {
        this.endTime = LocalDateTime.now();
        this.durationMinutes = (int) Duration.between(startTime, endTime).toMinutes();
        if (durationMinutes == 0) durationMinutes = 1;
    }

    public void endSession(int manualMinutes) {
        this.endTime = LocalDateTime.now();
        this.durationMinutes = manualMinutes;
    }

    public String getSubject() { return subject; }
    public String getTopic() { return topic; }
    public int getDurationMinutes() { return durationMinutes; }
    public void setNotes(String notes) { this.notes = notes; }

    public void displaySummary() {
        System.out.println("Study Session Complete!");
        System.out.println("   Subject : " + subject);
        System.out.println("   Topic   : " + topic);
        System.out.println("   Duration: " + durationMinutes + " minutes");
        if (notes != null) System.out.println("   Notes   : " + notes);
    }
}
