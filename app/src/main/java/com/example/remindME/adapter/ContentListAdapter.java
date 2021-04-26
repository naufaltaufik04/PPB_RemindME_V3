package com.example.remindME.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.remindME.model.Content;
import com.example.remindME.model.ContentViewHolder;

import java.util.concurrent.TimeUnit;

public class ContentListAdapter extends ListAdapter<Content, ContentViewHolder> {

    public ContentListAdapter(@NonNull DiffUtil.ItemCallback<Content> diffCallback) {
        super(diffCallback);
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ContentViewHolder.create(parent, getCurrentList());
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {
        Content current = getItem(position);

        String temp;
        switch (current.getDifficulty()){
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

        int timeRemaining = (int) TimeUnit.DAYS.convert(
                Long.valueOf(current.getDueDate().getTime()) - (current.getDateCreated().getTime()),
                TimeUnit.MILLISECONDS);

        holder.bind(current.getDueDate().toString().substring(4, 7),
                String.valueOf(current.getDueDate().getDate()),
                current.getTitle().toString(),
                ("Kesulitan: " + temp),
                timeRemaining,
                current.isCompleted()
        );
    }

    static public class WordDiff extends DiffUtil.ItemCallback<Content> {

        @Override
        public boolean areItemsTheSame(@NonNull Content oldItem, @NonNull Content newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Content oldItem, @NonNull Content newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    }
}