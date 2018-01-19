package com.uiresource.cookit.recycler;

/**
 * Created by Dytstudio.
 */

public class ItemRecipe {
    String time;
    String comment;
    String img;
    String like;

    public String getLike() {  return like;    }

    public void setLike(String like) {  this.like = like;    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment;  }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }
}
