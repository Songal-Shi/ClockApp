package com.example.clock;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.ArrayList;
import java.util.List;

public class ClockPagerAdapter extends FragmentStateAdapter {
    
    private final List<Fragment> fragments = new ArrayList<>();
    
    public ClockPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragments.add(new ClockFragment());
        fragments.add(new AlarmFragment());
        fragments.add(new StopwatchFragment());
        fragments.add(new TimerFragment());
    }
    
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }
    
    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
