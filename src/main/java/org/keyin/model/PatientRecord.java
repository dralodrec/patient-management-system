package org.keyin.model;

import java.time.LocalDate;

    public record PatientRecord(
            LocalDate visitDate,
            String diagnosis,
            String treatmentNotes
    )
    {

}