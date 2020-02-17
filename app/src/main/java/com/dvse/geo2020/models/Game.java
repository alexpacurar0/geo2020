package com.dvse.geo2020.models;

import androidx.core.util.Consumer;

import com.dvse.geo2020.repository.CountryRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {

    public GameSettings settings;
    private int rounds = 0;

    private GameRound currentRound;
    private GameResult result = new GameResult();
    private List<String> previouslyQuestions = new ArrayList<>();
    private List<Country> countries;


    public Consumer<GameRound> onNewRound;
    public Consumer<GameResult> onGameOver;

    public void start() {
        new CountryRepository().getCountries(settings.continent, new Consumer<List<Country>>() {
            @Override
            public void accept(List<Country> items) {
                countries = items;
                result.startTime = System.currentTimeMillis();
                nextRound();
            }
        });

    }

    void nextRound() {
        if (rounds == settings.nrQuesions) {
            result.duration = System.currentTimeMillis() - result.startTime;
            onGameOver.accept(result);
        } else {
            rounds++;
            currentRound = createNewRound(countries, previouslyQuestions);
            onNewRound.accept(currentRound);
        }
    }

    public void provideAnswer(String answer) {
        if (currentRound.provideAnswer(answer)) {
            result.won++;
        }
        nextRound();
    }

    private static GameRound createNewRound(List<Country> countries, List<String> previouslyQuestions) {
        GameRound res = new GameRound();
        Random random = new Random();
        Country question = getCountry(random, countries, previouslyQuestions);
        res.question = question.name;
        res.correctAnswer = question.capital;
        List<String> previouslyAnswers = new ArrayList<>();
        res.answers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Country country = getCountry(random, countries, previouslyAnswers);
            res.answers.add(country.capital);
        }
        res.answers.add(res.correctAnswer);
        Collections.shuffle(res.answers);
        return res;
    }

    private static Country getCountry(Random random, List<Country> countries, List<String> forbidden) {
        Country res = null;
        while (res == null) {
            res = countries.get(random.nextInt(countries.size()));
            if (forbidden.contains(res.name)) {
                res = null;
            }
        }
        forbidden.add(res.name);
        return res;
    }
}