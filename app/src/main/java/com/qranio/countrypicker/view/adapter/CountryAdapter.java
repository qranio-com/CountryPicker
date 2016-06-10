package com.qranio.countrypicker.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qranio.countrypicker.R;
import com.qranio.countrypicker.interfaces.CountryPickerListener;
import com.qranio.countrypicker.model.Country;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater inflater;
    private CountryPickerListener listener;
    private List<Country> list;
    private Context context;

    public CountryAdapter(Context context) {

        this.context = context;
        list = new ArrayList(0);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_country, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        Holder holder = (Holder) viewHolder;
        Country country = getItem(position);

        holder.tvCountry.setText(country.getName());
        int resId = country.getResId();

        if (resId != -1) {

            holder.ivCountry.setImageResource(resId);
        }
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public void setListener(CountryPickerListener listener) {

        this.listener = listener;
    }

    public final Country getItem(int position) {

        if (position >= list.size()) {

            return list.get(list.size() - 1);
        }

        return (position < 0) ? list.get(0) : list.get(position);
    }

    public Country remove(int position) {

        int pos = (position < 0) ? 0 : (position >= list.size()) ? (list.size() - 1) : position;
        return list.remove(pos);
    }

    public void addItems(Country... countries) {

        list.addAll(new ArrayList(Arrays.asList(countries)));
    }

    public void clear() {

        list.clear();

    }

    public List<Country> getItems() {

        return list;
    }

    protected class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivCountry;
        TextView tvCountry;

        public Holder(View view) {

            super(view);
            ivCountry = (ImageView) view.findViewById(R.id.iv_country);
            tvCountry = (TextView) view.findViewById(R.id.tv_country);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            onItemclicked(getAdapterPosition(), getItem(getAdapterPosition()));
        }
    }

    //==============================================================================================
    // Métodos privados
    //==============================================================================================

    private final void onItemclicked(int position, Country country) {

        if (listener != null) {

            listener.onItemClick(country, position);
        }
    }
}
