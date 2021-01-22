package app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Comment {

    private int         commentId;
    private String      commentValue;
    private User        author;
    private List<Like>  likes;
    private Date        publishDate;

    public Comment(int commentId, String commentValue, User author, List<Like> likes, Date publishDate) {
        this.commentId     = commentId;
        this.commentValue  = commentValue;
        this.author        = author;
        this.likes         = likes;
        this.publishDate   = publishDate;
    }

    public String getCommentValue() {
        return commentValue;
    }

    public void setCommentValue(String commentValue) {
        this.commentValue = commentValue;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public static List<Comment> generateExampleComments() {
        List<Comment> comments = new ArrayList<>();
        for (int i = 0 ; i < 10 ; i++) {
            comments.add(new Comment(i, "Jolie Photo !", User.generateExampleUser(),
                    Like.generateRandomLikes(), new Date()));
        }
        return comments;
    }
}
