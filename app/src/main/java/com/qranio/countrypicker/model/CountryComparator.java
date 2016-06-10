package com.qranio.countrypicker.model;

import java.util.Comparator;

public class CountryComparator implements Comparator<Country> {
    @Override
    public int compare(Country lhs, Country rhs) {

        //Support sorting the countries list
        return lhs.getName().compareTo(rhs.getName());
    }
}
