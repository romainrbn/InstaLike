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
    Date date;

    @BeforeEach
    public void setUp() {
        int id                  = 42;
        String profilePicture   = "https://www.parkiz.app/mygreatbackend?profilepicture";
        String username         = "@romainrbn";
        String friendlyName     = "Romain";

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            date = format.parse("2018/03/17 19:29:56");

            user = new User(id, profilePicture, username, friendlyName, date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConstructeur() {
        assertEquals(42,
                user.getId(),
                "L'identifiant est faux");


        assertEquals("https://www.parkiz.app/mygreatbackend?profilepicture",
                user.getProfilePicture(),
                "L'URL de photo de profil est fausse.");

        assertEquals("@romainrbn",
                user.getUsername(),
                "L'username est faux.");

        assertEquals("Romain",
                user.getFriendlyName(),
                "Le nom est faux.");
        assertEquals(date,
                user.getSignUpDate(),
                "La date de création du compte est fausse.");
    }

    @Test
    public void testAccessorId() {
        user.setId(90);
        assertEquals(90,
                user.getId(),
                "L'identifiant est faux");
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
}
