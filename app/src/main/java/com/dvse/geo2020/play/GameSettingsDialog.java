package com.dvse.geo2020.play;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.dvse.geo2020.R;

public class GameSettingsDialog {
    public void show(Context context) {
        new AlertDialog.Builder(context)
                .setView(R.layout.game_settings)
                .show();
    }
}