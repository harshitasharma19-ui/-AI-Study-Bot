package services;

import java.util.*;

public class AIEngine {
    private Map<String, List<String>> knowledgeBase;
    private Map<String, String> conversationPatterns;
    private List<String> motivationalQuotes;

    public AIEngine() {
        knowledgeBase = new HashMap<>();
        conversationPatterns = new HashMap<>();
        motivationalQuotes = new ArrayList<>();
        initializeKnowledge();
    }

    private void initializeKnowledge() {
        // Java OOP Knowledge
        knowledgeBase.put("oop", Arrays.asList(
                "OOP stands for Object-Oriented Programming.",
                "Four pillars: Encapsulation, Inheritance, Polymorphism, Abstraction.",
                "OOP models real-world entities as objects."
        ));

        knowledgeBase.put("class", Arrays.asList(
                "A class is a blueprint for creating objects.",
                "It defines attributes (fields) and behaviors (methods).",
                "Example: class Car { String color; void drive() {} }"
        ));

        knowledgeBase.put("object", Arrays.asList(
                "An object is an instance of a class.",
                "Created using 'new' keyword: Car myCar = new Car();",
                "Each object has its own copy of instance variables."
        ));

        knowledgeBase.put("inheritance", Arrays.asList(
                "Inheritance allows a class to inherit from another class.",
                "Uses 'extends' keyword: class Dog extends Animal {}",
                "Promotes code reusability.",
                "Types: Single, Multilevel, Hierarchical."
        ));

        knowledgeBase.put("polymorphism", Arrays.asList(
                "Polymorphism means 'many forms'.",
                "Compile-time: Method Overloading.",
                "Runtime: Method Overriding.",
                "Example: Animal a = new Dog(); a.makeSound();"
        ));

        knowledgeBase.put("encapsulation", Arrays.asList(
                "Wrapping data and methods in a single unit.",
                "Use private fields + public getters/setters.",
                "Also known as Data Hiding."
        ));

        knowledgeBase.put("abstraction", Arrays.asList(
                "Hides implementation details, shows only functionality.",
                "Achieved via abstract classes and interfaces.",
                "Abstract class: can have abstract + concrete methods."
        ));

        knowledgeBase.put("interface", Arrays.asList(
                "Interface is a contract classes must follow.",
                "A class 'implements' an interface.",
                "Supports multiple inheritance in Java."
        ));

        knowledgeBase.put("exception", Arrays.asList(
                "Exception disrupts normal program flow.",
                "Checked: must be handled (IOException).",
                "Unchecked: RuntimeException subclasses.",
                "Use try-catch-finally or throws."
        ));

        knowledgeBase.put("collection", Arrays.asList(
                "Framework provides: List, Set, Map, Queue.",
                "ArrayList: dynamic array, fast access.",
                "HashMap: key-value pairs, O(1) lookup."
        ));

        knowledgeBase.put("constructor", Arrays.asList(
                "Initializes an object when created.",
                "Same name as class, no return type.",
                "Can be default or parameterized."
        ));

        knowledgeBase.put("static", Arrays.asList(
                "Static belongs to the class, not instances.",
                "Static method: called without object.",
                "Static variable: shared across all objects."
        ));

        knowledgeBase.put("array", Arrays.asList(
                "Fixed-size collection of same type elements.",
                "Declared: int[] arr = new int[5];",
                "Zero-indexed, length property."
        ));

        knowledgeBase.put("string", Arrays.asList(
                "String is immutable in Java.",
                "StringBuilder for mutable strings.",
                "Common: length(), charAt(), substring(), equals()."
        ));

        // Conversation patterns
        conversationPatterns.put("hello", "Hello! I'm your Study Bot. How can I help?");
        conversationPatterns.put("hi", "Hi there! Ready to study? Ask me anything!");
        conversationPatterns.put("help", "I can help with:\n  1. Explaining topics\n  2. Quizzes\n  3. Flashcards\n  4. Study tips\nAsk about any Java/OOP concept!");
        conversationPatterns.put("thanks", "You're welcome! Keep up the great work!");
        conversationPatterns.put("bye", "Goodbye! Consistency is key! See you soon!");
        conversationPatterns.put("difficult", "Break it into smaller parts. Which concept confuses you?");
        conversationPatterns.put("tip", "Study Tip: Pomodoro technique - 25 min study, 5 min break!");

        // Motivational quotes
        motivationalQuotes.addAll(Arrays.asList(
                "'The expert in anything was once a beginner.' - Helen Hayes",
                "'Learning never exhausts the mind.' - Leonardo da Vinci",
                "'Education is the passport to the future.' - Malcolm X",
                "'The only way to do great work is to love what you do.' - Steve Jobs",
                "'Believe you can and you're halfway there.' - Theodore Roosevelt"
        ));
    }

    public String getResponse(String userInput) {
        String input = userInput.toLowerCase().trim();

        for (Map.Entry<String, String> pattern : conversationPatterns.entrySet()) {
            if (input.contains(pattern.getKey())) {
                return pattern.getValue();
            }
        }

        List<String> responses = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : knowledgeBase.entrySet()) {
            if (input.contains(entry.getKey())) {
                responses.addAll(entry.getValue());
            }
        }

        if (!responses.isEmpty()) {
            StringBuilder sb = new StringBuilder("Here's what I know:\n\n");
            for (String response : responses) {
                sb.append("  * ").append(response).append("\n");
            }
            sb.append("\nWould you like me to quiz you on this topic?");
            return sb.toString();
        }

        return "I'm not sure about that. Try asking about:\n" +
                "   OOP, Classes, Objects, Inheritance, Polymorphism,\n" +
                "   Encapsulation, Abstraction, Interfaces, Exceptions,\n" +
                "   Collections, Constructors, Static, Arrays, Strings\n" +
                "   Or type 'help' for more options!";
    }

    public String getMotivation() {
        Random rand = new Random();
        return motivationalQuotes.get(rand.nextInt(motivationalQuotes.size()));
    }

    public String suggestStudyTopic(Map<String, Double> subjectScores) {
        String weakest = null;
        double lowestScore = 100;

        for (Map.Entry<String, Double> entry : subjectScores.entrySet()) {
            if (entry.getValue() < lowestScore) {
                lowestScore = entry.getValue();
                weakest = entry.getKey();
            }
        }

        if (weakest != null && lowestScore < 70) {
            return "Suggestion: Focus on '" + weakest +
                    "' - your average score is " + String.format("%.0f", lowestScore) +
                    "%. Let me help you improve!";
        }
        return "Great job! You're doing well in all subjects!";
    }

    public void addKnowledge(String keyword, String information) {
        knowledgeBase.computeIfAbsent(keyword.toLowerCase(), k -> new ArrayList<>()).add(information);
        System.out.println("Knowledge added for: " + keyword);
    }
}