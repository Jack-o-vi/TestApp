package com.example.bjorn.testapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bjorn.testapp.db.dao.TimeDAO;

import java.util.Collections;
import java.util.List;

/**
 * In this activity contains only the list of time of all previous application`s openings.
 *
 * @author Vitaly Zeenko
 */
public class HistoryActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        TimeDAO timeDAO = new TimeDAO(this);

        List<String> times = timeDAO.getAllPreviousTime();
        LinearLayout linLayout = findViewById(R.id.linLayout);
        LayoutInflater ltInflater = getLayoutInflater();
        int[] colors = {Color.parseColor("#559966CC"), Color.parseColor("#55336699")};
        int i = 0;

        // Output from the previous enter to the latest
        Collections.reverse(times);
        for (String b : times) {
            View item = ltInflater.inflate(R.layout.item, linLayout, false);
            TextView tvName = item.findViewById(R.id.tvName);
            tvName.setText("Time: " + b);
            item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            item.setBackgroundColor(colors[i++ % 2]);
            linLayout.addView(item);
        }

    }

}
