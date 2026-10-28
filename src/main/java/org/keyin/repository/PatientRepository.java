package org.keyin.repository;

import org.keyin.model.Patient;
import java.util.ArrayList;
import java.util.List;

public class PatientRepository {
    private final List<Patient> patients = new ArrayList<>();

    public Patient findById(int id) {
        for (Patient patient : patients) {
            if (patient.getPatientId() == id) {
                return patient;
            }
        }

        return null;
    }

    public List<Patient> findByName(String name) {
        List<Patient> matches = new ArrayList<>();

        for (Patient patient : patients) {
            if (patient.getName().equalsIgnoreCase(name.trim())) {
                matches.add(patient);
            }
        }

        return matches;
    }

    public int nextAvailableId() {
        int highestId = 0;

        for (Patient patient : patients) {
            if (patient.getPatientId() > highestId) {
                highestId = patient.getPatientId();
            }
        }

        return highestId + 1;
    }

    public void addPatient(Patient patient) {
        if (findById(patient.getPatientId()) != null) {
            throw new IllegalArgumentException("Patient ID already exists.");
        }

        patients.add(patient);
    }
}