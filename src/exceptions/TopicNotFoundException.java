package exceptions;

public class TopicNotFoundException extends Exception {
    private final String topicName;

    public TopicNotFoundException(String topicName) {
        super("Topic not found: " + topicName);
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }
}

