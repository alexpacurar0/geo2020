package com.dvse.geo2020.models;

import java.util.List;

public class GameRound {
    public String question;
    public List<String> answers;
    public String correctAnswer;

    public boolean provideAnswer(String value) {
        return correctAnswer.equals(value);
    }
}