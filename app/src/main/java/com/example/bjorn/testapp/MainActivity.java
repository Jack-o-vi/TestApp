package com.example.bjorn.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bjorn.testapp.db.dao.TimeDAO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Main activity class.
 *
 * @author Vitaly Zeenko
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String LOG_TAG = "[MainActivity]";
    private TextView tvPrev;
    private TextView tvCur;
    private Button btnHistory;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCur = findViewById(R.id.tvCur);
        tvPrev = findViewById(R.id.tvPrev);
        btnHistory = findViewById(R.id.btnHistory);
        btnHistory.setOnClickListener(this);
        TimeDAO timeDAO = new TimeDAO(this);
        String currentTime = getDateTime();
        tvCur.setText("Current entering: " + currentTime);
        String previousTime = timeDAO.getPreviousTime();
        if (previousTime != null)
            tvPrev.setText("Last entering: " + previousTime);

        timeDAO.save(currentTime);
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
