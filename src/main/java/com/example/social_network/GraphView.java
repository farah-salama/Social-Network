package com.example.social_network;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GraphView extends Application {
    private String filePath;
    public void setFilePath(String filePath){
        this.filePath = filePath;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("graph.fxml"));
        Parent root = loader.load();

        // Set the social network data in the controller
        GraphViewController controller = loader.getController();

        controller.setSocialNetwork(xmlParser.createNetworkFromXML(filePath));
        controller.initialize();



        primaryStage.setTitle("Social Network Graph");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args, String filePath) {
        launch();
    }
}