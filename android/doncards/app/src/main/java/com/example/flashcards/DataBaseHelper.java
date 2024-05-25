package com.example.flashcards;

import static com.example.flashcards.DataBaseHelperRelationship.RELATIONSHIP_COLUMN_ID;
import static com.example.flashcards.DataBaseHelperRelationship.RELATIONSHIP_COLUMN_UNITS_ID;
import static com.example.flashcards.DataBaseHelperRelationship.RELATIONSHIP_COLUMN_VOCABULARY_ID;
import static com.example.flashcards.DataBaseHelperRelationship.RELATIONSHIP_TABLE;
import static com.example.flashcards.DataBaseHelperUnits.UNITS_COLUMN_ID;
import static com.example.flashcards.DataBaseHelperUnits.UNITS_COLUMN_NAME;
import static com.example.flashcards.DataBaseHelperUnits.UNITS_TABLE;
import static com.example.flashcards.DataBaseHelperVocabulary.VOCABULARY_COLUMN_FOREIGN;
import static com.example.flashcards.DataBaseHelperVocabulary.VOCABULARY_COLUMN_ID;
import static com.example.flashcards.DataBaseHelperVocabulary.VOCABULARY_COLUMN_NATIVE;
import static com.example.flashcards.DataBaseHelperVocabulary.VOCABULARY_TABLE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(@Nullable Context context) {
        super(context, "flashcards.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + UNITS_TABLE + " (" + UNITS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + UNITS_COLUMN_NAME + " TEXT)";
        db.execSQL(createTableStatement);
        createTableStatement = "CREATE TABLE " + VOCABULARY_TABLE + " (" + VOCABULARY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + VOCABULARY_COLUMN_NATIVE + " TEXT, " + VOCABULARY_COLUMN_FOREIGN + " TEXT)";
        db.execSQL(createTableStatement);
        createTableStatement = "CREATE TABLE " + RELATIONSHIP_TABLE + " (" + RELATIONSHIP_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + RELATIONSHIP_COLUMN_UNITS_ID + " INT, " + RELATIONSHIP_COLUMN_VOCABULARY_ID + " INT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
