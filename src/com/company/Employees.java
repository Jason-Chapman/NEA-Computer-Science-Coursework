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
