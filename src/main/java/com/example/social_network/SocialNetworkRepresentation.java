package com.example.social_network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocialNetworkRepresentation {
    
    private Map <User, List<User>> adjNodes;

    public SocialNetworkRepresentation() {
        this.adjNodes = new HashMap<User, List<User>>();
    }
    //insert user node
    public void addNode(User user) {
        this.adjNodes.putIfAbsent(user, new ArrayList<>());
    }
    //represent the follower relationship as edges
    public void addEdge(User user, User follower) {
        this.adjNodes.get(user).add(follower);
        
    }

    public List<User> getAdjNodes (User user){
        return adjNodes.get(user);
    }
    public void printNetwork() {
 
        for (Map.Entry<User, List<User>> entry : adjNodes.entrySet()) {
            User user = entry.getKey();
            List<User> followers = entry.getValue();
            System.out.println("User: " + user.getId());
            System.out.println("Followers:");
            for (User follower : followers) {
                System.out.println("\t" + follower.getId());
            }
        }
    }      
    
  
}
class User {
    private int id = 0;
    private String name;
    private List<Post> posts;
    private List<User> followers;

    // constructors, getters, setters

    public User(int id, String name, List<Post> posts, List<User> followers) {
        this.id  = id;  //to guarantee that each user has a unique id
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
        this.followers .add(follower);
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

class Post{
    private String body;
    private List<String> topics ;
    
    //constructor, setters and getters

    public Post() {
        this.body = null;
        this.topics = null;
     }

    public Post(String body, List<String> topics) {
        this.body = body;
        this.topics = topics;
    }



    public void setBody(String body) {
        this.body = body;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
    
    public void addTopic(String topic) {
        this.topics .add(topic);
    }

    public String getBody() {
        return body;
    }

    public List<String> getTopics() {
        return topics;
    }
    
}

