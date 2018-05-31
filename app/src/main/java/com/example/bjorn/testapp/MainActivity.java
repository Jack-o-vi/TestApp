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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String LOG_TAG = "[MainActivity]";
    private TextView tvPrev;
    private TextView tvCur;
    private Button btnHistory;
    private DBHelper dbHelper;

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

        String curTIME = getDateTime();
        Log.d(LOG_TAG, "Current time: " + curTIME);
        tvCur.setText("Current entering: " + curTIME);
        getPrevDate(db);
        cv.put("dt", curTIME);
        // вставляем запись и получаем ее ID
        long rowID = db.insert(dbHelper.getDatabaseName(), null, cv);
        btnHistory.setOnClickListener(this);
        db.close();
        dbHelper.close();
    }


    private void getPrevDate(SQLiteDatabase db) {
        String selectQuery = "SELECT  * FROM " + dbHelper.getDatabaseName();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToLast();
        String dt;
        //if (cursor.moveToNext()) {
            int timeID = cursor.getColumnIndex("dt");
            dt = cursor.getString(timeID);
            Log.d(LOG_TAG, "getPrev " + dt);
            if (dt != null) {
                tvPrev.setText("Last entering: " + dt);
            } else {
                Log.e("[MainActivity]", "No prev dtime");
            }
        //}

        cursor.close();
    }


    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "HH:mm:ss", Locale.getDefault());
        Date currentTime = Calendar.getInstance().getTime();
        String currentDateandTime = dateFormat.format(currentTime);
        return currentDateandTime;
    }

    private String parseDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

        try {
            return formatter.parse(date).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        intent = new Intent(this, HistoryActivity.class);
        startActivityForResult(intent, 0);
    }
}
