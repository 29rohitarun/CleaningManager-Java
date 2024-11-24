package edu.ncsu.csc316.cleaning.ui;

import java.io.FileNotFoundException;
import edu.ncsu.csc316.cleaning.manager.*;
import java.util.*;

public class CleaningManagerUI {

    public static void main(String[] args) {
        // Check if the correct number of command line arguments is provided
        if (args.length != 2) {
            System.out.println("Usage: java CleaningManagerUI <pathToRoomFile> <pathToLogFile>");
            return;
        }

        // Extract command line arguments
        String pathToRoomFile = args[0];
        String pathToLogFile = args[1];

        try {
            // Create a ReportManager with the specified input files
            ReportManager reportManager = new ReportManager(pathToRoomFile, pathToLogFile);

            // Initialize scanner for user input
            Scanner scanner = new Scanner(System.in);

            // Loop to let the user choose the type of report until they choose to exit
            boolean exit = false;
            while (!exit) {
                System.out.println("\nChoose a report type:");
                System.out.println("1. Frequency Report");
                System.out.println("2. Room Report");
                System.out.println("3. Vacuum Bag Report");
                System.out.println("4. Exit");

                // Get user choice
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Frequency Report
                        System.out.print("Enter the number of rooms: ");
                        int number = scanner.nextInt();
                        String frequencyReport = reportManager.getFrequencyReport(number);
                        System.out.println(frequencyReport);
                        break;

                    case 2:
                        // Room Report
                        String roomReport = reportManager.getRoomReport();
                        System.out.println(roomReport);
                        break;

                    case 3:
                        // Vacuum Bag Report
                        System.out.print("Enter the timestamp (MM/DD/YYYY HH:MM:SS): ");
                        scanner.nextLine(); // consume the newline character
                        String timestamp = scanner.nextLine();
                        String vacuumBagReport = reportManager.getVacuumBagReport(timestamp);
                        System.out.println(vacuumBagReport);
                        break;

                    case 4:
                        // Exit
                        exit = true;
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                        break;
                }
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}