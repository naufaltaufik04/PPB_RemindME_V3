package com.example.remindME.model;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.remindME.R;
import com.example.remindME.activity.DetailsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ContentViewHolder extends RecyclerView.ViewHolder {
    private TextView month, day, title, difficulty, timeRemaining;
    private LinearLayout contentCard;

    private ContentViewHolder(View itemView, List<Content> listContent) {
        super(itemView);

        month = itemView.findViewById(R.id.monthTextView);
        day = itemView.findViewById(R.id.dayTextView);
        title = itemView.findViewById(R.id.titleTextView);
        difficulty = itemView.findViewById(R.id.difficultyTextView);
        timeRemaining = itemView.findViewById(R.id.timeRemainingTextView);
        contentCard = itemView.findViewById(R.id.card);

        //Saat klik item pada recyclerview
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra("Content", listContent.get(getAdapterPosition()));
                view.getContext().startActivities(new Intent[]{intent});
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void bind(Content current){
        this.month.setText(current.getDueDate().toString().substring(4, 7));
        this.day.setText(String.valueOf(current.getDueDate().getDate()));
        this.title.setText(current.getTitle().toString());
        this.difficulty.setText("Kesulitan: " + getDifficulty(current));
        this.timeRemaining.setText(updateRemainingTime(current));

        setContentColor(getRemainingTIme(current.getDueDate()), current.isCompleted());
    }

    public String getDifficulty(Content currentItem){
        String temp;
        switch (currentItem.getDifficulty()){
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
        return temp;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getRemainingTIme(Date dueDate){
        LocalDate currentDate = LocalDate.now();
        Date now = null;
        try {
            now = new SimpleDateFormat("yyyy/MM/dd").parse(
                    currentDate.getYear() + "/" + currentDate.getMonthValue() + "/" + currentDate.getDayOfMonth()
            );
        } catch (ParseException e) { e.printStackTrace(); }

        int remainingTime = (int) TimeUnit.DAYS.convert(dueDate.getTime() - now.getTime(), TimeUnit.MILLISECONDS);
        return remainingTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String updateRemainingTime(Content current){
        String temp;
        int remainingTime = getRemainingTIme(current.getDueDate());
        if(remainingTime > 0){
            temp = "Tersisa " + remainingTime + " hari lagi";
        }
        else if(remainingTime == 0){
            temp = "Hari ini";
        }
        else{
            temp = "Terlambat";
        }

        if(current.isCompleted()){
            temp = "Selesai";
        }
        return temp;
    }

    public void setContentColor(int timeRemaining, boolean isCompleated){
        if(timeRemaining == 0) {
            contentCard.setBackgroundColor(Color.parseColor("#D9307FFF"));
        }
        else if(timeRemaining < 0){
            contentCard.setBackgroundColor(Color.parseColor("#D9FF6A6A"));
        }
        else{
            contentCard.setBackgroundColor(Color.parseColor("#F5F9FC"));
        }

        if(isCompleated){
            contentCard.setBackgroundColor(Color.parseColor("#D93ED709"));
        }
//        if(timeRemaining == 0) {
//            contentCard.setBackgroundColor(R.color.today_item_color);
//        }
//        else if(timeRemaining < 0){
//            contentCard.setBackgroundColor(R.color.late_item_color);
//        }
//        else{
//            contentCard.setBackgroundColor(R.color.default_item_color);
//        }
//
//        if(isCompleated){
//            contentCard.setBackgroundColor(R.color.complete_item_color);
//        }

    }

    public static ContentViewHolder create(ViewGroup parent, List<Content> listContent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new ContentViewHolder(view, listContent);
    }
}