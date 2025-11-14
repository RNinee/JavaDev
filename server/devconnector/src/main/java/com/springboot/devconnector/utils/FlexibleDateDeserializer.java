package com.springboot.devconnector.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class FlexibleDateDeserializer extends JsonDeserializer<LocalDate> {

    private static final List<DateTimeFormatter> FORMATTERS = Arrays.asList(
            DateTimeFormatter.ISO_LOCAL_DATE,           // 2006-05-01
            DateTimeFormatter.ofPattern("M-d-yyyy"),    // 1-5-2006
            DateTimeFormatter.ofPattern("d-M-yyyy"),    // 5-1-2006
            DateTimeFormatter.ofPattern("MM-dd-yyyy"),  // 01-05-2006
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),  // 05-01-2006
            DateTimeFormatter.ofPattern("M/d/yyyy"),    // 1/5/2006
            DateTimeFormatter.ofPattern("d/M/yyyy"),    // 5/1/2006
            DateTimeFormatter.ofPattern("MM/dd/yyyy"),  // 01/05/2006
            DateTimeFormatter.ofPattern("dd/MM/yyyy")   // 05/01/2006
    );

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String date = p.getText();

        if (date == null || date.trim().isEmpty()) {
            return null;
        }

        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                return LocalDate.parse(date, formatter);
            } catch (DateTimeParseException e) {
                // Try next formatter
            }
        }

        throw new IOException("Unable to parse date: " + date +
                ". Supported formats: YYYY-MM-DD, M-d-yyyy, d-M-yyyy, MM-dd-yyyy, dd-MM-yyyy, and slash variants");
    }
}
