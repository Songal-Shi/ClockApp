package com.example.clock;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import java.util.Locale;

public class StopwatchFragment extends Fragment {
    
    private TextView tvStopwatch;
    private boolean isRunning = false;
    private long startTime = 0;
    private long pauseTime = 0;
    private Handler handler;
    private Runnable updateRunnable;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        
        tvStopwatch = view.findViewById(R.id.tvStopwatch);
        handler = new Handler();
        
        view.findViewById(R.id.btnStartStop).setOnClickListener(v -> {
            if (!isRunning) {
                startStopwatch();
            } else {
                pauseStopwatch();
            }
        });
        
        view.findViewById(R.id.btnReset).setOnClickListener(v -> resetStopwatch());
        
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                updateStopwatch();
                handler.postDelayed(this, 10);
            }
        };
        
        updateStopwatch();
        
        return view;
    }
    
    private void startStopwatch() {
        if (pauseTime == 0) {
            startTime = System.currentTimeMillis();
        } else {
            startTime = System.currentTimeMillis() - pauseTime;
            pauseTime = 0;
        }
        isRunning = true;
        handler.post(updateRunnable);
    }
    
    private void pauseStopwatch() {
        pauseTime = System.currentTimeMillis() - startTime;
        isRunning = false;
        handler.removeCallbacks(updateRunnable);
    }
    
    private void resetStopwatch() {
        isRunning = false;
        startTime = 0;
        pauseTime = 0;
        handler.removeCallbacks(updateRunnable);
        updateStopwatch();
    }
    
    private void updateStopwatch() {
        long elapsedTime = isRunning ? System.currentTimeMillis() - startTime : pauseTime;
        
        long hours = elapsedTime / (1000 * 60 * 60);
        long minutes = (elapsedTime / (1000 * 60)) % 60;
        long seconds = (elapsedTime / 1000) % 60;
        long milliseconds = (elapsedTime % 1000) / 10;
        
        String time = String.format(Locale.getDefault(), 
            "%02d:%02d:%02d.%02d", hours, minutes, seconds, milliseconds);
        tvStopwatch.setText(time);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateRunnable);
    }
}
