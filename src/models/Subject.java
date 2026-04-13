package models;

import interfaces.Displayable;
import java.util.ArrayList;
import java.util.List;

public class Subject implements Displayable {
    private final String subjectId;
    private final String name;
    private final String description;
    private final List<Topic> topics;

    public Subject(String subjectId, String name, String description) {
        this.subjectId = subjectId;
        this.name = name;
        this.description = description;
        this.topics = new ArrayList<>();
    }

    public String getSubjectId() { return subjectId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<Topic> getTopics() { return topics; }

    public void addTopic(Topic topic) {
        topics.add(topic);
    }

    public Topic findTopic(String topicName) {
        return topics.stream()
                .filter(t -> t.getName().equalsIgnoreCase(topicName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void display() {
        System.out.println("\nSubject: " + name);
        System.out.println("   Description: " + description);
        System.out.println("   Topics (" + topics.size() + "):");
        for (int i = 0; i < topics.size(); i++) {
            System.out.println("   " + (i + 1) + ". " + topics.get(i).getName());
        }
    }

    @Override
    public String getSummary() {
        return name + " (" + topics.size() + " topics)";
    }
}

