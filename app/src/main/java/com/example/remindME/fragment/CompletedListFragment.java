package com.example.remindME.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.remindME.R;
import com.example.remindME.adapter.ContentListAdapter;
import com.example.remindME.customComponent.EmptyRecyclerView;
import com.example.remindME.customComponent.SpacingItemDecorator;
import com.example.remindME.model.Content;
import com.example.remindME.model.ContentViewModel;

public class CompletedListFragment extends Fragment {

    private EmptyRecyclerView recyclerView;
    private ContentViewModel contentViewModel;
    private ContentListAdapter adapter;

    public CompletedListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completed_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView2);
        recyclerView.setEmptyView(view.findViewById(R.id.emptyView));

        adapter = new ContentListAdapter(new ContentListAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        contentViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(ContentViewModel.class);
        contentViewModel.getCompletedContent().observe(this, content -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(content);
        });

        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(20, 10, 10);
        recyclerView.addItemDecoration(itemDecorator);

        removeContent();

        return view;
    }

    public void removeContent(){
        ItemTouchHelper.SimpleCallback itemTouchHelper =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        Content currentItem = adapter.getCurrentList().get(viewHolder.getAdapterPosition());
                        contentViewModel.deleteContent(currentItem);
                    }
                };
        //Set agar konten dapat di geser
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);
    }
}