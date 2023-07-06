package com.example.flashcards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREFERENCES = "shared_preferences_flash_cards";
    public static final String ANSWER_WITH = "answer_with";
    public static final String ANSWER_WITH_NATIVE = "native";
    public static final String ANSWER_WITH_FOREIGN = "foreign";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Button startBtn = (Button) findViewById(R.id.startBtn);
        startBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SessionActivity.class);
            intent.putExtra("REVERSE", "false");
            startActivity(intent);
        });
        Button answerWithBtn = (Button) findViewById(R.id.answerWithBtn);
        String answer = "Answer with: ";
        answerWithBtn.setText(sharedPreferences.getString(answer + ANSWER_WITH, answer + ANSWER_WITH_FOREIGN));
        answerWithBtn.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if(sharedPreferences.getString(ANSWER_WITH, ANSWER_WITH_FOREIGN).equals(ANSWER_WITH_FOREIGN)) {
                editor.putString(ANSWER_WITH, ANSWER_WITH_NATIVE);
                answerWithBtn.setText(answer + ANSWER_WITH_NATIVE);
            } else {
                editor.putString(ANSWER_WITH, ANSWER_WITH_FOREIGN);
                answerWithBtn.setText(answer + ANSWER_WITH_FOREIGN);
            }
            editor.apply();
        });
        Button addNewWordBtn = (Button) findViewById(R.id.addNewWordBtn);
        addNewWordBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
            startActivity(intent);
        });
    }
}