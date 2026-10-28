package org.keyin.ui;

import org.keyin.model.Patient;
import org.keyin.service.PatientQueue;

import java.util.Scanner;

public class WaitingRoomMenu {
    private final PatientQueue queue = new PatientQueue();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== PATIENT WAITING ROOM ===");
            System.out.println("1. Add patient");
            System.out.println("2. Serve next patient");
            System.out.println("3. Insert emergency patient");
            System.out.println("4. Display waiting patients");
            System.out.println("0. Exit");

            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1 -> {
                    Patient patient = readPatient();
                    queue.addPatient(patient);
                    System.out.println("Patient added.");
                }

                case 2 -> {
                    Patient patient = queue.servePatient();

                    if (patient == null) {
                        System.out.println("No patients waiting.");
                    } else {
                        System.out.println(
                                "Now serving: " + patient.getName()
                        );
                    }
                }

                case 3 -> {
                    Patient patient = readPatient();
                    int position = readInt("Insert at position (1 = front): ");

                    queue.insertPatient(patient, position);
                    queue.printPatients();
                }

                case 4 -> queue.printPatients();

                case 0 -> {
                    running = false;
                    System.out.println("Goodbye.");
                }

                default -> System.out.println("Invalid menu option.");
            }
        }
    }

    private Patient readPatient() {
        int patientId = readInt("Patient ID: ");
        String name = readText("Name: ");
        String reason = readText("Reason for visit: ");

        return new Patient(patientId, name, reason);
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);

            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("This field cannot be empty.");
        }
    }
}