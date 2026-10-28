package org.keyin;

import org.keyin.model.PatientRecord;
import org.keyin.service.PatientHistory;
import org.keyin.service.PatientQueue;
import org.keyin.ui.WaitingRoomMenu;

import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class Main {
    public static void main(String[] args) {
        PatientQueue queue = new PatientQueue();
        PatientHistory history = new PatientHistory();

        WaitingRoomMenu menu = new WaitingRoomMenu();
        menu.run();

//        queue.addPatient(new Patient(101, "Alex", "Headache"));
//        queue.addPatient(new Patient(102, "Sam", "Cough"));
//        queue.addPatient(new Patient(103, "Mia", "Sore throat"));
//
//        System.out.println("Before serving:");
//        queue.printPatients();
//
//        queue.servePatient();
//
//        System.out.println("\nAfter serving:");
//        queue.printPatients();

//        queue.addPatient(new Patient(101, "Alex", "Headache"));
//        queue.addPatient(new Patient(102, "Sam", "Cough"));
//
//        queue.insertPatient(new Patient(103, "Mia", "Emergency"),1);
//
//        queue.printPatients();
//
//
//        history.addRecord(new PatientRecord(
//                LocalDate.of(2026, 10, 20),
//                "Seasonal allergies",
//                "Follow-up scheduled."
//        ));
//
//        history.addRecord(new PatientRecord(
//                LocalDate.of(2026, 10, 24),
//                "Follow-up visit",
//                "Symptoms improved."
//        ));

//        System.out.println("Newest: " + history.getCurrentRecord());
//        System.out.println("Previous: " + history.previousRecord());
//        System.out.println("Previous again: " + history.previousRecord());
//        System.out.println("Still viewing: " + history.getCurrentRecord());
//        System.out.println("Next: " + history.nextRecord());
//        System.out.println("Oldest: " + history.oldestRecord());
//        System.out.println("Newest: " + history.newestRecord());

//        displayRecord("NEWEST RECORD", history.getCurrentRecord());
//        displayRecord("PREVIOUS RECORD", history.previousRecord());
//        displayRecord("PREVIOUS RECORD", history.previousRecord());
//        displayRecord("CURRENT RECORD", history.getCurrentRecord());
//        displayRecord("NEXT RECORD", history.nextRecord());
//        displayRecord("OLDEST RECORD", history.oldestRecord());
//        displayRecord("NEWEST RECORD", history.newestRecord());
//
//        displayHistory(history);

    }

    private static void displayRecord(String heading, PatientRecord record) {
        System.out.println("\n" + heading);

        if (record == null) {
            System.out.println("No record available for this action.");
            return;
        }

        DateTimeFormatter dateFormat =
                DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

        String rowFormat = "%-15s | %-25s | %s%n";

        System.out.printf(rowFormat, "Visit Date", "Diagnosis", "Treatment Notes");
        System.out.println("-".repeat(80));

        System.out.printf(
                rowFormat,
                record.visitDate().format(dateFormat),
                record.diagnosis(),
                record.treatmentNotes()
        );
    }

    private static void displayHistory(PatientHistory history) {
        DateTimeFormatter dateFormat =
                DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

        PatientRecord record = history.newestRecord();

        if (record == null) {
            System.out.println("No history records available.");
            return;
        }

        String rowFormat = "%-15s | %-25s | %s%n";

        System.out.println("\nPATIENT HISTORY — LATEST TO OLDEST");
        System.out.printf(rowFormat, "Visit Date", "Diagnosis", "Treatment Notes");
        System.out.println("-".repeat(80));

        while (record != null) {
            System.out.printf(
                    rowFormat,
                    record.visitDate().format(dateFormat),
                    record.diagnosis(),
                    record.treatmentNotes()
            );

            record = history.previousRecord();
        }
    }
}