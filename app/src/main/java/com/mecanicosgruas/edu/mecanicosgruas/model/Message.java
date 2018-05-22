package com.mecanicosgruas.edu.mecanicosgruas.model;

/**
 * Created by LUNA on 11/05/2018.
 */

public class Message {
    String nickname;
    String message;
    String endpointImg;
    String timeSend;
    boolean me;

    public Message(){

    }
    public Message(String nickname, String message, String endpointImg, boolean me) {
        this.nickname = nickname;
        this.message = message;
        this.endpointImg = endpointImg;
        this.me = me;
    }

    public String getTimeSend() {
        return timeSend;
    }

    public void setTimeSend(String timeSend) {
        this.timeSend = timeSend;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEndpointImg() {
        return endpointImg;
    }

    public void setEndpointImg(String endpointImg) {
        this.endpointImg = endpointImg;
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
