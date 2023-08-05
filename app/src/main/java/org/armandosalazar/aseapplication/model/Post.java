package org.armandosalazar.aseapplication.model;

public class Post {
    private int id;
    private final int userId;
    private final String content;
    private User user;
    private String createdAt;


    public Post(int userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
