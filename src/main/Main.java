package main;

// These imports allow Main to see your other files
import services.*;
import models.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. Initialize the Bot
        StudyBot bot = new StudyBot();
        Scanner scanner = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println("   WELCOME TO AI STUDY BOT (OOP)      ");
        System.out.println("======================================");

        // 2. Start the Bot's main loop
        // This calls the start() method in your StudyBot.java file
        bot.start();

        System.out.println("Bot has shut down. Goodbye!");
    }
}