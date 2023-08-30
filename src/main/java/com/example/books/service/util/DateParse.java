package com.example.books.service.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParse {

    public static boolean isDateParseable(String inDate) {
        if (inDate != null && !inDate.isEmpty()) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                format.parse(inDate);
            } catch (DateTimeParseException e) {
                return false;
            }
            return true;
        } return false;
    }
    public static LocalDate dateParse(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }
}
