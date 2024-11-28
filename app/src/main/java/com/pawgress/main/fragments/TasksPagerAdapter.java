package com.pawgress.main.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TasksPagerAdapter extends FragmentStateAdapter {
    public TasksPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new InProgressFragment();
        }
        return new ToDoFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
