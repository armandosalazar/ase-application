package org.armandosalazar.aseapplication.model;

public class Message {
    private int id;
    private int senderId;
    private int receiverId;
    private String content;

    public Message(int receiverId, String content) {
        this.receiverId = receiverId;
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
