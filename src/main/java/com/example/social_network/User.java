package com.example.social_network;

import java.util.List;
import java.util.Objects;

public class User {
    private int id = 0;
    private String name;
    private List<Post> posts;
    private List<User> followers;

    // constructors, getters, setters
    public User(int id, String name, List<Post> posts, List<User> followers) {
        this.id = id;  //to guarantee that each user has a unique id
        this.name = name;
        this.posts = posts;
        this.followers = followers;
    }

    public User(int id) {
        this.id = id;
        this.name = null;
        this.posts = null;
        this.followers = null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public void addFollowers(User follower) {
        this.followers.add(follower);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<User> getFollowers() {
        return followers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) &&
                Objects.equals(getName(), user.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
