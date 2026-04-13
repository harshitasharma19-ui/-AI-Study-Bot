package services;

import java.util.*;

public class StudyScheduler {

    public void suggestSchedule(List<String> subjects, int hoursPerDay) {
        System.out.println("\nSUGGESTED STUDY SCHEDULE");
        System.out.println("========================================");

        if (subjects.isEmpty()) {
            System.out.println("No subjects added yet!");
            return;
        }

        int minutesPerSubject = (hoursPerDay * 60) / subjects.size();

        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"};

        for (String day : days) {
            System.out.println("\n" + day + ":");
            Collections.shuffle(subjects);
            int startHour = 9;
            for (String subject : subjects) {
                int endMinute = startHour * 60 + minutesPerSubject;
                System.out.printf("   %02d:00 - %02d:%02d  ->  %s%n",
                        startHour, endMinute / 60, endMinute % 60, subject);
                startHour = endMinute / 60;
            }
            System.out.println("   " + String.format("%02d:00", startHour) +
                    " -> Break & Review");
        }

        System.out.println("\nTips:");
        System.out.println("   * Take 5-min breaks every 25 minutes");
        System.out.println("   * Review difficult topics before sleeping");
        System.out.println("   * Practice coding daily!");
        System.out.println("========================================");
    }
}