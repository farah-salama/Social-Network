/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.xmltojson;

/**
 *
 * @author Sarah
 */

import java.util.ArrayList;
import java.util.List;


public class Node {
        private String tagName=null;
        private String tagValue=null;
        private Node parent;
        private List<Node> children=null;
        private int depth;

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

        public Node(String tagName) {
            this.tagName = tagName;
            this.children = new ArrayList<>();
        }
       public static boolean getChildrenIsEmpty(Node node) {
    return node.getChildren().isEmpty();
}

        public Node(String tagName, String tagValue) {
            this.tagName = tagName;
            this.tagValue = tagValue;
            this.children = new ArrayList<>();
        }

        public String getTagName() {
            return tagName;
        }

        public String getTagValue() {
            return tagValue;
        }

        public void setTagValue(String tagValue) {
            this.tagValue = tagValue;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public List<Node> getChildren() {
            return children;
        }

        public void addChild(Node child) {
            children.add(child);
        }
    }