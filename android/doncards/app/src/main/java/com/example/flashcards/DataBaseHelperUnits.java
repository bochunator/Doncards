package com.example.flashcards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelperUnits extends DataBaseHelper {
    public static final String UNITS_TABLE = "UNITS_TABLE";
    public static final String UNITS_COLUMN_ID = "ID";
    public static final String UNITS_COLUMN_NAME = "NAME_COLUMN";
    public DataBaseHelperUnits(@Nullable Context context) {
        super(context);
    }

    public boolean addOne(Unit unit) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UNITS_COLUMN_NAME, unit.getName());
        long insert = sqLiteDatabase.insert(UNITS_TABLE, null, contentValues);
        return -1 != insert;
    }

    public List<Unit> getUnits() {
        List<Unit> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + UNITS_TABLE;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if(cursor.moveToFirst()) {
            do {
                int unitID = cursor.getInt(0);
                String unitName = cursor.getString(1);
                try {
                    Unit unit = new Unit(unitID, unitName);
                    returnList.add(unit);
                } catch (Exception e) {
                    Log.d("TAG", "getUnits: " + "unitId = " + unitID + ", " + e.getMessage());
                }
            } while(cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return returnList;
    }

    public boolean deleteOne(int unitId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String queryString = "DELETE FROM " + UNITS_TABLE + " WHERE " + UNITS_COLUMN_ID + " = " + unitId;
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        return cursor.moveToFirst();
    }
}
