/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.xmlparseextract;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sarah
 */
public class User {


    private String id;
    private String name;
    private List<String> posts;

    public List<String> getPosts() { 
        return posts;
    }

    public List<String> getFollowers() {
        return followers;
    }
    private List<String> followers;

    public User() { 
        this.posts = new ArrayList<>();
        this.followers = new ArrayList<>();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    // Getters and setters

    public void addPost(String post) {
        this.posts.add(post);
    }

    public void addFollower(String followerId) {
        this.followers.add(followerId);
    }
}


