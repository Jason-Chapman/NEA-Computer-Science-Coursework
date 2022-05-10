package com.company;

public class Movies {
    protected String name;
    protected String director;
    protected String ID;
    protected int ageRating;
    protected boolean rented;

    public Movies(String name, String director, String ID, int ageRating, boolean rented) {
        this.name = name;
        this.director = director;
        this.ID = ID;
        this.ageRating = ageRating;
        this.rented = rented;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(int ageRating) {
        this.ageRating = ageRating;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }
}
