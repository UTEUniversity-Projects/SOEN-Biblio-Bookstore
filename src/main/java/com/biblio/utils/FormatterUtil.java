package com.biblio.utils;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatterUtil {

    public static LocalDateTime toLocalDateTime(String dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return LocalDate.parse(dateTime, formatter).atStartOfDay();
    }

    public static String toDateTimeString(LocalDateTime dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateTime.format(formatter);
    }

    public static String percent(Double percent){
        DecimalFormat percentFormatter = new DecimalFormat("#.0");
        return percentFormatter.format(percent);
    }

    public static String description(String description, Integer length){
        if (description != null && description.length() > length) {
            return description.substring(0, length) + "...";
        }
        return description;
    }

    public static String commaNumber(Long count){
        return String.format(Locale.US, "%,d", count);
    }

    public static String dotNumber(Long finance){
        return String.format("%,d", finance);
    }

    public static String rating(Double rating){
        return String.format("%.1f", rating);
    }

}
