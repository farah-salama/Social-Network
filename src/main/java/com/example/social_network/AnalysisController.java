package com.example.social_network;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class AnalysisController {
    @FXML
    private TextArea user1;
    @FXML
    private TextArea user2;
    @FXML
    private TextArea user_suggest;
    @FXML
    private Label output_label;

    @FXML
    protected void OnMostActiveButtonClick (ActionEvent event) {
        output_label.setText(SocialNetworkAnalysis.theMostActiveUser());
    }
    @FXML
    protected void OnMostInfluentialButtonClick (ActionEvent event) {
        String str[] = SocialNetworkAnalysis.theMostInfluencerUsers());
        for (String userName: str){
             if(userName==null)
             {
                 break;
             }
             else
             {
                 output label.setText(userName);
             }
        }
    }
    /*
    protected void OnMostInfluentialButtonClick (ActionEvent event) {
        String str = "";
        for (String user:
                SocialNetworkAnalysis.theMostInfluencerUsers()) {
            str += user + '\n';
        }
        output_label.setText(str);
    }
    */
    @FXML
    protected void OnMutualButtonClick (ActionEvent event) {
        String str = "";
        for (String user:
                SocialNetworkAnalysis.getMutualFollowers(Integer.parseInt(user1.getText()),Integer.parseInt(user2.getText()))) {
            str += user + '\n';
        }
        output_label.setText(str);
    }
    @FXML
    protected void OnSuggestButtonClick (ActionEvent event) {
        String str = "";
        for (String user:
                SocialNetworkAnalysis.suggestedFollowers(Integer.parseInt(user_suggest.getText()))) {
            str += user + '\n';
        }
        output_label.setText(str);
    }
}
