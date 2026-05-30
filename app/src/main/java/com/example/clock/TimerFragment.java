package com.example.clock;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.util.Locale;

public class TimerFragment extends Fragment {
    
    private TextView tvTimer;
    private EditText etMinutes, etSeconds;
    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;
    private boolean isRunning = false;
    private long timeLeftInMillis = 0;
    private long totalTimeInMillis = 0;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        
        tvTimer = view.findViewById(R.id.tvTimer);
        etMinutes = view.findViewById(R.id.etMinutes);
        etSeconds = view.findViewById(R.id.etSeconds);
        progressBar = view.findViewById(R.id.progressBar);
        
        view.findViewById(R.id.btnStartPause).setOnClickListener(v -> {
            if (!isRunning) {
                startTimer();
            } else {
                pauseTimer();
            }
        });
        
        view.findViewById(R.id.btnReset).setOnClickListener(v -> resetTimer());
        
        updateTimerDisplay(0);
        
        return view;
    }
    
    private void startTimer() {
        if (timeLeftInMillis == 0) {
            // Get input time
            String minStr = etMinutes.getText().toString();
            String secStr = etSeconds.getText().toString();
            
            int minutes = minStr.isEmpty() ? 0 : Integer.parseInt(minStr);
            int seconds = secStr.isEmpty() ? 0 : Integer.parseInt(secStr);
            
            if (minutes == 0 && seconds == 0) {
                Toast.makeText(requireContext(), "请设置时间", Toast.LENGTH_SHORT).show();
                return;
            }
            
            timeLeftInMillis = (minutes * 60 + seconds) * 1000;
            totalTimeInMillis = timeLeftInMillis;
            progressBar.setMax(100);
        }
        
        isRunning = true;
        
        countDownTimer = new CountDownTimer(timeLeftInMillis, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerDisplay(millisUntilFinished);
                
                // Update progress bar
                int progress = (int) ((totalTimeInMillis - millisUntilFinished) * 100 / totalTimeInMillis);
                progressBar.setProgress(progress);
            }
            
            @Override
            public void onFinish() {
                isRunning = false;
                timeLeftInMillis = 0;
                updateTimerDisplay(0);
                progressBar.setProgress(100);
                Toast.makeText(requireContext(), "计时结束！", Toast.LENGTH_LONG).show();
            }
        }.start();
    }
    
    private void pauseTimer() {
        isRunning = false;
        countDownTimer.cancel();
    }
    
    private void resetTimer() {
        isRunning = false;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timeLeftInMillis = 0;
        totalTimeInMillis = 0;
        updateTimerDisplay(0);
        progressBar.setProgress(0);
        etMinutes.setText("");
        etSeconds.setText("");
    }
    
    private void updateTimerDisplay(long millis) {
        long totalSeconds = millis / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        long milliseconds = (millis % 1000) / 10;
        
        String time = String.format(Locale.getDefault(), 
            "%02d:%02d.%02d", minutes, seconds, milliseconds);
        tvTimer.setText(time);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
