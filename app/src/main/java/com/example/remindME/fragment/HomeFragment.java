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
import com.example.remindME.view.FloatingButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {

    private EmptyRecyclerView recyclerView;
    private ContentViewModel contentViewModel;
    private ContentListAdapter adapter;


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

        FloatingButton floatingButton = new FloatingButton();
        floatingButton.initialization(view, getContext());
        sortingContent(floatingButton, this);

        contentIsCompleate();
        return view;
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

    public void sortingContent(FloatingButton floatingButton, HomeFragment home){
        floatingButton.byDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentViewModel.getContentByDefault().observe(home, content -> {
                    adapter.submitList(content);
                });
            }
        });
        floatingButton.byDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentViewModel.getContentByDate().observe(home, content -> {
                    adapter.submitList(content);
                });
            }
        });
        floatingButton.byDifficulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentViewModel.getContentByDifficulty().observe(home, content -> {
                    adapter.submitList(content);
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}