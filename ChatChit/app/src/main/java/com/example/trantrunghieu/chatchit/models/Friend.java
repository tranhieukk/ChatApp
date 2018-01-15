package com.example.trantrunghieu.chatchit.models;

/**
 * Created by Tran Trung Hieu on 1/13/18.
 */

public class Friend {
    int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    String username=null;
    String fullName=null;
    String  image=null;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImage(){

        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }




    public Friend(String username, String fullName, String image) {
        this.username = username;
        this.fullName = fullName;
        this.image = image;
    }
}
