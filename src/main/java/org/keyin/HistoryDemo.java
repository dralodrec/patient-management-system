package org.keyin;

import org.keyin.model.Patient;
import org.keyin.model.PatientRecord;
import org.keyin.repository.PatientRepository;
import org.keyin.service.PatientHistory;

import java.time.LocalDate;

public class HistoryDemo {
    public static void main(String[] args) {
        PatientRepository repository = new PatientRepository();

        repository.addPatient(new Patient(101, "Alex", "Follow-up"));
        repository.addPatient(new Patient(102, "Betty", "Checkup"));

        PatientHistory alexHistory =
                repository.findHistoryByPatientId(101);

        alexHistory.addRecord(new PatientRecord(
                LocalDate.of(2026, 10, 20),
                "Seasonal allergies",
                "Follow-up scheduled."
        ));

        PatientHistory bettyHistory =
                repository.findHistoryByPatientId(102);

        System.out.println("Alex's record:");
        System.out.println(alexHistory.getCurrentRecord());

        System.out.println("\nBetty's record:");
        System.out.println(bettyHistory.getCurrentRecord());
    }
}