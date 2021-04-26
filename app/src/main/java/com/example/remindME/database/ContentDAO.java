package com.example.remindME.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.remindME.model.Content;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Dao
public interface ContentDAO {

    @Query("SELECT * FROM content_table WHERE isCompleted = 0 ORDER BY contentId DESC")
    LiveData<List<Content>> getContentByDefault();

    @Query("SELECT * FROM content_table WHERE isCompleted = 0 ORDER BY  dueDate ASC")
    LiveData<List<Content>> getContentByDate();

    @Query("SELECT * FROM content_table WHERE isCompleted = 0 ORDER BY difficulty ASC")
    LiveData<List<Content>> getContentByDifficulty();

    @Query("SELECT * FROM content_table WHERE isCompleted = 1 ORDER BY completedDate DESC")
    LiveData<List<Content>> getCompletedContent();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Content content);

    @Query("UPDATE content_table SET isCompleted = 1, completedDate = :now WHERE contentId = :contentId")
    int updateStatus(int contentId, LocalDateTime now);

    @Delete
    void deleteContent(Content content);

    @Query("DELETE FROM content_table")
    void deleteAll();

}