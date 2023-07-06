package com.example.flashcards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SessionActivity extends AppCompatActivity {
    private int counter;
    private boolean foreignIsShow = false;
    private int score;
    private TextView vocableTv;
    private List<Vocable> vocabulary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        Button exitBtn = findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(view -> {
            Intent intent = new Intent(SessionActivity.this, MainActivity.class);
            startActivity(intent);
        });
        boolean isReversed = getIntent().getExtras().getString("REVERSE").equals("true");
        DataBaseHelper dataBaseHelper;
        dataBaseHelper = new DataBaseHelper(SessionActivity.this);
        vocabulary = dataBaseHelper.getEveryone(isReversed);
        if(vocabulary.isEmpty()) {
            return;
        }
        counter = vocabulary.size() - 1;
        vocableTv = findViewById(R.id.vocableTv);
        vocableTv.setText(vocabulary.get(counter).getNativeWord());
        Button showBtn = findViewById(R.id.showBtn);
        showBtn.setOnClickListener(view -> {
            if(foreignIsShow) {
                vocableTv.setText(vocabulary.get(counter).getNativeWord());
            } else {
                vocableTv.setText(vocabulary.get(counter).getForeignWord());
            }
            foreignIsShow = !foreignIsShow;
        });
        Button noBtn = findViewById(R.id.noBtn);
        noBtn.setOnClickListener(view -> {
            pickNextVocable();
        });
        Button yesBtn = findViewById(R.id.yesBtn);
        yesBtn.setOnClickListener(view -> {
            score++;
            pickNextVocable();
        });
    }
    private void pickNextVocable() {
        foreignIsShow = false;
        if(0 == counter) {
            Intent intent = new Intent(SessionActivity.this, ScoreActivity.class);
            intent.putExtra("SCORE", Integer.toString(score));
            intent.putExtra("SIZE", Integer.toString(vocabulary.size()));
            intent.putExtra("REVERSE", getIntent().getExtras().getString("REVERSE"));
            startActivity(intent);
        } else {
            counter--;
            vocableTv.setText(vocabulary.get(counter).getNativeWord());
        }
    }
}