package com.example.social_network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlFile {
    private String filePath;
    private Stack<String> stack;
    private String errors;
    public XmlFile(String filePath){
        this.filePath = filePath;
        this.stack = new Stack<> ();
        this.errors = new String();
    }
    public String validateFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(this.filePath));
        String line = null;
        while ((line = br.readLine()) != null) {
            Pattern pattern = Pattern.compile("^\\s*<(.*)>(.*)</(.*)>$");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                if(matcher.group(1).equals(matcher.group(3))) continue;
                else{
                    errors = errors + "Unmatching tags Error..\n";
                }
            } else {
                Pattern pattern2 = Pattern.compile("^\\s*</(.*)>$");
                Matcher matcher2 = pattern2.matcher(line);
                if (matcher2.find()){
                    if(!stack.empty() && stack.peek().equals(matcher2.group(1))) stack.pop();
                    else{
                        if(stack.contains(matcher2.group(1))) errors = errors + "Missing closing tag Error..\n";
                        else errors = errors + "Missing opening tag Error..\n";
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
                            if(matcher4.group(2) != null) errors = errors + "Missing closing tag Error..\n";
                        }else{
                            Pattern pattern5 = Pattern.compile("^\\s*(.*)</(.*)>$");
                            Matcher matcher5 = pattern5.matcher(line);
                            if (matcher5.find()){
                                errors = errors + "Missing opening tag Error..\n";
                            }
                        }
                    }
                }
            }
        }
        if(!stack.empty()) errors = errors + "Missing closing tag Error..\n";
        br.close();
        return errors;
    }
}
