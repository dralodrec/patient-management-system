package org.keyin.service;

import org.keyin.model.PatientRecord;

public class PatientHistory {

    private static class Node {
        private PatientRecord record;
        private Node next;
        private Node previous;

        private Node(PatientRecord record) {
            this.record = record;
        }
    }

    private Node head;
    private Node tail;
    private Node current;

//    public void addRecord(PatientRecord record) {
//        Node newNode = new Node(record);
//
//        if (head == null) {
//            head = newNode;
//            tail = newNode;
//        } else {
//            tail.next = newNode;
//            newNode.previous = tail;
//            tail = newNode;
//        }
//
//        current = newNode;
//    }

    public void addRecord(PatientRecord record) {
        Node newNode = new Node(record);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            Node position = head;

            // Find the first record with a later visit date.
            while (position != null &&
                    !position.record.visitDate().isAfter(record.visitDate())) {
                position = position.next;
            }

            if (position == head) {
                // Insert before the oldest record.
                newNode.next = head;
                head.previous = newNode;
                head = newNode;

            } else if (position == null) {
                // Insert after the newest record.
                tail.next = newNode;
                newNode.previous = tail;
                tail = newNode;

            } else {
                // Insert between two existing records.
                newNode.previous = position.previous;
                newNode.next = position;

                position.previous.next = newNode;
                position.previous = newNode;
            }
        }

        current = newNode;
    }

    public PatientRecord getCurrentRecord() {
        if (current == null) {
            return null;
        }

        return current.record;
    }

    public PatientRecord previousRecord() {
        if (current == null || current.previous == null) {
            return null;
        }

        current = current.previous;
        return current.record;
    }

    public PatientRecord nextRecord() {
        if (current == null || current.next == null) {
            return null;
        }

        current = current.next;
        return current.record;
    }

    public PatientRecord newestRecord() {
        current = tail;
        return getCurrentRecord();
    }

    public PatientRecord oldestRecord() {
        current = head;
        return getCurrentRecord();
    }
}