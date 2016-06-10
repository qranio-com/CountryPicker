package com.qranio.countrypicker.interfaces;

import com.qranio.countrypicker.model.Country;

public interface CountryPickerListener {

    void onItemClick(Country country, int position);
}
