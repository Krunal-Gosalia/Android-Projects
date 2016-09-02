package com.firebase.firebase_contact;

import java.io.Serializable;

/**
 * Created by Krunal on 17-04-2016.
 */
public class Message implements Serializable {

    String post_id;

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    String time_stamp;
    String message_read;
    String message_text;
    String receiver;
    String sender;

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getMessage_read() {
        return message_read;
    }

    public void setMessage_read(String message_read) {
        this.message_read = message_read;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Message() {
    }

    public Message(String time_stamp, String message_read, String message_text, String receiver, String sender) {

        this.time_stamp = time_stamp;
        this.message_read = message_read;
        this.message_text = message_text;
        this.receiver = receiver;
        this.sender = sender;
    }
}
