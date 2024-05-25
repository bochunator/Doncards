package com.example.flashcards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREFERENCES = "shared_preferences_flash_cards";
    public static final String ANSWER_WITH = "answer_with";
    public static final String ANSWER_WITH_NATIVE = "native";
    public static final String ANSWER_WITH_FOREIGN = "foreign";
    public static final String DARK_MODE = "dark_mode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        AppCompatDelegate.setDefaultNightMode(sharedPreferences.getInt(DARK_MODE, AppCompatDelegate.MODE_NIGHT_NO));
        Button dailyBtn = findViewById(R.id.dailyBtn);
        dailyBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SessionActivity.class);
            startActivity(intent);
        });
        Button answerWithBtn = findViewById(R.id.answerWithBtn);
        String answer = "Answer with: ";
        SharedPreferences.Editor editor = sharedPreferences.edit();
        answerWithBtn.setText(answer + sharedPreferences.getString(ANSWER_WITH, ANSWER_WITH_FOREIGN));
        answerWithBtn.setOnClickListener(view -> {
            if(sharedPreferences.getString(ANSWER_WITH, ANSWER_WITH_FOREIGN).equals(ANSWER_WITH_FOREIGN)) {
                editor.putString(ANSWER_WITH, ANSWER_WITH_NATIVE);
                answerWithBtn.setText(answer + ANSWER_WITH_NATIVE);
            } else {
                editor.putString(ANSWER_WITH, ANSWER_WITH_FOREIGN);
                answerWithBtn.setText(answer + ANSWER_WITH_FOREIGN);
            }
            editor.apply();
        });
        Button studySetsBtn = findViewById(R.id.studySetsBtn);
        studySetsBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, UnitsActivity.class);
            startActivity(intent);
        });
        ImageButton darkModeIB = findViewById(R.id.darkModeIB);
        darkModeIB.setOnClickListener(view -> {
            if(sharedPreferences.getInt(DARK_MODE, AppCompatDelegate.MODE_NIGHT_NO) == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putInt(DARK_MODE, AppCompatDelegate.MODE_NIGHT_NO);
                editor.apply();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putInt(DARK_MODE, AppCompatDelegate.MODE_NIGHT_YES);
                editor.apply();
            }
        });
    }
}