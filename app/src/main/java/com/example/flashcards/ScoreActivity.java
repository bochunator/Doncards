package com.example.flashcards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Button playAgainBtn = findViewById(R.id.playAgainBtn);
        playAgainBtn.setOnClickListener(view -> {
            Intent intent = new Intent(ScoreActivity.this, SessionActivity.class);
            startActivity(intent);
        });
        Button homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(view -> {
            Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
            startActivity(intent);
        });
        TextView scoreTv = findViewById(R.id.scoreTv);
        scoreTv.setText("Your score is " + getIntent().getExtras().getString("SCORE") + "/" + getIntent().getExtras().getString("SIZE"));
    }
}