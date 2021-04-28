package com.example.remindME.adapter;

import android.os.Build;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.remindME.model.Content;
import com.example.remindME.model.ContentViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ContentListAdapter extends ListAdapter<Content, ContentViewHolder> {

    public ContentListAdapter(@NonNull DiffUtil.ItemCallback<Content> diffCallback) {
        super(diffCallback);
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ContentViewHolder.create(parent, getCurrentList());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {
        Content current = getItem(position);
        holder.bind(current);
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