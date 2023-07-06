package com.example.flashcards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        Button exitNewWordBtn = findViewById(R.id.exitNewWordBtn);
        exitNewWordBtn.setOnClickListener(view -> {
            Intent intent = new Intent(NewWordActivity.this, MainActivity.class);
            startActivity(intent);
        });
        EditText nativeEt = findViewById(R.id.nativeEt);
        EditText foreignEt = findViewById(R.id.foreignEt);
        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(view -> {
            Vocable vocable;
            try {
                vocable = new Vocable(
                        -1,
                        nativeEt.getText().toString(),
                        foreignEt.getText().toString()
                );
                DataBaseHelper dataBaseHelper = new DataBaseHelper(NewWordActivity.this);
                boolean success = dataBaseHelper.addOne(vocable);
                Toast.makeText(NewWordActivity.this, "Success " + success, Toast.LENGTH_SHORT).show();
                nativeEt.setText("");
                foreignEt.setText("");
            }catch (Exception e) {
                Toast.makeText(this, "Error creating vocable", Toast.LENGTH_SHORT).show();
            }
        });
    }
}