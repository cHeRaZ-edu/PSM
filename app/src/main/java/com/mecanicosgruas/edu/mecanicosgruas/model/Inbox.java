package com.mecanicosgruas.edu.mecanicosgruas.model;

/**
 * Created by LUNA on 11/05/2018.
 */

public class Inbox extends User{
    String last_message;
    public Inbox(String nickname,String last_message) {
        super(nickname);
        this.last_message = last_message;
    }

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }
}
