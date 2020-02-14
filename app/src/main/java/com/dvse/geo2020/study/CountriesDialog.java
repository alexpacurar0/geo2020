package com.dvse.geo2020.study;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dvse.geo2020.BaseRecyclerViewAdapter;
import com.dvse.geo2020.R;
import com.dvse.geo2020.models.Country;
import com.dvse.geo2020.repository.CountryRepository;

import java.util.List;

public class CountriesDialog {
    public void show(final Context context, String continent) {
        View view = LayoutInflater.from(context).inflate(R.layout.countries, null, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        final Adapter adapter = new Adapter(null);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new Consumer<Country>() {
            @Override
            public void accept(Country country) {
                Intent intent = new Intent(context, CountryActivity.class);
                intent.putExtra(CountryActivity.COUNTRY_KEY, country);
                context.startActivity(intent);
            }
        });
        new CountryRepository().getCountries(continent, new Consumer<List<Country>>() {
            @Override
            public void accept(List<Country> countries) {
                adapter.setItems(countries);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        new AlertDialog.Builder(context)
                .setTitle(continent)
                .setView(view)
                .show();
    }

    static class Adapter extends BaseRecyclerViewAdapter<Country, Adapter.VH> {

        public Adapter(List<Country> countries) {
            super(countries);
        }

        @Override
        protected VH onCreateViewHolder(View view) {
            return new VH(view);
        }

        @Override
        protected View getView(ViewGroup parent, int viewType) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
        }

        @Override
        protected void setupView(VH holder, int position, Country item) {
            holder.name.setText(item.name);
            holder.capital.setText(item.capital);
            Glide.with(holder.itemView.getContext()).load(item.flag).into(holder.flag);
        }

        static class VH extends RecyclerView.ViewHolder {

            public VH(@NonNull View itemView) {
                super(itemView);
                this.name = itemView.findViewById(R.id.name);
                this.capital = itemView.findViewById(R.id.capital);
                this.flag = itemView.findViewById(R.id.flag);
            }

            TextView name;
            TextView capital;
            ImageView flag;
        }
    }
}