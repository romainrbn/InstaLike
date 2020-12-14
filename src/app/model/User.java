package app.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class User {
    private int         id;
    private String      description;
    private String      profilePicture;
    private String      username;
    private String      friendlyName;
    private List<User>  followers;
    private List<User>  following;
    private Date        signUpTime;

    public User(int id, String description, String profilePicture, String username, String friendlyName,
                List<User> followers, List<User> following, Date signUpTime) {
        this.id                 = id;
        this.description        = description;
        this.profilePicture     = profilePicture;
        this.username           = username;
        this.friendlyName       = friendlyName;
        this.followers          = followers;
        this.following          = following;
        this.signUpTime         = signUpTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public Date getSignUpTime() {
        return signUpTime;
    }

    public void setSignUpTime(Date signUpTime) {
        this.signUpTime = signUpTime;
    }
}
