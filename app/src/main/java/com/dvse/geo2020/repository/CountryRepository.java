package com.dvse.geo2020.repository;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.core.util.Consumer;

import com.dvse.geo2020.models.Country;
import com.dvse.geo2020.models.CountryDetails;

import java.io.InputStream;
import java.util.List;
import java.util.SimpleTimeZone;

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
                updateFlags(country);
            }
        }
    }

    void updateFlags(Country country) {
        if (country != null) {
            country.flag = "https://www.countryflags.io/" + country.alpha2Code + "/flat/64.png";
        }
    }

    void updateTimezone(CountryDetails country) {
        if (country != null && country.timezones.size() > 0) {
            String timeZone = country.timezones.get(0);
            //"UTC","UTC+02:00","UTC-02:30"
            if (timeZone.startsWith("UTC")) {
                country.timeZone = new SimpleTimeZone(getRawOffset(timeZone.substring(3)), timeZone);
            }
        }
    }

    int getRawOffset(String timeZoneOffset) {
        int res = 0;
        if (timeZoneOffset.length() == 6) {
            res += 3600 * Integer.parseInt(timeZoneOffset.substring(1, 3));
            res += 60 * Integer.parseInt(timeZoneOffset.substring(4, 6));
        } else if (timeZoneOffset.length() == 3) {
            res += 3600 * Integer.parseInt(timeZoneOffset.substring(1, 3));
        }
        res = res * (timeZoneOffset.startsWith("-") ? -1 : 1);
        res = res * 1000;
        return res;
    }

    @SuppressLint("StaticFieldLeak")
    public void getCountryDetail(final String countryCode, final Consumer<CountryDetails> callback) {
        new AsyncTask<Void, Void, CountryDetails>() {
            @Override
            protected CountryDetails doInBackground(Void... voids) {
                try {
                    InputStream stream = Http.get("https://restcountries.eu/rest/v2/alpha/" + countryCode + "?fields=name;capital;alpha2Code;borders;area;population;timezones;");
                    CountryDetails res = Json.getItem(stream, null, CountryDetails.class);
                    updateFlags(res);
                    updateTimezone(res);
                    return res;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(CountryDetails countryDetails) {
                callback.accept(countryDetails);
            }
        }.execute();
    }
}