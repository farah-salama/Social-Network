package com.example.social_network;

import java.util.List;

public class Post {
    private String body;
    private List<String> topics;

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
        this.topics.add(topic);
    }

    public String getBody() {
        return body;
    }

    public List<String> getTopics() {
        return topics;
    }


}
