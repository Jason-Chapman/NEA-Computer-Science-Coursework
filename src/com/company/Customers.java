package com.company;

import java.util.Date;

public class Customers {
    protected String fName;
    protected String sName;
    protected String email;
    protected String ID;
    protected Date dateOfBirth;

    public Customers(String fName, String sName, String email, String ID, Date dateOfBirth) {
        this.fName = fName;
        this.sName = sName;
        this.email = email;
        this.ID = ID;
        this.dateOfBirth = dateOfBirth;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
