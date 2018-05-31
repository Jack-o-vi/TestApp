package com.example.bjorn.testapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * A helper class to manage database creation and version management.
 * Only onCreate was implemented in it.
 *
 * @author Vitaly Zeenko
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String dbName = "history";
    private static final String CREATE_QUERY = "create table "
            + dbName +
            "(id integer primary key autoincrement, " +
            "dt text);";

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, dbName, null, 1);
    }

    /**
     * @return database`s name
     */
    public static String getDbName() {
        return dbName;
    }

    /**
     * Creates a database`s table with name of dbName member.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // создаем таблицу с полями
        db.execSQL(CREATE_QUERY);
    }

    /**
     * @warning Unimplemented method
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}