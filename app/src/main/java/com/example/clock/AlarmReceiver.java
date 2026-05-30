package com.example.clock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    
    @Override
    public void onReceive(Context context, Intent intent) {
        String alarmInfo = intent.getStringExtra("ALARM_INFO");
        
        // Show notification
        Toast.makeText(context, "闹钟: " + alarmInfo, Toast.LENGTH_LONG).show();
        
        // Vibrate
        vibrate(context);
        
        // Start alarm service for sound
        Intent serviceIntent = new Intent(context, AlarmService.class);
        serviceIntent.putExtra("ALARM_INFO", alarmInfo);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent);
        } else {
            context.startService(serviceIntent);
        }
    }
    
    private void vibrate(Context context) {
        Vibrator vibrator;
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            VibratorManager vibratorManager = 
                (VibratorManager) context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
            vibrator = vibratorManager.getDefaultVibrator();
        } else {
            vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        }
        
        if (vibrator != null && vibrator.hasVibrator()) {
            // Vibrate pattern: vibrate 500ms, pause 500ms, repeat
            long[] pattern = {0, 500, 500};
            vibrator.vibrate(android.os.VibrationEffect.createWaveform(pattern, 0));
        }
    }
}
