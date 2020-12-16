package test.model;

import app.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTests {

    User user;
    List<Integer> followers;
    List<Integer> following;
    Date date;

    @BeforeEach
    public void setUp() {
        followers = new ArrayList<>();
        followers.add(448);
        followers.add(4);
        followers.add(3949);

        following = new ArrayList<>();
        following.add(923);
        following.add(394);
        following.add(91203);

        int id                  = 42;
        String profilePicture   = "https://www.parkiz.app/mygreatbackend?profilepicture";
        String description      = "Salut les copains.";
        String username         = "@romainrbn";
        String friendlyName     = "Romain";

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            date = format.parse("2018/03/17 19:29:56");

            user = new User(id, description, profilePicture, username, friendlyName, followers, following, date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConstructeur() {
        assertEquals(42,
                user.getId(),
                "L'identifiant est faux");

        assertEquals("Salut les copains.",
                user.getDescription(),
                "La description est fausse");

        assertEquals("https://www.parkiz.app/mygreatbackend?profilepicture",
                user.getProfilePicture(),
                "L'URL de photo de profil est fausse.");

        assertEquals("@romainrbn",
                user.getUsername(),
                "L'username est faux.");

        assertEquals("Romain",
                user.getFriendlyName(),
                "Le nom est faux.");

        assertEquals(followers,
                user.getFollowers(),
                "La liste de followers est fausse.");

        assertEquals(following,
                user.getFollowing(),
                "La liste de following est fausse.");

        assertEquals(date,
                user.getSignUpDate(),
                "La date de création du compte est fausse.");
    }

    @Test
    public void testAccessorDescription() {
        user.setDescription("Tomorrowland 2022 hype");
        assertEquals("Tomorrowland 2022 hype",
                user.getDescription(),
                "La description est fausse");
    }

    @Test
    public void testAccessorId() {
        user.setId(90);
        assertEquals(90,
                user.getId(),
                "L'identifiant est faux");
    }


    @Test
    public void testAccessorProfilePictureURL() {
        user.setProfilePicture("https://www.parkinglot.com/profilePic");
        assertEquals("https://www.parkinglot.com/profilePic",
                user.getProfilePicture(),
                "La photo de profil est fausse");
    }

    @Test
    public void testAccessorUsername() {
        user.setUsername("@Arthur_Lebled");
        assertEquals("@Arthur_Lebled",
                user.getUsername(),
                "L'username est faux");
    }

    @Test
    public void testAccessorFriendlyName() {
        user.setFriendlyName("Arthur Lebled");
        assertEquals("Arthur Lebled",
                user.getFriendlyName(),
                "Le friendly name est faux");
    }

    @Test
    public void testAccessorFollowers() {
        List<Integer> followers = new ArrayList<>();
        followers.add(20);
        followers.add(290);
        followers.add(167);

        user.setFollowers(followers);
        assertEquals(followers, user.getFollowers(), "La liste de followers est fausse.");
    }

    @Test
    public void testAccessorFollowing() {
        List<Integer> following = new ArrayList<>();
        following.add(78);
        following.add(27);
        following.add(42);

        user.setFollowers(following);
        assertEquals(following, user.getFollowers(), "La liste de followers est fausse.");
    }
}
