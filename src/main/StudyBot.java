package main;

import models.*;
import services.*;
import utils.*;
import exceptions.*;
import java.util.*;

public class StudyBot {
    private Student currentStudent;
    private AIEngine aiEngine;
    private QuizService quizService;
    private FlashcardService flashcardService;
    private ProgressTracker progressTracker;
    private StudyScheduler scheduler;
    private Scanner scanner;
    private boolean isRunning;

    public StudyBot() {
        aiEngine = new AIEngine();
        quizService = new QuizService();
        flashcardService = new FlashcardService();
        scheduler = new StudyScheduler();
        scanner = new Scanner(System.in);
        isRunning = true;
        FileManager.initializeDataDirectory();
    }

    public void start() {
        showWelcomeBanner();
        handleAuthentication();

        if (currentStudent != null) {
            progressTracker = new ProgressTracker(currentStudent.getUsername());
            mainMenu();
        }
    }

    private void showWelcomeBanner() {
        System.out.println("================================================");
        System.out.println("                                                ");
        System.out.println("        AI STUDY BOT                            ");
        System.out.println("        Your Intelligent Study Companion        ");
        System.out.println("                                                ");
        System.out.println("        Features:                               ");
        System.out.println("        * AI-Powered Q&A                        ");
        System.out.println("        * Interactive Quizzes                   ");
        System.out.println("        * Flashcard System                      ");
        System.out.println("        * Progress Tracking                     ");
        System.out.println("        * Study Scheduling                      ");
        System.out.println("                                                ");
        System.out.println("================================================");
        System.out.println();
    }

    private void handleAuthentication() {
        System.out.println("1. Register (New User)");
        System.out.println("2. Login (Existing User)");
        System.out.print("Choose option: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                register();
                break;
            case "2":
                login();
                break;
            default:
                System.out.println("Invalid option. Creating new account...");
                register();
        }
    }

    private void register() {
        System.out.println("\n--- REGISTRATION ---");

        String username;
        do {
            System.out.print("Username (3-20 chars): ");
            username = scanner.nextLine().trim();
        } while (!InputValidator.isValidUsername(username));

        String password;
        do {
            System.out.print("Password (min 4 chars): ");
            password = scanner.nextLine().trim();
        } while (!InputValidator.isValidPassword(password));

        String email;
        do {
            System.out.print("Email: ");
            email = scanner.nextLine().trim();
        } while (!InputValidator.isValidEmail(email));

        String userId = "STU" + System.currentTimeMillis() % 10000;
        currentStudent = new Student(userId, username, password, email);

        FileManager.saveToFile("data/users.txt", currentStudent.toFileString(), true);

        System.out.println("\nRegistration successful! Welcome, " + username + "!");
    }

    private void login() {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        List<String> users = FileManager.readFromFile("data/users.txt");
        boolean found = false;

        for (String userData : users) {
            String[] parts = userData.split("\\|");
            if (parts.length >= 3 && parts[1].equals(username) && parts[2].equals(password)) {
                currentStudent = new Student();
                currentStudent.fromFileString(userData);
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("\nWelcome back, " + username + "!");
        } else {
            System.out.println("Invalid credentials. Creating new account...");
            register();
        }
    }

    private void mainMenu() {
        while (isRunning) {
            System.out.println("\n========================================");
            System.out.println("            MAIN MENU                   ");
            System.out.println("========================================");
            System.out.println("  1. Chat with AI Bot");
            System.out.println("  2. Take a Quiz");
            System.out.println("  3. Flashcard Study");
            System.out.println("  4. Manage Subjects");
            System.out.println("  5. Log Study Session");
            System.out.println("  6. View Progress & Analytics");
            System.out.println("  7. Study Schedule");
            System.out.println("  8. Get Motivation");
            System.out.println("  9. View Profile");
            System.out.println("  0. Exit");
            System.out.println("========================================");
            System.out.print("  Choose option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    chatWithAI();
                    break;
                case "2":
                    takeQuiz();
                    break;
                case "3":
                    flashcardMenu();
                    break;
                case "4":
                    manageSubjects();
                    break;
                case "5":
                    logStudySession();
                    break;
                case "6":
                    viewProgress();
                    break;
                case "7":
                    studySchedule();
                    break;
                case "8":
                    getMotivation();
                    break;
                case "9":
                    viewProfile();
                    break;
                case "0":
                    exitBot();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void chatWithAI() {
        System.out.println("\nAI CHAT MODE");
        System.out.println("--------------------------------");
        System.out.println("Ask me anything about programming!");
        System.out.println("Type 'back' to return to menu.\n");

        while (true) {
            System.out.print("You: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("exit")) {
                System.out.println("Bot: See you later! Happy studying!");
                break;
            }
            if (input.isEmpty()) continue;

            String response = aiEngine.getResponse(input);
            System.out.println("\nBot: " + response + "\n");
        }
    }

    private void takeQuiz() {
        System.out.println("\nQUIZ MODE");
        System.out.println("Available subjects: " + quizService.getAvailableSubjects());
        System.out.print("Enter subject: ");
        String subject = scanner.nextLine().trim();

        System.out.print("Number of questions (1-10): ");
        String numStr = scanner.nextLine().trim();
        int num = 5;
        try {
            num = Integer.parseInt(numStr);
            num = Math.max(1, Math.min(10, num));
        } catch (NumberFormatException e) {
            System.out.println("Using default: 5 questions");
        }

        try {
            int score = quizService.startQuiz(subject, num, scanner);
            currentStudent.addQuizScore(subject, score);
            progressTracker.recordQuizScore(subject, score, num * 10);
        } catch (QuizNotAvailableException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void flashcardMenu() {
        System.out.println("\nFLASHCARD MENU");
        System.out.println("1. Start Flashcard Session");
        System.out.println("2. Create New Flashcard");
        System.out.println("3. View All Flashcards");
        System.out.print("Choose: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                System.out.print("Subject for flashcards: ");
                String sub = scanner.nextLine().trim();
                flashcardService.startFlashcardSession(sub, scanner);
                break;
            case "2":
                System.out.print("Front (Question): ");
                String front = scanner.nextLine().trim();
                System.out.print("Back (Answer): ");
                String back = scanner.nextLine().trim();
                System.out.print("Subject: ");
                String subj = scanner.nextLine().trim();
                flashcardService.createFlashcard(front, back, subj);
                break;
            case "3":
                System.out.print("Subject: ");
                String s = scanner.nextLine().trim();
                flashcardService.showAllFlashcards(s);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void manageSubjects() {
        System.out.println("\nSUBJECT MANAGEMENT");
        System.out.println("1. Add Subject");
        System.out.println("2. View Subjects");
        System.out.println("3. Add Topic to Subject");
        System.out.println("4. Remove Subject");
        System.out.print("Choose: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                System.out.print("Subject name: ");
                String name = scanner.nextLine().trim();
                System.out.print("Description: ");
                String desc = scanner.nextLine().trim();
                String id = "SUB" + System.currentTimeMillis() % 1000;
                Subject subject = new Subject(id, name, desc);
                currentStudent.addSubject(subject);
                break;
            case "2":
                List<Subject> subjects = currentStudent.getEnrolledSubjects();
                if (subjects.isEmpty()) {
                    System.out.println("No subjects added yet.");
                } else {
                    subjects.forEach(Subject::display);
                }
                break;
            case "3":
                System.out.print("Subject name: ");
                String subName = scanner.nextLine().trim();
                Subject sub = currentStudent.getEnrolledSubjects().stream()
                        .filter(su -> su.getName().equalsIgnoreCase(subName))
                        .findFirst().orElse(null);
                if (sub != null) {
                    System.out.print("Topic name: ");
                    String topicName = scanner.nextLine().trim();
                    System.out.print("Topic content/notes: ");
                    String content = scanner.nextLine().trim();
                    Topic topic = new Topic(topicName, content);
                    sub.addTopic(topic);
                    System.out.println("Topic added!");
                } else {
                    System.out.println("Subject not found.");
                }
                break;
            case "4":
                System.out.print("Subject to remove: ");
                String removeName = scanner.nextLine().trim();
                currentStudent.removeSubject(removeName);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + choice);
        }
    }

    private void logStudySession() {
        System.out.println("\nLOG STUDY SESSION");
        System.out.print("Subject: ");
        String subject = scanner.nextLine().trim();
        System.out.print("Topic: ");
        String topic = scanner.nextLine().trim();
        System.out.print("Duration (minutes): ");

        try {
            int minutes = Integer.parseInt(scanner.nextLine().trim());
            StudySession session = new StudySession(subject, topic);
            session.endSession(minutes);

            System.out.print("Any notes? (or press Enter to skip): ");
            String notes = scanner.nextLine().trim();
            if (!notes.isEmpty()) session.setNotes(notes);

            progressTracker.recordStudySession(session);
            currentStudent.addStudyTime(minutes);
            currentStudent.incrementStreak();
        } catch (NumberFormatException e) {
            System.out.println("Invalid duration.");
        }
    }

    private void viewProgress() {
        System.out.println("\nPROGRESS & ANALYTICS");
        System.out.println("1. Overall Performance Report");
        System.out.println("2. Study History");
        System.out.println("3. Quiz History");
        System.out.println("4. AI Recommendations");
        System.out.print("Choose: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                progressTracker.showProgress();
                break;
            case "2":
                progressTracker.showStudyHistory();
                break;
            case "3":
                progressTracker.showQuizHistory();
                break;
            case "4":
                Map<String, Double> avgs = progressTracker.getSubjectAverages();
                if (avgs.isEmpty()) {
                    System.out.println("Take some quizzes first!");
                } else {
                    System.out.println(aiEngine.suggestStudyTopic(avgs));
                }
                break;
        }
    }

    private void studySchedule() {
        List<String> subjectNames = new ArrayList<>();
        for (Subject s : currentStudent.getEnrolledSubjects()) {
            subjectNames.add(s.getName());
        }

        if (subjectNames.isEmpty()) {
            System.out.println("Add subjects first!");
            manageSubjects();
            return;
        }

        System.out.print("Hours per day for study (1-8): ");
        try {
            int hours = Integer.parseInt(scanner.nextLine().trim());
            hours = Math.max(1, Math.min(8, hours));
            scheduler.suggestSchedule(subjectNames, hours);
        } catch (NumberFormatException e) {
            scheduler.suggestSchedule(subjectNames, 3);
        }
    }

    private void getMotivation() {
        System.out.println("\n" + aiEngine.getMotivation());
    }

    private void viewProfile() {
        currentStudent.display();
    }

    private void exitBot() {
        System.out.println("\n========================================");
        System.out.println("   Thank you for using StudyBot!        ");
        System.out.println("   Keep learning, keep growing!         ");
        System.out.println("   See you next time!                   ");
        System.out.println("========================================");
        isRunning = false;
        FileManager.saveToFile("data/users.txt", currentStudent.toFileString(), true);
    }
}