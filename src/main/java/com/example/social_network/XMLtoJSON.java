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

    private static Node parseXML(String xml) {
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
                        } else {
                            Node newNode = new Node(tagName);
                            newNode.setParent(currentNode);
                            if (currentNode != null) {
                                currentNode.addChild(newNode);
                            } else {
                                root = newNode;
                            }
                            currentNode = newNode;
                        }
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

    //new
    private static void printJsonTree(Node node, int level, StringBuilder jsonBuilder, boolean is_multilevel) {
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



