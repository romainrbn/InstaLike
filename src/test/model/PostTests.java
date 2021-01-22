package test.model;

import app.model.Comment;
import app.model.Like;
import app.model.Post;
import app.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PostTests {
    Post post;


    @BeforeEach
    public void setUp() {
        // Generate a mockup post for testing.
        post = new Post(1, 1, 1, new Date(),
                Comment.generateExampleComments(), Like.generateRandomLikes(), "Angers, France",
                Post.PostState.POSTED, true, "Ma super description");
    }

    @Test
    public void testConstructor() {
    }
}