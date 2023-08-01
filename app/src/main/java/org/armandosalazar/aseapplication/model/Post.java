package org.armandosalazar.aseapplication.model;

public class Post {
    private int id;
    private int userId;
    private String username;
    private String content;

    public Post(int userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }
}
