package com.example.bjorn.testapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private static final DBManager ourInstance = new DBManager();
    private static DBHelper dbHelper = null;
    private static SQLiteDatabase database;
    public static DBManager getInstance() {
        return ourInstance;
    }

    private DBManager() {
    }

    public SQLiteDatabase getSQLDatabase(Context context){
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return database;
    }

    public void closeDatabase(){
        dbHelper.close();
        database.close();
    }

}
