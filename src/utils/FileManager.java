package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public static void saveToFile(String filename, String data, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, append))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    public static List<String> readFromFile(String filename) {
        List<String> lines = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) return lines;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return lines;
    }

    public static void clearFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("");
        } catch (IOException e) {
            System.out.println("Error clearing file: " + e.getMessage());
        }
    }

    public static void initializeDataDirectory() {
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("Data directory created.");
        }
    }
}