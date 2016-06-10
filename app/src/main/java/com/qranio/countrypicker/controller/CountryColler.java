package com.qranio.countrypicker.controller;

import android.content.Context;
import android.util.Base64;

import com.qranio.countrypicker.R;
import com.qranio.countrypicker.model.Country;
import com.qranio.countrypicker.model.CountryComparator;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class CountryColler {

    private Context context;

    public CountryColler(Context context) {

        this.context = context;
    }

    public List<Country> loadAllCountries() {

        List<Country> countryList = new ArrayList<Country>();
        loadCountriesFromFile(countryList);
        Collections.sort(countryList, new CountryComparator());

        return countryList;
    }

    public List<Country> getCountryListByLocale(Locale locale) {

        List<Country> countriesList = new ArrayList();
        String[] isoCountries = locale.getISOCountries();

        for (String isoCountry : isoCountries) {

            countriesList.add(new Country(new Locale("", isoCountry).getDisplayCountry(), isoCountry));
        }

        return countriesList;
    }

    //==============================================================================================
    // Métodos privados
    //==============================================================================================

    private void loadCountriesFromFile(List<Country> countryList) {

        try {

            // Read from local file
            String countries = readFileAsString();
            JSONObject json = new JSONObject(countries);
            Iterator<?> keys = json.keys();

            // Add the data to all countries list
            while (keys.hasNext()) {

                String key = (String) keys.next();
                Country country = new Country();
                country.setCode(key);
                country.setName(json.getString(key));
                countryList.add(country);
            }

        } catch (Exception e) {
        }
    }

    private String readFileAsString() throws java.io.IOException {

        String base64 = context.getResources().getString(R.string.countries);
        byte[] data = Base64.decode(base64, Base64.DEFAULT);

        return new String(data, "UTF-8");
    }
}
