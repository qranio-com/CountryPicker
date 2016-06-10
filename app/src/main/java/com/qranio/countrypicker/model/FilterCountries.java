package com.qranio.countrypicker.model;

import com.qranio.countrypicker.interfaces.Filter;

import java.text.Normalizer;

public class FilterCountries implements Filter<Country> {

    private String[] nameList;
    private String comp;

    public FilterCountries(String... nameItens) {

        this.nameList = nameItens;
    }

    @Override
    public boolean match(Country country) {

        for (String name : nameList) {

            comp = normaLize(country.getName());
            name = normaLize(name);

            if (comp.contains(name)) {

                return true;
            }
        }

        return false;
    }

    private String normaLize(String item) {

        String temp = Normalizer.normalize(item, Normalizer.Form.NFD);
        return temp.replaceAll("[^\\p{ASCII}]", "").toLowerCase();
    }
}
