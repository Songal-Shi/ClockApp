package com.example.clock;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ClockFragment extends Fragment {
    
    private TextView tvTime, tvDate;
    private Handler handler;
    private Runnable updateTimeRunnable;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clock, container, false);
        
        tvTime = view.findViewById(R.id.tvTime);
        tvDate = view.findViewById(R.id.tvDate);
        
        handler = new Handler();
        updateTimeRunnable = new Runnable() {
            @Override
            public void run() {
                updateTime();
                handler.postDelayed(this, 1000);
            }
        };
        
        updateTime();
        
        return view;
    }
    
    private void updateTime() {
        Calendar calendar = Calendar.getInstance();
        
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String currentTime = timeFormat.format(calendar.getTime());
        tvTime.setText(currentTime);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 EEEE", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        tvDate.setText(currentDate);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        handler.post(updateTimeRunnable);
    }
    
    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(updateTimeRunnable);
    }
}
