package app.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Like extends User {

    private int     likeId;
    private Date    publishTime;

    public Like(User user, int likeId, Date publishTime) {
        super(user.getId(), user.getDescription(), user.getProfilePicture(), user.getUsername(), user.getFriendlyName(), user.getFollowers(), user.getFollowing(), user.getSignUpTime());
        this.publishTime = publishTime;
        this.likeId      = likeId;
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
