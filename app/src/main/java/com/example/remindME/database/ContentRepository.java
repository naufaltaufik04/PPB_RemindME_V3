package com.example.remindME.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.remindME.model.Content;

import java.time.LocalDateTime;
import java.util.List;

public class ContentRepository {

    private ContentDAO contentDAO;
    private LiveData<List<Content>> allContent;

    public ContentRepository(Application application) {
        ContentRoomDatabase db = ContentRoomDatabase.getDatabase(application);
        contentDAO = db.contentDAO();
        allContent = contentDAO.getContentByDefault();
    }

    public LiveData<List<Content>> getContentByDefault() {
        return allContent;
    }

    public LiveData<List<Content>> getContentByDate() {
        return contentDAO.getContentByDate();
    }

    public LiveData<List<Content>> getContentByDifficulty() {
        return contentDAO.getContentByDifficulty();
    }

    public LiveData<List<Content>> getCompletedContent() { return contentDAO.getCompletedContent(); }

    public void insert(Content content) {
        ContentRoomDatabase.databaseWriteExecutor.execute(() -> {
            contentDAO.insert(content);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateStatus(int contentId){
        ContentRoomDatabase.databaseWriteExecutor.execute(() -> {
            contentDAO.updateStatus(contentId, LocalDateTime.now());
        });
    }

    public void deleteContent(Content content){
        ContentRoomDatabase.databaseWriteExecutor.execute(() -> {
            contentDAO.deleteContent(content);
        });
    }

    public void deleteAll(){
        ContentRoomDatabase.databaseWriteExecutor.execute(() -> {
            contentDAO.deleteAll();
        });
    }
}