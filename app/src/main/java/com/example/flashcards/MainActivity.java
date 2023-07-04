package com.example.flashcards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startBtn = (Button) findViewById(R.id.startBtn);
        startBtn.setOnClickListener(view->{
            Intent intent = new Intent(MainActivity.this, SessionActivity.class);
            startActivity(intent);
        });
    }
}