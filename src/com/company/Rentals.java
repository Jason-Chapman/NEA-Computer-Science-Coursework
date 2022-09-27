package com.company;

import java.util.Date;

public class Rentals {
    protected int RentalID;
    protected int CustomerID;
    protected int MovieID;
    protected Date DateRented;
    protected Date DateDue;

    public Rentals(int rentalID, int customerID, int movieID, Date dateRented, Date dateDue) {
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

    public Date getDateRented() {
        return DateRented;
    }

    public void setDateRented(Date dateRented) {
        DateRented = dateRented;
    }

    public Date getDateDue() {
        return DateDue;
    }

    public void setDateDue(Date dateDue) {
        DateDue = dateDue;
    }
}
