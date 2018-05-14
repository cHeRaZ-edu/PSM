package com.mecanicosgruas.edu.mecanicosgruas.model;

/**
 * Created by LUNA on 11/05/2018.
 */

public class Message {
    String nickname;
    String message;
    boolean me;

    public Message(String nickname, String message, boolean me) {
        this.nickname = nickname;
        this.message = message;
        this.me = me;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isMe() {
        return me;
    }

    public void setMe(boolean me) {
        this.me = me;
    }
}
