package com.example.remindME.model;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.remindME.database.ContentRepository;

import java.util.List;

public class ContentViewModel extends AndroidViewModel {

    private ContentRepository repository;

    private final LiveData<List<Content>> allContent;

    public ContentViewModel(Application application) {
        super(application);
        repository = new ContentRepository(application);
        allContent = repository.getContentByDefault();
    }

    public LiveData<List<Content>> getContentByDefault() { return allContent; }

    public LiveData<List<Content>> getContentByDate() { return repository.getContentByDate(); }

    public LiveData<List<Content>> getContentByDifficulty() { return repository.getContentByDifficulty(); }

    public LiveData<List<Content>> getCompletedContent() { return repository.getCompletedContent(); }

    public void insert(Content content) { repository.insert(content); }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateStatus(int contentId) { repository.updateStatus(contentId); }

    public void deleteContent(Content content){ repository.deleteContent(content); }

    public void deleteAll(){ repository.deleteAll(); }

}