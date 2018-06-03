package com.example.bjorn.testapp.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.bjorn.testapp.db.DBHelper;
import com.example.bjorn.testapp.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class TimeDAO {

    private DBManager dbManager;
    private Context context;

    public TimeDAO(Context context) {
        this.context = context;
    }

    public void save(String time) {
        // создаем объект для данны
        ContentValues cv = new ContentValues();
        cv.put("dt", time);
        // вставляем запись и получаем ее ID
        dbManager = DBManager.getInstance();
        dbManager.getSQLDatabase(context).
                insert(DBHelper.getDBName(), null, cv);
    }

    public String getPreviousTime() {
        String selectQuery = "SELECT  * FROM " + DBHelper.getDBName();
        dbManager = DBManager.getInstance();
        SQLiteDatabase db = dbManager.getSQLDatabase(context);
        String prevTime = null;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToLast()) {
            int timeID = cursor.getColumnIndex("dt");
            prevTime = cursor.getString(timeID);
        }
        cursor.close();
        dbManager.closeDatabase();
        return prevTime;
    }

    public List<String> getAllPreviousTime() {
        dbManager = DBManager.getInstance();
        Cursor c = dbManager.getSQLDatabase(context).
                query(DBHelper.getDBName(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        List<String> timeHistory = new ArrayList<>();

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            int timeID = c.getColumnIndex("dt");
            do {
                timeHistory.add(c.getString(timeID));
            } while (c.moveToNext());
        } else
            Log.d("HISTORY", "0 rows");

        c.close();
        dbManager.closeDatabase();
        return timeHistory;
    }

}
