package com.company;

import java.util.Date;

public class Rentals {
    protected int RentalID;
    protected int CustomerID;
    protected int MovieID;
    protected java.sql.Date DateRented;
    protected java.sql.Date DateDue;

    public Rentals(int rentalID, int customerID, int movieID, java.sql.Date dateRented, java.sql.Date dateDue) {
        RentalID = rentalID;
        CustomerID = customerID;
        MovieID = movieID;
        DateRented = dateRented;
        DateDue = dateDue;
    }

    public int getRentalID() {
        return RentalID;
    }

    public void setRentalID(int rentalID) {
        RentalID = rentalID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public int getMovieID() {
        return MovieID;
    }

    public void setMovieID(int movieID) {
        MovieID = movieID;
    }

    public java.sql.Date getDateRented() {
        return DateRented;
    }

    public void setDateRented(java.sql.Date dateRented) {
        DateRented = dateRented;
    }

    public java.sql.Date getDateDue() {
        return DateDue;
    }

    public void setDateDue(java.sql.Date dateDue) {
        DateDue = dateDue;
    }
}
