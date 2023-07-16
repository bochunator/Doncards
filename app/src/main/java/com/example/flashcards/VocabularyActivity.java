package com.example.flashcards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

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
        Button addNewVocableBtn = findViewById(R.id.addNewVocableBtn);
        addNewVocableBtn.setOnClickListener(view -> {
            Intent intent = new Intent(VocabularyActivity.this, NewVocableActivity.class);
            intent.putExtra("UNIT_ID", unitId);
            startActivity(intent);
        });
        DataBaseHelperRelationship dataBaseHelperRelationship = new DataBaseHelperRelationship(VocabularyActivity.this);
        ArrayAdapter<Vocable> vocabularyAdapter = new ArrayAdapter<>(VocabularyActivity.this, android.R.layout.simple_list_item_1, dataBaseHelperRelationship.getUnitVocabulary(Integer.parseInt(unitId)));
        ConstraintLayout vocabularyConstraintLayout = findViewById(R.id.vocabularyConstraintLayout);

        ListView vocabularyLv = findViewById(R.id.vocabularyLv);
        vocabularyLv.setAdapter(vocabularyAdapter);
        vocabularyLv.setOnItemClickListener((parent, view, position, id) -> {
            Vocable clickedVocable = (Vocable) parent.getItemAtPosition(position);
            Snackbar.make(vocabularyConstraintLayout, "Do you want to delete - " + clickedVocable.toString() + "?", Snackbar.LENGTH_SHORT)
                    .setAction("DELETE", secondView -> {
                        dataBaseHelperRelationship.deleteOne(Integer.parseInt(unitId), clickedVocable);
                        ArrayAdapter<Vocable> vocableArrayAdapter = new ArrayAdapter<>(VocabularyActivity.this, android.R.layout.simple_list_item_1, dataBaseHelperRelationship.getUnitVocabulary(Integer.parseInt(unitId)));
                        vocabularyLv.setAdapter(vocableArrayAdapter);
                    })
                    .show();
        });
        Button startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(view -> {
            Intent intent = new Intent(VocabularyActivity.this, SessionActivity.class);
            intent.putExtra("UNIT_ID", getIntent().getExtras().getString("UNIT_ID"));
            startActivity(intent);
        });
        Button removeAllBtn = findViewById(R.id.removeAllBtn);
        removeAllBtn.setOnClickListener(view -> {
            Snackbar.make(vocabularyConstraintLayout, "Do you want to delete all?", Snackbar.LENGTH_SHORT)
                    .setAction("DELETE", secondView -> {
                        DataBaseHelperUnits dataBaseHelperUnits = new DataBaseHelperUnits(VocabularyActivity.this);
                        dataBaseHelperUnits.deleteOne(Integer.parseInt(unitId));
                        Intent intent = new Intent(VocabularyActivity.this, UnitsActivity.class);
                        startActivity(intent);
                    })
                    .show();
        });
    }
}