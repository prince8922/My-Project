package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.google.android.material.tabs.TabLayout;

public class TabDemoActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_demo);

        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.tab_viewpager);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {tabLayout.setupWithViewPager(viewPager); }
        });
        //Activity To Activity => Intent
        //Activity To Fragment => getSupportFragmentManager()

        viewPager.setAdapter(new TabDemoAdapter(getSupportFragmentManager()));


    }

    private class TabDemoAdapter extends FragmentPagerAdapter {
        public TabDemoAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
                case 0:
                    return "Chat";
                case 1:
                    return "Status";
                case 2:
                    return "Call";
                default:
                    return "Demo";
            }
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new ChatFragment();
                case 1:
                    return new StatusFragment();
                case 2:
                    return new  CallFragment();
                default:
                    return new DemoFragment();
            }
        }

        @Override
        public int getCount() {
            return 10;
        }


    }
}