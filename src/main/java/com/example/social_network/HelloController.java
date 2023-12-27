package com.example.social_network;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HelloController {
    private String output_text;
   
    @FXML
    private TextArea txtBox;

    @FXML
    private Label output_label;
    @FXML
    private Label input_label;
    @FXML
    protected void onProcessButtonClick(ActionEvent event) throws IOException {
        //System.out.println(txtBox.getText());
        String userInput = txtBox.getText();
        XmlFile xmlFile;
        if (userInput.trim().startsWith("<")) {
            xmlFile = new XmlFile(userInput);
        }else{
            userInput = new String(Files.readAllBytes(Paths.get(userInput)));
            xmlFile = new XmlFile(userInput);
        }
        input_label.setText(userInput);
        String errors = xmlFile.validateFile();
        if(errors.isEmpty()) output_label.setText("No Errors Detected!");
        else output_label.setText("Errors Detected:\n" + errors);
    }
    @FXML
    protected void OnCompressButtonClick (ActionEvent event) {
    }

          
    @FXML
    protected void OnDecompressButtonClick (ActionEvent event) {
       
    
    }

    @FXML
    protected void OnFormatButtonClick (ActionEvent event) {
       
    }

    @FXML
    protected void OnMinifyButtonClick (ActionEvent event) {
       

    }
    @FXML
    protected void OnJSONButtonClick (ActionEvent event) {
       

    }
    @FXML
    protected void OnGraphButtonClick (ActionEvent event) {
       

    }

    @FXML
    protected void OnErrorsButtonClick (ActionEvent event) throws IOException {
        String userInput = txtBox.getText();
        XmlFile xmlFile;
        if (userInput.trim().startsWith("<")) {
            xmlFile = new XmlFile(userInput);
        }else{
            userInput = new String(Files.readAllBytes(Paths.get(userInput)));
            xmlFile = new XmlFile(userInput);
        }
        input_label.setText(userInput);
        String newFile = xmlFile.correctErrors();
        newFile = Prettifying.prettify(newFile);
        output_label.setText(newFile);
        output_text = newFile;
    }

    @FXML
    protected void OnNewFileButtonClick (ActionEvent event) {
        File file = new File("output.xml");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] bytes = Prettifying.prettify(output_text).getBytes();
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
