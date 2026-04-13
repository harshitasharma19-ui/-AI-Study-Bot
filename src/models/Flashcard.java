package models;

import interfaces.Displayable;

public class Flashcard implements Displayable {
    private static int idCounter = 0;
    private final int cardId;
    private final String front;
    private final String back;
    private final String subject;
    private int timesReviewed;
    private int timesCorrect;
    private boolean mastered;

    public Flashcard(String front, String back, String subject) {
        this.cardId = ++idCounter;
        this.front = front;
        this.back = back;
        this.subject = subject;
        this.timesReviewed = 0;
        this.timesCorrect = 0;
        this.mastered = false;
    }

    public int getCardId() { return cardId; }
    public String getFront() { return front; }
    public String getBack() { return back; }
    public String getSubject() { return subject; }
    public boolean isMastered() { return mastered; }

    public void reviewCorrect() {
        timesReviewed++;
        timesCorrect++;
        if (timesCorrect >= 3) mastered = true;
    }

    public void reviewIncorrect() {
        timesReviewed++;
        mastered = false;
    }

    public double getAccuracy() {
        if (timesReviewed == 0) return 0;
        return (double) timesCorrect / timesReviewed * 100;
    }

    @Override
    public void display() {
        System.out.println("+--------------------------------+");
        System.out.println("| FLASHCARD #" + cardId);
        System.out.println("| Front: " + front);
        System.out.println("| Subject: " + subject);
        System.out.println("| Mastered: " + (mastered ? "Yes" : "Not yet"));
        System.out.println("+--------------------------------+");
    }

    public void showAnswer() {
        System.out.println("Answer: " + back);
    }

    @Override
    public String getSummary() {
        return "Card #" + cardId + ": " + front + " | Accuracy: " +
                String.format("%.0f", getAccuracy()) + "%";
    }
}