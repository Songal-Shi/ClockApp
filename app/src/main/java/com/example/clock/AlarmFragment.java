package com.example.clock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlarmFragment extends Fragment {
    
    private ListView lvAlarms;
    private List<String> alarmList;
    private ArrayAdapter<String> adapter;
    private AlarmManager alarmManager;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        
        lvAlarms = view.findViewById(R.id.lvAlarms);
        alarmList = new ArrayList<>();
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, alarmList);
        lvAlarms.setAdapter(adapter);
        
        alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        
        // Add sample alarms
        addAlarm(8, 0, "早上闹钟");
        addAlarm(12, 0, "午餐提醒");
        addAlarm(18, 30, "下班提醒");
        
        view.findViewById(R.id.btnAddAlarm).setOnClickListener(v -> {
            // In real app, show time picker dialog
            Toast.makeText(requireContext(), "点击添加闹钟", Toast.LENGTH_SHORT).show();
        });
        
        return view;
    }
    
    private void addAlarm(int hour, int minute, String label) {
        String time = String.format("%02d:%02d", hour, minute);
        String alarmInfo = time + " - " + label;
        alarmList.add(alarmInfo);
        adapter.notifyDataSetChanged();
        
        // Set alarm
        setAlarm(hour, minute, alarmInfo);
    }
    
    private void setAlarm(int hour, int minute, String alarmInfo) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        
        // If time already passed, set for next day
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        
        Intent intent = new Intent(requireContext(), AlarmReceiver.class);
        intent.putExtra("ALARM_INFO", alarmInfo);
        
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
            requireContext(), 
            alarmInfo.hashCode(), 
            intent, 
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.getTimeInMillis(),
            pendingIntent
        );
    }
}
