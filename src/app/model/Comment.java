package app.model;

import java.sql.Timestamp;


public class Comment {

    private int         commentId;
    private String      commentValue;
    private Timestamp   publishDate;
    private int         userID;
    private int         postID;

    public Comment(int commentId, String commentValue, Timestamp publishDate, int userID, int postID) {
        this.commentId = commentId;
        this.commentValue = commentValue;
        this.publishDate = publishDate;
        this.userID = userID;
        this.postID = postID;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentValue() {
        return commentValue;
    }

    public void setCommentValue(String commentValue) {
        this.commentValue = commentValue;
    }

    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }
}
