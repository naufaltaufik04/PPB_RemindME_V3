package com.example.remindME.view;

import android.view.View;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.remindME.R;
import com.example.remindME.adapter.ViewPageAdapter;
import com.example.remindME.customComponent.SwipeDisabledViewPager;

public class BottomNavigation{
    private final int ID_ADD_CONTENT = 1;
    private final int ID_HOME = 2;
    private final int ID_COMPLETE = 3;

    MeowBottomNavigation bottomNavigation;

    public void initializationBottomNav(SwipeDisabledViewPager viewPager, View view) {
        bottomNavigation = view.findViewById(R.id.footer);
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_ADD_CONTENT, R.drawable.ic_add_content));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME, R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_COMPLETE, R.drawable.ic_complete));

        onClickListener(viewPager);

        bottomNavigation.show(ID_HOME, true);
    }

    public void onClickListener(SwipeDisabledViewPager viewPager){
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

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) { }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) { }
        });
    }
}
