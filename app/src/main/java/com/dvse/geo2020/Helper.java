package com.dvse.geo2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Helper {
    public static List<String> getContinents() {
        return new ArrayList<>(Arrays.asList("Africa", "Americas", "Asia", "Europe", "Oceania"));
    }

    public static List<Integer> getNrQuestions() {
        return new ArrayList<>(Arrays.asList(5, 10, 15));
    }
}