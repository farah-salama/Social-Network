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
    private String compressed_text;

    @FXML
    private TextArea txtBox;
    @FXML
    private TextArea searchBox;

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
    protected void OnCompressButtonClick (ActionEvent event) throws IOException {
        String input = txtBox.getText();
        output_label.setText("Choose file path to save the compressed version,please");
        File outputFile = promptUserForOutputFile(); // Get output file from user
        if ((input.trim().startsWith("<"))|(input.trim().startsWith("{"))) {
            input_label.setText(input);
            HuffmanCompression.compress(input,outputFile);
        }else {
           String text_input = new String(Files.readAllBytes(Paths.get(input)));
            input_label.setText(text_input);
            File inputFile = new File(input);
            HuffmanCompression.compress(inputFile, outputFile);
        }
        InputStream InputStream = System.in;
        try {
            System.setIn(new FileInputStream(outputFile));
        } catch (FileNotFoundException ee) {
            ee.printStackTrace();
        }
        String output= BitInputStream.readString();
        BitInputStream.close();
        System.setIn(InputStream);
        output_label.setText(output);
        compressed_text=output;
    }
    private File promptUserForOutputFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Output File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Compressed XML Files", "*.txt"));
        return fileChooser.showSaveDialog(new Stage());
    }


    @FXML
    protected void OnDecompressButtonClick (ActionEvent event)  {

        //String input = txtBox.getText(); // Get input from the text box
        //  File inputFile = new File(input); // Assume input is a file path
        input_label.setText(compressed_text);
        output_label.setText("Choose the compressed file path and file path to save the decompressed version ,please");
        File inputFile = promptUserForOutputFile();
        File outputFile = promptUserForOutputFile(); // Prompt for output file

        try {
            HuffmanCompression.decompress(inputFile,outputFile); // Perform decompression
            output_label.setText("Decompression successful!");
            // Optionally, display decompressed content on output_label:
            String decompressedText = Files.readString(outputFile.toPath());
            output_label.setText(decompressedText);
        } catch (IOException e) {
            output_label.setText("Decompression failed: " + e.getMessage());
            e.printStackTrace();
        }
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
    protected void OnGraphButtonClick (ActionEvent event) throws Exception {
        String userInput = txtBox.getText();
        if (userInput.trim().startsWith("<")) {
            File infile = new File("input.xml");
            try (FileOutputStream fos = new FileOutputStream(infile)) {
                byte[] bytes = userInput.getBytes();
                fos.write(bytes);
                GraphView graphView = new GraphView();
                Stage graphStage = new Stage();
                graphView.setFilePath("input.xml");
                graphView.start(graphStage);
                graphStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            infile.delete();
        }else{
            GraphView graphView = new GraphView();
            Stage graphStage = new Stage();
            graphView.setFilePath(userInput);
            graphView.start(graphStage);
            graphStage.show();
            userInput = new String(Files.readAllBytes(Paths.get(userInput)));
        }
        output_label.setText("\tDisplaying Graph...");
        input_label.setText(userInput);
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
    protected void OnPostSearchButtonClick (ActionEvent event) {
      try {

            String userInput = txtBox.getText();
            String filePath;
            File file = null;
            if (userInput.trim().startsWith("<")) {
                filePath="post.xml";
                 file = new File(filePath);

                FileOutputStream fos = new FileOutputStream(file) ;
                byte[] bytes = Prettifying.prettify(txtBox.getText()).getBytes();
                fos.write(bytes);
                fos.close();
            }else{
                filePath=userInput;
                userInput = new String(Files.readAllBytes(Paths.get(userInput)));
               
            }
            input_label.setText(userInput);
            String output= PostSearch.convertPostsToString(PostSearch.postSearch(filePath,searchBox.getText()));
            output_label.setText(output);
            if (file!=null){

               file.delete();

            }

        }
        catch (Exception e){
            output_label.setText(e.getMessage());
           
        }
    }

    @FXML
    protected void OnNetworkAnalysisButtonClick (ActionEvent event) throws IOException {
        String userInput = txtBox.getText();
        if (userInput.trim().startsWith("<")) {
            File infile = new File("input.xml");
            try (FileOutputStream fos = new FileOutputStream(infile)) {
                byte[] bytes = userInput.getBytes();
                fos.write(bytes);
                AnalysisApplication analysis = new AnalysisApplication();
                Stage AnalysisStage = new Stage();
                SocialNetworkAnalysis.filePath = "input.xml";
                analysis.start(AnalysisStage);
                AnalysisStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //infile.delete();
        }else{
            AnalysisApplication analysis = new AnalysisApplication();
            Stage AnalysisStage = new Stage();
            SocialNetworkAnalysis.filePath = userInput;
            analysis.start(AnalysisStage);
            AnalysisStage.show();
            userInput = new String(Files.readAllBytes(Paths.get(userInput)));
        }
        output_label.setText("Displaying Network Analysis App...");
        input_label.setText(userInput);
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
