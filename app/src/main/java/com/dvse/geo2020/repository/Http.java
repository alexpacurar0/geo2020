package com.dvse.geo2020.repository;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Http {

    public static InputStream get(String url) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        return connection.getInputStream();
    }
}