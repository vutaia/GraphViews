package com.vutaia.graphviews;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Integer[] nums = { 1, 1, 4, 3, 5, 8, 4, 10, 20, 18, 14, 12, 12, 11, 8, 18, 19, 14, 14, 14, 14, 12, 11};
        final List<Integer> data = Arrays.asList(nums);
        final HistogramView histogramView = (HistogramView) findViewById(R.id.graphview);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                histogramView.setData(data);
            }
        }, 800);

    }
}
