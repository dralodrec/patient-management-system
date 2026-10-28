package org.keyin.service;

import org.keyin.model.Patient;

public class PatientQueue {

    private static class Node {
        private Patient patient;
        private Node next;

        private Node(Patient patient) {
            this.patient = patient;
        }
    }

    private Node front;
    private Node rear;

    public void addPatient(Patient patient) {
        Node newNode = new Node(patient);

        if (front == null) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

    public Patient servePatient() {
        if (front == null) {
            return null;
        }

        Patient servedPatient = front.patient;
        front = front.next;

        if (front == null) {
            rear = null;
        }

        return servedPatient;
    }

    public void printPatients() {
        if (front == null) {
            System.out.println("No patients waiting.");
            return;
        }

        Node current = front;

        while (current != null) {
            Patient patient = current.patient;

            System.out.println(
                    patient.getPatientId() + " | "
                            + patient.getName() + " | "
                            + patient.getReasonForVisit()
            );

            current = current.next;
        }
    }

    public void insertPatient(Patient patient, int position) {
        if (position < 1) {
            System.out.println("Invalid position.");
            return;
        }

        Node newNode = new Node(patient);

        if (position == 1) {
            newNode.next = front;
            front = newNode;

            if (rear == null) {
                rear = newNode;
            }

            return;
        }

        Node current = front;

        for (int i = 1; i < position - 1; i++) {
            if (current == null) {
                System.out.println("Invalid position.");
                return;
            }

            current = current.next;
        }

        if (current == null) {
            System.out.println("Invalid position.");
            return;
        }

        newNode.next = current.next;
        current.next = newNode;

        if (newNode.next == null) {
            rear = newNode;
        }
    }

    public boolean containsPatient(int patientId) {
        Node current = front;

        while (current != null) {
            if (current.patient.getPatientId() == patientId) {
                return true;
            }

            current = current.next;
        }

        return false;
    }
}