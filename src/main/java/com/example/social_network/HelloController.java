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
    FileChooser fileChooser = new FileChooser();
    File inputpath, outputpath;
    @FXML
    private TextArea txtBox;

    private TextArea input;
    private TextArea output;

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
    @FXML
    protected void OnCompressButtonClick (ActionEvent event) {
        output.clear();
         String xml = input.getText();
        outputpath = fileChooser.showSaveDialog(new Stage());
        if (outputpath != null)
        {
