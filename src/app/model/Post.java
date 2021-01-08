package app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// backend.com/getFeed?user=@romainrbn => [Post]

public class Post {

    private int             postId;
    private User            author;
    private String          photoURL;
    private Date            publishTime;
    private List<Comment>   commentsList;
    private List<Like>      likesList;
    private String          localisation;
    private PostState       state;
    // Sert a determiner si afficher pour recharger a partir de la db.
    // ! ordre chronologie
    private Boolean         displayed;
    private String          description;

    public Post(int postId, User author, String photoURL, Date publishTime, List<Comment> commentsList, List<Like>
            likesList, String localisation, PostState state, Boolean displayed, String description) {
        this.postId         = postId;
        this.author         = author;
        this.photoURL       = photoURL;
        this.publishTime    = publishTime;
        this.commentsList   = commentsList;
        this.likesList      = likesList;
        this.localisation   = localisation;
        this.state          = state;
        this.displayed      = displayed;
        this.description    = description;
    }


    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public List<Comment> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comment> commentsList) {
        this.commentsList = commentsList;
    }

    public List<Like> getLikesList() {
        return likesList;
    }

    public void setLikesList(List<Like> likesList) {
        this.likesList = likesList;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public PostState getState() {
        return state;
    }

    public void setState(PostState state) {
        this.state = state;
    }

    public Boolean getDisplayed() {
        return displayed;
    }

    public void setDisplayed(Boolean displayed) {
        this.displayed = displayed;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", author=" + author.toString() +
                ", photoURL='" + photoURL + '\'' +
                ", publishTime=" + publishTime +
                ", commentsList=" + commentsList +
                ", likesList=" + likesList +
                ", localisation='" + localisation + '\'' +
                ", state=" + state +
                ", displayed=" + displayed +
                ", description='" + description + '\'' +
                '}';
    }

    public enum PostState {
        POSTED, DELETED
    }

    public static Post generateExamplePost() {
        return new Post(1, User.generateExampleUser(), "https://www.google.com", new Date(),
                Comment.generateExampleComments(), Like.generateRandomLikes(), "Angers, France",
                Post.PostState.POSTED, true, "Ma super description");
    }



}
