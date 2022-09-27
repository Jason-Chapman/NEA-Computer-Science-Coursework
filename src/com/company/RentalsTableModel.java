package com.company;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class RentalsTableModel extends AbstractTableModel {
    private List<Rentals> data;
    private List<String> columnNames = new ArrayList<String>();

    public RentalsTableModel(Collection<? extends Rentals> rentals) {
        data = new ArrayList<>(rentals);
        columnNames.add("Rental ID");
        columnNames.add("Customer ID");
        columnNames.add("Movie ID");
        columnNames.add("Date Rented");
        columnNames.add("Date Due");
    }

    @Override
    public Class<?> getColumnClass(int col) {
        return (col == 3 || col == 4) ? Date.class : Integer.class;
    }

    @Override
    public int getRowCount() { return data.size(); }

    @Override
    public int getColumnCount() { return columnNames.size(); }

    @Override
    public String getColumnName(int ix) { return columnNames.get(ix); }

    @Override
    public Object getValueAt(int row, int col) {
        Rentals rental = data.get(row);
        switch (col) {
            case 0: return rental.getRentalID();
            case 1: return rental.getCustomerID();
            case 2: return rental.getMovieID();
            case 3: return rental.getDateRented();
            case 4: return rental.getDateDue();
        }
        return null;
    }
}