package app.model;

import java.util.List;

public class Feed {
    private User        currentUser;
    private List<Post>  posts;
    private Boolean     shouldReloadFromRemote;

    public Feed(User currentUser, List<Post> posts, Boolean shouldReloadFromRemote) {
        this.currentUser            = currentUser;
        this.posts                  = posts;
        this.shouldReloadFromRemote = shouldReloadFromRemote;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Boolean getShouldReloadFromRemote() {
        return shouldReloadFromRemote;
    }

    public void setShouldReloadFromRemote(Boolean shouldReloadFromRemote) {
        this.shouldReloadFromRemote = shouldReloadFromRemote;
    }
}
