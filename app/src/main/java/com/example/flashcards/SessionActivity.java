package com.example.flashcards;

import static com.example.flashcards.MainActivity.ANSWER_WITH;
import static com.example.flashcards.MainActivity.ANSWER_WITH_FOREIGN;
import static com.example.flashcards.MainActivity.SHARED_PREFERENCES;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
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
        boolean isReversed = false;
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if(!sharedPreferences.getString(ANSWER_WITH, ANSWER_WITH_FOREIGN).equals(ANSWER_WITH_FOREIGN)) {
            isReversed = true;
        }
        try {
            String unitId = getIntent().getExtras().getString("UNIT_ID");
            DataBaseHelperRelationship dataBaseHelperRelationship = new DataBaseHelperRelationship(SessionActivity.this);
            vocabulary = dataBaseHelperRelationship.getUnitVocabulary(Integer.parseInt(unitId), isReversed);
        } catch (Exception e) {
            DataBaseHelperVocabulary dataBaseHelperVocabulary = new DataBaseHelperVocabulary(SessionActivity.this);
            vocabulary = dataBaseHelperVocabulary.getVocabulary(isReversed, 30);
        }
        if(vocabulary.isEmpty()) {
            return;
        }
        Collections.shuffle(vocabulary);
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
            startActivity(intent);
        } else {
            counter--;
            vocableTv.setText(vocabulary.get(counter).getNativeWord());
        }
    }
}