//package ru.javawebinar.topjava.util;
//
//import org.springframework.format.Formatter;
//
//import java.text.ParseException;
//import java.time.LocalDate;
//import java.util.Locale;
//
//public class DateFormater extends Formatter<LocalDate> {
//
//    @Override
//    public LocalDate parse(String s, Locale locale) throws ParseException {
//        if (s.length() == 0) {
//            return null;
//        }
//        return DateTimeUtil.parseLocalDate(s);
//    }
//
//    @Override
//    public String print(LocalDate localDate, Locale locale) {
//        return localDate.toString();
//    }
//}
