package com.example.homestay_kha.Adapers;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.homestay_kha.Views.Fragments.History_Fragment;
import com.example.homestay_kha.Views.Fragments.Hotel_Fragment;

public class Adapter_viewpaper_Home extends FragmentPagerAdapter {
    Hotel_Fragment homeFragment;
    History_Fragment historyFragment;

    public Adapter_viewpaper_Home(FragmentManager fm) {
        super(fm);
        homeFragment = new Hotel_Fragment();
        historyFragment = new History_Fragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return homeFragment;
            case 1:
                return historyFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
