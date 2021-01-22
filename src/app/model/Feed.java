package app.model;

import java.util.List;

public class Feed {
    private List<Post>  posts;

    public Feed(List<Post> posts) {
        this.posts                  = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
