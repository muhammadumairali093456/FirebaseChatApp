package com.example.firebasechatapp.Model;

public class Message {
    String content,senderId ,ReceiverId,type;

    public Message() {
    }

    public Message(String content, String senderId, String receiverId, String type) {
        this.content = content;
        this.senderId = senderId;
        ReceiverId = receiverId;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return ReceiverId;
    }

    public void setReceiverId(String receiverId) {
        ReceiverId = receiverId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
