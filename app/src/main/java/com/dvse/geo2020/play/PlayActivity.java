package com.dvse.geo2020.play;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dvse.geo2020.R;
import com.dvse.geo2020.models.GameSettings;

public class PlayActivity extends AppCompatActivity {
    public static String SETTINGS_KEY = "settings";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        GameSettings settings = (GameSettings) getIntent().getSerializableExtra(SETTINGS_KEY);
        ((TextView) findViewById(R.id.test)).setText(settings.continent + "|" + settings.nrQuesions);
    }
}