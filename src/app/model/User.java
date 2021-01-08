package app.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    private int             id;
    private String          description;
    private String          profilePicture;
    private String          username;
    private String          friendlyName;
    private List<Integer>   followers;
    private List<Integer>   following;
    private Date signUpDate;

    public User(int id, String description, String profilePicture, String username, String friendlyName,
                List<Integer> followers, List<Integer> following, Date signUpDate) {
        this.id                 = id;
        this.description        = description;
        this.profilePicture     = profilePicture;
        this.username           = username;
        this.friendlyName       = friendlyName;
        this.followers          = followers;
        this.following          = following;
        this.signUpDate         = signUpDate;
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

    public List<Integer> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Integer> followers) {
        this.followers = followers;
    }

    public List<Integer> getFollowing() {
        return following;
    }

    public void setFollowing(List<Integer> following) {
        this.following = following;
    }

    public Date getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(Date signUpDate) {
        this.signUpDate = signUpDate;
    }

    public static User generateExampleUser() {
        int id = 10;
        String description      = "Super description";
        String profilePicture   = "https://cdn.maikoapp.com/3d4b/4quqa/150.jpg";
        String username         = "@romainrbn";
        String friendlyName     = "Romain";
        List<Integer> followers = new ArrayList<>();
        followers.add(10);
        followers.add(193);
        followers.add(42);
        List<Integer> following = new ArrayList<>();
        following.add(293);
        following.add(183);
        following.add(284);
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            date = format.parse("2018/03/17 19:29:56");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        User exampleUser = new User(id, description, profilePicture, username, friendlyName, followers, following, date);
        return exampleUser;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", username='" + username + '\'' +
                ", friendlyName='" + friendlyName + '\'' +
                ", followers=" + followers +
                ", following=" + following +
                ", signUpDate=" + signUpDate +
                '}';
    }
}
