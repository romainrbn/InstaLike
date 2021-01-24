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
