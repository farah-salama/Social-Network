/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.example.social_network;

import java.util.Stack;

/**
 *
 * @author win10
 */
public class UNDO_REDO {
    public static Stack<String> undoStack = new Stack<>();
        public static Stack<String> redoStack = new Stack<>();
        
        public static String undoModification(String xmlContent) {
        if (!undoStack.isEmpty()) {
           // try {
                // Save the current state for redo
                //redoStack.push(xmlContent);
                redoStack.push(undoStack.pop());

                // Undo the modification
                String previousState = undoStack.pop();
                //Files.writeString(Path.of(filePath), previousState);

                return "Undone modification:\n" + previousState;

            }/* catch (IOException e) {
                e.printStackTrace();
            }*/
         else {
            return "Cannot undo. No previous state available.";
        }
    }

    public static String redoModification(String xmlContent) {
        if (!redoStack.isEmpty()) {
           // try {
                // Save the current state for undo
                undoStack.push(xmlContent);

                // Redo the undone modification
                String nextState = redoStack.pop();
                //Files.writeString(Path.of(filePath), nextState);

                return "Redone modification:\n" + nextState;

            } /*catch (IOException e) {
                e.printStackTrace();
            }*/
         else {
            return "Cannot redo. No next state available.";
        }
    }
}
