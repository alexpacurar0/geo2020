package com.dvse.geo2020.repository;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.core.util.Consumer;

import com.dvse.geo2020.models.Country;

import java.io.InputStream;
import java.util.List;

public class CountryRepository {

    @SuppressLint("StaticFieldLeak")
    public void getCountries(final String continent, final Consumer<List<Country>> callback) {
        new AsyncTask<Void, Void, List<Country>>() {
            @Override
            protected List<Country> doInBackground(Void... voids) {
                try {
                    InputStream stream = Http.get("https://restcountries.eu/rest/v2/region/" + continent + "?fields=name;capital;alpha2Code;");
                    List<Country> res = Json.getList(stream, null, Country.class);
                    updateFlags(res);
                    return res;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<Country> countries) {
                callback.accept(countries);
            }
        }.execute();
    }

    void updateFlags(List<Country> countries) {
        if (countries != null) {
            for (Country country : countries) {
                country.flag = "https://www.countryflags.io/" + country.alpha2Code + "/flat/64.png";
            }
        }
    }
}