package com.example.flashcards;

import static com.example.flashcards.MainActivity.ANSWER_WITH;
import static com.example.flashcards.MainActivity.ANSWER_WITH_FOREIGN;
import static com.example.flashcards.MainActivity.SHARED_PREFERENCES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class SessionActivity extends AppCompatActivity {
    private int counter;
    private boolean foreignIsShow = false;
    private int score;
    private TextView vocableTv;
    private List<Vocable> vocabulary;
    private float previousX;
    DisplayMetrics displayMetrics;
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
        /*
        ConstraintLayout sessionCL = findViewById(R.id.sessionCL);
        sessionCL.setOnClickListener(view -> {

        });
         */
        ImageView noIv = findViewById(R.id.noIv);
        noIv.setOnClickListener(view -> {
            pickNextVocable();
        });
        ImageView yesIv = findViewById(R.id.yesIv);
        yesIv.setOnClickListener(view -> {
            score++;
            pickNextVocable();
        });
        displayMetrics = new DisplayMetrics();
    }
    private void pickNextVocable() {
        foreignIsShow = false;
        if(0 == counter) {
            Intent intent = new Intent(SessionActivity.this, ScoreActivity.class);
            intent.putExtra("SCORE", Integer.toString(score));
            intent.putExtra("SIZE", Integer.toString(vocabulary.size()));
            try {
                intent.putExtra("UNIT_ID", getIntent().getExtras().getString("UNIT_ID"));
            } catch (Exception e) {
                Log.d("TAG", "pickNextVocable: You are in Daily Mode");
            }
            startActivity(intent);
        } else {
            counter--;
            vocableTv.setText(vocabulary.get(counter).getNativeWord());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                previousX = x;
            } break;
            case  MotionEvent.ACTION_UP: {
                if(previousX - x > displayMetrics.widthPixels / 10f) {
                    pickNextVocable();
                } else if(x - previousX > displayMetrics.widthPixels / 10f) {
                    score++;
                    pickNextVocable();
                } else {
                    if(foreignIsShow) {
                        vocableTv.setText(vocabulary.get(counter).getNativeWord());
                    } else {
                        vocableTv.setText(vocabulary.get(counter).getForeignWord());
                    }
                    foreignIsShow = !foreignIsShow;
                }
            } return true;
        }
        return super.onTouchEvent(event);
    }
}