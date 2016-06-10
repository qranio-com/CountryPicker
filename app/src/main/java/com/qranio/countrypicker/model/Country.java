package com.qranio.countrypicker.model;

import com.qranio.countrypicker.R;

import org.parceler.Parcel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Parcel
public class Country {

    String code;
    String name;
    transient static String preferedCountries[] = {"BR", "US", "ES", "PT", "CN"};

    public Country() {
    }

    public Country(String name, String code) {

        this.name = name;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static final ArrayList<Country> getPreferedCountries() {

        ArrayList<Country> countries = new ArrayList<Country>();
        List<Country> prefered = new ArrayList<Country>();
        String[] isoCountries = getIsoCountries();
        Locale locale = null;

        for (String isoContry : isoCountries) {

            locale = new Locale("", isoContry);

            if (isPreferedCountry(isoContry)) {

                prefered.add(new Country(locale.getDisplayCountry(), isoContry));

            } else {

                countries.add(new Country(locale.getDisplayCountry(), isoContry));
            }
        }

        countries.addAll(0, prefered);

        return countries;
    }

    public static String[] getIsoCountries() {

        return Locale.getISOCountries();
    }

    public int getResId() {

        try {

            String drawableName = "flag_" + getCode().toLowerCase(Locale.ENGLISH);
            Class res = R.drawable.class;
            Field field = res.getField(drawableName);
            int drawableId = field.getInt(null);

            return drawableId;

        } catch (Exception e) {
        }

        return -1;
    }

    //==============================================================================================
    // Métodos privados
    //==============================================================================================

    private static boolean isPreferedCountry(String isoContry) {

        for (String country : preferedCountries) {

            if (isoContry.equals(country)) {

                return true;
            }
        }

        return false;
    }
}