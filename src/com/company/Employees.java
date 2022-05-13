package com.company;

public class Employees {
    protected String fName;
    protected String sName;
    protected String ID;

    public Employees(String fName, String sName, String ID) {
        this.fName = fName;
        this.sName = sName;
        this.ID = ID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}

// CREATES LOCALDATE OBJECT
// OBJECT FORMAT: Year, Month, Day
//LocalDate date = LocalDate.of(2022, 5, 9);

// PRINTS FULL DATE
// DATE FORMAT: YYYY-MM-DD
//System.out.println("Date: " + date);

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
