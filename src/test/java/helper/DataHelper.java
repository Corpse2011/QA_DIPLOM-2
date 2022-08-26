package helper;

import com.github.javafaker.Faker;
import data.first;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;

public class DataHelper {
    public static Faker faker = new Faker();

    public static String validName() {
        return faker.name().fullName();
    }//1

    public static String validMonth() {
        int monthNow;
        monthNow = LocalDate.now().getMonthValue();
        if (monthNow < 10) {
            NumberFormat f = new DecimalFormat("00");
            String month2 = String.valueOf(f.format(monthNow));
            return month2;
        } else {
            return String.valueOf(monthNow);
        }
    }

    public static String nonValidMonth() {
        LocalDate monthMinus;
        monthMinus = LocalDate.now().minusMonths(2);
        int month = monthMinus.getMonthValue();
        if (month < 10) {
            NumberFormat f = new DecimalFormat("00");
            String month2 = String.valueOf(f.format(month));
            return month2;
        } else {
            return String.valueOf(month);
        }
    }

    public static String validYear() {
        int year = LocalDate.now().getYear();
        return String.valueOf(year % 100);
    }

    public static String notValidYear() {
        int date = LocalDate.now().getYear();
        int year;
        year = date - 1;
        return String.valueOf(year % 100);
    }

    public static String cvcCode() {
        return String.valueOf(faker.number().numberBetween(100, 999));
    }

    public static String numberCards(int number) {
        String card = first.numberCard[number];
        return card;
    }

    private DataHelper() {
    }

    public static first validInfo() {
        return new first(validMonth(), validYear(), validName(), cvcCode());
    }
}