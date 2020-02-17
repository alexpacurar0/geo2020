package com.dvse.geo2020.play;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;

import com.dvse.geo2020.R;
import com.dvse.geo2020.models.Game;
import com.dvse.geo2020.models.GameResult;
import com.dvse.geo2020.models.GameRound;
import com.dvse.geo2020.models.GameSettings;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class PlayActivity extends AppCompatActivity {
    public static String SETTINGS_KEY = "settings";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);

        question = findViewById(R.id.question);
        answers = new ArrayList<>();
        for (int id : new int[]{R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4}) {
            Button button = findViewById(id);
            button.setOnClickListener(onButtonClickListener);
            answers.add(button);
        }
        answers = new ArrayList<>(Arrays.asList((Button) findViewById(R.id.answer1), (Button) findViewById(R.id.answer2), (Button) findViewById(R.id.answer3), (Button) findViewById(R.id.answer4)));

        game = new Game();
        game.settings = (GameSettings) getIntent().getSerializableExtra(SETTINGS_KEY);
        game.onNewRound = new Consumer<GameRound>() {
            @Override
            public void accept(GameRound round) {
                showRound(round);
            }
        };
        game.onGameOver = new Consumer<GameResult>() {
            @Override
            public void accept(GameResult result) {
                showResult(result);
            }
        };
        ((TextView) findViewById(R.id.continent)).setText(game.settings.continent.toUpperCase());
        game.start();
    }

    Game game;
    TextView question;
    List<Button> answers;

    View.OnClickListener onButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            game.provideAnswer(((Button) v).getText().toString());
        }
    };

    void showRound(GameRound round) {
        if (round != null) {
            //question.setText(round.question + "|" + round.correctAnswer);
            question.setText(round.question);
            for (int i = 0; i < answers.size(); i++) {
                answers.get(i).setText(round.answers.get(i));
            }
        }
    }

    void showResult(GameResult result) {
        View view = LayoutInflater.from(this).inflate(R.layout.game_results, null, false);
        ((TextView) view.findViewById(R.id.date)).setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(result.startTime)));
        ((TextView) view.findViewById(R.id.wins)).setText(String.valueOf(result.won));
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        ((TextView) view.findViewById(R.id.duration)).setText(dateFormat.format(new Date(result.duration)));
        new AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton("ok", null)
                .setTitle("Results")
                .show()
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                });
    }
}