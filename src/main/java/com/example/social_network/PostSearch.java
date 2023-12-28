package com.example.social_network;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Zeyad
 */
public class PostSearch {
  //function that returns list of posts that contain certain word or topic
  public static List<Post>   postSearch(String filePath,String searchWord)throws Exception{
    List<Post> matchingPosts=new ArrayList<>() ;
    String searchWordFiltered=searchWord.trim().toLowerCase();
    if(searchWordFiltered.isEmpty()){
      throw new Exception("Please enter a search word");
    }
    List <User> users=xmlParser.parse(filePath);
    if(users.isEmpty()){
      throw new Exception("Please enter a valid xml file that contain users");
    }
    for (User user:users){
      List <Post> posts=user.getPosts();
      for(Post post:posts){
        if(post.getBody().toLowerCase().contains(searchWordFiltered)){
          matchingPosts.add(post);
          continue;
        }
        List<String> topics=post.getTopics();
        for (String topic:topics){
          if(topic.toLowerCase().contains(searchWordFiltered)){
            matchingPosts.add(post);
            break;
          }
        }

      }
    }
    return matchingPosts;
  }
  public static String convertPostsToString(List <Post> posts){

    StringBuilder postsConvertedToString;
    if (posts.isEmpty()){
      postsConvertedToString = new StringBuilder("No Posts Found\n");
      return postsConvertedToString.toString();
    }


    postsConvertedToString = new StringBuilder("Found Posts\n");

    int counter=1;
    for(Post post: posts) {
      postsConvertedToString.append("Post").append(counter).append(" ").append(post.getBody()).append("\n");
      counter++;
    }
    return postsConvertedToString.toString();
    }
  }
