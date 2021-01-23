package app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Like extends User {

    private int likeId;
    private Date publishTime;
    private User user;

    public Like(User user, int likeId, Date publishTime) {
        super(user.getId(), user.getProfilePicture(), user.getUsername(), user.getFriendlyName(), user.getSignUpDate());
        this.publishTime = publishTime;
        this.likeId = likeId;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }

}
