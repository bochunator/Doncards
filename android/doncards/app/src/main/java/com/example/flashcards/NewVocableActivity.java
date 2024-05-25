package com.example.flashcards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewVocableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vocable);
        EditText nativeEt = findViewById(R.id.nativeEt);
        EditText foreignEt = findViewById(R.id.foreignEt);
        Button backNewVocableBtn = findViewById(R.id.backNewVocableBtn);
        backNewVocableBtn.setOnClickListener(view -> {
            Intent intent = new Intent(NewVocableActivity.this, VocabularyActivity.class);
            intent.putExtra("UNIT_ID", getIntent().getExtras().getString("UNIT_ID"));
            startActivity(intent);
        });
        Button addVocableBtn = findViewById(R.id.addVocableBtn);
        addVocableBtn.setOnClickListener(view -> {

            //TODO: Zmień nazwe DataBaseHelper, tutaj będą dwie bazy, oprócz tej to jeszcze druga z connectem UNITS - VOCABULARY
            try {
                Vocable vocable = new Vocable(
                        -1,
                        nativeEt.getText().toString(),
                        foreignEt.getText().toString()
                );
                DataBaseHelperVocabulary dataBaseHelperVocabulary = new DataBaseHelperVocabulary(NewVocableActivity.this);
                String statusVocabulary = dataBaseHelperVocabulary.addOne(vocable);
                DataBaseHelperRelationship dataBaseHelperRelationship = new DataBaseHelperRelationship(NewVocableActivity.this);
                String statusRelationship = dataBaseHelperRelationship.addOne(Integer.parseInt(getIntent().getExtras().getString("UNIT_ID")), dataBaseHelperVocabulary.getIdPosition(vocable));
                Toast.makeText(NewVocableActivity.this, statusVocabulary + "\n" + statusRelationship, Toast.LENGTH_SHORT).show();
                nativeEt.setText("");
                foreignEt.setText("");
            }catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}