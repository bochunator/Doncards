package com.example.flashcards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class UnitsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_units);
        Button backUnitsBtn = findViewById(R.id.backUnitsBtn);
        backUnitsBtn.setOnClickListener(view -> {
            Intent intent = new Intent(UnitsActivity.this, MainActivity.class);
            startActivity(intent);
        });
        Button addOneUnitBtn = findViewById(R.id.addOneUnitBtn);
        addOneUnitBtn.setOnClickListener(view -> {
            Intent intent = new Intent(UnitsActivity.this, NewUnitActivity.class);
            startActivity(intent);
        });
        DataBaseHelperUnits dataBaseHelperUnits = new DataBaseHelperUnits(UnitsActivity.this);
        List<Unit> units = dataBaseHelperUnits.getUnits();
        ArrayAdapter<Unit> unitsAdapter = new ArrayAdapter<>(UnitsActivity.this, android.R.layout.simple_list_item_1, units);
        ListView unitsLv = findViewById(R.id.unitsLv);
        unitsLv.setAdapter(unitsAdapter);
        unitsLv.setOnItemClickListener((parent, view, position, id) -> {
            Unit clickedUnit = (Unit) parent.getItemAtPosition(position);
            Intent intent = new Intent(UnitsActivity.this, VocabularyActivity.class);
            intent.putExtra("UNIT_ID", Integer.toString(clickedUnit.getId()));
            startActivity(intent);
        });
    }
}