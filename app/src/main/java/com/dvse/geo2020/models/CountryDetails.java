package com.dvse.geo2020.models;

import java.util.List;
import java.util.TimeZone;

public class CountryDetails extends Country {
    public List<String> borders;
    public int area;
    public int population;
    public List<String> timezones;

    public TimeZone timeZone;
}