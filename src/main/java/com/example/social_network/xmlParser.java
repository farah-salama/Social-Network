
package com.example.social_network;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class xmlParser {

    public static void main(String[] args) {
        String filePath = "F:\\college\\1st Semester\\ds\\Project social network\\socialNetworkRepresentation\\src\\main\\java\\com\\project\\socialnetworkrepresentation\\sample.xml";
        SocialNetworkRepresentation network = xmlParser.createNetworkFromXML(filePath);
        network.printNetwork();

        /*
        List<User> users = xmlParser.parse(filePath);
        for (User user : users) {
            System.out.println("User name: " + user.getName());
            System.out.println("User id: " + user.getId());

            System.out.println("Followers:");
            for (User follower : user.getFollowers()) {
                System.out.println("follower name:" + follower.getName());
                System.out.println("follower id: " + follower.getId());
            }

            System.out.println("Posts:");
            for (Post post : user.getPosts()) {
                System.out.println("Post body:" + post.getBody());
                System.out.println("Post topic: " + post.getTopics());
            }


        }
*/
    }

    public static SocialNetworkRepresentation createNetworkFromXML(String filePath) {
        List<User> users = xmlParser.parse(filePath);
        SocialNetworkRepresentation network = new SocialNetworkRepresentation();

        for (User user : users) {
            network.addNode(user);
            for (User follower : user.getFollowers()) {
                network.addEdge(user, follower);
            }
        }
        return network;
    }

    public static List<User> parse(String xml) {
        //we will use a map to store the user objects with their IDs as keys
        //so that we can efficiently look up a user by their ID
        Map<Integer, User> users = new HashMap<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xml);
            doc.getDocumentElement().normalize();

            //Phase 1: create all user objects first without followers
            NodeList nList = doc.getElementsByTagName("user");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    //get the id
                    int id = Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent());

                    //get the name
                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();

                    //get the posts list
                    List<Post> posts = new ArrayList<>();
                    NodeList postList = eElement.getElementsByTagName("post");
                    for (int i = 0; i < postList.getLength(); i++) {
                        Node pNode = postList.item(i);
                        if (pNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element pElement = (Element) pNode;

                            //get the post body
                            String body = pElement.getElementsByTagName("body").item(0).getTextContent();

                            //get the post list of topics
                            List<String> topics = new ArrayList<>();
                            NodeList topicList = pElement.getElementsByTagName("topic");
                            for (int j = 0; j < topicList.getLength(); j++) {
                                topics.add(topicList.item(j).getTextContent());
                            }
                            posts.add(new Post(body, topics));
                        }
                    }

                    //add the user attributes to the user object
                    User user = new User(id, name, posts, new ArrayList<>());
                    users.put(id, user);
                }
            }

            //phase 2: add followers to each user
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    int id = Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent());
                    User user = users.get(id);
                    NodeList followerList = eElement.getElementsByTagName("follower");
                    for (int i = 0; i < followerList.getLength(); i++) {
                        Node fNode = followerList.item(i);
                        if (fNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element fElement = (Element) fNode;
                            int followerId = Integer.parseInt(fElement.getElementsByTagName("id").item(0).getTextContent());
                            User follower = users.get(followerId);
                            if (follower != null) {
                                user.getFollowers().add(follower);
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //return the users as list
        return new ArrayList<>(users.values());
    }

}
