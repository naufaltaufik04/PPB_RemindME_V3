package com.example.remindME.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.remindME.fragment.AddContentFragment;
import com.example.remindME.fragment.CompletedListFragment;
import com.example.remindME.fragment.HomeFragment;
import com.example.remindME.R;
import com.example.remindME.customComponent.SwipeDisabledViewPager;
import com.example.remindME.adapter.ViewPageAdapter;
import com.example.remindME.view.BottomNavigation;

public class MainActivity<view> extends AppCompatActivity{

    private SwipeDisabledViewPager viewPager;
    private ViewPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ubah warna status bar
        setStatusBarColor(getResources().getColor(R.color.secondary));

        //Sembuyikan header/toolbar
        getSupportActionBar().hide();

        //Inisiasi
        initializationViewPager();
        initializationAdapter();

        BottomNavigation bottomNavigation = new BottomNavigation();;
        bottomNavigation.initializationBottomNav(viewPager, ((ViewGroup) (findViewById(android.R.id.content))).getChildAt(0));
    }

    private void setStatusBarColor(int color){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
        }
    }

    private void initializationViewPager(){
        viewPager = findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageSelected(int position) { }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    private void initializationAdapter(){
        adapter = new ViewPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new AddContentFragment(), "AddContact");
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new CompletedListFragment(), "CompleatedList");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}