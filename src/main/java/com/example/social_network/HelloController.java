package com.example.social_network;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloController {
    @FXML
    private TextArea txtBox;
    @FXML
    private Label label;

    @FXML
    protected void onProcessButtonClick(ActionEvent event) throws IOException {
        //System.out.println(txtBox.getText());
        String userInput = txtBox.getText();
        XmlFile xmlFile;
        if (userInput.trim().startsWith("<")) {
            xmlFile = new XmlFile(userInput);
        }else{
            xmlFile = new XmlFile(new String(Files.readAllBytes(Paths.get(userInput))));
        }
        String errors = xmlFile.validateFile();
        if(errors.isEmpty()) label.setText("No Errors Detected!");
        else label.setText("Errors Detected:\n" + errors);
    }
}