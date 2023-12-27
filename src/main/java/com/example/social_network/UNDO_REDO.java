/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.undo_redo;

import java.util.Stack;

/**
 *
 * @author win10
 */
public class UNDO_REDO {
    public static Stack<String> undoStack = new Stack<>();
        public static Stack<String> redoStack = new Stack<>();
        
        private static void undoModification(String xmlContent) {
        if (!undoStack.isEmpty()) {
           // try {
                // Save the current state for redo
                redoStack.push(xmlContent);

                // Undo the modification
                String previousState = undoStack.pop();
                //Files.writeString(Path.of(filePath), previousState);

                System.out.println("Undone modification:\n" + previousState);

            }/* catch (IOException e) {
                e.printStackTrace();
            }*/
         else {
            System.out.println("Cannot undo. No previous state available.");
        }
    }

    private static void redoModification(String xmlContent) {
        if (!redoStack.isEmpty()) {
           // try {
                // Save the current state for undo
                undoStack.push(xmlContent);

                // Redo the undone modification
                String nextState = redoStack.pop();
                //Files.writeString(Path.of(filePath), nextState);

                System.out.println("Redone modification:\n" + nextState);

            } /*catch (IOException e) {
                e.printStackTrace();
            }*/
         else {
            System.out.println("Cannot redo. No next state available.");
        }
    }
}
