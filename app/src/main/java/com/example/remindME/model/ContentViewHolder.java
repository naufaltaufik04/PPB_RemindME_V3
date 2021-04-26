package com.example.remindME.model;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.remindME.R;
import com.example.remindME.activity.DetailsActivity;

import java.util.List;

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

        //Saat klik recyclerview item
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra("Content", listContent.get(getAdapterPosition()));
                view.getContext().startActivities(new Intent[]{intent});
            }
        });
    }

    public void bind(String month, String day, String title, String difficulty, int timeRemaining, boolean isCompleated){
        this.month.setText(month);
        this.day.setText(day);
        this.title.setText(title);
        this.difficulty.setText(difficulty);
        this.timeRemaining.setText("Tersisa " + String.valueOf(timeRemaining) + " hari lagi");

        setContentColor(timeRemaining, isCompleated);
    }

    public void setContentColor(int timeRemaining, boolean isCompleated){
        if(timeRemaining == 0) {
            contentCard.setBackgroundColor(Color.parseColor("#307FFF"));
        }
        else if(timeRemaining < 0){
            contentCard.setBackgroundColor(Color.parseColor("#FF6A6A"));
        }
        else{
            contentCard.setBackgroundColor(Color.parseColor("#F5F9FC"));
        }

        if(isCompleated){
            contentCard.setBackgroundColor(Color.parseColor("#3ED709"));
        }
    }

    public static ContentViewHolder create(ViewGroup parent, List<Content> listContent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new ContentViewHolder(view, listContent);
    }
}