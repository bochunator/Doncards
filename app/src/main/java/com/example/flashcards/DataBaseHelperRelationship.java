package com.example.flashcards;

import static com.example.flashcards.DataBaseHelperVocabulary.VOCABULARY_COLUMN_ID;
import static com.example.flashcards.DataBaseHelperVocabulary.VOCABULARY_TABLE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelperRelationship extends DataBaseHelper {
    public static final String RELATIONSHIP_TABLE = "RELATIONSHIP_TABLE";
    public static final String RELATIONSHIP_COLUMN_ID = "ID";
    public static final String RELATIONSHIP_COLUMN_UNITS_ID = "UNITS_ID_COLUMN";
    public static final String RELATIONSHIP_COLUMN_VOCABULARY_ID = "VOCABULARY_ID_COLUMN";
    public DataBaseHelperRelationship(@Nullable Context context) {
        super(context);
    }

    public String addOne(int unitId, Vocable vocable) {
        try {
            getIdPosition(unitId, vocable);
        } catch (Exception e) {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(RELATIONSHIP_COLUMN_UNITS_ID, unitId);
            contentValues.put(RELATIONSHIP_COLUMN_VOCABULARY_ID, vocable.getId());
            long insert = sqLiteDatabase.insert(RELATIONSHIP_TABLE, null, contentValues);
            return -1 != insert ? "Vocable has been added to this unit" : "Vocable hasn't been addded to this unit";
        }
        return "Found vocable in unit";
    }

    public int getIdPosition(int unitId, Vocable vocable) throws IllegalArgumentException{
        String queryString = "SELECT * FROM " + RELATIONSHIP_TABLE +" WHERE " + RELATIONSHIP_COLUMN_UNITS_ID + "=\"" + unitId
                + "\" AND " + RELATIONSHIP_COLUMN_VOCABULARY_ID + "=\"" + vocable.getId() + "\"";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if(!cursor.moveToFirst()) {
            throw new IllegalArgumentException("Vocable found in unit");
        }
        int returnId = cursor.getInt(0);
        cursor.close();
        sqLiteDatabase.close();
        return returnId;
    }

    public List<Vocable> getUnitVocabulary(int unitId) {
        return this.getUnitVocabulary(unitId, false);
    }
    public List<Vocable> getUnitVocabulary(int unitId, boolean isReversed) {
        List<Integer> vocabularyId = new ArrayList<>();
        String queryString = "SELECT * FROM " + RELATIONSHIP_TABLE + " WHERE " + RELATIONSHIP_COLUMN_UNITS_ID + "=\"" + unitId + "\"";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if(cursor.moveToFirst()) {
            do {
                vocabularyId.add(cursor.getInt(2));
                Log.d("TAG", "getIdPosition: ID VOCABULARY TO " + cursor.getInt(2));
            } while(cursor.moveToNext());
        }
        cursor.close();

        List<Vocable> returnList = new ArrayList<>();
        for (Integer i : vocabularyId) {
            queryString = "SELECT * FROM " + VOCABULARY_TABLE + " WHERE " + VOCABULARY_COLUMN_ID + "=\"" + i + "\"";
            cursor = sqLiteDatabase.rawQuery(queryString, null);
            if(cursor.moveToFirst()) {
                try {
                    returnList.add(new Vocable(
                            cursor.getInt(0),
                            cursor.getString(isReversed ? 2 : 1),
                            cursor.getString(isReversed ? 1 : 2)
                    ));
                } catch (Exception e) {
                    Log.d("TAG", "getIdPosition: " + e.getMessage());
                }
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return returnList;
    }

    public boolean deleteOne(int unitId, Vocable vocable) {
        SQLiteDatabase sqLiteDatabase =this.getWritableDatabase();
        String queryString = "DELETE FROM " + RELATIONSHIP_TABLE + " WHERE " + RELATIONSHIP_COLUMN_UNITS_ID + " = " + unitId + " AND " +
                RELATIONSHIP_COLUMN_VOCABULARY_ID + " = " + vocable.getId();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        return cursor.moveToFirst();
    }
}
