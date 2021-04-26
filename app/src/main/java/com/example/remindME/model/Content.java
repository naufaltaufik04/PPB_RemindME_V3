package com.example.remindME.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(tableName = "content_table")
public class Content implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private Integer contentId;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "details")
    private String details;

    @NonNull
    @ColumnInfo(name = "difficulty")
    private Integer difficulty;

    @NonNull
    @ColumnInfo(name = "dueDate")
    private Date dueDate;

    @NonNull
    @ColumnInfo(name = "dataCreated")
    private Date dateCreated;

    @NonNull
    @ColumnInfo(name = "isReminded")
    private boolean isReminded;

    @NonNull
    @ColumnInfo(name = "isCompleted")
    private boolean isCompleted;

    @ColumnInfo(name = "completedDate")
    private LocalDateTime completedDate;


    public Content(@NonNull String title, @NonNull String details, @NonNull Integer difficulty,
                   @NonNull Date dueDate, @NonNull Date dateCreated, @NonNull boolean isReminded) {
        this.title = title;
        this.details = details;
        this.difficulty = difficulty;
        this.dueDate = dueDate;
        this.dateCreated = dateCreated;
        this.isReminded = isReminded;
        this.isCompleted = false;
    }

    /*
     * ================================================ Getter & Setter =======================================================
     * */
    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) { this.contentId = contentId; }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public boolean isReminded() {
        return isReminded;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) { this.isCompleted = isCompleted; }

    public LocalDateTime getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
    }

    /*
     * ================================================ Parcelable =======================================================
     * */
    protected Content(Parcel in) {
        contentId = in.readInt();
        title = in.readString();
        details = in.readString();
        difficulty = in.readInt();
        dueDate = (Date) in.readSerializable();
        dateCreated = (Date) in.readSerializable();
        isReminded = in.readBoolean();
        isCompleted = in.readBoolean();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(contentId);
        dest.writeString(title);
        dest.writeString(details);
        dest.writeInt(difficulty);
        dest.writeSerializable(dueDate);
        dest.writeSerializable(dateCreated);
        dest.writeBoolean(isReminded);
        dest.writeBoolean(isCompleted);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Content> CREATOR = new Creator<Content>() {
        @Override
        public Content createFromParcel(Parcel in) {
            return new Content(in);
        }

        @Override
        public Content[] newArray(int size) {
            return new Content[size];
        }
    };
}