
package com.example.social_network;

import java.util.*;
import java.io.*;
/**
 *
 * @author Mariz
 */
public class MinifyingXML {
  
   public static void Minifying(String INPUT_FILE_PATH,String OUTPUT_FILE_PATH) throws Exception
    {
        
         File inputFile = new File(INPUT_FILE_PATH);

           // Make sure that the input file is an xml file or JSON file (to be used before compressing)
            if (!inputFile.getPath().endsWith(".xml") && !inputFile.getPath().endsWith(".json")) {
                throw new Exception("Invalid input file");
            }

            // make sure that the file is not empty
            if (inputFile.length()==0) {
                throw new Exception("Invalid input file");
            }

            FileInputStream inputStream = new FileInputStream(inputFile);
            StringBuilder stringBuilder = new StringBuilder();

            int byteRead;
            while ((byteRead = inputStream.read()) != -1) {
                stringBuilder.append((char) byteRead);
            }

            String data = stringBuilder.toString();
            Scanner scanner = new Scanner(data);

            // Reuse StringBuilder for output
            stringBuilder = new StringBuilder(); 
            while (scanner.hasNextLine()) {
                // Remove leading and trailing whitespace
                String line = scanner.nextLine().trim();
                // Replace multiple whitespaces with single space
                line = line.replaceAll("\\s+", " "); 
                // Skip empty lines
                if (!line.isEmpty()) { 
                    // Add the line to string
                    stringBuilder.append(line); 
                }
            }

            FileOutputStream outputStream = new FileOutputStream(OUTPUT_FILE_PATH);
            outputStream.write(stringBuilder.toString().getBytes());

            inputStream.close();
            outputStream.close();
    }
/*
    public static void main(String[] args) throws Exception {
        try {
            
            String INPUT_FILE_PATH = "C:\\Users\\Envy\\Desktop\\Fall23\\Data Structure and Algorithms\\Project\\input_sample.xml";
            String OUTPUT_FILE_PATH = "C:\\Users\\Envy\\Desktop\\Fall23\\Data Structure and Algorithms\\Project\\SampleOut.xml";
            
            Minifying(INPUT_FILE_PATH,OUTPUT_FILE_PATH);
        } 
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
 */
  
}
   
/* Another Trial */
/*
public void minifying(string INPUT_FILE_PATH,string OUTPUT_FILE_PATH)
{
File inputFile = new File(INPUT_FILE_PATH);

            // Make sure that the input file is an aml file
            if (!inputFile.getPath().endsWith(".xml")) {
                throw new Exception("Non Valid input file");
            }

            // make sure that the file is not empty
            if (inputFile.length() == 0) {
                throw new Exception("Empty file Exception");
            }

            FileInputStream inputStream = new FileInputStream(inputFile);
            
            // using stringBuilder for the output
            StringBuilder stringBuilder = new StringBuilder();

            // To store each byte from the input file
            int byteRead;
            
            // to store the original data
            Queue<Character> data = new LinkedList<>();
            // to store the processed data to write it in the output file
            Queue<Character> processedData = new LinkedList<>();

            while ((byteRead = inputStream.read()) != -1) {
                data.add((char) byteRead);
            }

            char current = 0;
            char prev = 0;

            // dequeuing the data queue elements until he data become empty
            while (!data.isEmpty()) {
                // dequeuing and returning the value of the first element in the current variable
                current = data.poll();
                
                if((current=='\n'))
                {
                    while(Character.isWhitespace(current) || (current=='\n'))
                    {
                        current = data.poll();
                        if(current=='>')
                            break;
                    }
                    processedData.add(current);
                    prev=current;                    
                }

                // to leave the attributes'values without modification
                else if ((prev == '>')) { 
                    if (Character.isWhitespace(current)) {
                        while (Character.isWhitespace(current)) {
                            current = data.poll();
                        }
                        prev = current;
                    }
                    while ((current != '<')) {
                  
                        // the end of each attribut's value
                        if (current == '.') {
                            break;
                        } 
                        
                        else if(prev==' ' && current==' ')
                        {
                            while(prev==' ' && current==' ')
                            {
                            current = data.poll(); 
                            }
                            break;
                        }
                        
                        else 
                        {
                            // storing the value of the attribute
                            if (current != '\n')
                            {
                                processedData.add(current);
                                current = data.poll();
                                break;
                            }
                        }
                    }
                    
                    if(current!='\n'){
                         processedData.add(current); // adding '.'
                         prev=current;
                    }         
                    else{
                         prev=current;
                        current = data.poll();
                    }
                } 
                
                else 
                {
                    processedData.add(current);
                    prev = current;
                }
            }

            //enqueing the processed data queue and storing it in the stringBuilder  
            while (!processedData.isEmpty()) {
                stringBuilder.append(processedData.poll());
            }

            FileOutputStream outputStream = new FileOutputStream(OUTPUT_FILE_PATH);
            outputStream.write(stringBuilder.toString().getBytes());

            inputStream.close();
            outputStream.close();
}
*/

