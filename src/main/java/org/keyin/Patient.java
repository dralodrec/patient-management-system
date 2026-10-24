package org.keyin;

public class Patient {
    private int patientId;
    private String name;
    private String reasonForVisit;

    public Patient(int patientId, String name, String reasonForVisit) {
        this.patientId = patientId;
        this.name = name;
        this.reasonForVisit = reasonForVisit;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public String getReasonForVisit() {
        return reasonForVisit;
    }
}