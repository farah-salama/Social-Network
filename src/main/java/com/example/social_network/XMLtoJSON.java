/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.example.social_network;

/**
 *
 * @author Sarah
 */import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLtoJSON{
    public static void main(String[] args) {
        try {
            // Get the filename or use a hardcoded value
            String filename = "C:\\Users\\Sarah\\Documents\\NetBeansProjects\\mavenproject10\\src\\main\\java\\com\\mycompany\\mavenproject10\\user.xml";
            if (args.length != 0) {
                filename = args[0];
            }

            // Read the XML file
            File file = new File(filename);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            inputStream.read(data);
            inputStream.close();

            // Convert the file content into string
            String xmlContent = new String(data);

            // Parse the XML
            Node root = parseXML(xmlContent);
            StringBuilder JsonBuilder = new StringBuilder();


            JsonBuilder.append("{\n");
            printJsonTree(root, 1, JsonBuilder, false);
            JsonBuilder.append("\n}");

            System.out.println(JsonBuilder);
            //printTree(root, 0);

            //            // Extract tag names and values
            //            List<TagValuePair> tagValuePairs = extractTagValues(root);
            //            for (TagValuePair pair : tagValuePairs) {
            ////                System.out.println("Tag: " + pair.getTag() + ", Value: " + pair.getValue());
            //            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//ParseXML and Build the tree
    public static Node parseXML(String xml) {
        Node root = null;
        Node currentNode = null;
        StringBuilder currentText = new StringBuilder();
        Scanner scanner = new Scanner(xml);

        while (scanner.hasNextLine()) { //while we didn't reach end of xml text
            String line = scanner.nextLine();

            if (line.contains("<")) { //on finding open bracket of xml Tag ex:<user>
                String tagName = extractTagName(line); //extract the name of the tag, passing the line where the name is present
                if (tagName.startsWith("/")) { // if the name starts with '/' indicating Closing tag ex:</user>
                    currentNode.setTagValue(currentText.toString().trim()); // then the current text carries value of tag that is to be set
                    currentNode = currentNode.getParent(); // Return to parent node
                } else {
                    if (line.contains("</")) { //if line contains open and close tag ex: <id> 2 </id>
                        String tagValue = extractTagValue(line, tagName); //extract tag value
                        Node newNode = new Node(tagName, tagValue); //create a new node
                        if (currentNode != null) {
                            currentNode.addChild(newNode); // parent of this node is current node
                        } else {
                            root = newNode;
                        }
                    } else { // Opening tag
                        if (line.contains(">")) { //<user>
                            String tagValue = extractTagValue(line, tagName);
                            Node newNode = new Node(tagName, tagValue);
                            newNode.setParent(currentNode);
                            if (currentNode != null) {
                                currentNode.addChild(newNode);
                            } else {
                                root = newNode;
                            }
                            currentNode = newNode;
                        } 
                        // else {
                        //     Node newNode = new Node(tagName);
                        //     newNode.setParent(currentNode);
                        //     if (currentNode != null) {
                        //         currentNode.addChild(newNode);
                        //     } else {
                        //         root = newNode;
                        //     }
                        //     currentNode = newNode;
                        // }
                    }
                }
                currentText.setLength(0);
            } else {
                currentText.append(line.trim());
            }
        }

        return root;
    }

    private static String extractTagName(String line) {
        Pattern pattern = Pattern.compile("<(.*?)>");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    private static String extractTagValue(String line, String tagName) {
        Pattern pattern = Pattern.compile("<" + tagName + ">(.*?)<\\/" + tagName + ">");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return "";
    }

    
    public static void printJsonTree(Node node, int level, StringBuilder jsonBuilder, boolean is_multilevel) {
        /* This block creates the indentation */
        String indentation = "    ";

        StringBuilder indent = new StringBuilder();
        indent.append(indentation.repeat(Math.max(0, level)));
        jsonBuilder.append(indent); //append the indentation to the stringbuilder


        int child_count = node.getChildren().size();

        //append tag name
        if (!is_multilevel) {
            jsonBuilder.append('"').append(node.getTagName()).append("\": "); //appending tag name of none leaf node
        }

        //base case -> every tree ends with a leaf node
        if (child_count == 0) {
            jsonBuilder.append('"').append(node.getTagValue()).append('"');
            return;
        }

        //(Not Leaf)

        //parent has one child or many children
        boolean many_children_and_same_name = (child_count != 1) && (node.getChildren().get(0).getTagName()).equals(node.getChildren().get(1).getTagName()); //used to check if the children has same name

        //parent has many children of different names
        if (!many_children_and_same_name) {
            jsonBuilder.append("{\n");
            for (int i = 0; i < child_count; i++) {
                Node child = node.getChildren().get(i);
                printJsonTree(child, level + 1, jsonBuilder, false);
                if (i < node.getChildren().size() - 1) {
                    jsonBuilder.append(",");
                }
                jsonBuilder.append("\n");
            }
            jsonBuilder.append(indent).append("}");
            return;
        }

        //if all else didn't happen, then
        //parent has children of same names
        jsonBuilder.append("{\n").append(indent).append(indentation).append('"').append(node.getChildren().get(0).getTagName()).append("\": [\n"); //append child name once ex: topic[..,..]
        for (int i = 0; i < child_count; i++) {
            Node child = node.getChildren().get(i);
            printJsonTree(child, level + 2, jsonBuilder, true);

            if (i < node.getChildren().size() - 1) {
                jsonBuilder.append(",\n");
            }
        }
        jsonBuilder.append("\n").append(indent).append(indentation).append("]\n").append(indent).append("}");
    }
}

//First Edit of Class User for Part 2
/*public class User {


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
 */
// First Edition for XMLParseExtract
/* 
public class XMLParseExtract {
    public static void main(String[] args) {
        try {
            // Get the filename or use a hardcoded value
            String filename = "C:\\Users\\Sarah\\Documents\\NetBeansProjects\\mavenproject10\\src\\main\\java\\com\\mycompany\\mavenproject10\\user.xml";
            if (args.length != 0) {
                filename = args[0];
            }
            // Read the XML file
            File file = new File(filename);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];

            inputStream.read(data);
            inputStream.close();

            // Convert the file content into string
            String xmlContent = new String(data);

            // Parsing the XML Content after converting it into string
            List<User> users = parseXML(xmlContent);

            // Print the extracted information
            for (User user : users) {
                System.out.println("User ID: " + user.getId());
                System.out.println("User Name: " + user.getName());
                System.out.println("Posts: " + user.getPosts());
                System.out.println("Followers: " + user.getFollowers());
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<User> parseXML(String xmlContent) {
        List<User> users = new ArrayList<>();
        Scanner scanner = new Scanner(xmlContent);
        User user = null;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("<user>")) {
                user = new User();
                String idLine = scanner.nextLine(); // Read the next line containing the ID
                user.setId(extractTagValue(idLine, "id", scanner)); // Pass the ID line and scanner to extractTagValue
              // System.err.println(user.getId());

                String nameLine = scanner.nextLine(); // Read the next line containing the name
                user.setName(extractTagValue(nameLine, "name", scanner)); // Pass the name line and scanner to extractTagValue
                //System.err.println(user.getName());

                // Read the next line manually (not using scanner)
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                }
            } else if (line.contains("<post>")) {
                String postLine = scanner.nextLine();
                String post_text = extractTagValue(postLine, "body", scanner);
               //  System.err.println(post_text);
 
                if (user != null) {
                    user.addPost(post_text.trim());
                }
             } else if (line.contains("<post>")) {
                String postLine = scanner.nextLine();
                String post_text = extractTagValue(postLine, "body", scanner);
               //  System.err.println(post_text);
 
                if (user != null) {
                    user.addPost(post_text.trim());
                }
            } 
             else if (line.contains("<topic>")) {
                String topicLine = scanner.nextLine();
                String topic_text = extractTagValue(topicLine, "topic", scanner);
                System.err.println(topic_text);
 
                
            } 
            else if (line.contains("<follower>")) {
                String followerLine = scanner.nextLine();

                String followerID = extractTagValue(followerLine, "id", scanner);
                    //System.err.println(followerID);
                if (user != null) {
                    user.addFollower(followerID.trim());
                }
            } else if (line.contains("</user>") && user != null) {
                users.add(user);
                user = null;
            }
        }
        return users;
    }
   private static String parseValue(StringBuilder sb, int closingTagIndex) {

  if(sb.length() == 0) {
    return ""; 
  }

  String value = sb.substring(0, closingTagIndex).trim();
  return value;

}
 
private static String extractTagValue(String line, String tagName, Scanner scanner) {
String startTag = "<" + tagName + ">";
String endTag = "</" + tagName + ">";
int startIndex = line.indexOf(startTag) + startTag.length();
int endIndex = line.indexOf(endTag);
//
//if (startIndex == -1 || endIndex == -1) {
//return "";
//}

// If the tags are on the same line
if ( endIndex != -1) {
return line.substring(startIndex, endIndex).trim();
}

// If the tags span multiple lines
StringBuilder sb = new StringBuilder();
sb.append(line.substring(startIndex).trim());
//System.err.println(sb);

while(scanner.hasNextLine()) {

line = scanner.nextLine();

if(line.contains(endTag)) {
break;
}

sb.append("\n");
sb.append(line.trim());

}
return sb.toString().trim();
}
}
*/
