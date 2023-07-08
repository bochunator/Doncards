package com.example.flashcards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelperVocabulary extends DataBaseHelper {
    public static final String VOCABULARY_TABLE = "VOCABULARY_TABLE";
    public static final String VOCABULARY_COLUMN_ID = "ID";
    public static final String VOCABULARY_COLUMN_NATIVE = "NATIVE_COLUMN";
    public static final String VOCABULARY_COLUMN_FOREIGN = "FOREIGN_COLUMN";
    public DataBaseHelperVocabulary(@Nullable Context context) {
        super(context);
    }

    public List<Vocable> getVocabulary(boolean isReversed, int maxSize) {
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
            } while(cursor.moveToNext() && 0 < --maxSize);
        }
        cursor.close();
        sqLiteDatabase.close();
        return returnList;
    }

    public String addOne(Vocable vocable) {
        try {
            getIdPosition(vocable);
        } catch (Exception e) {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(VOCABULARY_COLUMN_NATIVE, vocable.getNativeWord());
            contentValues.put(VOCABULARY_COLUMN_FOREIGN, vocable.getForeignWord());
            long insert = sqLiteDatabase.insert(VOCABULARY_TABLE, null, contentValues);
            return -1 != insert ? "Vocable has been added to database" : "Vocable hasn't been addded to database";
        }
        return "Found vocable in database";

    }

    public Vocable getIdPosition(Vocable vocable) throws IllegalArgumentException{
        String queryString = "SELECT * FROM " + VOCABULARY_TABLE +" WHERE " + VOCABULARY_COLUMN_NATIVE + "=\"" + vocable.getNativeWord()
                + "\" AND " + VOCABULARY_COLUMN_FOREIGN + "=\"" + vocable.getForeignWord() + "\"";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if(!cursor.moveToFirst()) {
            throw new IllegalArgumentException("Vocable is not in database");
        }
        Vocable returnVocable = new Vocable(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2)
        );
        cursor.close();
        sqLiteDatabase.close();
        return returnVocable;
    }
}
