package com.example.clock;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ClockPagerAdapter pagerAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        
        pagerAdapter = new ClockPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("时钟");
                    tab.setIcon(R.drawable.ic_clock);
                    break;
                case 1:
                    tab.setText("闹钟");
                    tab.setIcon(R.drawable.ic_alarm);
                    break;
                case 2:
                    tab.setText("秒表");
                    tab.setIcon(R.drawable.ic_stopwatch);
                    break;
                case 3:
                    tab.setText("计时器");
                    tab.setIcon(R.drawable.ic_timer);
                    break;
            }
        }).attach();
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // Handle alarm dismissal from notification
        if (intent.getBooleanExtra("DISMISS_ALARM", false)) {
            // Stop alarm service
            stopService(new Intent(this, AlarmService.class));
        }
    }
}
