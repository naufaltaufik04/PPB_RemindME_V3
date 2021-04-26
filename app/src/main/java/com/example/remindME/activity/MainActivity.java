package com.example.remindME.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.remindME.fragment.AddContentFragment;
import com.example.remindME.fragment.CompletedListFragment;
import com.example.remindME.fragment.HomeFragment;
import com.example.remindME.R;
import com.example.remindME.customComponent.SwipeDisabledViewPager;
import com.example.remindME.model.ViewFloatingButton;
import com.example.remindME.adapter.ViewPageAdapter;

public class MainActivity<view> extends AppCompatActivity{

    private final int ID_ADD_CONTENT = 1;
    private final int ID_HOME = 2;
    private final int ID_COMPLETE = 3;

    private SwipeDisabledViewPager viewPager;
    private ViewPageAdapter adapter;
    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ubah warna status bar
        setStatusBarColor(getResources().getColor(R.color.secondary));

        //Sembuyikan header/toolbar
        getSupportActionBar().hide();

        //Inisiasi
        ViewFloatingButton viewFloatingButton = new ViewFloatingButton();

        //Inisiasi recyclerview
        initializationAdapter();

        //Inisiasi bottom navigation
        MeowBottomNavigation bottomNav = findViewById(R.id.footer);
        bottomNav.setVisibility(View.VISIBLE);
        initializationBottomNav();

    }

    private void setStatusBarColor(int color){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
        }
    }

    private void initializationAdapter(){
        viewPager = findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageSelected(int position) { }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        adapter = new ViewPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new AddContentFragment(), "AddContact");
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new CompletedListFragment(), "CompleatedList");
        viewPager.setAdapter(adapter);
    }

    private void initializationBottomNav() {
        MeowBottomNavigation bottomNavigation = findViewById(R.id.footer);
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_ADD_CONTENT, R.drawable.ic_add_content));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME, R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_COMPLETE, R.drawable.ic_complete));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener(){
            @Override
            public void onShowItem(MeowBottomNavigation.Model item){
                int selectedPage;
                switch (item.getId()){
                    case 1:{
                        selectedPage = 0;
                        break;
                    }
                    case 2:{
                        selectedPage = 1;
                        break;
                    }
                    case 3:{
                        selectedPage = 2;
                        break;
                    }
                    default:{
                        selectedPage = 1;
                        break;
                    }
                }
                viewPager.setCurrentItem(selectedPage, true);
            }
        });

        bottomNavigation.show(ID_HOME, true);
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) { }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) { }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}