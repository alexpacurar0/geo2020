package com.dvse.geo2020.study;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dvse.geo2020.Helper;
import com.dvse.geo2020.R;

public class StudyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_activity);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ContinentsAdapter adapter = new ContinentsAdapter(Helper.getContinents());
        adapter.setOnContinentClickListener(new Consumer<String>() {
            @Override
            public void accept(String continent) {
                new CountriesDialog().show(StudyActivity.this, continent);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}