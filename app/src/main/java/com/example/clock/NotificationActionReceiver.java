package com.example.clock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationActionReceiver extends BroadcastReceiver {
    
    public static final String ACTION_DISMISS = "com.example.clock.DISMISS_ALARM";
    public static final String ACTION_SNOOZE = "com.example.clock.SNOOZE_ALARM";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        
        if (ACTION_DISMISS.equals(action)) {
            // Stop alarm service
            Intent serviceIntent = new Intent(context, AlarmService.class);
            context.stopService(serviceIntent);
            
            Toast.makeText(context, "闹钟已关闭", Toast.LENGTH_SHORT).show();
        } 
        else if (ACTION_SNOOZE.equals(action)) {
            // Stop current alarm
            Intent serviceIntent = new Intent(context, AlarmService.class);
            context.stopService(serviceIntent);
            
            // Set new alarm for 5 minutes later
            // In real implementation, use AlarmManager to set snooze
            Toast.makeText(context, "5分钟后再次提醒", Toast.LENGTH_SHORT).show();
        }
    }
}
