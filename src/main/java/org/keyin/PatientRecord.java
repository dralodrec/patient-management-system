package org.keyin;

import java.time.LocalDate;

public record PatientRecord(
        LocalDate visitDate,
        String diagnosis,
        String treatmentNotes
) {
}