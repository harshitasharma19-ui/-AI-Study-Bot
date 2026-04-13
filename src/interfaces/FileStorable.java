package interfaces;

public interface FileStorable {
    String toFileString();
    void fromFileString(String data);
}
