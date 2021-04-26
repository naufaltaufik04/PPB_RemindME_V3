package com.example.remindME.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.remindME.model.Content;
import com.example.remindME.adapter.ContentListAdapter;
import com.example.remindME.model.ContentViewModel;
import com.example.remindME.customComponent.EmptyRecyclerView;
import com.example.remindME.R;
import com.example.remindME.customComponent.SpacingItemDecorator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {

    private EmptyRecyclerView recyclerView;
    private ContentViewModel contentViewModel;
    private ContentListAdapter adapter;

    FloatingActionButton actionButton;
    FloatingActionButton byDifficulty;
    FloatingActionButton byDate;
    FloatingActionButton byDefault;
    Animation rotateOpen;
    Animation rotateClose;
    Animation fromBottom;
    Animation toBottom;

    private boolean isClicked;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setEmptyView(view.findViewById(R.id.emptyView));

        adapter = new ContentListAdapter(new ContentListAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        contentViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(ContentViewModel.class);
        contentViewModel.getContentByDefault().observe(this, content -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(content);
        });
        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(20, 10, 10);
        recyclerView.addItemDecoration(itemDecorator);

        byDifficulty = view.findViewById(R.id.sortByDifficultyButton);
        byDate = view.findViewById(R.id.sortByDateButton);
        byDefault = view.findViewById(R.id.sortByDefaultButton);
        rotateOpen = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_open_animation);
        rotateClose = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_cloes_animation);
        fromBottom = AnimationUtils.loadAnimation(getContext(), R.anim.from_bottom_animation);
        toBottom = AnimationUtils.loadAnimation(getContext(), R.anim.to_bottom_animation);

        actionButton = view.findViewById(R.id.viewFloatingButton);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFloatingButtonClicked(view);
            }
        });

        onFloatingButtonClick();
        contentIsCompleate();
        return view;
    }

    private void onFloatingButtonClicked(View view){
        setVisibiity(view);
        if(isClicked){
            isClicked = false;
        }
        else{
            isClicked = true;
        }
    }

    private void setVisibiity(View view){
        if(!isClicked){
            byDifficulty.setVisibility(View.VISIBLE);
            byDate.setVisibility(View.VISIBLE);
            byDefault.setVisibility(View.VISIBLE);

            byDifficulty.startAnimation(fromBottom);
            byDate.startAnimation(fromBottom);
            byDefault.startAnimation(fromBottom);

        }
        else{
            byDifficulty.setVisibility(View.INVISIBLE);
            byDate.setVisibility(View.INVISIBLE);
            byDefault.setVisibility(View.INVISIBLE);

            byDifficulty.startAnimation(toBottom);
            byDate.startAnimation(toBottom);
            byDefault.startAnimation(toBottom);
        }
    }


    private void onFloatingButtonClick(){
        byDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortingContent(1);
            }
        });
        byDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortingContent(2);
            }
        });
        byDifficulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortingContent(3);
            }
        });
    }

    public void contentIsCompleate(){
        ItemTouchHelper.SimpleCallback itemTouchHelper =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        Content currentItem = adapter.getCurrentList().get(viewHolder.getAdapterPosition());
                        contentViewModel.updateStatus(currentItem.getContentId());
                    }
                };
        //Set agar konten dapat di geser
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);
    }

    public void sortingContent(int choosed){
        switch (choosed){
            case 1:{
                contentViewModel.getContentByDefault().observe(this, content -> {
                    adapter.submitList(content);
                });
                break;
            }
            case 2:{
                contentViewModel.getContentByDate().observe(this, content -> {
                    adapter.submitList(content);
                });
                break;
            }
            case 3:{
                contentViewModel.getContentByDifficulty().observe(this, content -> {
                    adapter.submitList(content);
                });
                break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}