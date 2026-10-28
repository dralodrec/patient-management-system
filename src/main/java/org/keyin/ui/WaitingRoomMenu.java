package org.keyin.ui;

import org.keyin.model.Patient;
import org.keyin.service.PatientQueue;
import org.keyin.repository.PatientRepository;

import java.util.List;
import java.util.Scanner;

public class WaitingRoomMenu {
    private final PatientQueue queue = new PatientQueue();
    private final Scanner scanner = new Scanner(System.in);
    private final PatientRepository repository = new PatientRepository();

    public void run() {
        boolean running = true;

        while (running) {
            clearScreen();

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

                    if (patient != null) {
                        queue.addPatient(patient);
                        System.out.println("\nPatient added.");
                    }
                }

                case 2 -> {
                    Patient patient = queue.servePatient();

                    if (patient == null) {
                        System.out.println("\nNo patients waiting.");
                    } else {
                        System.out.println(
                                "Now serving: " + patient.getName()
                        );
                    }
                }

                case 3 -> {
                    Patient patient = readPatient();

                    if (patient != null) {
                        int position = readInt("Insert at position (1 = front): ");
                        queue.insertPatient(patient, position);
                        queue.printPatients();
                    }
                }

                case 4 -> queue.printPatients();

                case 0 -> {
                    running = false;
                    System.out.println("\nGoodbye.");
                }

                default -> System.out.println("\nInvalid menu option.");
            }

            if (running) {
                pause();
            }
        }
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void pause() {
        System.out.print("\nPress Enter to return to the main menu...");
        scanner.nextLine();
    }

    private Patient readPatient() {
        int id = readInt("Patient ID (0 = suggest an available ID): ");

        if (id < 0) {
            System.out.println("Patient ID must be positive.");
            return null;
        }

        if (id == 0) {
            id = repository.nextAvailableId();

            if (!confirm("Use available ID " + id + "?")) {
                return null;
            }
        }

        Patient existing = repository.findById(id);

        if (existing != null) {
            System.out.println("ID " + id + " belongs to "
                    + existing.getName() + ".");

            if (!confirm("Is this the returning patient?")) {
                System.out.println("Next available ID: "
                        + repository.nextAvailableId());
                return null;
            }

            if (queue.containsPatient(id)) {
                System.out.println("This patient is already waiting.");
                return null;
            }

            String reason = readText("Reason for today's visit: ");

            return new Patient(id, existing.getName(), reason);
        }

        String name = readText("Name: ");
        List<Patient> matches = repository.findByName(name);

        if (!matches.isEmpty()) {
            System.out.println("Registered patients with this name:");

            for (Patient match : matches) {
                System.out.println(match.getPatientId() + " | " + match.getName());
            }

            if (!confirm("Is this a different person with the same name?")) {
                System.out.println("Choose Add patient again and use the existing ID.");
                return null;
            }
        }

        String reason = readText("Reason for visit: ");
        Patient patient = new Patient(id, name, reason);
        repository.addPatient(patient);

        return patient;
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);

            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a valid whole number.");
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

            System.out.println("\nThis field cannot be empty.");
        }
    }

    private boolean confirm(String prompt) {
        while (true) {
            String answer = readText(prompt + " (y/n): ");

            if (answer.equalsIgnoreCase("y")) {
                return true;
            }

            if (answer.equalsIgnoreCase("n")) {
                return false;
            }

            System.out.println("Please enter y or n.");
        }
    }
}