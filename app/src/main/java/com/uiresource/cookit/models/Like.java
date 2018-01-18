package com.uiresource.cookit.models;

import java.util.Calendar;

/**
 * Created by Tran Trung Hieu on 1/16/18.
 */

public class  Like {

    private String id;
    private String authorId;
    private long createdDate;


    public Like() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public Like(String authorId) {
        this.authorId = authorId;
        this.createdDate = Calendar.getInstance().getTimeInMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreatedDate() {
        return createdDate;
    }

}
