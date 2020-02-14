package com.dvse.geo2020.study;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dvse.geo2020.BaseRecyclerViewAdapter;
import com.dvse.geo2020.R;
import com.dvse.geo2020.models.Country;
import com.dvse.geo2020.models.CountryDetails;
import com.dvse.geo2020.repository.CountryRepository;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CountryActivity extends AppCompatActivity {

    public static final String COUNTRY_KEY = "country";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_activity);
        Country country = (Country) getIntent().getSerializableExtra(COUNTRY_KEY);
        if (country instanceof CountryDetails) {
            showCountryDetails((CountryDetails) country);
        } else {
            showCountry(country);
            new CountryRepository().getCountryDetail(country.alpha2Code, new Consumer<CountryDetails>() {
                @Override
                public void accept(CountryDetails countryDetails) {
                    showCountryDetails(countryDetails);
                }
            });
        }
    }

    void showCountry(Country country) {
        ((TextView) findViewById(R.id.name)).setText(String.format("%s (%s)", country.name, country.alpha2Code));
        ((TextView) findViewById(R.id.capital)).setText(country.capital);
        Glide.with(this).load(country.flag).into((ImageView) findViewById(R.id.flag));
    }

    void showCountryDetails(CountryDetails details) {
        showCountry(details);
        ((TextView) findViewById(R.id.area)).setText(String.format("%s km²", new DecimalFormat().format(details.area)));
        ((TextView) findViewById(R.id.population)).setText(String.format("%s", new DecimalFormat().format(details.population)));
        startUpdateTime(details);
        if (details.borders != null && details.borders.size() > 0) {
            RecyclerView recyclerView = findViewById(R.id.neighbours);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            Adapter adapter = new Adapter(details.borders);
            adapter.setOnItemClickListener(new Consumer<String>() {
                @Override
                public void accept(String country) {
                    new CountryRepository().getCountryDetail(country, new Consumer<CountryDetails>() {
                        @Override
                        public void accept(CountryDetails countryDetails) {
                            Intent intent = new Intent(CountryActivity.this, CountryActivity.class);
                            intent.putExtra(COUNTRY_KEY, countryDetails);
                            startActivity(intent);
                        }
                    });
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            findViewById(R.id.noNeighboursHint).setVisibility(View.VISIBLE);
        }
    }

    void updateTime(CountryDetails details) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        dateFormat.setTimeZone(details.timeZone);
        ((TextView) findViewById(R.id.time)).setText(String.format("%s (%s)", dateFormat.format(new Date()), details.timeZone.getID()));
    }

    void startUpdateTime(final CountryDetails details) {
        if (!isDestroyed()) {
            updateTime(details);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startUpdateTime(details);
                }
            }, 1000);
        }
    }

    static class Adapter extends BaseRecyclerViewAdapter<String, Adapter.VH> {

        public Adapter(List<String> items) {
            super(items);
        }

        @Override
        protected VH onCreateViewHolder(View view) {
            return new VH(view);
        }

        @Override
        protected View getView(ViewGroup parent, int viewType) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.country_boder_item, parent, false);
        }

        @Override
        protected void setupView(VH holder, int position, String item) {
            holder.textView.setText(item);
        }

        static class VH extends RecyclerView.ViewHolder {

            public VH(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.text);
            }

            TextView textView;
        }
    }
}