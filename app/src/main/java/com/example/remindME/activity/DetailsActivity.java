package com.example.remindME.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.remindME.model.Content;
import com.example.remindME.R;

public class DetailsActivity extends AppCompatActivity {

    private Content content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setStatusBarColor();

        //Sembuyikan header/toolbar
        getSupportActionBar().hide();

        Intent intent = getIntent();
        content = intent.getParcelableExtra("Content");

        OnBackButtonClickListener();
        setCardContent();
    }

    private void setStatusBarColor(){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.secondary));
        }
    }

    private void OnBackButtonClickListener(){
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setCardContent(){
        TextView title = findViewById(R.id.titleInput);
        TextView details = findViewById(R.id.detailsInput);
        TextView difficulty = findViewById(R.id.difficultyInput);
        TextView dueDate = findViewById(R.id.dueDateInput);
        CheckBox remindMeBox = findViewById(R.id.remindMeBox);

        title.setText(content.getTitle());
        details.setText(content.getDetails());

        String temp;
        switch (content.getDifficulty()){
            case 1:{
                temp = "Sulit";
                break;
            }
            case 2:{
                temp = "Sedang";
                break;
            }
            case 3:{
                temp = "Mudah";
                break;
            }
            default:{
                temp = " - ";
                break;
            }
        }
        difficulty.setText(temp);

        dueDate.setText(content.getDueDate().getDate() +
                " - " + (content.getDueDate().getMonth()+1) +
                " - " + (content.getDueDate().getYear()+1900));

        remindMeBox.setChecked(content.isReminded());
    }
}