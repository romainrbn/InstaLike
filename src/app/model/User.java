package app.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    private int             id;
    private String          profilePicture;
    private String          username;
    private String          friendlyName;
    private Date signUpDate;

    public User(int id, String profilePicture, String username, String friendlyName, Date signUpDate) {
        this.id                 = id;
        this.profilePicture     = profilePicture;
        this.username           = username;
        this.friendlyName       = friendlyName;
        this.signUpDate         = signUpDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        User exampleUser = new User(id, profilePicture, username, friendlyName, date);
        return exampleUser;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", username='" + username + '\'' +
                ", friendlyName='" + friendlyName + '\'' +
                ", signUpDate=" + signUpDate +
                '}';
    }
}
