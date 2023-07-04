package com.example.flashcards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SessionActivity extends AppCompatActivity {
    int counter;
    boolean foreignIsShow = false;
    int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        Vocabulary vocabulary = new Vocabulary(getIntent().getExtras().getString("REVERSE").equals("true"));
        vocabulary.add(new Vocable("nad/powyżej", "above"));
        vocabulary.add(new Vocable("za granicą", "abroad"));
        vocabulary.add(new Vocable("nieobecny", "absent"));
        vocabulary.add(new Vocable("wypadek", "accident"));
        vocabulary.add(new Vocable("żołędź", "acorn"));
        vocabulary.add(new Vocable("ogłoszenie", "ad"));
        vocabulary.add(new Vocable("dodawać", "add"));
        vocabulary.add(new Vocable("adres", "address"));
        vocabulary.add(new Vocable("podziwiać", "admire"));
        vocabulary.add(new Vocable("dorosły", "adult"));
        counter = vocabulary.size() - 1;
        TextView vocableTv = findViewById(R.id.vocableTv);
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
        });
        Button yesBtn = findViewById(R.id.yesBtn);
        yesBtn.setOnClickListener(view -> {
            foreignIsShow = false;
            score++;
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
        });
        Button exitBtn = findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(view -> {
            Intent intent = new Intent(SessionActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}