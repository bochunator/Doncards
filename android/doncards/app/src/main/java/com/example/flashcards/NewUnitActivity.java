package com.example.flashcards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewUnitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_unit);
        Button cancelNewUnitBtn = findViewById(R.id.cancelNewUnitBtn);
        cancelNewUnitBtn.setOnClickListener(view -> {
            Intent intent = new Intent(NewUnitActivity.this, UnitsActivity.class);
            startActivity(intent);
        });
        EditText unitEt = findViewById(R.id.unitEt);
        Button addUnitBtn = findViewById(R.id.addUnitBtn);
        addUnitBtn.setOnClickListener(view -> {
            try {
                Unit unit = new Unit(
                        -1,
                        unitEt.getText().toString()
                );
                DataBaseHelperUnits dataBaseHelperUnits = new DataBaseHelperUnits(NewUnitActivity.this);
                boolean success = dataBaseHelperUnits.addOne(unit);
                Toast.makeText(this, "Success " + success, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewUnitActivity.this, UnitsActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}