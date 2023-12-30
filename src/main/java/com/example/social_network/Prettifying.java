/*
Task : Prettifying an xml file
Author : Carol Maged
*/

package com.example.social_network;

/*import static com.mycompany.proj1.Proj1.redoStack;
import static com.mycompany.proj1.Proj1.undoStack;*/

import static com.example.social_network.UNDO_REDO.redoStack;
import static com.example.social_network.UNDO_REDO.undoStack;

public class Prettifying {

    private static int spacing = 0;

    public static String prettify(String xmlFile) {
        // Save the current state for undo
        undoStack.push(xmlFile);
        //redoStack.clear();

        /* Check if the file is empty */
        if (xmlFile == null || xmlFile.length() == 0) {
            return "";
        }

        StringBuilder str = new StringBuilder();

        /* Insert a new line after each tag or data */
        String[] lines = xmlFile.trim().replaceAll("<", "\n<").split("\n");

        for (int i = 0; i < lines.length; i++) {
            /* Check if the line doesn't contain any data */
            if (lines[i].trim().length() == 0 || lines[i] == null) {
                continue;
            }

            lines[i] = lines[i].trim();
            StringBuilder indentation = new StringBuilder();

            /* Adjusts the indentation for closing tag */
            if (lines[i].startsWith("</")) {
                spacing--;
                for (int j = 0; j < spacing; j++) {
                    indentation.append("    ");
                }
                if (i > 0 && lines[i - 1].startsWith("<")) {
                    str.replace(str.lastIndexOf("\n"), str.length(), "" + lines[i] + "\n");
                } else {
                    str.append(indentation).append(lines[i]).append("\n");
                }
            }
            /* Adjusts the indentation for opening tag */
            else if (lines[i].startsWith("<")) {
                for (int j = 0; j < spacing; j++) {
                    indentation.append("    ");
                }
                str.append(indentation).append(lines[i]).append("\n");
                spacing++;
            }
            
            /* Adjusts indentation for data */
            else {
                for (int j = 0; j < spacing; j++) {
                    indentation.append("    ");
                }

                // Check if the line contains more than 80 characters
                if (lines[i].length() > 80) {
                    // Break the line into chunks of 80 characters
                    int chunkSize = 80;
                    for (int k = 0; k < lines[i].length(); k += chunkSize) {
                        int endIdx = Math.min(k + chunkSize, lines[i].length());
                        String chunk = lines[i].substring(k, endIdx);
                        str.append(indentation).append(chunk.trim()).append("\n");
                    }
                } else {
                    // If there is no comma or period, append the line with indentation
                    str.append(indentation).append(lines[i]).append("\n");
                }
            }
        }
        return str.toString();
    }
}

/*
this is my first trial but it doesnot works well with ending tags
*/

/*private static String formatXml(String xmlContent) {
    StringBuilder formattedXml = new StringBuilder();
    int indentLevel = 0;

    // Split the XML content into lines
    String[] lines = xmlContent.split("\n");

    for (String line : lines) {
        line = line.trim();

        if (line.startsWith("</")) {
                indentLevel--;
            
        }

        for (int i = 0; i < indentLevel; i++) {
            formattedXml.append("    ");
        }

        formattedXml.append(line).append("\n");

        if (line.startsWith("<") && !line.startsWith("</")) {
            indentLevel++;
        }
    }

    return formattedXml.toString();
   }
}
*/