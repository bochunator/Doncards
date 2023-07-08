package com.example.flashcards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class VocabularyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);
        String unitId = getIntent().getExtras().getString("UNIT_ID");
        Button backVocabularyBtn = findViewById(R.id.backVocabularyBtn);
        backVocabularyBtn.setOnClickListener(view -> {
            Intent intent = new Intent(VocabularyActivity.this, UnitsActivity.class);
            startActivity(intent);
        });
        Button addOneVocableBtn = findViewById(R.id.addOneVocableBtn);
        addOneVocableBtn.setOnClickListener(view -> {
            Intent intent = new Intent(VocabularyActivity.this, NewVocableActivity.class);
            intent.putExtra("UNIT_ID", unitId);
            startActivity(intent);
        });
        DataBaseHelperRelationship dataBaseHelperRelationship = new DataBaseHelperRelationship(VocabularyActivity.this);
        ArrayAdapter<Vocable> vocabularyAdapter = new ArrayAdapter<>(VocabularyActivity.this, android.R.layout.simple_list_item_1, dataBaseHelperRelationship.getUnitVocabulary(Integer.parseInt(unitId)));

        ListView vocabularyLv = findViewById(R.id.vocabularyLv);
        vocabularyLv.setAdapter(vocabularyAdapter);
        Button startLearningBtn = findViewById(R.id.startLearningBtn);
        startLearningBtn.setOnClickListener(view -> {
            Intent intent = new Intent(VocabularyActivity.this, SessionActivity.class);
            intent.putExtra("UNIT_ID", getIntent().getExtras().getString("UNIT_ID"));
            startActivity(intent);
        });
    }
}