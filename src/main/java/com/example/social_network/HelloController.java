package com.example.social_network;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class HelloController {
    @FXML
    private TextArea txtBox;
    @FXML
    private Label label;

    @FXML
    protected void onProcessButtonClick(ActionEvent event) throws IOException {
        System.out.println(txtBox.getText());
        XmlFile xmlFile = new XmlFile(txtBox.getText());
        String errors = xmlFile.validateFile();
        if(errors == null) label.setText("No Errors Detected!");
        else label.setText("Errors Detected:\n" + errors);
        //xmlFileProcess(txtBox.getText());
    }
}