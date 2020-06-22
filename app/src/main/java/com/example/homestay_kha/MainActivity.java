package com.example.homestay_kha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.homestay_kha.Adapers.Adapter_viewpaper_Home;
import com.example.homestay_kha.Model.Hotel_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        RadioGroup.OnCheckedChangeListener {
    ViewPager viewPagerHome;
    RadioButton btn_hotel_current, btn_history_route;
    RadioGroup radioGroup_Home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        viewPagerHome = findViewById(R.id.viewpaper_Home);
        btn_hotel_current = findViewById(R.id.hotel_current_btn);
        btn_history_route= findViewById(R.id.history_btn);
        radioGroup_Home = findViewById(R.id.radio_group_home);

        Adapter_viewpaper_Home adapterViewpaperHome = new Adapter_viewpaper_Home(getSupportFragmentManager());
        viewPagerHome.setAdapter(adapterViewpaperHome);
        viewPagerHome.addOnPageChangeListener(this);
        radioGroup_Home.setOnCheckedChangeListener(this);

    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    @Override
    public void onPageSelected(int position) {
        switch (position)
        {
            case 0:
                btn_hotel_current.setChecked(true);
                break;
            case 1:
                btn_history_route.setChecked(true);
                break;
        }

    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId)
        {
            case R.id.hotel_current_btn:
                viewPagerHome.setCurrentItem(0);
                break;
            case R.id.history_btn:
                viewPagerHome.setCurrentItem(1);
                break;

        }

    }
}
