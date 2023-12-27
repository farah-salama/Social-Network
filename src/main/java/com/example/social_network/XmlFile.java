package com.example.social_network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlFile {
    private String fileContent;
    private Stack<String> stack;
    private String errors;
    public XmlFile(String fileContent){
        this.fileContent = fileContent;
        this.stack = new Stack<> ();
        this.errors = new String();
    }
    public String validateFile() throws IOException {
        BufferedReader br = new BufferedReader(new StringReader(this.fileContent));
        String line;
        while ((line = br.readLine()) != null) {
            Pattern pattern = Pattern.compile("^\\s*<(.*)>(.*)</(.*)>$");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                if(matcher.group(1).equals(matcher.group(3))) continue;
                else{
                    errors += "Unmatching tags Error in line: " + line + '\n';
                }
            } else {
                Pattern pattern2 = Pattern.compile("^\\s*</(.*)>$");
                Matcher matcher2 = pattern2.matcher(line);
                if (matcher2.find()){
                    if(!stack.empty() && stack.peek().equals(matcher2.group(1))) stack.pop();
                    else{
                        if(stack.contains(matcher2.group(1))) {
                            while(!stack.peek().equals(matcher2.group(1))){
                                errors += "Missing closing tag Error for the opening tag: <" + stack.peek() + ">\n";
                                stack.pop();
                            }
                            stack.pop();
                        }
                        else errors += "Missing opening tag Error for the closing tag: </" + matcher2.group(1) + ">\n";
                    }
                }else{
                    Pattern pattern3 = Pattern.compile("^\\s*<(.*)>$");
                    Matcher matcher3 = pattern3.matcher(line);
                    if (matcher3.find()){
                        stack.push(matcher3.group(1));
                    }else{
                        Pattern pattern4 = Pattern.compile("^\\s*<(.*)>(.*)$");
                        Matcher matcher4 = pattern4.matcher(line);
                        if (matcher4.find()){
                            if(matcher4.group(2) != null) errors += "Missing closing tag Error in the line: " + line + '\n';
                        }else{
                            Pattern pattern5 = Pattern.compile("^\\s*(.*)</(.*)>$");
                            Matcher matcher5 = pattern5.matcher(line);
                            if (matcher5.find()){
                                errors += "Missing opening tag Error in the line: " + line + '\n';
                            }
                        }
                    }
                }
            }
        }
        while(!stack.empty()) {
            errors += "Missing closing tag Error for the opening tag: <" + stack.peek() + ">\n";
            stack.pop();
        }
        br.close();
        return errors;
    }

    public String correctErrors() throws IOException {
        BufferedReader br = new BufferedReader(new StringReader(this.fileContent));
        String line;
        String output = null;
        while ((line = br.readLine()) != null) {
            Pattern pattern = Pattern.compile("^\\s*<(.*)>(.*)</(.*)>$");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                if(matcher.group(1).equals(matcher.group(3))) output += line.trim();
                else{
                    output += "<" + matcher.group(1) + ">" + matcher.group(2) + "</" + matcher.group(1) + ">" + '\n';
                }
            } else {
                Pattern pattern2 = Pattern.compile("^\\s*</(.*)>$");
                Matcher matcher2 = pattern2.matcher(line);
                if (matcher2.find()){
                    if(!stack.empty() && stack.peek().equals(matcher2.group(1))){
                        stack.pop();
                        output += line.trim();
                    }
                    else{
                        if(stack.contains(matcher2.group(1))) {
                            while(!stack.peek().equals(matcher2.group(1))){
                                output += "</" + stack.peek() + ">\n";
                                stack.pop();
                            }
                            output += "</" + stack.peek() + ">\n";
                            stack.pop();
                        }
                        else output += "<" + matcher2.group(1) + "> </" + matcher2.group(1) + ">\n";
                    }
                }else{
                    Pattern pattern3 = Pattern.compile("^\\s*<(.*)>$");
                    Matcher matcher3 = pattern3.matcher(line);
                    if (matcher3.find()){
                        stack.push(matcher3.group(1));
                        output += "<" + matcher3.group(1) + ">\n";
                    }else{
                        Pattern pattern4 = Pattern.compile("^\\s*<(.*)>(.*)$");
                        Matcher matcher4 = pattern4.matcher(line);
                        if (matcher4.find()){
                            //if(matcher4.group(2) != null)
                            output += line + "</" + matcher4.group(1) + ">\n";
                        }else{
                            Pattern pattern5 = Pattern.compile("^\\s*(.*)</(.*)>$");
                            Matcher matcher5 = pattern5.matcher(line);
                            if (matcher5.find()){
                                output += "<" + matcher5.group(2) + ">" + matcher5.group(1) + "</" + matcher5.group(2) + ">\n";
                            }
                        }
                    }
                }
            }
        }
        while(!stack.empty()) {
            output += "</" + stack.peek() + ">\n";
            stack.pop();
        }
        br.close();
        //output.prettify();
        return output;
    }
}
