package com.example.remindME.view;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.remindME.R;
import com.example.remindME.adapter.ContentListAdapter;
import com.example.remindME.fragment.HomeFragment;
import com.example.remindME.model.ContentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FloatingButton {
    public FloatingActionButton actionButton;
    public FloatingActionButton byDifficulty;
    public FloatingActionButton byDate;
    public FloatingActionButton byDefault;

    public Animation rotateOpen;
    public Animation rotateClose;
    public Animation fromBottom;
    public Animation toBottom;

    private boolean isClicked;

    public void initialization(View view, Context context){
        actionButton = view.findViewById(R.id.viewFloatingButton);
        byDifficulty = view.findViewById(R.id.sortByDifficultyButton);
        byDate = view.findViewById(R.id.sortByDateButton);
        byDefault = view.findViewById(R.id.sortByDefaultButton);
        rotateOpen = AnimationUtils.loadAnimation(context, R.anim.rotate_open_animation);
        rotateClose = AnimationUtils.loadAnimation(context, R.anim.rotate_cloes_animation);
        fromBottom = AnimationUtils.loadAnimation(context, R.anim.from_bottom_animation);
        toBottom = AnimationUtils.loadAnimation(context, R.anim.to_bottom_animation);

        onClickListener();
    }

    public void onClickListener(){
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFloatingButtonClicked();
            }
        });
    }

    public void onFloatingButtonClicked(){
        setVisibiity();
        setAnimation();
        if(isClicked){
            this.isClicked = false;
        }
        else{
            this.isClicked = true;
        }
    }

    private void setVisibiity(){
        if(!isClicked){
            byDifficulty.setVisibility(View.VISIBLE);
            byDate.setVisibility(View.VISIBLE);
            byDefault.setVisibility(View.VISIBLE);
        }
        else {
            byDifficulty.setVisibility(View.INVISIBLE);
            byDate.setVisibility(View.INVISIBLE);
            byDefault.setVisibility(View.INVISIBLE);
        }
    }

    private void setAnimation(){
        if(!isClicked){
            byDifficulty.startAnimation(fromBottom);
            byDate.startAnimation(fromBottom);
            byDefault.startAnimation(fromBottom);

        }
        else{
            byDifficulty.startAnimation(toBottom);
            byDate.startAnimation(toBottom);
            byDefault.startAnimation(toBottom);
        }
    }

}
