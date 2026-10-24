package org.keyin;

public class Main {
    public static void main(String[] args) {
        PatientQueue queue = new PatientQueue();

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

        queue.addPatient(new Patient(101, "Alex", "Headache"));
        queue.addPatient(new Patient(102, "Sam", "Cough"));

        queue.insertPatient(new Patient(103, "Mia", "Emergency"),1);

        queue.printPatients();

    }
}