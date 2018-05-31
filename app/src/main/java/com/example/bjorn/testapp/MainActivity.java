package com.example.bjorn.testapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bjorn.testapp.db.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Main activity class.
 * @author Vitaly Zeenko
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String LOG_TAG = "[MainActivity]";
    private TextView tvPrev;
    private TextView tvCur;
    private Button btnHistory;
    private DBHelper dbHelper;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCur = findViewById(R.id.tvCur);
        tvPrev = findViewById(R.id.tvPrev);
        btnHistory = findViewById(R.id.btnHistory);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // создаем объект для данны
        ContentValues cv = new ContentValues();

        String currentTime = getDateTime();
        Log.d(LOG_TAG, "Current time: " + currentTime);
        tvCur.setText("Current entering: " + currentTime);
        getPrevDate(db);
        addTimeToDB(currentTime, db);
        btnHistory.setOnClickListener(this);
        db.close();
        dbHelper.close();
    }

    /**
     * @param currentTime
     * @param db
     */
    private  void addTimeToDB(String currentTime, SQLiteDatabase db){
        // создаем объект для данны
        ContentValues cv = new ContentValues();
        cv.put("dt", currentTime);
        // вставляем запись и получаем ее ID
        db.insert(dbHelper.getDatabaseName(), null, cv);
    }

    /**
     * @param db is a created and open SQLiteDatabase
     */
    private void getPrevDate(SQLiteDatabase db) {
        String selectQuery = "SELECT  * FROM " + dbHelper.getDatabaseName();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToLast();
        if(cursor != null){
            String dt;
            int timeID = cursor.getColumnIndex("dt");
            dt = cursor.getString(timeID);
            Log.d(LOG_TAG, "getPrev " + dt);
            if (dt != null) {
                tvPrev.setText("Last entering: " + dt);
            } else {
                Log.e("[MainActivity]", "No prev dtime");
            }
        }
        cursor.close();
    }

    /**
     * @return currentTimeStr
     */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "HH:mm:ss", Locale.getDefault());
        Date currentTime = Calendar.getInstance().getTime();
        String currentTimeStr = dateFormat.format(currentTime);
        return currentTimeStr;
    }

    /**
     * After clicking on the btn calls the HistoryActivity
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent;
        intent = new Intent(this, HistoryActivity.class);
        startActivityForResult(intent, 0);
    }
}
