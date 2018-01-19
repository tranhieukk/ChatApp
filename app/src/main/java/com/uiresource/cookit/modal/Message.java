package com.uiresource.cookit.modal;

/**
 * Created by Tran Trung Hieu on 1/13/18.
 */

public class Message {
    public int getID() {
        return id;
    }

    int id;
    String content;
    Boolean in;
    String user;

    public String getContent() {
        return content;
    }

    public Boolean getIn() {
        return in;
    }

    public String getUser() {
        return user;
    }

    public Message(int id, String content, Boolean in, String user) {
        this.id = id;
        this.content = content;
        this.in = in;
        this.user = user;
    }

}
