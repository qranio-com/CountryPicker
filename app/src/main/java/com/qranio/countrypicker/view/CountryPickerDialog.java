package com.qranio.countrypicker.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.qranio.countrypicker.R;
import com.qranio.countrypicker.controller.CountryColler;
import com.qranio.countrypicker.controller.SearchController;
import com.qranio.countrypicker.interfaces.CountryPickerListener;
import com.qranio.countrypicker.model.Country;
import com.qranio.countrypicker.model.FilterCountries;
import com.qranio.countrypicker.view.adapter.CountryAdapter;

import org.parceler.Parcels;

import java.util.List;
import java.util.Locale;

public class CountryPickerDialog extends DialogFragment {

    public static final String TAG_COUNTRY_PICKER = "tag_country_picker";
    private static final String KEY_LIST = "key_list";
    private static final String KEY_PATTERN = "key_pattern";

    private CountryColler countryColler;
    private CountryAdapter adapter;
    private CountryPickerListener listener;
    private LinearLayoutManager linearLayoutManager;
    private SearchController<Country> searchController;
    private List<Country> countryList;
    private String lastPattern;

    // Views
    View view;
    private EditText etSearch;
    private RecyclerView rvList;

    public CountryPickerDialog() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.country_picker, container, false);
        adapter = new CountryAdapter(getContext());
        countryColler = new CountryColler(getContext());
        setScreenComponents();
        setListeners();

        if (savedInstanceState == null) {

            setCountriesAdapter();
        }

        return view;
    }

    public void setCountryList(List<Country> countryList) {

        this.countryList = countryList;
    }

    public List<Country> getCountryListByLocale(@NonNull Locale locale) {

        return countryColler.getCountryListByLocale(locale);
    }

    public void addCountries(Country... countries) {

        adapter.addItems(countries);
    }

    public Country remove(int position) {

        return adapter.remove(position);
    }

    public void setListener(CountryPickerListener listener) {

        this.listener = listener;
    }

    public void showDialog(FragmentManager supportFragmentManager) {

        show(supportFragmentManager, TAG_COUNTRY_PICKER);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {

        if (bundle != null) {

            boolean isStarted = searchController != null && searchController.isStarted();
            List<Country> list = isStarted ? searchController.getItensList() : adapter.getItems();

            bundle.putParcelable(KEY_LIST, Parcels.wrap(list));
            bundle.putString(KEY_PATTERN, lastPattern);
        }

        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle bundle) {

        super.onViewStateRestored(bundle);

        if (bundle == null) {

            return;
        }

        String pattern = bundle.getString(KEY_PATTERN);
        countryList = Parcels.unwrap(bundle.getParcelable(KEY_LIST));
        setCountriesAdapter();
        lastPattern = null;
        search(pattern);
    }

    //==============================================================================================
    // Métodos privados
    //==============================================================================================

    private SearchController.SearchResultListener<Country> searchResultListener = new SearchController.SearchResultListener<Country>() {

        @Override
        public void searchResult(List<Country> itens) {

            adapter.clear();
            adapter.addItems(itens.toArray(new Country[itens.size()]));
            adapter.notifyDataSetChanged();
        }
    };

    private TextWatcher etSearchTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            search(s.toString());
        }
    };

    private CountryPickerListener countryPickerListener = new CountryPickerListener() {
        @Override
        public void onItemClick(Country country, int position) {

            if (listener != null) {

                listener.onItemClick(country, position);
            }

            dismiss();
        }
    };

    private void setListeners() {

        adapter.setListener(countryPickerListener);
        etSearch.addTextChangedListener(etSearchTextWatcher);
    }

    private void setScreenComponents() {

        etSearch = (EditText) view.findViewById(R.id.et_search);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvList = (RecyclerView) view.findViewById(R.id.rv_list);
        rvList.setLayoutManager(linearLayoutManager);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setAdapter(adapter);
    }

    private void setCountriesAdapter() {

        adapter.clear();

        if (countryList != null) {

            adapter.addItems(countryList.toArray(new Country[countryList.size()]));
        }

        adapter.notifyDataSetChanged();
    }

    private void search(String pattern) {

        if (countryList == null) {

            return;
        }

        if (searchController == null) {

            searchController = new SearchController(countryList, searchResultListener);
        }

        if (pattern == null || pattern.isEmpty()) {

            lastPattern = null;
            searchController.clearFilter();

        } else if (!pattern.equals(lastPattern)) {

            lastPattern = pattern;
            searchController.doSearchApplyFilter(new FilterCountries(lastPattern));
        }
    }
}
