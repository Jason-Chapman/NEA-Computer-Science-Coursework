package com.company;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // CREATES LOCALDATE OBJECT
        // OBJECT FORMAT: Year, Month, Day
        LocalDate date = LocalDate.of(2022, 5, 9);

        // PRINTS FULL DATE
        // DATE FORMAT: YYYY-MM-DD
        System.out.println("Date: " + date);

        // LOCALDATE METHODS
        //
        // .getYear() - Gets the year as an 'int'. Eg "2022"
        //
        // .getMonth() - Gets the month as a 'month'. Eg "MAY"
        // .getMonthValue - Gets the month as an 'int'. Eg "5"
        //
        // .getDayOfWeek() - Gets the day was a 'string'. Eg "Monday"
        // .getDayOfMonth() - Gets the day as an 'int' in range 1-31. Eg "9"
        // .getDayOfYear() - Gets the day as an 'int' in range 1-365. Eg "35"
    }

    public static String getInput(String prompt) {
        System.out.print(prompt);
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }
}
