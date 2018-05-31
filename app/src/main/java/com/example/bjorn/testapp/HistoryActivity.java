package com.example.bjorn.testapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bjorn.testapp.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
// создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);

        // подключаемся к БД
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            onRefresh(db);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbHelper.close();
        }


    }

    public void onRefresh(SQLiteDatabase db) {
        Cursor c = db.query(dbHelper.getDatabaseName(), null, null, null, null, null, null);
        List<String> times = new ArrayList<>();
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            int timeID = c.getColumnIndex("dt");

            do {
                times.add(c.getString(timeID));

                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d("HISTORY", "0 rows");

        LinearLayout linLayout = findViewById(R.id.linLayout);

        LayoutInflater ltInflater = getLayoutInflater();
        int[] colors = {Color.parseColor("#559966CC"), Color.parseColor("#55336699")};
        int i = 0;
        for (String b : times) {
            View item = ltInflater.inflate(R.layout.item, linLayout, false);
            TextView tvName = item.findViewById(R.id.tvName);
            tvName.setText("Time: " + b);
            item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            item.setBackgroundColor(colors[i++ % 2]);
            linLayout.addView(item);
        }

        c.close();

    }

}
