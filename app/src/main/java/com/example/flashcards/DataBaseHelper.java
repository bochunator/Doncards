package com.example.flashcards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String VOCABULARY_TABLE = "VOCABULARY_TABLE";
    public static final String COLUMN_VOCABULARY_NATIVE = "VOCABULARY_NATIVE";
    public static final String COLUMN_VOCABULARY_FOREIGN = "VOCABULARY_FOREIGN";
    public static final String COLUMN_ID = "ID";
    public DataBaseHelper(@Nullable Context context) {
        super(context, "vocabulary.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + VOCABULARY_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_VOCABULARY_NATIVE + " TEXT, " + COLUMN_VOCABULARY_FOREIGN + " TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(Vocable vocable) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_VOCABULARY_NATIVE, vocable.getNativeWord());
        contentValues.put(COLUMN_VOCABULARY_FOREIGN, vocable.getForeignWord());
        long insert = sqLiteDatabase.insert(VOCABULARY_TABLE, null, contentValues);
        return -1 != insert;
    }
    public List<Vocable> getEveryone(boolean isReversed) {
        List<Vocable> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + VOCABULARY_TABLE;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if(cursor.moveToFirst()) {
            do {
                int vocableID = cursor.getInt(0);
                String vocableNative = isReversed ? cursor.getString(2) : cursor.getString(1);
                String vocableForeign = isReversed ? cursor.getString(1) : cursor.getString(2);
                try {
                    Vocable vocable = new Vocable(vocableID, vocableNative, vocableForeign);
                    returnList.add(vocable);
                } catch (Exception e) {
                    Log.d("TAG", "getEveryone: some of words were empty, so they haven't been included");
                }
            } while(cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return returnList;
    }
}
