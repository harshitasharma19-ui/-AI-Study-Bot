package services;

import models.Flashcard;
import java.util.*;
import java.util.stream.Collectors;

public class FlashcardService {
    private List<Flashcard> allFlashcards;

    public FlashcardService() {
        allFlashcards = new ArrayList<>();
        loadSampleFlashcards();
    }

    private void loadSampleFlashcards() {
        allFlashcards.add(new Flashcard("What is Encapsulation?",
                "Wrapping data and methods with access control", "Java"));
        allFlashcards.add(new Flashcard("What is a Constructor?",
                "Special method that initializes an object", "Java"));
        allFlashcards.add(new Flashcard("== vs .equals()?",
                "== compares references, .equals() compares values", "Java"));
        allFlashcards.add(new Flashcard("What is an Abstract Class?",
                "Class that cannot be instantiated, may have abstract methods", "Java"));
        allFlashcards.add(new Flashcard("What is the 'final' keyword?",
                "Variable: constant, Method: can't override, Class: can't inherit", "Java"));
        allFlashcards.add(new Flashcard("ArrayList vs LinkedList?",
                "ArrayList: fast access, slow insert. LinkedList: fast insert, slow access", "Java"));
        allFlashcards.add(new Flashcard("What is Polymorphism?",
                "Object taking many forms - overloading & overriding", "Java"));
        allFlashcards.add(new Flashcard("What is an Interface?",
                "Contract with abstract methods a class must implement", "Java"));
    }

    public void createFlashcard(String front, String back, String subject) {
        Flashcard card = new Flashcard(front, back, subject);
        allFlashcards.add(card);
        System.out.println("Flashcard created successfully!");
        card.display();
    }

    public void startFlashcardSession(String subject, Scanner scanner) {
        List<Flashcard> cards = allFlashcards.stream()
                .filter(f -> f.getSubject().equalsIgnoreCase(subject) && !f.isMastered())
                .collect(Collectors.toList());

        if (cards.isEmpty()) {
            System.out.println("No flashcards for '" + subject + "' or all mastered!");
            return;
        }

        Collections.shuffle(cards);
        System.out.println("\nFLASHCARD SESSION - " + subject.toUpperCase());
        System.out.println("Cards to review: " + cards.size());
        System.out.println("Press ENTER to reveal answer, then y/n\n");

        int correct = 0;
        for (int i = 0; i < cards.size(); i++) {
            Flashcard card = cards.get(i);
            System.out.println("--- Card " + (i + 1) + "/" + cards.size() + " ---");
            System.out.println("Q: " + card.getFront());
            System.out.print("   Press ENTER to see answer...");
            scanner.nextLine();

            card.showAnswer();

            System.out.print("   Did you know it? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("y")) {
                card.reviewCorrect();
                correct++;
                System.out.println("   Great!" + (card.isMastered() ? " MASTERED!" : ""));
            } else {
                card.reviewIncorrect();
                System.out.println("   You'll get it next time!");
            }
            System.out.println();
        }

        System.out.println("Session Complete! " + correct + "/" + cards.size() + " correct.");
    }

    public void showAllFlashcards(String subject) {
        List<Flashcard> cards = allFlashcards.stream()
                .filter(f -> f.getSubject().equalsIgnoreCase(subject))
                .collect(Collectors.toList());

        if (cards.isEmpty()) {
            System.out.println("No flashcards for: " + subject);
            return;
        }

        System.out.println("\nAll Flashcards - " + subject);
        cards.forEach(Flashcard::display);
    }

    public List<Flashcard> getAllFlashcards() { return allFlashcards; }
}
