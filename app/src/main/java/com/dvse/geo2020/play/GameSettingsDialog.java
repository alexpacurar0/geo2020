package com.dvse.geo2020.play;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;

import com.dvse.geo2020.Helper;
import com.dvse.geo2020.R;
import com.dvse.geo2020.models.GameSettings;

public class GameSettingsDialog {

    public void show(Context context) {
        Dialog dialog = new AlertDialog.Builder(context)
                .setView(R.layout.game_settings)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GameSettings settings = new GameSettings();
                        settings.continent = (String) ((Spinner) ((Dialog) dialog).findViewById(R.id.continents)).getSelectedItem();
                        settings.nrQuesions = (int) ((Spinner) ((Dialog) dialog).findViewById(R.id.nrQuestions)).getSelectedItem();
                        Intent intent = new Intent(((Dialog) dialog).getContext(), PlayActivity.class);
                        intent.putExtra(PlayActivity.SETTINGS_KEY, settings);
                        ((Dialog) dialog).getContext().startActivity(intent);
                    }
                })
                .show();
        Spinner continents = dialog.findViewById(R.id.continents);
        continents.setAdapter(new ArrayAdapter<>(dialog.getContext(), android.R.layout.simple_list_item_1, Helper.getContinents()));

        Spinner nrQuestions = dialog.findViewById(R.id.nrQuestions);
        nrQuestions.setAdapter(new ArrayAdapter<>(dialog.getContext(), android.R.layout.simple_list_item_1, Helper.getNrQuestions()));
    }
}