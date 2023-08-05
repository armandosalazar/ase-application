package org.armandosalazar.aseapplication.model;

public class FavoritePost {
    private int id;
    private int userId;
    private int postId;

    public FavoritePost(int postId) {
        this.postId = postId;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getPostId() {
        return postId;
    }
}
