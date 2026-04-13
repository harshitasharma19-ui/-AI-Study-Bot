package models;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    private final String name;
    private final String content;
    private final List<String> keyPoints;
    private final List<Question> questions;
    private final List<Flashcard> flashcards;
    private boolean isCompleted;

    public Topic(String name, String content) {
        this.name = name;
        this.content = content;
        this.keyPoints = new ArrayList<>();
        this.questions = new ArrayList<>();
        this.flashcards = new ArrayList<>();
        this.isCompleted = false;
    }

    public String getName() { return name; }
    public String getContent() { return content; }
    public List<String> getKeyPoints() { return keyPoints; }
    public List<Question> getQuestions() { return questions; }
    public List<Flashcard> getFlashcards() { return flashcards; }
    public boolean isCompleted() { return isCompleted; }

    public void addKeyPoint(String point) { keyPoints.add(point); }
    public void addQuestion(Question question) { questions.add(question); }
    public void addFlashcard(Flashcard flashcard) { flashcards.add(flashcard); }
    public void markCompleted() { isCompleted = true; }

    public void displayContent() {
        System.out.println("\nTopic: " + name);
        System.out.println("--------------------------------");
        System.out.println(content);
        if (!keyPoints.isEmpty()) {
            System.out.println("\nKey Points:");
            for (int i = 0; i < keyPoints.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + keyPoints.get(i));
            }
        }
        System.out.println("--------------------------------");
    }
}