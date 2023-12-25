/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.example.social_network;

/**
 *
 * @author Sarah
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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