
package com.example.social_network;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 *
 * @author Mariz
 */
public class SocialNetworkAnalysis {
    
/* Description: get the most influencer user (has the most followers) */
public static String[]theMostInfluencerUsers(String filePath) {
    SocialNetworkRepresentation network = xmlParser.createNetworkFromXML(filePath);

    List<User> users = xmlParser.parse(filePath);

    // To store the most influencer users
    List<User> mostInfluentialUsers = new ArrayList<>();
    int maxNumberOfFollowers = 0;

    for (User user : users) {
        // to store the number of followers for each user in each iteration
        int numberOfFollowers = user.getFollowers().size();

        if (numberOfFollowers > maxNumberOfFollowers) {
            // Found a new maximum, clear the previous list
            maxNumberOfFollowers = numberOfFollowers;
            mostInfluentialUsers.clear();
            mostInfluentialUsers.add(new User(user.getId(), user.getName(), user.getPosts(), user.getFollowers()));
        } 
        else if (numberOfFollowers == maxNumberOfFollowers)
        {
            // Add this user to the list since they have the same maximum count
            mostInfluentialUsers.add(new User(user.getId(), user.getName(), user.getPosts(), user.getFollowers()));
        }
    }
    // Convert the list of names to an array
    String[] mostInfluentialUserNames = new String[mostInfluentialUsers.size()];
    int i = 0;
    for (User influentialUser : mostInfluentialUsers) {
        mostInfluentialUserNames[i++] = influentialUser.getName();
    }

    return mostInfluentialUserNames;
}

    /* Description: get  the most active user (connected to lots of users)
     using Degree Centrality
    */
    public static String theMostActiveUser(String filePath) {
    SocialNetworkRepresentation network = xmlParser.createNetworkFromXML(filePath);     
    List<User> users = xmlParser.parse(filePath);

    // To store the most active user (connected to lots of users)
    User mostActiveUser = null;
    int mostActiveUserID = 0;

    // Making the adjacency matrix
    int[][] adjMatrix = new int[users.size()][users.size()];
    for (User trackedUser : users) {
        for (User user : users) {
            if (trackedUser.getFollowers().contains(user)) {
                adjMatrix[trackedUser.getId() - 1][user.getId() - 1] = 1;
            }
        }
    }

    // Calculating the Degree Centrality for each user
    int maxDegree = 0;
    for (int i = 0; i < adjMatrix.length; i++) {
        int degree = 0;
        for (int j = 0; j < adjMatrix[i].length; j++) {
            if (adjMatrix[i][j] == 1) {
                degree++;
            }
        }

        // Normalize degree for degree centrality
        double degreeCentrality = (double) degree / (users.size() - 1); // Avoid division by zero

        if (degreeCentrality > maxDegree) {
            maxDegree = degree;
            mostActiveUserID = i;
        }
    }

    mostActiveUser = users.get(mostActiveUserID);
    return mostActiveUser.getName();
    }
    
    //Description: get the mutual followers between 2 users
    public static String[] getMutualFollowers(String user1Name, String user2Name,String filePath) {
        
        SocialNetworkRepresentation network = xmlParser.createNetworkFromXML(filePath);     
        List<User> users = xmlParser.parse(filePath);
        User user1=null;
        User user2=null;
        // To break immediately after finding the two users
        int done=0;
        
        for(User user: users)
        {
            if(user1Name.equals(user.getName()))
            {
              user1=new User(user.getId(), user.getName(), user.getPosts(), user.getFollowers()); 
              done++;
            }
            
            if(user2Name.equals(user.getName()))
            {
              user2=new User(user.getId(), user.getName(), user.getPosts(), user.getFollowers()); 
              done++;
            }
            
            if(done==2)
                break;      
        }
       
        if(user1==null)
        {
           return new String[]{user1Name+" isn't found"};
        }
        if(user2==null)
        {
            return new String[]{user2Name+" isn't found"};
        }
      
        List<User> followersUser1 = user1.getFollowers();
        List<User> followersUser2 = user2.getFollowers();

        //using the retainAll method to find the intersection (mutual followers)
        List<User> mutualFollowers = new ArrayList<>(followersUser1);
        mutualFollowers.retainAll(followersUser2);

        // store their names in a string array to be returned
        int i=0;
        String[] names =new String[users.size()];
        for(User follower:mutualFollowers)
        {
            names[i++]=follower.getName();
        } 
        
        if(names.length == 0)
            return new String[]{"No mutual followers found"};
        else
           return names;
    }
    
    //for each user, suggest a list of users to follow (the followers of his followers)
    public static String[] suggestedFollowers(String userName,String filePath)
    {
        SocialNetworkRepresentation network = xmlParser.createNetworkFromXML(filePath);     
        List<User> users = xmlParser.parse(filePath);
        User inputUser=null;
        // Searching for the input user
        for(User user: users)
        {
            if(userName.equals(user.getName()))
            {
              inputUser=new User(user.getId(), user.getName(), user.getPosts(), user.getFollowers()); 
            }
        }
        // Check if the input user is found
       if (inputUser == null) {
           return new String[]{"User not found"};
    }
         
        List<User> followers = inputUser.getFollowers();

        List<User> suggestedUsers = new ArrayList<>();

        // Iterate through the followers of the user
        for (User follower : followers) {
               List<User> followersOfFollower =follower.getFollowers();    
            // Add followers of the follower to the suggestions
            suggestedUsers.addAll(followersOfFollower);         
        }

        // Remove the original user and the users they are already following from the suggestions
        suggestedUsers.remove(inputUser);
        suggestedUsers.removeAll(inputUser.getFollowers());

        //remove duplicates 
        Set<User>setWithoutDuplicates = new HashSet<>(suggestedUsers);
        List<User> listWithoutDuplicates = new ArrayList<>(setWithoutDuplicates);
        
        String[] suggestedUsersNames = new String[listWithoutDuplicates.size()];
        int i=0;
        for(User suggestedFollowerName:setWithoutDuplicates)
        {
            suggestedUsersNames[i++]=suggestedFollowerName.getName();
        }
        
        return suggestedUsersNames;
    }
    
    /*
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Envy\\Desktop\\Fall23\\Data Structure and Algorithms\\Project\\input_sample.xml";
        
        String[] mostInfluencial= new String[5];
        mostInfluencial=theMostInfluencerUsers(filePath);
        System.out.println("-The most influencer user:");
        for(String suggestedUser: mostInfluencial)
        {
            if(suggestedUser==null)
                break;
            System.out.println(suggestedUser);
        }
        
        //*******************************************************
        System.out.println("-The most Active user:");
        System.out.println(theMostActiveUser(filePath));
        //*******************************************************
        String name1="Yasser Ahmed";
        String name2="Mohamed Sherif";
        String[] mutualFollowers = new String[5];
        System.out.println("-The mutual Followers between "+name1+" and "+name2+":");
        mutualFollowers=getMutualFollowers(name1,name2,filePath);
        for(String follower: mutualFollowers)
        {
            if(follower==null)
                break;
            System.out.println(follower);
        }
        //*******************************************************
        String name="Yasser Ahmed";
        String[] suggestedFollowers = new String[5];
        System.out.println("-The sugested followers for "+name+":");
        suggestedFollowers=suggestedFollowers(name,filePath);
        for(String suggestedUser: suggestedFollowers)
        {
            if(suggestedUser==null)
                break;
            System.out.println(suggestedUser);
        }
}
*/ 
    
}