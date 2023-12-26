package org.example.graphvisualization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocialNetworkRepresentation {

    private Map<User, List<User>> adjNodes;

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

    public List<User> getAdjNodes(User user) {

        return adjNodes.get(user);
    }

    // method to get all users in the social network
    public List<User> getUsers() {
        return new ArrayList<>(adjNodes.keySet());
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

