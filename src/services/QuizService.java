package services;

import models.*;
import exceptions.*;
import java.util.*;

public class QuizService {
    private final Map<String, List<Question>> questionBank;

    public QuizService() {
        questionBank = new HashMap<>();
        Random random = new Random();
        loadSampleQuestions();
    }

    private void loadSampleQuestions() {
        List<Question> javaQuestions = new ArrayList<>();

        javaQuestions.add(new MCQQuestion(
                "Which keyword is used to inherit a class in Java?",
                "B", "Think about parent-child class relationship",
                "EASY",
                Arrays.asList("implements", "extends", "inherits", "super")
        ));

        javaQuestions.add(new MCQQuestion(
                "Which OOP concept is about hiding internal details?",
                "C", "Think about data protection",
                "EASY",
                Arrays.asList("Inheritance", "Polymorphism", "Encapsulation", "Abstraction")
        ));

        javaQuestions.add(new MCQQuestion(
                "What is method overloading?",
                "A", "Same name but...",
                "MEDIUM",
                Arrays.asList(
                        "Same method name, different parameters",
                        "Same method name, different return type only",
                        "Different method name, same parameters",
                        "Overriding a parent method"
                )
        ));

        javaQuestions.add(new MCQQuestion(
                "Which is NOT a Java access modifier?",
                "D", "Think about visibility levels",
                "MEDIUM",
                Arrays.asList("public", "private", "protected", "friend")
        ));

        javaQuestions.add(new MCQQuestion(
                "What does JVM stand for?",
                "B", "Virtual environment for Java",
                "EASY",
                Arrays.asList(
                        "Java Variable Machine",
                        "Java Virtual Machine",
                        "Java Visual Manager",
                        "Java Version Manager"
                )
        ));

        javaQuestions.add(new TrueFalseQuestion(
                "Java supports multiple inheritance through classes.",
                "FALSE", "Think about the diamond problem", "MEDIUM"
        ));

        javaQuestions.add(new TrueFalseQuestion(
                "An abstract class can have constructors.",
                "TRUE", "Abstract classes can have constructors", "HARD"
        ));

        javaQuestions.add(new TrueFalseQuestion(
                "A final method can be overridden.",
                "FALSE", "Final means...", "EASY"
        ));

        javaQuestions.add(new MCQQuestion(
                "Which collection allows duplicate elements?",
                "A", "Ordered vs unique collections",
                "MEDIUM",
                Arrays.asList("ArrayList", "HashSet", "TreeSet", "LinkedHashSet")
        ));

        javaQuestions.add(new MCQQuestion(
                "What is the parent class of all Java classes?",
                "C", "Every class implicitly extends this",
                "EASY",
                Arrays.asList("Class", "Main", "Object", "System")
        ));

        questionBank.put("java", javaQuestions);
        questionBank.put("oop", javaQuestions);

        // DSA Questions
        List<Question> dsQuestions = new ArrayList<>();

        dsQuestions.add(new MCQQuestion(
                "Time complexity of binary search?",
                "B", "Divides array in half each time",
                "MEDIUM",
                Arrays.asList("O(n)", "O(log n)", "O(n^2)", "O(1)")
        ));

        dsQuestions.add(new MCQQuestion(
                "Which data structure uses LIFO?",
                "A", "Last In, First Out",
                "EASY",
                Arrays.asList("Stack", "Queue", "Array", "LinkedList")
        ));

        dsQuestions.add(new TrueFalseQuestion(
                "A binary tree can have at most 2 children per node.",
                "TRUE", "Binary means two", "EASY"
        ));

        questionBank.put("data structures", dsQuestions);
        questionBank.put("dsa", dsQuestions);
    }

    public int startQuiz(String subject, int numberOfQuestions, Scanner scanner)
            throws QuizNotAvailableException {

        String key = subject.toLowerCase();
        if (!questionBank.containsKey(key)) {
            throw new QuizNotAvailableException(subject);
        }

        List<Question> allQuestions = questionBank.get(key);
        List<Question> quizQuestions = new ArrayList<>(allQuestions);
        Collections.shuffle(quizQuestions);

        int count = Math.min(numberOfQuestions, quizQuestions.size());
        int score = 0;
        int totalPoints = 0;

        System.out.println("\n========================================");
        System.out.println("          QUIZ TIME!                    ");
        System.out.println("  Subject: " + subject);
        System.out.println("  Questions: " + count);
        System.out.println("========================================\n");

        for (int i = 0; i < count; i++) {
            Question q = quizQuestions.get(i);
            System.out.println("--- Question " + (i + 1) + "/" + count + " ---");
            q.display();
            totalPoints += q.getPoints();

            System.out.print("\n   Your answer (or 'hint' for help): ");
            String answer = scanner.nextLine().trim();

            if (answer.equalsIgnoreCase("hint")) {
                System.out.println("   Hint: " + q.getHint());
                System.out.print("   Your answer: ");
                answer = scanner.nextLine().trim();
            }

            if (q.checkAnswer(answer)) {
                System.out.println("   CORRECT! +" + q.getPoints() + " points");
                score += q.getPoints();
            } else {
                System.out.println("   WRONG! Answer was: " + q.getCorrectAnswer());
            }
            System.out.println();
        }

        double percentage = (double) score / totalPoints * 100;
        System.out.println("========================================");
        System.out.println("          QUIZ RESULTS                  ");
        System.out.println("  Score: " + score + "/" + totalPoints);
        System.out.println("  Percentage: " + String.format("%.1f", percentage) + "%");
        System.out.println("========================================");

        return score;
    }

    public void addQuestion(String subject, Question question) {
        questionBank.computeIfAbsent(subject.toLowerCase(), k -> new ArrayList<>()).add(question);
    }

    public List<String> getAvailableSubjects() {
        return new ArrayList<>(questionBank.keySet());
    }
}
