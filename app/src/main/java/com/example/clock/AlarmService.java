package com.example.clock;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import androidx.core.app.NotificationCompat;

public class AlarmService extends Service {
    
    private static final String CHANNEL_ID = "alarm_channel";
    private MediaPlayer mediaPlayer;
    
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String alarmInfo = intent.getStringExtra("ALARM_INFO");
        
        // Create notification
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("闹钟提醒")
            .setContentText(alarmInfo)
            .setSmallIcon(R.drawable.ic_alarm)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .build();
        
        // Start foreground service
        startForeground(1, notification);
        
        // Play alarm sound
        playAlarmSound();
        
        return START_STICKY;
    }
    
    private void playAlarmSound() {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(this, 
                android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "闹钟通知",
                NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("闹钟提醒通知");
            
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
