package com.vutaia.graphviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.List;

public class HistogramView extends LinearLayout {

    private List<Integer> mData = null;
    private int NUM_BARS = 10;

    public HistogramView(Context context) {
        super(context);
        System.out.println("HistogramView(context)");
    }

    public HistogramView(Context context, List<Integer> data) {
        super(context);
        System.out.println("HistogramView(context, data)");
        mData = data;
    }

    public HistogramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        System.out.println("HistogramView(context, attrs)");
    }

    public void setData(List<Integer> data) {
        mData = data;
        invalidate();
        requestLayout();
    }

    public Object getDatapoint(final int position) {
        return mData.get(position);
    }

    public int getValue(final int position) {
        return mData.get(position);
    }

    @Override
    public void invalidate() {
        super.invalidate();
        System.out.println("HistogramView.invalidate()");
        updateView();
    }

    private void updateView() {
        System.out.println("HistogramView.updateView(): " + getHeight() + ", " + getWidth());
        removeAllViews();
        if (mData == null) {
            return;
        } else if (mData.isEmpty()) {
            return;
        }
        drawHistogram(getContext(), getHeight(), getWidth());
    }

    private void drawHistogram(final Context context, final int layoutHeight, final int layoutWidth) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.BOTTOM);

        // find the ceiling
        int maxValue = 0;
        for (int i=0; i<mData.size(); i++) {
            final int currentValue = getValue(i);
            if (currentValue > maxValue) {
                maxValue = currentValue;
            }
        }

        // putting count in bucket
        HashMap<Integer, Integer> histogramData = new HashMap(mData.size());
        final int interval = maxValue / NUM_BARS;
        for (int i=0; i<mData.size(); i++) {
            final int currentValue = getValue(i);
            int bucketIndex = (currentValue - 1) / interval;
            if (histogramData.get(bucketIndex) == null) {
                histogramData.put(bucketIndex, 0);
            }
            int count = histogramData.get(bucketIndex);
            histogramData.put(bucketIndex, ++count);
        }

        // find the most occurrences
        int mostOccurrence = 0;
        for (int i=0; i<NUM_BARS; i++) {
            if (histogramData.get(i) != null) {
                mostOccurrence = Math.max(mostOccurrence, histogramData.get(i));
            }
        }
        if (mostOccurrence == 0) {
            return;
        }

        // rendering
        final LayoutInflater inflater = LayoutInflater.from(context);
        final int barWidth = layoutWidth/NUM_BARS;
        for (int i=0; i<NUM_BARS; i++) {
            System.out.println("HistogramView.bar: "+i);
            int barHeight = 0;
            if (histogramData.get(i) != null) {
                int count = histogramData.get(i);
                barHeight = (int)(1f * count / mostOccurrence * layoutHeight);
            }
            System.out.println("HistogramView.barHeight: "+barHeight);
            final View bar = inflater.inflate(R.layout.histogram_bar, null);
            bar.findViewById(R.id.filled_space).setLayoutParams(new LayoutParams(barWidth, barHeight));
            if (barHeight == 0) {
                bar.setVisibility(View.INVISIBLE);
            }
            addView(bar);
        }
    }
}
